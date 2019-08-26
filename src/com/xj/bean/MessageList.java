package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class MessageList {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer messageListId;
	
	private String emobIdFrom;
	
	private String emobIdTo;
	
	private Integer createTime;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String nickname; 
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getMessageListId() {
		return messageListId;
	}

	public void setMessageListId(Integer messageListId) {
		this.messageListId = messageListId;
	}

	public String getEmobIdFrom() {
		return emobIdFrom;
	}

	public void setEmobIdFrom(String emobIdFrom) {
		this.emobIdFrom = emobIdFrom;
	}

	public String getEmobIdTo() {
		return emobIdTo;
	}

	public void setEmobIdTo(String emobIdTo) {
		this.emobIdTo = emobIdTo;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
