package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class WebImActivies {
	private Integer activityId; //活动id
	
	private String activityTitle; // 活动名称
	
	private String activityDetail; // 活动详情
	
	private String emobIdOwner; // 活动人环信id
	
	private String emobGroupId; // 环信群号
	
	private String place;
	
	private String status; // 当前群的状态
	
	private String nickName; // 发起人 人名
	
	private Integer createTime;
	
	private Integer communityId; // 小区id
	
	private String phone; //发起人联系方式
	
	private String room; // 发起人地址

	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<ActivityPhoto> list;
	
	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String getActivityDetail() {
		return activityDetail;
	}

	public void setActivityDetail(String activityDetail) {
		this.activityDetail = activityDetail;
	}

	public String getEmobIdOwner() {
		return emobIdOwner;
	}

	public void setEmobIdOwner(String emobIdOwner) {
		this.emobIdOwner = emobIdOwner;
	}

	public String getEmobGroupId() {
		return emobGroupId;
	}

	public void setEmobGroupId(String emobGroupId) {
		this.emobGroupId = emobGroupId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public List<ActivityPhoto> getList() {
		return list;
	}

	public void setList(List<ActivityPhoto> list) {
		this.list = list;
	}
	
}
