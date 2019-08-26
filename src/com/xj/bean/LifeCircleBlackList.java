package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 圈黑名单列表
 * @author Administrator
 *
 */
public class LifeCircleBlackList {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer lifeCircleBlackListId;
	
	private String emobIdFrom;
	
	private String emobIdTo;
	
	private Integer communityId;
	
	private Integer createTime;

	public Integer getLifeCircleBlackListId() {
		return lifeCircleBlackListId;
	}

	public void setLifeCircleBlackListId(Integer lifeCircleBlackListId) {
		this.lifeCircleBlackListId = lifeCircleBlackListId;
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

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
