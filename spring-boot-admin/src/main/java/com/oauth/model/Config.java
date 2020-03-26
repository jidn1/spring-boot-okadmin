package com.oauth.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> Config </p>
 * @author:         jidn
 * @Date :          2019-12-27 18:14:58  
 */
@Data
@Table(name="fh_config")
public class Config extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**
	 * 基础配置key
	 */
	@Column(name= "configkey")
	private String configkey;
	
	/**
	 * 名称
	 */
	@Column(name= "configname")
	private String configname;
	
	/**
	 * 配置描述
	 */
	@Column(name= "configdescription")
	private String configdescription;
	
	/**
	 * 类型
	 */
	@Column(name= "datatype")
	private Integer datatype;
	
	/**
	 * 值
	 */
	@Column(name= "value")
	private String value;
	
	/**
	 * 父类型
	 */
	@Column(name= "typekey")
	private String typekey;
	
	/**
	 * 父类型名称
	 */
	@Column(name= "typename")
	private String typename;
	
	/**
	 * 父类型描述
	 */
	@Column(name= "description")
	private String description;
	
	
	
}
