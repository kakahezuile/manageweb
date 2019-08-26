package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class RepairStatisticsCenter {
	
	private Integer shopId; // 店家id
	
	private String shopName; // 店家名称
	
	private String nickname; // 昵称
	
	private String avatar; // 头像地址
	
	private String phone; // 联系电话
	
	private String status; // 状态
	
	private String logo; // 店铺图片
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<ItemCategory> list;

	public List<ItemCategory> getList() {
		return list;
	}

	public void setList(List<ItemCategory> list) {
		this.list = list;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
