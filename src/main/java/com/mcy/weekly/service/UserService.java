package com.mcy.weekly.service;

import com.mcy.weekly.pojo.StateEnum;
import com.mcy.weekly.pojo.StatusEnum;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.pojo.UserParams;

import java.util.List;

public interface UserService {
    User getUserByEmailByPassword(String email, String password);

    int updateUser(User user);

    User getUserById(int id);

    List<User> getUserByUserParams(UserParams userParams);

    int insertUserSim(String email, String password, StatusEnum status, StateEnum state);

    User getUserByEmail(String email);

    int updateUserSim(String email, StatusEnum status, StateEnum state, String password);

    int updateUserPassword(int id, String newPassword);

    int deleteUserById(Integer id);

    List<User> getAll();
}
