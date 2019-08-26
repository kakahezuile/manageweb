package com.xj.bean;

public class ExtNode {

    
    private String title;  //通知标题
    
    private String content; //通知内容
    
    private Long timestamp;  //通知发出时间
    

	private Integer CMD_CODE = 100;
	
	private String nickname; // 昵称
	
	private String avatar; // 头像
	
	private String imgUrl; 
	
	private Long messageId; // 消息id
	
	private Integer communityId; // 小区id
	
	
	private Integer count=0; //这条消息推送的次数
	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

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


	
}
