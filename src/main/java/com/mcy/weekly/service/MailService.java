package com.mcy.weekly.service;

import com.mcy.weekly.pojo.Meeting;

import java.util.List;

public interface MailService {

    //发送邮件

    /**
     *发送邮件
     * @param subject 主题
     * @param content 内容
     * @param to 收件人
     */
    void sendMail(String subject, String content , String... to);

    /**
     * 定时发送邮件
     */
    void timingTask();

    boolean sendAll(Meeting meeting);

    Boolean sendMeeting(Meeting meeting, String... strings);
}
