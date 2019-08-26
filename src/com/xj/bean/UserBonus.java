package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 用户的帮帮券
 * @author Administrator
 *
 */
public class UserBonus {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer userBonusId;
	
	private Integer bonusId; // 帮帮券id
	
	private Integer userId; // 用户id
	
	private Integer startTime; // 开始时间
	
	private Integer expireTime; // 截止时间
	
	
	private String bonusPar; // 金额
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String startTimeStr;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String expireTimeStr;
	
	private Integer useTime; // 使用时间
	
	private Integer orderId; // 订单id  唯一标识
	
	private Integer createTime;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String messageValue;
	
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String phoneList;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer messageIsSend;
	
	private String bonusStatus;
	
	private Integer bonusType; // 帮帮券类型
	
	private String serial; // 订单号
	
	
	
	
	

	public String getBonusPar() {
		return bonusPar;
	}

	public void setBonusPar(String bonusPar) {
		this.bonusPar = bonusPar;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getBonusType() {
		return bonusType;
	}

	public void setBonusType(Integer bonusType) {
		this.bonusType = bonusType;
	}

	public String getBonusStatus() {
		return bonusStatus;
	}

	public void setBonusStatus(String bonusStatus) {
		this.bonusStatus = bonusStatus;
	}

	public Integer getMessageIsSend() {
		return messageIsSend;
	}

	public void setMessageIsSend(Integer messageIsSend) {
		this.messageIsSend = messageIsSend;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getExpireTimeStr() {
		return expireTimeStr;
	}

	public void setExpireTimeStr(String expireTimeStr) {
		this.expireTimeStr = expireTimeStr;
	}

	public String getMessageValue() {
		return messageValue;
	}

	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}

	public Integer getUserBonusId() {
		return userBonusId;
	}

	public void setUserBonusId(Integer userBonusId) {
		this.userBonusId = userBonusId;
	}

	public Integer getBonusId() {
		return bonusId;
	}

	public void setBonusId(Integer bonusId) {
		this.bonusId = bonusId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getUseTime() {
		return useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(String phoneList) {
		this.phoneList = phoneList;
	}
	
	
}
