package com.xj.bean;

import java.util.List;

/**
 * 生活圈新消息
 * @author Administrator
 *
 */
public class NewTips {
	
	private Integer contentSum; // 评论数
	
	private Integer praiseSum;  // 点赞数
	
	private Integer lifeCircleId;
	
	private List<TipsUsers> users;
	

	public List<TipsUsers> getUsers() {
		return users;
	}

	public void setUsers(List<TipsUsers> users) {
		this.users = users;
	}

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}

	public Integer getContentSum() {
		return contentSum;
	}

	public void setContentSum(Integer contentSum) {
		this.contentSum = contentSum;
	}

	public Integer getPraiseSum() {
		return praiseSum;
	}

	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}
}
