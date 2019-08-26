package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class BankCategory {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer bankCategoryId;
	
	private String bankName;
	
	private String bankCate;

	public Integer getBankCategoryId() {
		return bankCategoryId;
	}

	public void setBankCategoryId(Integer bankCategoryId) {
		this.bankCategoryId = bankCategoryId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCate() {
		return bankCate;
	}

	public void setBankCate(String bankCate) {
		this.bankCate = bankCate;
	}

}
