package com.mcy.weekly.service.impl;

import com.mcy.weekly.mapper.TaskMapper;
import com.mcy.weekly.mapper.UserMapper;
import com.mcy.weekly.pojo.Meeting;
import com.mcy.weekly.pojo.StateEnum;
import com.mcy.weekly.pojo.Task;
import com.mcy.weekly.pojo.User;
import com.mcy.weekly.service.MailService;
import com.mcy.weekly.service.TaskService;
import com.mcy.weekly.service.UserService;
import com.mcy.weekly.utils.My;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender = null;

    @Autowired
    private SimpleMailMessage simpleMailMessage = null;

    @Override
    public void sendMail(String subject, String content, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(simpleMailMessage.getFrom());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    @Autowired
    private UserMapper userMapper = null;

    @Autowired
    private TaskService taskService = null;

    //定时任务每周6查看是否写周报没写自动提醒
    @Override
    @Scheduled(cron = "0 0 18 ? * SAT")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void timingTask() {

        if (!My.isRemind()){
            return;
        }
        if (true)
        return;
        List<User> userList = userMapper.getAll();
        StringBuffer buffer = new StringBuffer();
        for (User user :
                userList) {
            List<Task> taskList = taskService.getThisTasks(user.getId());
            if (taskList.isEmpty()){
                buffer.append(user.getEmail() + ",");
            }
        }
        if (!(buffer.length() > 0)){
            return;
        }
        String str = buffer.substring(0, buffer.length() - 1);
        String[] strings = str.split(",");
        sendMail("【周报-提醒】", "没写周报", strings);
    }
    //发送邮件给所有在校的人
    @Override
    public boolean sendAll(Meeting meeting){
        List<User> userList = userMapper.getAll();
        StringBuffer buffer = new StringBuffer();
        for (User user :
                userList) {
            if (user.getState() != null && user.getState().getId() == StateEnum.IN_SCHOOL.getId()){
                buffer.append(user.getEmail() + ",");
            }
        }
        if (buffer.length() > 0) {
            String str = buffer.substring(0, buffer.lastIndexOf(","));
            String[] strings = str.split(",");
            sendMeeting(meeting, strings);
        }
        return true;
    }

    @Override
    public Boolean sendMeeting(Meeting meeting, String... strings){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String content = "时间:" + simpleDateFormat.format(meeting.getMeetingDate()) + "\n" +
                "<img src=\"" + meeting.getPicture() + "\" />\n" +
                meeting.getContent() + "\n" +
                "文件链接：" + meeting.getDocumentLink();

        sendMail("【周报-会议记录】",content
                ,
                strings);
        return true;
    }
}
