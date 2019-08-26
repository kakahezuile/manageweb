package com.xj.bean;

public class ReckoningHistory {

//	private String monthGroup;
	private String timeGroup;
	private String endedNum;
	//private String noEndedNum;
	private String reckoningPrice;
	//private String deductionShopNum;
	private String deductionPrice;

	
	//新加
	private String startTime;
	private String endTime;
	
	public String getTimeGroup() {
		return timeGroup;
	}
	public void setTimeGroup(String timeGroup) {
		this.timeGroup = timeGroup;
	}
	public String getEndedNum() {
		return endedNum;
	}
	public void setEndedNum(String endedNum) {
		this.endedNum = endedNum;
	}
	public String getReckoningPrice() {
		return reckoningPrice;
	}
	public void setReckoningPrice(String reckoningPrice) {
		this.reckoningPrice = reckoningPrice;
	}
	public String getDeductionPrice() {
		return deductionPrice;
	}
	public void setDeductionPrice(String deductionPrice) {
		this.deductionPrice = deductionPrice;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
