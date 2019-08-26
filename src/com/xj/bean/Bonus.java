package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 帮帮券表
 * @author Administrator
 *
 */
public class Bonus {
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer bonusId;
	
	private String bonusName; // 帮帮券名字
	
	private Integer bonusType; // 帮帮券类型
	
	private Integer createTime; // 创建时间
	
	private String bonusPar; // 金额
	
	private String bonusUrl; // 图片地址
	
	
	@NotUpdataAnnotation
	@NotInsertAnnotation
	@MyAnnotation
	private String serviceId;
	
	private String bonusDetail; // 备注

	private String bonusR;
	
	private String bonusG;
	
	private String bonusB;
	
	public String getBonusDetail() {
		return bonusDetail;
	}

	public void setBonusDetail(String bonusDetail) {
		this.bonusDetail = bonusDetail;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getBonusId() {
		return bonusId;
	}

	public void setBonusId(Integer bonusId) {
		this.bonusId = bonusId;
	}

	public String getBonusName() {
		return bonusName;
	}

	public void setBonusName(String bonusName) {
		this.bonusName = bonusName;
	}

	public Integer getBonusType() {
		return bonusType;
	}

	public void setBonusType(Integer bonusType) {
		this.bonusType = bonusType;
	}



	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getBonusPar() {
		return bonusPar;
	}

	public void setBonusPar(String bonusPar) {
		this.bonusPar = bonusPar;
	}

	public String getBonusUrl() {
		return bonusUrl;
	}

	public void setBonusUrl(String bonusUrl) {
		this.bonusUrl = bonusUrl;
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
	
}
