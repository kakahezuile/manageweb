

package com.xj.bean;

import java.io.Serializable;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;



/**
 * 店家表
 */
public class Shops implements Serializable {
	@NotInsertAnnotation
	@MyAnnotation
	private static final long serialVersionUID = 1L;
	@NotUpdataAnnotation
	private Long shopId;
    private String shopName; // 店家名称

    private String shopsDesc;// 店家明细
    private String address; // 地址
    private Integer communityId; // 小区id
    private String emobId; // 环信id
    private String phone; // 电话
    private String logo; // 店家图片
    private String status; // 店家状态
    private String sort;  // 店家类型
    private Long createTime; // 创建时间
    private String authCode; // f码
    
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private Integer score;
    
    private String businessStartTime; // 营业开始时间
    
    private String businessEndTime; // 营业结束时间
  
    @NotUpdataAnnotation
    @NotInsertAnnotation
    private Integer orderSum;
    
    @MyAnnotation
    @NotInsertAnnotation
    @NotUpdataAnnotation
    private Users user;
    
    
	private Float longitude;//经度
	private Float latitude;//纬度
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(Integer orderSum) {
		this.orderSum = orderSum;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
	public String getShopsDesc() {
		return shopsDesc;
	}

	public void setShopsDesc(String shopsDesc) {
		this.shopsDesc = shopsDesc;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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
	
}

