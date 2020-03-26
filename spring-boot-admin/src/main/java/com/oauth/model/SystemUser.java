package com.oauth.model;

import com.mvc.base.model.BaseModel;
import lombok.Data;

import javax.persistence.*;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 16:52
 * @Description:
 */
@Data
@Table(name="fh_system_user")
public class SystemUser extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * 用户名
     */
    @Column(name= "username")
    private String username;

    /**
     * 密码
     */
    @Column(name= "password")
    private String password;

    /**
     * 盐值
     */
    @Column(name= "salt")
    private String salt;

    /**
     * 是否删除，0否1是
     */
    @Column(name= "isdelete")
    private Integer isdelete;

}
