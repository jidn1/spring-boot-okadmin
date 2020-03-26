package com.oauth.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> SystemRole </p>
 * @author:         jidn
 * @Date :          2019-12-19 15:41:58  
 */
@Data
@Table(name="fh_system_role")
public class SystemRole extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**
	 * 权限名称
	 */
	@Column(name= "name")
	private String name;
	
	/**
	 * 权限描述
	 */
	@Column(name= "description")
	private String description;
	
	
	
}
