package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 流水表
 * @author Administrator
 *
 */
public class XjBill {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer id;
	
	private Integer billId; // 流水id
	
	private String amt;
	
	private String billInfo;
	
	private String billUser; // 使用者
	
	private String billType;
	
	private String billChannel;
	
	private String billRet;
	
	private Integer billTime;
	
	private String tradeNo;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getBillTime() {
		return billTime;
	}

	public void setBillTime(Integer billTime) {
		this.billTime = billTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(String billInfo) {
		this.billInfo = billInfo;
	}

	public String getBillUser() {
		return billUser;
	}

	public void setBillUser(String billUser) {
		this.billUser = billUser;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillChannel() {
		return billChannel;
	}

	public void setBillChannel(String billChannel) {
		this.billChannel = billChannel;
	}

	public String getBillRet() {
		return billRet;
	}

	public void setBillRet(String billRet) {
		this.billRet = billRet;
	}
}
