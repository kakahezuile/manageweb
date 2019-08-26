package com.xj.bean;

/**
 * 商家端看到的我的评价
 * @author Administrator
 *
 */
public class ShopsComments {
	private String shopName; // 商家名称
	
	private Integer shopId; // 商家id
	
	private String avatar; // 商家头像
	
	private Integer positiveComment; // 好评数
	
	private Integer moderateComment; // 中评数
	
	private Integer negtiveComment; // 差评数
	
	private String score;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getPositiveComment() {
		return positiveComment;
	}

	public void setPositiveComment(Integer positiveComment) {
		this.positiveComment = positiveComment;
	}

	public Integer getModerateComment() {
		return moderateComment;
	}

	public void setModerateComment(Integer moderateComment) {
		this.moderateComment = moderateComment;
	}

	public Integer getNegtiveComment() {
		return negtiveComment;
	}

	public void setNegtiveComment(Integer negtiveComment) {
		this.negtiveComment = negtiveComment;
	}

	
}
