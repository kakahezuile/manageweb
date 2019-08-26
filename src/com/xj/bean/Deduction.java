package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 扣款表
 * @author Administrator
 *
 */
public class Deduction {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer deductionId; // 主键
	
	private Integer startTime; // 受理时间
	
	private Integer endTime; // 受理结束时间
	
	private String status; //  状态 -   ongoing - 受理中   ended - 已扣款
	
	private String deductionDetail; // 扣款理由
	
	private String emobIdCustomer; // 客服emobId
	
	private String emobIdAgent; // 管理员emobId
	
	private Integer complaintId; // 投诉唯一id
	
	private String deductionPrice; // 扣款金额

	public Integer getDeductionId() {
		return deductionId;
	}

	public void setDeductionId(Integer deductionId) {
		this.deductionId = deductionId;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeductionDetail() {
		return deductionDetail;
	}

	public void setDeductionDetail(String deductionDetail) {
		this.deductionDetail = deductionDetail;
	}

	public String getEmobIdCustomer() {
		return emobIdCustomer;
	}

	public void setEmobIdCustomer(String emobIdCustomer) {
		this.emobIdCustomer = emobIdCustomer;
	}

	public String getEmobIdAgent() {
		return emobIdAgent;
	}

	public void setEmobIdAgent(String emobIdAgent) {
		this.emobIdAgent = emobIdAgent;
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintsId) {
		this.complaintId = complaintsId;
	}

	public String getDeductionPrice() {
		return deductionPrice;
	}

	public void setDeductionPrice(String deductionPrice) {
		this.deductionPrice = deductionPrice;
	}
}
