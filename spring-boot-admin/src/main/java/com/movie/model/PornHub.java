package com.movie.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> PornHub </p>
 * @author:         jidn
 * @Date :          2019-12-30 17:05:17  
 */
@Data
@Table(name="fh_porn_hub")
public class PornHub extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**
	 * 名称
	 */
	@Column(name= "name")
	private String name;
	
	/**
	 * 类型
	 */
	@Column(name= "type")
	private String type;
	
	/**
	 * 图片
	 */
	@Column(name= "picture")
	private String picture;
	
	/**
	 * 播放源
	 */
	@Column(name= "m3u8")
	private String m3u8;
	
	/**
	 * 热度
	 */
	@Column(name= "fire")
	private Integer fire;
	
	/**
	 * 更新时间
	 */
	@Column(name= "last")
	private String last;
	
	
	
}
