package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class SensitiveWordsHistory {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer sensitiveWordsHistoryId;
	
	private String sensitiveWords;
	
	private String groupName;
	
	private String groupId;
	
	private Integer communityId;
	
	private String communityName;
	
	private Integer historyTime;
	
	private String status;
	
	private String nickname;
	
	private String emobId;
	
	private Integer createTime;
	
	private Integer updateTime;
	
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Integer getSensitiveWordsHistoryId() {
		return sensitiveWordsHistoryId;
	}

	public void setSensitiveWordsHistoryId(Integer sensitiveWordsHistoryId) {
		this.sensitiveWordsHistoryId = sensitiveWordsHistoryId;
	}

	public String getSensitiveWords() {
		return sensitiveWords;
	}

	public void setSensitiveWords(String sensitiveWords) {
		this.sensitiveWords = sensitiveWords;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getHistoryTime() {
		return historyTime;
	}

	public void setHistoryTime(Integer historyTime) {
		this.historyTime = historyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
