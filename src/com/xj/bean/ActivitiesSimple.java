package com.xj.bean;

import java.util.List;

public class ActivitiesSimple {

	private Integer activityId; // 活动id
	private String activityTitle; // 活动标题
	private String activityDetail; // 活动内容
	private String emobIdOwner; // 用户环信id
	private String nickname;
	private Integer createTime; // 创建时间
	private Integer communityId; // 小区id
	private String emobGroupId; // 环信群id
	private Integer activityTime; // 活动时间
	private Integer activityUserSum;
	private List<ActivityPhoto> photoList;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getEmobGroupId() {
		return emobGroupId;
	}

	public void setEmobGroupId(String emobGroupId) {
		this.emobGroupId = emobGroupId;
	}

	public Integer getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Integer activityTime) {
		this.activityTime = activityTime;
	}

	public Integer getActivityUserSum() {
		return activityUserSum;
	}

	public void setActivityUserSum(Integer activityUserSum) {
		this.activityUserSum = activityUserSum;
	}

	public List<ActivityPhoto> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<ActivityPhoto> photoList) {
		this.photoList = photoList;
	}

}
