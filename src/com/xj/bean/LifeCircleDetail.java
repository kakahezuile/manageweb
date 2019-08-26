package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 生活圈评论详情
 * @author Administrator
 *
 */
public class LifeCircleDetail {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer lifeCircleDetailId;
	
	private String emobIdFrom; // 评论者
	
	private String emobIdTo; // 被评论者
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String fromName; // 评论者名称
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String toName; // 被评论者名称
	
	private Integer praiseSum; //赞的个数 
	
	private Integer createTime; // 添加时间
	
	private Integer updateTime; // 修改时间
	
	private Integer lifeCircleId;
	
	private String detailContent; // 评论内容

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public Integer getLifeCircleDetailId() {
		return lifeCircleDetailId;
	}

	public void setLifeCircleDetailId(Integer lifeCircleDetailId) {
		this.lifeCircleDetailId = lifeCircleDetailId;
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

	public Integer getPraiseSum() {
		return praiseSum;
	}

	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
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

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}
}
