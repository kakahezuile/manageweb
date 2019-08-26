package com.xj.bean;

public class PayOff {

	private Integer shopId;
	private String emobId;
	private String shopName;
	private String phone;
	private Double priceSum;
	private String status;
	private Integer charge;
	private Double deductionPrice;
	private String cardNo;
	private String bonusSum;
	private Integer clearingId;
	private Integer timeNum;
	
	public Integer getTimeNum() {
		return timeNum;
	}
	public void setTimeNum(Integer timeNum) {
		this.timeNum = timeNum;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getPriceSum() {
		return priceSum;
	}
	public void setPriceSum(Double priceSum) {
		this.priceSum = priceSum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Double getDeductionPrice() {
		return deductionPrice;
	}
	public void setDeductionPrice(Double deductionPrice) {
		this.deductionPrice = deductionPrice;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
	public String getEmobId() {
		return emobId;
	}
	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}
	public String getBonusSum() {
		return bonusSum;
	}
	public void setBonusSum(String bonusSum) {
		this.bonusSum = bonusSum;
	}
	public Integer getClearingId() {
		return clearingId;
	}
	public void setClearingId(Integer clearingId) {
		this.clearingId = clearingId;
	}
	
	
}
