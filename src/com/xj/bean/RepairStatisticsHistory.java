package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 历史维修列表对象
 * @author Administrator
 *
 */
public class RepairStatisticsHistory {
	
	private String avatar; // 用户头像地址
	
	private String logo; // 商家头像地址
	
	private String shopId; // 商家id
	
	private String shopName; // 商家名称
	
	private String phone; // 联系电话
	
	private Integer endOrder;
	
	private Integer unsolvedOrder;
	
	private Integer abandonCount;
	
	private  String orderPric;
	
	private Integer repairStatisticsCount; // 维修总次数
	
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
	private Integer tousu; // 维修总次数
	

	public Integer getTousu() {
		return tousu;
	}

	public void setTousu(Integer tousu) {
		this.tousu = tousu;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
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

	public Integer getRepairStatisticsCount() {
		return repairStatisticsCount;
	}

	public void setRepairStatisticsCount(Integer repairStatisticsCount) {
		this.repairStatisticsCount = repairStatisticsCount;
	}

	public Integer getEndOrder() {
		return endOrder;
	}

	public void setEndOrder(Integer endOrder) {
		this.endOrder = endOrder;
	}

	public Integer getUnsolvedOrder() {
		return unsolvedOrder;
	}

	public void setUnsolvedOrder(Integer unsolvedOrder) {
		this.unsolvedOrder = unsolvedOrder;
	}

	public String getOrderPric() {
		return orderPric;
	}

	public void setOrderPric(String orderPric) {
		this.orderPric = orderPric;
	}

	public Integer getAbandonCount() {
		return abandonCount;
	}

	public void setAbandonCount(Integer abandonCount) {
		this.abandonCount = abandonCount;
	}


}
