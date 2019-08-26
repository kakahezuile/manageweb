package com.xj.bean;

import java.util.List;

public class ExtBean {
		private String title;  //通知标题
	    
	    private String content; //通知内容

		private Integer CMD_CODE = 100;
		
		private Long  timestamp;
		
		private String nickname; // 昵称
		
		private String avatar; // 头像
		
		private String imgUrl; 
		
		private Long messageId; // 消息id
		
		private Integer communityId; // 小区id

		private List<String> users;//发送给的用户
		
		private String adminId;
		
		private String senderObject;

		public String getAdminId() {
			return adminId;
		}

		public void setAdminId(String adminId) {
			this.adminId = adminId;
		}

		public String getSenderObject() {
			return senderObject;
		}

		public void setSenderObject(String senderObject) {
			this.senderObject = senderObject;
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

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public Long getMessageId() {
			return messageId;
		}

		public void setMessageId(Long messageId) {
			this.messageId = messageId;
		}

		public Integer getCommunityId() {
			return communityId;
		}

		public void setCommunityId(Integer communityId) {
			this.communityId = communityId;
		}

		public List<String> getUsers() {
			return users;
		}

		public void setUsers(List<String> users) {
			this.users = users;
		}
		
}
