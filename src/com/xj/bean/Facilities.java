package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;

/**
 * 周边设施表
 */
public class Facilities {
	@NotInsertAnnotation
	private Long facilityId;
	private String facilityName; // 设施名称
	private String facilitiesDesc; // 设施明细
	private String address; // 地址
	private String phone; // 手机号
	private String logo; // 图片地址
	private String status; // 类型
	private Integer facilitiesClassId; // 分类id
	private Integer createTime; //创建时间
	private Integer communityId; // 小区id
	private Float longitude; // 经度
	private Float latitude; // 维度
	private String businessStartTime; // 营业开始时间

	private String businessEndTime; // 营业结束时间
	
	private Integer adminId;
	
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	private String distance;
	
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilitiesDesc() {
		return facilitiesDesc;
	}

	public void setFacilitiesDesc(String facilitiesDesc) {
		this.facilitiesDesc = facilitiesDesc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFacilitiesClassId() {
		return facilitiesClassId;
	}

	public void setFacilitiesClassId(Integer facilitiesClassId) {
		this.facilitiesClassId = facilitiesClassId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getBusinessStartTime() {
		return businessStartTime;
	}

	public void setBusinessStartTime(String businessStartTime) {
		this.businessStartTime = businessStartTime;
	}

	public String getBusinessEndTime() {
		return businessEndTime;
	}

	public void setBusinessEndTime(String businessEndTime) {
		this.businessEndTime = businessEndTime;
	}

}
