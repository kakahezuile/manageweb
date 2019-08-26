package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 结款表
 * @author Administrator
 *
 */
public class XjAccounter {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer accounterId;
	
	private String emobId;
	
	private Integer accounterMonth;
	
	private Integer accounterDate;
	
	private String accounterPrice;
	
	private String status = "ended";


	public Integer getAccounterId() {
		return accounterId;
	}

	public void setAccounterId(Integer accounterId) {
		this.accounterId = accounterId;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Integer getAccounterMonth() {
		return accounterMonth;
	}

	public void setAccounterMonth(Integer accounterMonth) {
		this.accounterMonth = accounterMonth;
	}

	public Integer getAccounterDate() {
		return accounterDate;
	}

	public void setAccounterDate(Integer accounterDate) {
		this.accounterDate = accounterDate;
	}

	public String getAccounterPrice() {
		return accounterPrice;
	}

	public void setAccounterPrice(String accounterPrice) {
		this.accounterPrice = accounterPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}}
