package com.mcy.weekly.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

//一些静态常量设置
@Component
@Scope("singleton")
public class My {

    private static int autoLogin = 30;//设置cookie实现自动登录时间(单位天)

    //由于没有用redis所以只能用这么low的方式减少访问数据库或者文件
    private static boolean remind = true;//设置是否开启自动提醒

    private static String meetingPicture = "/home/mcy/meetingPicture/"; //"/home/mcy/IDEA/weekly/src/main/upload/meetingPicture/";

    //确认是自己的图片链接
    private static String local = "http://47.95.244.73/weekly_war/";

    public static String getLocal() {
        return local;
    }

    public static void setLocal(String local) {
        My.local = local;
    }

    public static String getMeetingPicture() {
        return meetingPicture;
    }

    public static void setMeetingPicture(String meetingPicture) {
        My.meetingPicture = meetingPicture;
    }

    public static boolean isRemind() {
        return remind;
    }

    public static void setRemind(boolean remind) {
        My.remind = remind;
    }

    public static int getAutoLogin() {
        return autoLogin;
    }

    public static void setAutoLogin(int autoLogin) {
        My.autoLogin = autoLogin;
    }
}
