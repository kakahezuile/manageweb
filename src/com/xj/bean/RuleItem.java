package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class RuleItem {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer ruleItemId;
	
	private Integer adminId;

	
	private Integer communityId;
	
	private Integer createTime;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getRuleItemId() {
		return ruleItemId;
	}

	public void setRuleItemId(Integer ruleItemId) {
		this.ruleItemId = ruleItemId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
}
