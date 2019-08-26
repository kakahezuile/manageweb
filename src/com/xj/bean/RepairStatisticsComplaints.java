package com.xj.bean;

/**
 * 师傅个人被投诉列表头部
 * @author Administrator
 *
 */
public class RepairStatisticsComplaints {
	
	private Integer complaintsCountHistory; //  历史投诉次数
	
	private Integer repairCountHistory; // 历史维修次数
	
	private Integer unComplaintsCount; //未解决次数
	
	private Integer abandonCount; //未解决次数
	
	private Integer repairCountHistoryPart; //分包次数
	
	//private String repairMoneyHistory; // 历史维修金额
	
	private Integer shopId; // 店铺id
	
	private String shopName; // 店铺名称
	
	private String nickname; // 昵称
	
	private Integer userId; // 用户id
	
	
	private String avatar; // 用户id

	public Integer getComplaintsCountHistory() {
		return complaintsCountHistory;
	}

	public void setComplaintsCountHistory(Integer complaintsCountHistory) {
		this.complaintsCountHistory = complaintsCountHistory;
	}

	public Integer getRepairCountHistory() {
		return repairCountHistory;
	}

	public void setRepairCountHistory(Integer repairCountHistory) {
		this.repairCountHistory = repairCountHistory;
	}

	public Integer getUnComplaintsCount() {
		return unComplaintsCount;
	}

	public void setUnComplaintsCount(Integer unComplaintsCount) {
		this.unComplaintsCount = unComplaintsCount;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getRepairCountHistoryPart() {
		return repairCountHistoryPart;
	}

	public void setRepairCountHistoryPart(Integer repairCountHistoryPart) {
		this.repairCountHistoryPart = repairCountHistoryPart;
	}

	public Integer getAbandonCount() {
		return abandonCount;
	}

	public void setAbandonCount(Integer abandonCount) {
		this.abandonCount = abandonCount;
	}
	
}
