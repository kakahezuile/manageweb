package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;

/**
 * 投诉表   来自A对B 的投诉内容
 * @author Administrator
 *
 */
public class Complaints {
	@NotInsertAnnotation
	private Integer complaintId;
	
	private String emobIdFrom; // A 用户环信id
	
	private String emobIdTo; // B 用户环信id
	
	private String title; // 投诉头部
	
	private String detail; // 投诉内容
	
	private Integer occurTime; // 投诉时间
	
	private Integer orderId; // 订单id
	
	private Integer communityId; //　小区id
	
	@NotInsertAnnotation
	@MyAnnotation
	private String emobIdShop; // 店铺环信id
	
	private String emobIdAgent; // 受理管理员id
	
	private String processDetail; // 处理内容
	
	private String deductionPrice; // 扣款金额
	
	public String getDeductionPrice() {
		return deductionPrice;
	}

	public void setDeductionPrice(String deductionPrice) {
		this.deductionPrice = deductionPrice;
	}

	public String getProcessDetail() {
		return processDetail;
	}

	public void setProcessDetail(String processDetail) {
		this.processDetail = processDetail;
	}

	public String getEmobIdAgent() {
		return emobIdAgent;
	}

	public void setEmobIdAgent(String emobIdAgent) {
		this.emobIdAgent = emobIdAgent;
	}

	public String getEmobIdShop() {
		return emobIdShop;
	}

	public void setEmobIdShop(String emobIdShop) {
		this.emobIdShop = emobIdShop;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	@NotInsertAnnotation
	private String status;

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Integer occurTime) {
		this.occurTime = occurTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
