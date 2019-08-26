package com.xj.bean;


import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 小区表
 * @author Administrator
 *
 */
public class Communities {
	@NotInsertAnnotation

    private Integer communityId;  // 小区id
    private String communityName; // 小区名称
    private Integer population;  
    private String communitiesDesc;
    private Float longitude; // 经度
    private Float latitude; // 维度
    private String shopServices;
    private String facilityServices;
    private Integer cityId;
    private Integer createTime;
    @NotInsertAnnotation
    private String status;
    
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private String appVersion;
    
    private String communityAddress;
    
	public String getCommunityAddress() {
		return communityAddress;
	}
	public void setCommunityAddress(String communityAddress) {
		this.communityAddress = communityAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer population) {
		this.population = population;
	}
	
	
	public String getCommunitiesDesc() {
		return communitiesDesc;
	}
	public void setCommunitiesDesc(String communitiesDesc) {
		this.communitiesDesc = communitiesDesc;
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
	public String getShopServices() {
		return shopServices;
	}
	public void setShopServices(String shopServices) {
		this.shopServices = shopServices;
	}
	public String getFacilityServices() {
		return facilityServices;
	}
	public void setFacilityServices(String facilityServices) {
		this.facilityServices = facilityServices;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
}
