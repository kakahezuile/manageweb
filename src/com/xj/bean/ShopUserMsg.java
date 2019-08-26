package com.xj.bean;

public class ShopUserMsg {
	private Integer sumOrder; // 总订单数
	
	private String nickname; // 商家名称
	
	private String phone; // 联系电话
	
	private Integer haoping; // 好评数
	
	private Integer zhongping; // 中评数
	
	private Integer chaping; // 差评数
	
	private String avatar; // 师傅头像
	
	private String logo; // 师傅头像
	
	private Integer complaintNum;
	
	private Integer occurTime;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public Integer getSumOrder() {
		return sumOrder;
	}

	public void setSumOrder(Integer sumOrder) {
		this.sumOrder = sumOrder;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getHaoping() {
		return haoping;
	}

	public void setHaoping(Integer haoping) {
		this.haoping = haoping;
	}

	public Integer getZhongping() {
		return zhongping;
	}

	public void setZhongping(Integer zhongping) {
		this.zhongping = zhongping;
	}

	public Integer getChaping() {
		return chaping;
	}

	public void setChaping(Integer chaping) {
		this.chaping = chaping;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getComplaintNum() {
		return complaintNum;
	}

	public void setComplaintNum(Integer complaintNum) {
		this.complaintNum = complaintNum;
	}

	public Integer getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Integer occurTime) {
		this.occurTime = occurTime;
	}
	
	
}
