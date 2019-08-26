package com.xj.bean;

/**
 * 消息统计
 * @author Administrator
 *
 */
public class StatPushMessageBean {
	private String time;  //消息发送时间
	private String context; //消息主题
	
	private int  pushNum;  //推送数量
	private int successNum; //推送成功的数量
	private int failNum;//推送失败的数量
	private String useTime;//耗时
	private long  messageId; //消息的id（当前时间毫秒值）
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getPushNum() {
		return pushNum;
	}
	public void setPushNum(int pushNum) {
		this.pushNum = pushNum;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	
}
