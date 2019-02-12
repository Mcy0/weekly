package com.mcy.weekly.mapper;

import com.mcy.weekly.pojo.StateEnum;
import com.mcy.weekly.pojo.StatusEnum;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.pojo.UserParams;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    User getUserByEmailByPassword(@Param("email") String email, @Param("password") String password);//唯一搜索 密码手动加密

    User getUserById(int id);

    List<User> findUsers(String userName);//模糊匹配

    int insertUser(User user);

    int updateUser(User user);

    int delete(String email);

    List<User> getUserByUserParams(UserParams userParams);

    int insertUserSim(@Param("email") String email, @Param("password") String password, @Param("status") StatusEnum status, @Param("state")StateEnum state);

    User getUserByEmail(String email);

    int updateUserSim(@Param("email") String email,@Param("status")  StatusEnum status, @Param("state") StateEnum state, @Param("password") String password);

    int updateUserPassword(@Param("id")int id, @Param("newPassword")String newPassword);

    List<User> getAll();

    int deleteUserById(int id);
}
