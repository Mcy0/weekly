package com.mcy.weekly.pojo;

public enum LearningDirectionEnum {
    FRONT_END(0, "前端"),
    BACH_END(1, "后端"),
    PRODUCT(2, "产品经理"),
    OPERATING(3, "运营"),
    VISUAL(4, "视觉");


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
    private LearningDirectionEnum(int id, String name){
        this.id = id;
        this.name = name;
    }
    public static LearningDirectionEnum getLearningDirectionById(int id) {
        for (LearningDirectionEnum learningDirection :
                LearningDirectionEnum.values()) {
            if (learningDirection.getId() == id) {
                return learningDirection;
            }
        }
        return null;
    }
}
