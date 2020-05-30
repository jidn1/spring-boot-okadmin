package com.web.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p> PornHub </p>
 * @author:         jidn
 * @Date :          2019-12-30 17:05:17
 */
@Data
public class PornHub implements Serializable {


	/**
	 *
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 图片
	 */
	private String picture;

	/**
	 * 播放源
	 */
	private String m3u8;

	/**
	 * 热度
	 */
	private Integer fire;

	/**
	 * 更新时间
	 */
	private String last;

	private Date created;

	private Date modified;

}
