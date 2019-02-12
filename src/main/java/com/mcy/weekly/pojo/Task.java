package com.mcy.weekly.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Task {
    private int id;//id not null
    private int userId;//userId not null
    //使用spring格式器
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date taskDate;//周报时间 not null
    private String taskName;//名称 not null
    private String content;//内容 null or not null
    private int completeDegree;//完成度0-100 null or not null
    private String timeConsuming;//完成时间【自己】描述 null or not null

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCompleteDegree() {
        return completeDegree;
    }

    public void setCompleteDegree(int completeDegree) {
        this.completeDegree = completeDegree;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
    }
}
