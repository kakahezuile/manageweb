package com.xj.bean;

public class AlipayRequest {
	
	private String subject;
	
	private String body;
	
	private String price;
	
	private Integer userBonusId;
	
	private String orderId;
	
	private String conversationId;
	
	private String messageId;
	
	private String bonusPrice;
	
	private String realPrice;
	
	private String address;

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getBonusPrice() {
		return bonusPrice;
	}

	public void setBonusPrice(String bonusPrice) {
		this.bonusPrice = bonusPrice;
	}

	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

		

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserBonusId() {
		return userBonusId;
	}

	public void setUserBonusId(Integer userBonusId) {
		this.userBonusId = userBonusId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
