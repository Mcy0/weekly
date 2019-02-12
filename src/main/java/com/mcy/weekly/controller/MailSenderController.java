package com.mcy.weekly.controller;

import com.mcy.weekly.pojo.User;
import com.mcy.weekly.service.MailService;
import com.mcy.weekly.service.UserService;
import com.mcy.weekly.service.impl.MailServiceImpl;
import com.mcy.weekly.utils.My;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/mail")
public class MailSenderController {

    @Autowired
    private UserService userService = null;

    @Autowired
    private MailService mailService = null;

    //测试
    @RequestMapping("/send")
    @ResponseBody
    public Map<String, Object> send(String subject, String content, String to){
        Map<String, Object> map = new HashMap<>();
        mailService.sendMail(subject, content, to);
        map.put("success", true);
        map.put("msg", "发送成功");
        return map;
    }
    //忘记密码 发送验证码 模板
    @RequestMapping("/sendAuthCode")
    @ResponseBody
    public Map<String, Object> sendAuthCode(String email,HttpSession session){
        Map<String, Object> map = new HashMap<>();

        if (email == null || email.equals("")){
            map.put("success", false);
            map.put("msg", "email参数null");
        }
        if (!User.isEmail(email)){
            map.put("success", false);
            map.put("msg", "email者格式不对");
        }
        User user = userService.getUserByEmail(email);
        if (user == null){
            map.put("success", false);
            map.put("msg", "用户不存在 ");
            return map;
        }
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        //生成4为纯数字验证码0-9
        for (int i = 0; i < 4; i++) {
            buffer.append(random.nextInt(10));
        }
        String code = buffer.toString();
        mailService.sendMail("【周报-验证码】", "验证码为:" + code + "\n 三次验证机会", email);
        session.setAttribute("code", code);
        session.setAttribute("codeCount", 3);//验证码有三次验证机会
        map.put("success", true);
        map.put("msg", "发送成功");
        return map;
    }


    //是否启动自动提醒功能
    @RequestMapping("/remind")
    @ResponseBody
    public Map<String, Object> sendAuthCode(Boolean b){
        Map<String, Object> map = new HashMap<>();
        if (b == null){
            map.put("success", false);
            map.put("msg", "参数为null");
            return map;
        }
        My.setRemind(b);
        map.put("success", true);
        map.put("msg", "修改成功" + My.isRemind());
        return map;
    }
    //发送会议记录

}
