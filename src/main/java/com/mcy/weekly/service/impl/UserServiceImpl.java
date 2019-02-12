package com.mcy.weekly.service.impl;

import com.mcy.weekly.mapper.UserMapper;
import com.mcy.weekly.pojo.StateEnum;
import com.mcy.weekly.pojo.StatusEnum;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.pojo.UserParams;
import com.mcy.weekly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public User getUserByEmailByPassword(String email, String password) {
        return userMapper.getUserByEmailByPassword(email, password);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<User> getUserByUserParams(UserParams userParams) {
        return userMapper.getUserByUserParams(userParams);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertUserSim(String email, String password, StatusEnum status, StateEnum state) {
        return userMapper.insertUserSim(email, password, status, state);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public int updateUserSim(String email, StatusEnum status, StateEnum state,String password) {
        return userMapper.updateUserSim(email, status, state, password);
    }

    @Override
    public int updateUserPassword(int id, String newPassword) {
        return userMapper.updateUserPassword(id, newPassword);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }


}
