package com.mcy.weekly.plugin;

import com.mcy.weekly.pojo.PageParams;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
//分页插件
@Intercepts(
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
)
public class PagePlugin implements Interceptor {
    /**
     *插件默认参数，可配置默认值
     */
    private Integer defaultPage;//默认页码
    private Integer defaultPageSize;//默认每页总条数
    private Boolean defaultUseFlag;//默认是否启用插件
    private Boolean defaultCheckFlag;//默认是否检测页码参数
    private Boolean defaultCleanOrderBy;//默认是否清楚最后一个order by语句
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)getUnProxyObject(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.
                getValue("delegate.mappedStatement");
        //不是select语句
        if (!checkSelect(sql)){
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql)metaStatementHandler.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();
        PageParams pageParams = getPageParamsForParamObj(parameterObject);
        if (pageParams == null){//无法获取页面参数，不进行分页
            return invocation.proceed();
        }
        //获取配置中是否启用分页功能
        Boolean useFlage = pageParams.getUseFlag() == null ? this.defaultUseFlag : pageParams.getUseFlag();
        if (!useFlage){//不适用分页插件
            return invocation.proceed();
        }
        Integer pageNum = pageParams.getPage() == null ? defaultPage : pageParams.getPage();
        Integer pageSize = pageParams.getPageSize() == null ? defaultPageSize : pageParams.getPageSize();
        Boolean checkFlag = pageParams.getCheckFlag() == null ? defaultCheckFlag : pageParams.getCheckFlag();
        Boolean cleanOrderBy = pageParams.getCleanOrderBy() == null ? defaultCleanOrderBy : pageParams.getCleanOrderBy();
        int total = getTotal(invocation, metaStatementHandler, boundSql, cleanOrderBy);
        //回填总条数到分页参数
        pageParams.setTotal(total);
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        pageParams.setTotalPage(totalPage);
        //检查当前页码有效性
        checkPage(checkFlag, pageNum, totalPage);
        //修改sql
        return preparedSQL(invocation, metaStatementHandler, boundSql, pageNum, pageSize);
    }

    /**
     * 预编译改写后的SQL，并设置分页参数
     *
     * @param invocation 入参
     * @param metaStatementHandler MetaObject绑定的StatementHandler
     * @param boundSql boundSql对象
     * @param pageNum 当前页
     * @param pageSize 最大页
     * @return
     */
    private Object preparedSQL(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql, Integer pageNum, Integer pageSize) throws InvocationTargetException, IllegalAccessException, SQLException {
        String sql = boundSql.getSql();
        String newSql = "select * from (" + sql + ") $_paging_table limit ?, ?";
        //修改当前要执行的sql
        metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
        //执行预编译，就差两个参数pageNum, pageSize
        Object statementObj = invocation.proceed();
        this.preparePageDataParams((PreparedStatement)statementObj, pageNum, pageSize);
        return statementObj;
    }

    /**
     * 使用PreparedStatement预编译两个分页参数，如果数据库规则不一样则改写参数规则
     *
     */
    private void preparePageDataParams(PreparedStatement statementObj, Integer pageNum, Integer pageSize) throws SQLException {
        int idx = statementObj.getParameterMetaData().getParameterCount();
        statementObj.setInt(idx - 1, (pageNum - 1) * pageSize);
        statementObj.setInt(idx, pageSize);
    }

    /**
     * 检查当前页码的有效性
     *
     * @param checkFlag 检测标志
     * @param pageNum 当前页码
     * @param totalPage 最打野吗
     * @throws Exception 异常
     */
    private void checkPage(Boolean checkFlag, Integer pageNum, int totalPage) throws Exception {
        if (checkFlag){
            if (pageNum > totalPage){
                throw new Exception("查询失败， 查询页码【" + pageNum + "】大于总页数【" + totalPage + "】");
            }
        }
    }

    /**
     * 获取总数
     * @param invocation 入参
     * @param metaStatementHandler statementHandler
     * @param boundSql sql
     * @param cleanOrderBy 是否清除order by语句
     * @return sql 查询总数
     * @throws Throwable 异常
     */
    private int getTotal(Invocation invocation, MetaObject metaStatementHandler,
                         BoundSql boundSql, Boolean cleanOrderBy) throws SQLException {
        //获取当前的mappedStatement
        MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.
                getValue("delegate.mappedStatement");
        //配置对象
        Configuration cfg = mappedStatement.getConfiguration();
        //当前要执行的sql
        String sql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
        if (cleanOrderBy){
            sql = this.cleanOrderByForSql(sql);
        }
        //改写为统计总数的SQL
        String countSql = "select count(*) as total from (" + sql +") $_paging";
        Connection connection = (Connection)invocation.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try{
            ps = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(cfg, countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            ParameterHandler handler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(),
                    countBoundSql);
            handler.setParameters(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(ps != null){
                ps.close();
            }
        }
        return total;
    }

    private String cleanOrderByForSql(String sql) {
        StringBuilder sb = new StringBuilder(sql);
        String newSql = sql.toLowerCase();
        if(newSql.indexOf("order") == -1){
            return sql;
        }
        int idx = newSql.lastIndexOf("order");
        return sb.substring(0, idx).toString();
    }

    /**
     * 分离出页面参数
     * @param parameterObject 执行参数
     * @return 分页参数
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private PageParams getPageParamsForParamObj(Object parameterObject) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PageParams pageParams = null;
        if (parameterObject == null){
            return null;
        }
        //处理map参数，多个匿名参数和@param注解都是map
        if (parameterObject instanceof Map){
            Map<String, Object> paramMap = (Map<String, Object>)parameterObject;
            Set<String> keySet = paramMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                Object value = paramMap.get(key);
                if (value instanceof PageParams){
                    return (PageParams) value;
                }
            }
        } else if (parameterObject instanceof PageParams){
            return (PageParams)parameterObject;
        } else{
            Field[] fields = parameterObject.getClass().getDeclaredFields();
            for (Field field : fields){
                if (field.getType() == PageParams.class){
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), parameterObject.getClass());
                    Method method = pd.getReadMethod();
                    return (PageParams)method.invoke(parameterObject);
                }
            }
        }
        return null;
    }

    /**
     * 判断sql是否select语句
     * @param sql --当前执行的sql
     * @return 是否是select语句
     */
    private boolean checkSelect(String sql) {
        String trimSql = sql.trim();
        int idx = trimSql.toLowerCase().indexOf("select");
        return idx == 0;
    }

    /**
     * 从代理对象中分离出真实对象
     * @param target 入参
     * @return 非代理StatementHandler对象
     */
    private Object getUnProxyObject(Object target) {
        MetaObject metaStatementHandler = SystemMetaObject.forObject(target);
        //分离代理对象链
        Object object = null;
        while (metaStatementHandler.hasGetter("h")){
            object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        if (object == null){
            return target;
        }
        return object;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String strDefaultPage = properties.getProperty("default.page", "1");
        String strDefaultPageSize = properties.getProperty("default.pageSize", "10");
        String strDefaultUseFlage = properties.getProperty("default.useFlag", "false");
        String strDefaultCheckFlag = properties.getProperty("default.checkFlag", "false");
        String strDefaultCleanOrderBy = properties.getProperty("default.cleanOrderBy", "false");
        this.defaultPage = Integer.parseInt(strDefaultPage);
        this.defaultPageSize = Integer.parseInt(strDefaultPageSize);
        this.defaultUseFlag = Boolean.parseBoolean(strDefaultUseFlage);
        this.defaultCheckFlag = Boolean.parseBoolean(strDefaultCheckFlag);
        this.defaultCleanOrderBy = Boolean.parseBoolean(strDefaultCleanOrderBy);
    }
}
