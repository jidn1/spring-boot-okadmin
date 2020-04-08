package com.chat.model;

import com.mvc.base.model.BaseModel;
import lombok.Data;

import javax.persistence.*;

/**
 * <p> ChatMessage </p>
 * @author:         jidn
 * @Date :          2020-04-03 18:34:51
 */
@Data
@Table(name="fh_chat_message")
public class ChatMessage extends BaseModel {


	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 * 发送人
	 */
	@Column(name= "fromUserId")
	private String fromUserId;

	/**
	 * 接收人
	 */
	@Column(name= "toUserId")
	private String toUserId;

	/**
	 * 内容 如果是图片 语音 保存的就是 连接
	 */
	@Column(name= "sendtext")
	private String sendtext;

	/**
	 * 消息类型 0 文本 1图片 2语音
	 */
	@Column(name= "msgType")
	private Integer msgType;

	/**
	 * 消息状态 0 未读 1 已读
	 */
	@Column(name= "msgStatus")
	private Integer msgStatus;



}
