package com.xj.stat.po;

public class PushMessageBean {

	private String title;  //通知标题
    
    private String content; //通知内容
    
    private Long timestamp;  //通知发出时间
    
	private Integer CMD_CODE = 100;
	
	private String nickname; // 昵称
	
	private String type; // 通知状态;   read - 已读   unread - 未读
	
	private String emobId; // 用户环信id
	
	private Long messageId; // 消息id
	
	private String equipment; // 设备信息
	
	private Integer communityId;

	private String dateTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCMD_CODE() {
		return CMD_CODE;
	}

	public void setCMD_CODE(Integer cMD_CODE) {
		CMD_CODE = cMD_CODE;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
