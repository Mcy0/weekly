package com.mcy.weekly.typeHandler;

import com.mcy.weekly.pojo.LearningDirectionEnum;
import org.apache.ibatis.type.*;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(LearningDirectionEnum.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class LearningDirectionEnumTypeHandler extends BaseTypeHandler<LearningDirectionEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LearningDirectionEnum learningDirectionEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, learningDirectionEnum.getId());
    }

    @Override
    public LearningDirectionEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getInt(s);
        return LearningDirectionEnum.getLearningDirectionById(id);
    }

    @Override
    public LearningDirectionEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(i);
        return LearningDirectionEnum.getLearningDirectionById(id);
    }

    @Override
    public LearningDirectionEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int id = callableStatement.getInt(i);
        return LearningDirectionEnum.getLearningDirectionById(id);
    }
}
