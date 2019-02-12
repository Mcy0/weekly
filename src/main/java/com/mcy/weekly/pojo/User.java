package com.mcy.weekly.pojo;

import java.util.List;
import java.util.regex.Pattern;

public class User {
    private int id;
    private String userName;//用户名 1
    private String email;//邮箱
    private String password;//密码
    private String professionalClass;//专业班级 1
    private String tel;//电话 1
    private String address;//地址 1
    private LearningDirectionEnum learningDirection;//方向 前端 后端 1
    private StatusEnum status;//地位 管理员 非管理员 1
    private StateEnum state;//状态 在校 不在校 1
    //格式验证,正则表达式
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,18}$";
    private static final String REGEX_PCLASS = "^[\u4e00-\u9fa5]{2,4}[0-9]{4}$";
    private static final String REGEX_TEL = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private static final String REGEX_ADDRESS = "^[\u4e00-\u9fa5]{2,5}省[\u4e00-\u9fa5]{2,5}市$";
    //邮箱验证
    public static boolean isEmail(String email){
        return Pattern.matches(REGEX_EMAIL, email);
    }
    //密码验证
    public static boolean isPassword(String password){
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    //专业班级验证
    public static boolean isPClass(String professionalClass){
        return Pattern.matches(REGEX_PCLASS, professionalClass);
    }
    //手机号验证
    public static boolean isTel(String tel){
        return Pattern.matches(REGEX_TEL, tel);
    }
    //地址验证
    public static boolean isAddress(String address){
        return Pattern.matches(REGEX_ADDRESS, address);
    }

    //setter, getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfessionalClass() {
        return professionalClass;
    }

    public void setProfessionalClass(String professionalClass) {
        this.professionalClass = professionalClass;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LearningDirectionEnum getLearningDirection() {
        return learningDirection;
    }

    public void setLearningDirection(LearningDirectionEnum learningDirection) {
        this.learningDirection = learningDirection;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }
}
