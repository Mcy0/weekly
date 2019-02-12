package com.mcy.weekly.pojo;

import com.mcy.weekly.plugin.PagePlugin;

public class UserParams {
    private String userName;//用户名模糊匹配
    private LearningDirectionEnum learningDirection;//学习方向查找
    private PageParams pageParams;//分页液查找

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LearningDirectionEnum getLearningDirection() {
        return learningDirection;
    }

    public void setLearningDirection(LearningDirectionEnum learningDirection) {
        this.learningDirection = learningDirection;
    }

    public PageParams getPageParams() {
        return pageParams;
    }

    public void setPageParams(PageParams pageParams) {
        this.pageParams = pageParams;
    }
}
