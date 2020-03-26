package com.web.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> Movie </p>
 * @author:         jidn
 * @Date :          2020-01-02 13:40:48  
 */
@Data
@Table(name="fh_movie")
public class Movie extends BaseModel {
	
	
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**
	 * 电影名称
	 */
	@Column(name= "moviceName")
	private String moviceName;
	
	/**
	 * 电影海报路径
	 */
	@Column(name= "movicePictureUrl")
	private String movicePictureUrl;
	
	/**
	 * 本地图片路径
	 */
	@Column(name= "moviceLocalUrl")
	private String moviceLocalUrl;
	
	/**
	 * 电影播放路径
	 */
	@Column(name= "movicePlayerUrl")
	private String movicePlayerUrl;
	
	/**
	 * 上映时间
	 */
	@Column(name= "moviceReleaseTime")
	private String moviceReleaseTime;
	
	/**
	 * 电影类型
	 */
	@Column(name= "moviceType")
	private String moviceType;
	
	/**
	 * 国别
	 */
	@Column(name= "country")
	private String country;
	
	/**
	 * 语言
	 */
	@Column(name= "language")
	private String language;
	
	/**
	 * 导演
	 */
	@Column(name= "director")
	private String director;
	
	/**
	 * 主要演员
	 */
	@Column(name= "mainCharacter")
	private String mainCharacter;
	
	/**
	 * 时长
	 */
	@Column(name= "duration")
	private Integer duration;
	
	/**
	 * 是否删除 0 未删除 1 已删除
	 */
	@Column(name= "isdelete")
	private Integer isdelete;
	
	
	
}
