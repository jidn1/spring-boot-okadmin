package com.hothub.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> TopHot </p>
 * @author:         jidn
 * @Date :          2019-12-26 17:33:13  
 */
@Data
@Table(name="fh_top_hot")
public class TopHot extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**
	 * 热榜标题
	 */
	@Column(name= "title")
	private String title;
	
	/**
	 * 热榜外链
	 */
	@Column(name= "url")
	private String url;
	
	/**
	 * 原始链接
	 */
	@Column(name= "originalUrl")
	private String originalUrl;
	
	/**
	 * 热榜类型 1博客园  2 百度热搜...
	 */
	@Column(name= "type")
	private Integer type;
	
	
	
}
