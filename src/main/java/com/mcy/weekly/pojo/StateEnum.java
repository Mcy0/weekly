package com.mcy.weekly.pojo;

public enum StateEnum {
    IN_SCHOOL(1, "在校"),
    OUT_SCHOOL(0, "不在校");

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

    private StateEnum(int id, String name){
            this.id = id;
            this.name = name;
    }
    public static StateEnum getStateById(int id){
        for (StateEnum state :
                StateEnum.values()) {
            if (state.getId() == id){
                return state;
            }
        }
        return null;
    }


}
