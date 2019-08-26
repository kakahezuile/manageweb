package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 维修统计首页top数据
 * @author Administrator
 *
 */
public class RepairStatisticsTop {
	
	private Integer repairCount; // 维修次数
	private Integer todayComplaintsCount; // 投诉次数
	
	private Integer todayUnComplaintsCount; // 未解决次数
	
	
	private Integer abandonCount; // 未解决次数
	
	
	private Integer endOrder; // 未解决次数
	
	private Integer subpackageNum;//分包
	
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String repairMoney; // 金额

	public Integer getRepairCount() {
		return repairCount;
	}

	public void setRepairCount(Integer repairCount) {
		this.repairCount = repairCount;
	}

	public Integer getTodayComplaintsCount() {
		return todayComplaintsCount;
	}

	public void setTodayComplaintsCount(Integer todayComplaintsCount) {
		this.todayComplaintsCount = todayComplaintsCount;
	}

	

	public Integer getTodayUnComplaintsCount() {
		return todayUnComplaintsCount;
	}

	public void setTodayUnComplaintsCount(Integer todayUnComplaintsCount) {
		this.todayUnComplaintsCount = todayUnComplaintsCount;
	}

	public Integer getEndOrder() {
		return endOrder;
	}

	public void setEndOrder(Integer endOrder) {
		this.endOrder = endOrder;
	}

	public String getRepairMoney() {
		return repairMoney;
	}

	public void setRepairMoney(String repairMoney) {
		this.repairMoney = repairMoney;
	}

	public Integer getSubpackageNum() {
		return subpackageNum;
	}

	public void setSubpackageNum(Integer subpackageNum) {
		this.subpackageNum = subpackageNum;
	}

	public Integer getAbandonCount() {
		return abandonCount;
	}

	public void setAbandonCount(Integer abandonCount) {
		this.abandonCount = abandonCount;
	}

	
	
}
