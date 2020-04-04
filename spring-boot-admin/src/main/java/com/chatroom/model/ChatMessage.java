package com.chatroom.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	 * 消息类型
	 */
	@Column(name= "msgType")
	private Integer msgType;
	
	/**
	 * 消息状态 0 未读 1 已读
	 */
	@Column(name= "msgStatus")
	private Integer msgStatus;
	
	
	
}
