package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class AdvisoryHistory {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer advisoryHistoryId;
	
	private String emobIdUser;
	
	private String status;
	
	
	private String userName;
	
	private String emobIdAgent;
	
	private String agentName;
	
	private Integer createTime;
	
	private Integer updateTime;
	
	private Integer advisoryTime;
	
	private String phone;
	
	private Integer communityId;

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

	public Integer getAdvisoryHistoryId() {
		return advisoryHistoryId;
	}

	public void setAdvisoryHistoryId(Integer advisoryHistoryId) {
		this.advisoryHistoryId = advisoryHistoryId;
	}

	public String getEmobIdUser() {
		return emobIdUser;
	}

	public void setEmobIdUser(String emobIdUser) {
		this.emobIdUser = emobIdUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmobIdAgent() {
		return emobIdAgent;
	}

	public void setEmobIdAgent(String emobIdAgent) {
		this.emobIdAgent = emobIdAgent;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
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

	public Integer getAdvisoryTime() {
		return advisoryTime;
	}

	public void setAdvisoryTime(Integer advisoryTime) {
		this.advisoryTime = advisoryTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
