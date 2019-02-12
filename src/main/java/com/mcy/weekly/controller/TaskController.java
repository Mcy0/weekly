package com.mcy.weekly.controller;

import com.mcy.weekly.pojo.PageParams;
import com.mcy.weekly.pojo.Task;
import com.mcy.weekly.pojo.TaskParams;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.service.TaskService;
import com.mcy.weekly.utils.CommonHandling;
import com.mcy.weekly.validator.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService = null;

    //自定义验证器
    @InitBinder("task")
    public void initBinder(DataBinder binder){
        binder.addValidators(new TaskValidator());
    }

    //插入周报返回周报id周报唯一索引
    @RequestMapping("/addTask")
    @ResponseBody
    public Map<String, Object> addTask(@Valid Task task, Errors errors, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        //判null
        if (task.getTaskName() == null || task.getTaskDate() == null){
            map.put("success", false);
            map.put("msg", "name nad date 参数不能为空");
            map.put("code", "1003");
            return map;
        }
        //判断格式
        if (!CommonHandling.format(map, errors)){
            return map;
        }
        User user = (User) session.getAttribute("user");
        task.setUserId(user.getId());
        taskService.addTask(task);
        map.put("id", task.getId());
        map.put("success", true);
        map.put("code", "2000");
        map.put("msg", "添加成功");
        return map;
    }
    @RequestMapping("/deleteTask")
    @ResponseBody
    public Map<String, Object> deleteTask(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
                                          HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (id == 0){
            map.put("success", false);
            map.put("msg", "id参数为空");
            map.put("code", "1003");
            return map;
        }
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        Task task = taskService.getTaskByIdByUserId(id, userId);
        if (task == null){
            map.put("success", false);
            map.put("msg", "该周报不存在");
            map.put("code", "3002");
            return map;
        }
        taskService.deleteByIdByUserId(id, userId);
        map.put("success", true);
        map.put("msg", "删除成功");
        map.put("code", "2000");
        return map;
    }
    @RequestMapping("/updateTask")
    @ResponseBody
    public Map<String, Object> updateTask(@Valid Task task, Errors errors, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (task.getId() == 0){
            map.put("success", false);
            map.put("msg", "id不能为null");
            map.put("code", "1003");
            return map;
        }
        if (!CommonHandling.format(map, errors)){
            return map;
        }
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        task.setUserId(userId);
        int count = taskService.updateTask(task);
        map.put("success", true);
        map.put("code", "2000");
        map.put("msg", "更新成功");
        return map;
    }
    //日期返回【时间戳】注意转换
    //获取全部周报
    @RequestMapping("/getAllTasks")
    @ResponseBody
    public Map<String, Object> getAllTasks(PageParams pageParams) {
        Map<String, Object> map = new HashMap<>();
        List<Task> taskList = taskService.getAllTasks(pageParams);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("tasks", taskList);
        map.put("code", "2000");
        map.put("total", pageParams.getTotal());
        map.put("totalPage", pageParams.getTotalPage());
        return map;
    }
    @RequestMapping("/getAllTasksByUserId")
    @ResponseBody
    public Map<String, Object> getAllTasksByUserId(@RequestBody TaskParams taskParams) {
        Map<String, Object> map = new HashMap<>();
        if (taskParams.getUserId() == 0){
            map.put("success", false);
            map.put("code", "1003");
            map.put("msg", "参数为空");
            return map;
        }
        List<Task> taskList = taskService.getAllTasksByUserId(taskParams.getUserId(), taskParams.getPageParams());
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("code", "2000");
        map.put("tasks", taskList);
        map.put("total", taskParams.getPageParams().getTotal());
        map.put("totalPage", taskParams.getPageParams().getTotalPage());
        return map;
    }
    //过去三周全部周报
    @RequestMapping("/getTasks")
    @ResponseBody
    public Map<String, Object> getTasks(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        if (userId == null){
            map.put("success", false);
            map.put("msg", "参数为空");
            map.put("code", "1003");
            return map;
        }
        List<Task> lastTask = taskService.getLastTasks(userId);
        List<Task> thisTask = taskService.getThisTasks(userId);
        List<Task> nextTask = taskService.getNextTasks(userId);
        map.put("success", true);
        map.put("msg", "获取成功");
        map.put("code", "2000");
        map.put("lastTask", lastTask);
        map.put("thisTask", thisTask);
        map.put("nextTask", nextTask);
        return map;
    }
}
