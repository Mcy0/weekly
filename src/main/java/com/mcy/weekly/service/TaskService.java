package com.mcy.weekly.service;

import com.mcy.weekly.pojo.PageParams;
import com.mcy.weekly.pojo.Task;

import java.util.List;

public interface TaskService {
    //添加周报
    int addTask(Task task);
    //更新周报
    int updateTask(Task task);
    //根据周报id删除周报
    int deleteByIdByUserId(int id, int userId);

    //获取全部周报
    List<Task> getAllTasks(PageParams pageParams);
    //根据userId获取全部周报
    List<Task> getAllTasksByUserId(int userId, PageParams pageParams);
    //根据userId获取本周周报
    List<Task> getThisTasks(int userId);
    //根据userId获取上周周周报
    List<Task> getLastTasks(int userId);
    //根据userId获取下周周报
    List<Task> getNextTasks(int userId);

    Task getTaskByIdByUserId(int id, int userId);
}
