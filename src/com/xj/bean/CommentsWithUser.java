package com.xj.bean;



public class CommentsWithUser {
	
	private Integer commentId;
    private String emobIdFrom;
    private String emobIdTo;
    private int score;
    private String content;
    private int createTime;
    private String nickname;
    private String avatar;
    private String userFloor;
	private String userUnit;
	private String room;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
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
	public String getUserFloor() {
		return userFloor;
	}
	public void setUserFloor(String userFloor) {
		this.userFloor = userFloor;
	}
	public String getUserUnit() {
		return userUnit;
	}
	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
    
}
