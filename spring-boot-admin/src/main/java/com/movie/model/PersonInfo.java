package com.movie.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> PersonInfo </p>
 * @author:         jidn
 * @Date :          2019-12-25 16:09:31  
 */
@Data
@Table(name="fh_person_info")
public class PersonInfo extends BaseModel {
	
	
	/**
	 * 
	 */
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
	 * 邮箱
	 */
	@Column(name= "email")
	private String email;

	/**
	 * 手机号
	 */
	@Column(name= "mobilePhone")
	private String mobilePhone;
	
	/**
	 * 状态 0 未激活  1已激活
	 */
	@Column(name= "status")
	private Integer status;
	
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
	 * 是否是会员  0 不是 1是
	 */
	@Column(name= "isVip")
	private Integer isVip;
	
	/**
	 * 会员到期时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name= "vipExpirTime")
	private Date vipExpirTime;
	
	/**
	 * 用户IP
	 */
	@Column(name= "ip")
	private String ip;
	
	/**
	 * 会员类型  月  年
	 */
	@Column(name= "vipType")
	private String vipType;
	
	
	
}
