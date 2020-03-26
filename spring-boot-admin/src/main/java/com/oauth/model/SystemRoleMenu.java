package com.oauth.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> SystemRoleMenu </p>
 * @author:         jidn
 * @Date :          2019-12-19 15:42:24  
 */
@Data
@Table(name="fh_system_role_menu")
public class SystemRoleMenu extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	/**
	 * 权限ID
	 */
	@Column(name= "roleId")
	private Integer roleId;
	
	/**
	 * 菜单id
	 */
	@Column(name= "menuId")
	private Integer menuId;
	
	
	
}
