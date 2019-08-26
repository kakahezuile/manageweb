package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;



/**
 * 生活圈赞模型
 * @author Administrator
 *
 */
public class LifePraise {
	
	private Integer lifePraiseId;
	
	private String emobIdFrom; // 评论者
	
	private String emobIdTo; // 被评论者
	
	private Integer createTime; // 添加时间
	
	private Integer lifeCircleId;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer lifeCircleDetailId;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer status; // 

	public Integer getLifeCircleDetailId() {
		return lifeCircleDetailId;
	}

	public void setLifeCircleDetailId(Integer lifeCircleDetailId) {
		this.lifeCircleDetailId = lifeCircleDetailId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}

	public Integer getLifePraiseId() {
		return lifePraiseId;
	}

	public void setLifePraiseId(Integer lifePraiseId) {
		this.lifePraiseId = lifePraiseId;
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
}
