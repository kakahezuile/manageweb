package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 黑名单表   - 用户A对B集合的
 * @author Administrator
 *
 */
public class BlackList {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer blackListId;
	
	private String emobIdFrom; // 用户A 环信id
	
	private String emobIdTo; // 用户B 环信id
	
	private Integer createTime; // 创建时间
	
	private Integer updateTime; // 更新时间
	
	private Integer communityId; // 小区id
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String nickname; // 昵称
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String avatar; // 头像地址
	
	private String status; // 类型       activity  与  circle

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getBlackListId() {
		return blackListId;
	}

	public void setBlackListId(Integer blackListId) {
		this.blackListId = blackListId;
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

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
}
