package com.mcy.weekly.validator;

import com.mcy.weekly.pojo.Task;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TaskValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Task.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Task task = (Task) o;


        if (task.getTaskName() != null && !"".equals(task.getTaskName()) && task.getTaskName().length() > 40){
            errors.rejectValue("taskName", null, "标题name过长");
        }
        if (task.getContent() != null && !"".equals(task.getContent()) && task.getContent().length() > 1024){
            errors.rejectValue("content", null, "content长度过长");
        }
        if (task.getCompleteDegree() > 100 || task.getCompleteDegree() < 0){
            errors.rejectValue("completeDegree", null, "completeDegree数值不符合规范");
        }
        if (task.getTimeConsuming() != null && !"".equals(task.getTimeConsuming()) && task.getTimeConsuming().length() > 40){
            errors.rejectValue("timeConsuming", null, "timeConsuming长度过长");
        }
    }
}
