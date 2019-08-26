package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 生活圈
 * @author Administrator
 *
 */
public class LifeCircle {
	
	

	@NotInsertAnnotation
	private String activityTitle;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer lifeCircleId;
	
	private Integer communityId; // 小区id
	
	private Integer type;
	
	private String emobId; // 环信id
	
	private Integer createTime; // 创建时间
	
	private String lifeContent; // 内容
	
	private Integer praiseSum; //赞的个数
	
	@NotUpdataAnnotation
	private String photoes;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String typeContent = "";
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String avatar; // 头像
	 
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String nickname; // 昵称
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<LifePhoto> lifePhotos; // 生活圈图片
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer characterValues; // 人品值
	
	private String emobGroupId; // 群id
	

	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<LifeCircleDetail> lifeCircleDetails; // 评论内容
	
	@MyAnnotation
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer isCreate; // 是否创建群 1 - 是  2 - 否
	
	private Integer updateTime;
	
	@MyAnnotation
	private Integer contentSum;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Double characterPercent;
	

	
	@NotInsertAnnotation
	@MyAnnotation
	private String status;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<LifePraiseContent> lifePraises;
	
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String emobIdShop;
	
	@MyAnnotation
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String extContent;
	
	@MyAnnotation
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String time ;
	
	public String getExtContent() {
		return extContent;
	}

	public void setExtContent(String extContent) {
		this.extContent = extContent;
	}

	public String getEmobIdShop() {
		return emobIdShop;
	}

	public void setEmobIdShop(String emobIdShop) {
		this.emobIdShop = emobIdShop;
	}
	

	public String getTypeContent() {
		return typeContent;
	}

	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<LifePraiseContent> getLifePraises() {
		return lifePraises;
	}

	public void setLifePraises(List<LifePraiseContent> lifePraises) {
		this.lifePraises = lifePraises;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Double getCharacterPercent() {
		return characterPercent;
	}

	public void setCharacterPercent(Double characterPercent) {
		this.characterPercent = characterPercent;
	}

	public Integer getContentSum() {
		return contentSum;
	}

	public void setContentSum(Integer contentSum) {
		this.contentSum = contentSum;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Integer isCreate) {
		this.isCreate = isCreate;
	}

	public List<LifeCircleDetail> getLifeCircleDetails() {
		return lifeCircleDetails;
	}

	public void setLifeCircleDetails(List<LifeCircleDetail> lifeCircleDetails) {
		this.lifeCircleDetails = lifeCircleDetails;
	}

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

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

	public List<LifePhoto> getLifePhotos() {
		return lifePhotos;
	}

	public void setLifePhotos(List<LifePhoto> lifePhotos) {
		this.lifePhotos = lifePhotos;
	}

	public Integer getCharacterValues() {
		return characterValues;
	}

	public void setCharacterValues(Integer characterValues) {
		this.characterValues = characterValues;
	}

	public String getEmobGroupId() {
		return emobGroupId;
	}

	public void setEmobGroupId(String emobGroupId) {
		this.emobGroupId = emobGroupId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPhotoes() {
		return photoes;
	}

	public void setPhotoes(String photoes) {
		this.photoes = photoes;
	}
}
