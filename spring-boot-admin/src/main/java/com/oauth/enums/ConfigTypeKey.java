package com.oauth.enums;

public enum ConfigTypeKey {

    /**
     *
     */
    TYPE_OK_ADMIN("okAdmin","okAdmin基础配置"),
    TYPE_OK_HEAD("headportrait","后台用户头像"),


    MENU("menu","菜单"),
    BUTTON("button","菜单");




    ;

    private String key;
    private String remark;
    ConfigTypeKey(String key,String remark) {
        this.remark = remark;
        this.key = key;
    }
    public String getRemark() {
        return remark;
    }

    public String getKey(){
        return key;
    }
}
