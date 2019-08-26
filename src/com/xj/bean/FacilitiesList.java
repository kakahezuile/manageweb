package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;

public class FacilitiesList {
	@NotInsertAnnotation
    private Long facilityId;
    private String facilityName;
    private String facilitiesDesc;
    private String address;
    private String phone;
    private String logo;
    private String status;
    private Integer facilitiesClassId;
    private Integer createTime;
    private Integer communityId;
    private Float longitude;
    private Float latitude;
    private String typeName;
    private String businessStartTime; // 营业开始时间
    
    private String businessEndTime; // 营业结束时间
    
    private Integer adminId;
    
    private String distance;
  
    
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Long getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
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
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
