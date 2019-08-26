package com.xj.bean;

public class PushStatistics implements Comparable<PushStatistics>{
	
	private Integer CMD_CODE;
	
	private String title;
	
	private String content;
	
	private Integer sum;
	
	private Integer unsum;
	
	private Long messageId;
	
	private String emobId;
	
	private String communityId;
	

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCMD_CODE() {
		return CMD_CODE;
	}

	public void setCMD_CODE(Integer cMD_CODE) {
		CMD_CODE = cMD_CODE;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getUnsum() {
		return unsum;
	}

	public void setUnsum(Integer unsum) {
		this.unsum = unsum;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Override
	public int compareTo(PushStatistics o) {
		return (int) (-this.getMessageId()+o.getMessageId());
	}
}
