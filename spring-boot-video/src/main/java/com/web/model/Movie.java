package com.web.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p> Movie </p>
 * @author:         jidn
 * @Date :          2020-01-02 13:40:48
 */
@Data
public class Movie implements Serializable {


	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 电影名称
	 */
	private String moviceName;

	/**
	 * 电影海报路径
	 */
	private String movicePictureUrl;

	/**
	 * 本地图片路径
	 */
	private String moviceLocalUrl;

	/**
	 * 电影播放路径
	 */
	private String movicePlayerUrl;

	/**
	 * 上映时间
	 */
	private String moviceReleaseTime;

	/**
	 * 电影类型
	 */
	private String moviceType;

	/**
	 * 国别
	 */
	private String country;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 导演
	 */
	private String director;

	/**
	 * 主要演员
	 */
	private String mainCharacter;

	/**
	 * 时长
	 */
	private Integer duration;

	/**
	 * 是否删除 0 未删除 1 已删除
	 */
	private Integer isdelete;

	private Date created;

	private Date modified;

}
