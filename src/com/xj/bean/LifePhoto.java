package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 生活圈图片
 * @author Administrator
 *
 */
public class LifePhoto {
	
	@NotUpdataAnnotation
	@NotInsertAnnotation
	private Integer lifePhotoId;
	
	private Integer lifeCircleId;
	
	private String photoUrl;
	
	private Integer createTime;
	
	public LifePhoto(String photoUrl) {
		super();
		this.photoUrl = photoUrl;
	}
	
	public LifePhoto() {
		super();
	}
	public Integer getLifePhotoId() {
		return lifePhotoId;
	}

	public void setLifePhotoId(Integer lifePhotoId) {
		this.lifePhotoId = lifePhotoId;
	}

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
