package com.chat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvc.base.model.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * <p> ChatUserInfo </p>
 * @author:         jidn
 * @Date :          2020-04-03 16:10:59
 */
@Data
@Table(name="fh_chat_user_info")
public class ChatUserInfo extends BaseModel {


	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 *
	 */
	@Column(name= "userid")
	private String userid;

	/**
	 * 昵称
	 */
	@Column(name= "nickName")
	private String nickName;

	/**
	 * 头像
	 */
	@Column(name= "avatarImg")
	private String avatarImg;

	/**
	 * 签名
	 */
	@Column(name= "signature")
	private String signature;

	/**
	 * 性别 0 女 1男  2 未知
	 */
	@Column(name= "gender")
	private Integer gender;

	/**
	 * 过期时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name= "expirationTime")
	private Date expirationTime;

	/**
	 * 是否删除 0 未 1 已
	 */
	@Column(name= "ifdelete")
	private Integer ifdelete;


	@Transient
	private String username;
	@Transient
	private String password;
}
