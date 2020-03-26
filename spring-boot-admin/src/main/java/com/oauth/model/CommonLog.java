package com.oauth.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> CommonLog </p>
 * @author:         jidn
 * @Date :          2019-12-26 14:45:30  
 */
@Data
@Table(name="fh_common_log")
public class CommonLog extends BaseModel {
	
	
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
	private Long userId;
	
	/**
	 * 用户名
	 */
	@Column(name= "userName")
	private String userName;
	
	/**
	 * ip
	 */
	@Column(name= "ip")
	private String ip;
	
	/**
	 * pc、phone
	 */
	@Column(name= "name")
	private String name;
	
	/**
	 * 方法名
	 */
	@Column(name= "methodName")
	private String methodName;
	
	/**
	 * 参数
	 */
	@Column(name= "args")
	private String args;
	
	/**
	 * 织入增强处理的目标对象
	 */
	@Column(name= "target")
	private String target;
	
	/**
	 * 
	 */
	@Column(name= "remark")
	private String remark;
	
	
	
}
