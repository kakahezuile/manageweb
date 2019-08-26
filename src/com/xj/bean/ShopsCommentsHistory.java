package com.xj.bean;



import com.xj.annotation.MyAnnotation;

public class ShopsCommentsHistory {
	
	private String shopName;
	
	private String emobId;
	
	private String score;
	
	private String logo;
	
	@MyAnnotation
	private Page<UserComments> list;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Page<UserComments> getList() {
		return list;
	}

	public void setList(Page<UserComments> list) {
		this.list = list;
	}


}
