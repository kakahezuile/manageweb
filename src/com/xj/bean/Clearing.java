package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class Clearing {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer clearingId; // 主键
	
	private Integer startTime; // 结款记录的开始时间
	
	private Integer endTime; // 结款记录的结束时间
	
	private Integer clearingTime; // 结款时间
	
	private Integer updateTime; //  最后更新时间
	
	private Integer createTime; // 创建时间
	
	private String emobIdShop; // 商家的 emobI
	
	private String emobIdAgent; // 管理员emobiD
	
	private String cardNo; // 银行卡号
	
	private String onlinePrice; // 在线支付金额
	
	private String bonusPrice; // 帮帮券支付金额
	
	private String deductionApplyPrice; // 申请扣款金额
	
	private String deductionPrice; //　实际扣款金额
	
	private String status;// 状态   ongoing - 申请中      ended - 结款完成
	
	private Integer communityId;

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getClearingId() {
		return clearingId;
	}

	public void setClearingId(Integer clearingId) {
		this.clearingId = clearingId;
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

	public Integer getClearingTime() {
		return clearingTime;
	}

	public void setClearingTime(Integer clearingTime) {
		this.clearingTime = clearingTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getEmobIdShop() {
		return emobIdShop;
	}

	public void setEmobIdShop(String emobIdShop) {
		this.emobIdShop = emobIdShop;
	}

	public String getEmobIdAgent() {
		return emobIdAgent;
	}

	public void setEmobIdAgent(String emobIdAgent) {
		this.emobIdAgent = emobIdAgent;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOnlinePrice() {
		return onlinePrice;
	}

	public void setOnlinePrice(String onlinePrice) {
		this.onlinePrice = onlinePrice;
	}

	public String getBonusPrice() {
		return bonusPrice;
	}

	public void setBonusPrice(String bonusPrice) {
		this.bonusPrice = bonusPrice;
	}

	public String getDeductionApplyPrice() {
		return deductionApplyPrice;
	}

	public void setDeductionApplyPrice(String deductionApplyPrice) {
		this.deductionApplyPrice = deductionApplyPrice;
	}

	public String getDeductionPrice() {
		return deductionPrice;
	}

	public void setDeductionPrice(String deductionPrice) {
		this.deductionPrice = deductionPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
