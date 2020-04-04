package com.chatroom.model;

import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> ChatFriend </p>
 * @author:         jidn
 * @Date :          2020-04-03 18:35:16  
 */
@Data
@Table(name="fh_chat_friend")
public class ChatFriend extends BaseModel {
	
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**
	 * 用户id
	 */
	@Column(name= "userId")
	private String userId;
	
	/**
	 * 好友id
	 */
	@Column(name= "friendUserId")
	private String friendUserId;
	
	/**
	 * 好友状态 
	 */
	@Column(name= "status")
	private Integer status;
	
	
	
}
