package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;

public class ShopsDeduction {
	
	private String months;
	
	private Integer count;
	@MyAnnotation
	private String startTime;
	@MyAnnotation
	private String endTime;
	
	@MyAnnotation
	private List<Deduction> list;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Deduction> getList() {
		return list;
	}

	public void setList(List<Deduction> list) {
		this.list = list;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
