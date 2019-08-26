package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class XjBankCard {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer bankCardId;
	
	private String cardNo;
	
	private String cardName;
	
	private String cardPhone;
	
	private String emobId;
	
	private Integer createTime;
	
	private Integer bankCategoryId;
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private String bankName;
	
	private Integer communityId;

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getBankCategoryId() {
		return bankCategoryId;
	}

	public void setBankCategoryId(Integer bankCategoryId) {
		this.bankCategoryId = bankCategoryId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Integer bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardPhone() {
		return cardPhone;
	}

	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}
}
