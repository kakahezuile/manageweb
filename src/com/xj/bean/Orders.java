package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;


/**
 * 订单表
 * @author Administrator
 *
 */
public class Orders {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer orderId;
	@NotUpdataAnnotation	
	private String serial;
	
	private String emobIdUser; // 用户环信id
	
	private String emobIdShop; // 商家环信id
	
	private Integer messageId; //
	
	private String status; // 订单状态
	
	private Integer startTime; // 开始时间
	
	private Integer endTime; // 结束时间
	
	private Integer communityId; // 小区id
	
	private Integer orderYear; // 订单的年份
	
	private Integer orderMonth; // 订单月份
	
	private String orderPrice; // 订单金额

	private String action; // 缴费类型
	 
	private String online; // 是否线上   yes or no
	
	private String orderAddress;
	
	private String orderType; // 订单类型
	
	private String orderDetail; // 订单明细
	
	private String share; // 是否已分享   yes or no

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(Integer orderYear) {
		this.orderYear = orderYear;
	}

	public Integer getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(Integer orderMonth) {
		this.orderMonth = orderMonth;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getEmobIdUser() {
		return emobIdUser;
	}

	public void setEmobIdUser(String emobIdUser) {
		this.emobIdUser = emobIdUser;
	}

	public String getEmobIdShop() {
		return emobIdShop;
	}

	public void setEmobIdShop(String emobIdShop) {
		this.emobIdShop = emobIdShop;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
}
