package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class LifeCircleSelect {

	private Integer lifeCircleId;
	private String emobId;
	private Integer createTime;
	private String lifeContent;
	private Integer praiseSum;
	private Integer contentSum;
	private String avatar;
	private String nickname;
	private String userUnit;
	private String userFloor;
	private String room; // 发起人地址
	private String photoes;

	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<LifePhoto> list;
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public String getLifeContent() {
		return lifeContent;
	}
	public void setLifeContent(String lifeContent) {
		this.lifeContent = lifeContent;
	}
	public Integer getPraiseSum() {
		return praiseSum;
	}
	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}
	public Integer getContentSum() {
		return contentSum;
	}
	public void setContentSum(Integer contentSum) {
		this.contentSum = contentSum;
	}
	public Integer getLifeCircleId() {
		return lifeCircleId;
	}
	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}
	public List<LifePhoto> getList() {
		return list;
	}
	public void setList(List<LifePhoto> list) {
		this.list = list;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserUnit() {
		return userUnit;
	}
	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}
	public String getUserFloor() {
		return userFloor;
	}
	public void setUserFloor(String userFloor) {
		this.userFloor = userFloor;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmobId() {
		return emobId;
	}
	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}
	public String getPhotoes() {
		return photoes;
	}
	public void setPhotoes(String photoes) {
		this.photoes = photoes;
	}
}
