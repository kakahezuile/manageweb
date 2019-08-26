package com.xj.bean;

public class UserMessageBean {
	
	private String type;
	
	private String from; //发送人username
	
	private String msg_id; //消息id
	
	private String chat_type; //用来判断单聊还是群聊。chat:单聊，groupchat:群聊
	
	private PayloadBean payload; // 消息相关详细信息
	
	private Long timestamp; //消息发送时间
	
	private String to; //接收人的username或者接收group的id
	
	private String uuid;
	
	private String direction;
	
	private Long created;
	
	private Long modified;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getModified() {
		return modified;
	}

	public void setModified(Long modified) {
		this.modified = modified;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getChat_type() {
		return chat_type;
	}

	public void setChat_type(String chat_type) {
		this.chat_type = chat_type;
	}

	public PayloadBean getPayload() {
		return payload;
	}

	public void setPayload(PayloadBean payload) {
		this.payload = payload;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
