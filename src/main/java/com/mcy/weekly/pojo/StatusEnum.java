package com.mcy.weekly.pojo;

import jdk.net.SocketFlow;

public enum StatusEnum{

    ADMINISTRATOR(1, "管理员"),
    NONADMINISTRATOR(0, "非管理员");

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private StatusEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static StatusEnum getStatusEnum(int id){
        for (StatusEnum status :
                StatusEnum.values()) {
            if (status.getId() == id){
                return status;
            }
        }
        return null;
    }

}
