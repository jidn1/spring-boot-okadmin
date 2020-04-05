package com.chat.model;

import com.mvc.base.model.BaseModel;
import lombok.Data;

import javax.persistence.*;

/**
 * <p> ChatUser </p>
 * @author:         jidn
 * @Date :          2020-04-03 16:10:39
 */
@Data
@Table(name="fh_chat_user")
public class ChatUser extends BaseModel {


	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 * 用户ID
	 */
	@Column(name= "userId")
	private String userId;

	/**
	 * 用户名 可以用中文、数字、英文字母（不区分大小写） ps 不可以使用特殊符号 账号长度在 18位
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




}
