package com.xj.bean;

public class RuleRelation {
	private Integer ruleRelationId;
	
	private Integer ruleId;
	
	private Integer adminId;
	
	private Integer createTime;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRuleRelationId() {
		return ruleRelationId;
	}

	public void setRuleRelationId(Integer ruleRelationId) {
		this.ruleRelationId = ruleRelationId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
