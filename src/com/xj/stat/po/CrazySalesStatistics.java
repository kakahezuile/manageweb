package com.xj.stat.po;

public class CrazySalesStatistics {

	private String date;
	private Integer crazySalesStatisticsId;
	private Integer download;
	private Integer register;
	private Integer pub;
	private Double money;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCrazySalesStatisticsId() {
		return crazySalesStatisticsId;
	}

	public void setCrazySalesStatisticsId(Integer crazySalesStatisticsId) {
		this.crazySalesStatisticsId = crazySalesStatisticsId;
	}

	public Integer getDownload() {
		return download;
	}

	public void setDownload(Integer download) {
		this.download = download;
	}

	public Integer getRegister() {
		return register;
	}

	public void setRegister(Integer register) {
		this.register = register;
	}

	public Integer getPub() {
		return pub;
	}

	public void setPub(Integer pub) {
		this.pub = pub;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
}