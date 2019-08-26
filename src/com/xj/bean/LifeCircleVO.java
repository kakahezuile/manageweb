package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;

/**
 * 生活圈列表
 * @author Administrator
 *
 */
public class LifeCircleVO {
	
	private String timstamp; // 时间
	
	@MyAnnotation
	private List<LifePhoto> listPhotos; // 生活圈图片
	
	private String content; // 生活圈内容
	
	private Integer contentSum; // 评论数
	
	private Integer praiseSum; // 点赞数
	
	private Integer createTime; // 发布时间
	
	private Integer lifeCircleId;

	public Integer getLifeCircleId() {
		return lifeCircleId;
	}

	public void setLifeCircleId(Integer lifeCircleId) {
		this.lifeCircleId = lifeCircleId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getTimstamp() {
		return timstamp;
	}

	public void setTimstamp(String timstamp) {
		this.timstamp = timstamp;
	}

	public List<LifePhoto> getListPhotos() {
		return listPhotos;
	}

	public void setListPhotos(List<LifePhoto> listPhotos) {
		this.listPhotos = listPhotos;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
