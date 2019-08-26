package com.xj.bean;

import java.util.List;

public class StandardEntryInApp {
	
	private String unitPrice;
	
	private List<StandardEntry> list;

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public List<StandardEntry> getList() {
		return list;
	}

	public void setList(List<StandardEntry> list) {
		this.list = list;
	}
}
