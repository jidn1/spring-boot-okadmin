package com.oauth.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * <p> SystemMenu </p>
 * @author:         jidn
 * @Date :          2019-12-19 15:35:25
 */
@Data
@Table(name="fh_system_menu")
public class SystemMenu extends BaseModel {


	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	/**
	 * 菜单名字
	 */
	@Column(name= "name")
	private String name;

	/**
	 * 类型  button 按钮 menu 菜单
	 */
	@Column(name= "resourceType")
	private String resourceType;

	/**
	 * 路径
	 */
	@Column(name= "url")
	private String url;

	/**
	 *
	 */
	@Column(name= "icon")
	private String icon;

	/**
	 *
	 */
	@Column(name= "mkey")
	private String mkey;

	/**
	 * 父菜单
	 */
	@Column(name= "pkey")
	private String pkey;

	/**
	 * 子菜单
	 */
	@Transient
	private List<SystemMenu> sonMenu;

}
