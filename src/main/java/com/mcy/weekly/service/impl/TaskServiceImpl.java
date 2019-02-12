package com.mcy.weekly.service.impl;

import com.mcy.weekly.mapper.TaskMapper;
import com.mcy.weekly.pojo.PageParams;
import com.mcy.weekly.pojo.Task;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper = null;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addTask(Task task) {
        return taskMapper.insertTask(task);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int updateTask(Task task){
        return taskMapper.update(task);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int deleteByIdByUserId(int id, int userId) {
        return taskMapper.deleteByIdByUserId(id, userId);
    }

    @Override
    public List<Task> getAllTasks(PageParams pageParams) {
        return taskMapper.getAllTasks(pageParams);
    }

    @Override
    public List<Task> getAllTasksByUserId(int userId, PageParams pageParams) {
        return taskMapper.getAllTasksByUserId(userId, pageParams);
    }

    //根据日期获取本周周一的日期
    private Calendar getFirstDateOfWeek(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//配合calendar.getFirstDayOfWeek()(根据MONDAY返回固定值2意思是(外国习惯)的本周的第二天)
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);//本周第几天(周日为第一天)
        if (dayWeek == 1){//根据中国习惯如果为周日则较少一天
            calendar.add(Calendar.DATE, -1);
            dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        }
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - dayWeek);//减少天数
//        System.out.println("周一日期" + simpleDateFormat.format(calendar.getTime()));
        return calendar;
    }
    //获取本周周日日期
    private Calendar getLastDateOfWeek(){
        Calendar calendar = getFirstDateOfWeek();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }
    //改变日期
    private Calendar addCalendar(Calendar calendar, int co){
        calendar.add(Calendar.DATE, co);
        return calendar;
    }
    @Override
    public List<Task> getThisTasks(int userId) {
        Date startDate = getFirstDateOfWeek().getTime();
        Date endDate = getLastDateOfWeek().getTime();
        return taskMapper.getTasks(startDate, endDate, userId);
    }

    @Override
    public List<Task> getLastTasks(int userId) {

        Date startDate = addCalendar(getFirstDateOfWeek(), -7).getTime();
        Date endDate = addCalendar(getFirstDateOfWeek(), -1).getTime();
        return taskMapper.getTasks(startDate, endDate, userId);
    }

    @Override
    public List<Task> getNextTasks(int userId) {
        Date startDate = addCalendar(getLastDateOfWeek(), 1).getTime();
        Date endDate = addCalendar(getLastDateOfWeek(), 7).getTime();
        return taskMapper.getTasks(startDate, endDate, userId);
    }

    @Override
    public Task getTaskByIdByUserId(int id, int userId) {
        return taskMapper.getTaskByIdByUserId(id, userId);
    }
}
