package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 用户帮帮卷对象
 * @author Administrator
 *
 */
public class BonusAndService {
	
	private Integer userBonusId;
	
	private Integer bonusId;
	
	private Integer startTime;
	
	private Integer expireTime;
	
	private String bonusPar;
	
	private Integer useTime;
	
	private Integer orderId;
	
	private String bonusStatus;
	
	private String bonusUrl;
	
	private String bonusDetail;
	
	private String bonusName;
	
	private String bonusR;
	
	private String bonusG;
	
	private String bonusB;
	
	private Integer bonusType;
	
	public Integer getBonusType() {
		return bonusType;
	}

	public void setBonusType(Integer bonusType) {
		this.bonusType = bonusType;
	}

	public String getBonusR() {
		return bonusR;
	}

	public void setBonusR(String bonusR) {
		this.bonusR = bonusR;
	}

	public String getBonusG() {
		return bonusG;
	}

	public void setBonusG(String bonusG) {
		this.bonusG = bonusG;
	}

	public String getBonusB() {
		return bonusB;
	}

	public void setBonusB(String bonusB) {
		this.bonusB = bonusB;
	}

	@MyAnnotation
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private List<BonusServiceAndName> list;

	public List<BonusServiceAndName> getList() {
		return list;
	}

	public void setList(List<BonusServiceAndName> list) {
		this.list = list;
	}

	public Integer getUserBonusId() {
		return userBonusId;
	}

	public void setUserBonusId(Integer userBonusId) {
		this.userBonusId = userBonusId;
	}

	public Integer getBonusId() {
		return bonusId;
	}

	public void setBonusId(Integer bonusId) {
		this.bonusId = bonusId;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public String getBonusPar() {
		return bonusPar;
	}

	public void setBonusPar(String bonusPar) {
		this.bonusPar = bonusPar;
	}

	public Integer getUseTime() {
		return useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	

	public String getBonusStatus() {
		return bonusStatus;
	}

	public void setBonusStatus(String bonusStatus) {
		this.bonusStatus = bonusStatus;
	}

	public String getBonusUrl() {
		return bonusUrl;
	}

	public void setBonusUrl(String bonusUrl) {
		this.bonusUrl = bonusUrl;
	}

	public String getBonusDetail() {
		return bonusDetail;
	}

	public void setBonusDetail(String bonusDetail) {
		this.bonusDetail = bonusDetail;
	}

	public String getBonusName() {
		return bonusName;
	}

	public void setBonusName(String bonusName) {
		this.bonusName = bonusName;
	}
	
	
}
