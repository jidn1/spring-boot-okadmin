package com.hothub.enums;

public enum HotType {

    /**
     *
     */
    TYPE1(1,"博客园"),
    TYPE2(2,"百度热搜"),
    TYPE3(3,"知乎"),
    TYPE4(4,"虎扑"),
    TYPE5(5,"IT之家"),

    ;

    private Integer code;
    private String remark;
    HotType(Integer code,String remark) {
        this.remark = remark;
        this.code = code;
    }
    public String getRemark() {
        return remark;
    }

    public Integer getCode() {
        return code;
    }
}
