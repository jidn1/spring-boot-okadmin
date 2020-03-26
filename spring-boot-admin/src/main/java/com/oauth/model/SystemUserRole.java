package com.oauth.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> SystemUserRole </p>
 * @author:         jidn
 * @Date :          2019-12-19 15:42:39  
 */
@Data
@Table(name="fh_system_user_role")
public class SystemUserRole extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**
	 * 用户id
	 */
	@Column(name= "userId")
	private Integer userId;
	
	/**
	 * 角色Id
	 */
	@Column(name= "roleId")
	private Integer roleId;
	
	
	
}
