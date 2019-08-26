package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class XjPay {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer payId;
	
	private Integer handleTime; // 处理时间
	
	private Integer createTime; // 创建时间
	
	private Integer completeTime; // 完成时间
	
	private Integer payStatus; // 0 - 待处理  1- 处理中  2- 处理完成
	
	private String paySum;
	
	private Integer userId;
	
	private String payNo;
	
	private Integer waitTime;
	
	private String ownerName;
	
	private Integer payType; // 1 电费 2 水费 3 燃气费 4 宽带费
	
	private String lastPay;
	
	private String nextPay;
	
	private String payAddress;
	
	public String getPayAddress() {
		return payAddress;
	}

	public void setPayAddress(String payAddress) {
		this.payAddress = payAddress;
	}

	@MyAnnotation
	@NotUpdataAnnotation
	@NotInsertAnnotation
	private Integer orderId;
	
	

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Integer completeTime) {
		this.completeTime = completeTime;
	}

	public String getLastPay() {
		return lastPay;
	}

	public void setLastPay(String lastPay) {
		this.lastPay = lastPay;
	}

	public String getNextPay() {
		return nextPay;
	}

	public void setNextPay(String nextPay) {
		this.nextPay = nextPay;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Integer handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPaySum() {
		return paySum;
	}

	public void setPaySum(String paySum) {
		this.paySum = paySum;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
	
}
