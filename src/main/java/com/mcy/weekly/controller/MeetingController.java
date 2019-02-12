package com.mcy.weekly.controller;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.mcy.weekly.pojo.*;
import com.mcy.weekly.service.FileService;
import com.mcy.weekly.service.MailService;
import com.mcy.weekly.service.MeetingService;
import com.mcy.weekly.service.UserService;
import com.mcy.weekly.utils.CommonHandling;
import com.mcy.weekly.utils.My;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService = null;

    @Autowired
    private FileService fileService = null;

    @Autowired
    private MailService mailService = null;

    @Autowired
    private UserService userService = null;

    //以后可以整一个专属的文件服务器或者用第三方的
    @RequestMapping("/addMeeting")
    @ResponseBody
    public Map<String, Object> addMeeting(Meeting meeting, HttpServletRequest request,
                                          HttpSession session){
        Map<String, Object> map = new HashMap<>();
        if (meeting.getContent() == null)
        {
            map.put("success", false);
            map.put("msg", "content is not null");
            map.put("code", "1003");
            return map;
        }
        if (meeting.getContent().length() > 800){
            map.put("success", false);
            map.put("msg", "content内容过长");
            map.put("code", "1004");
            return map;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DATE), 0, 0, 0);
        Date date = calendar.getTime();
        Meeting localMeeting = meetingService.getMeetingByDate(date);
        if (localMeeting != null){
            map.put("success", false);
            map.put("msg", "本天已经写过会议记录");
            map.put("code", "3001");
            return map;
        }
        String picture = null;
        //上传文件
        picture = fileService.addPictureFile(request, date, map);
        if (picture == null || "".equals(picture)){
            return map;
        }
        else if (!picture.equals("false")){
            meeting.setPicture(picture);
        }
        User user = (User) session.getAttribute("user");
        meeting.setUserId(user.getId());
        meeting.setMeetingDate(date);
        meetingService.insertMeeting(meeting);
        map.put("success", true);
        map.put("msg", "上传文件成功");
        map.put("code", "2000");
        return map;
    }

    /**
     * 根据路径显示图片
     * @param picture 图片路径
     * @param response 打印图片
     */
    @RequestMapping("/picture")
    @ResponseBody
    public void picture(String picture, HttpServletResponse response) {
        if (picture == null) {
            return;
        }
        if (picture.indexOf(".jpg") < 0 && picture.indexOf(".jpeg") < 0 && picture.indexOf(".png") < 0 && picture.indexOf(".gif") < 0){

            return;
        }
        File file = new File(picture);
        FileInputStream fis = null;
        try {
            response.setContentType("image/gif");
            OutputStream out = response.getOutputStream();
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            while(fis.read(b) > 0)
            {
                out.write(b);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/deleteMeeting")
    @ResponseBody
    public Map<String, Object> deleteMeeting(Integer id){
       Map<String, Object> map = new HashMap<>();
       if (id == null){
           map.put("success", false);
           map.put("msg", "id不能为空");
           map.put("code", "1003");
           return map;
       }
       Meeting meeting = meetingService.getMeetingById(id);
       if (meeting == null){
           map.put("success", false);
           map.put("msg", "会议不存在");
           map.put("code", "3002");
           return map;
       }
       String filePath = meeting.getPicture();
       if (filePath != null && !"".equals(filePath)){

           if (filePath.indexOf(My.getLocal()) >= 0){
               int index = filePath.indexOf("picture=") + 8;
               filePath = filePath.substring(index);
               fileService.deleteFile(filePath);
           }
       }
       meetingService.deleteMeeting(id);
       map.put("success", true);
       map.put("msg", "删除成功");
        map.put("code", "2000");
       return map;
    }
    @RequestMapping("/updateMeeting")
    @ResponseBody
    public Map<String, Object> updateMeeting(Meeting meeting, HttpServletRequest request,
                                             HttpSession session){
        Map<String, Object> map = new HashMap<>();
        if (meeting.getId() == 0){
            map.put("success", false);
            map.put("msg", "id is not null");
            map.put("code", "1003");
            return map;
        }
        if (meeting.getContent() == null)
        {
            map.put("success", false);
            map.put("msg", "content is not null ");
            map.put("code", "1003");
            return map;
        }
        if (meeting.getContent().length() > 800){
            map.put("success", false);
            map.put("msg", "content内容过长 ");
            map.put("code", "1004");
            return map;
        }
        Meeting localMeeting = meetingService.getMeetingById(meeting.getId());
        if (localMeeting == null){
            map.put("success", false);
            map.put("msg", "id对应的会议不存在");
            map.put("code", "3002");
            return map;
        }
        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
        Date date = localMeeting.getMeetingDate();
        System.out.println(date);
        //更新文件
        String picture = fileService.addPictureFile(request, date, map);
        if (picture == null || "".equals(picture)){
            return map;
        }
        else if(!picture.equals("false")){
            meeting.setPicture(picture);
        }
        User user = (User) session.getAttribute("user");
        meeting.setUserId(user.getId());
        meeting.setMeetingDate(date);
        meetingService.updateMeeting(meeting);
        map.put("success", true);
        map.put("msg", "更新文件成功");
        map.put("code", "2000");
        return map;

    }
    @RequestMapping("/getMeetings")
    @ResponseBody
    public Map<String, Object> getMeetings(PageParams pageParams){
        Map<String, Object> map = new HashMap<>();

        List<Meeting> meetingList = meetingService.getMeetings(pageParams);

        map.put("success", true);
        map.put("msg", "查询成功");
        map.put("meetings", meetingList);
        map.put("total", pageParams.getTotal());
        map.put("totalPage", pageParams.getTotalPage());
        map.put("code", "2000");
        return map;
    }

    //发送会议
    @RequestMapping("/sendMeetings")
    @ResponseBody
    public Map<String, Object> sendMeetings(Integer id) {
        Map<String, Object> map = new HashMap<>();
        if (!CommonHandling.isNull(map, id)){
            return map;
        }
        Meeting meeting = meetingService.getMeetingById(id);
        if (meeting == null){
            map.put("msg", "会议不存在");
            map.put("success", false);
            map.put("code", "3002");
            return map;
        }
        Boolean b = mailService.sendAll(meeting);
        if (!b){
            map.put("msg", "发送失败");
            map.put("success", false);
            map.put("code", "4000");
            return map;
        }
        map.put("msg", "发送成功");
        map.put("success", true);
        map.put("code", "2000");
        return map;
    }
    @RequestMapping("/sendMeetingsByUser")
    @ResponseBody
    public Map<String, Object> sendMeetings(@RequestBody StringAndIntParams params) {
        Map<String, Object> map = new HashMap<>();
        String[] emailList = params.getEmailList();
        Integer id = params.getId();
        if (emailList.length <= 0 || id == null){
            map.put("msg", "参数为空");
            map.put("success", false);
            map.put("code", "1003");
            return map;
        }
        for (String email : emailList) {
            if (!User.isEmail(email)){
                map.put("msg", email + "格式错误");
                map.put("success", false);
                map.put("code", "1004");
                return map;
            }
            else {

            }
        }
        Meeting meeting = meetingService.getMeetingById(id);
        if (meeting == null){
            map.put("msg", "会议不存在 ");
            map.put("success", false);
            map.put("code", "3002");
            return map;
        }
        Boolean b = mailService.sendMeeting(meeting, emailList);
        map.put("msg", "发送成功");
        map.put("success", true);
        map.put("code", "2000");
        return map;
    }
}
