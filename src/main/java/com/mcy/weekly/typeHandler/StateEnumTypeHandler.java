package com.mcy.weekly.typeHandler;

import com.mcy.weekly.pojo.StateEnum;
import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(StateEnum.class)
public class StateEnumTypeHandler extends BaseTypeHandler<StateEnum> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, StateEnum stateEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, stateEnum.getId());
    }

    @Override
    public StateEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getInt(s);
        return StateEnum.getStateById(id);
    }

    @Override
    public StateEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(i);
        return StateEnum.getStateById(id);
    }

    @Override
    public StateEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int id = callableStatement.getInt(i);
        return StateEnum.getStateById(id);
    }
}