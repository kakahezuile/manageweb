package com.xj.bean;

import java.util.List;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 通知内容集合表
 * @author Administrator
 *
 */
public class Bulletin {
	@NotInsertAnnotation
	private Integer id;
	
	private String bulletinText;
	
	private Integer createTime;
	
	private Integer communityId;
	
	private Integer adminId;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private ExtNode ext;

	private String theme;
	
	private String senderObject;
	
	private Long messageId; // 消息id
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer CMD_CODE;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private List<String> target;
	
	
	
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public ExtNode getExt() {
		return ext;
	}

	public void setExt(ExtNode ext) {
		this.ext = ext;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBulletinText() {
		return bulletinText;
	}

	public void setBulletinText(String bulletinText) {
		this.bulletinText = bulletinText;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getSenderObject() {
		return senderObject;
	}

	public void setSenderObject(String senderObject) {
		this.senderObject = senderObject;
	}

	public Integer getCMD_CODE() {
		return CMD_CODE;
	}

	public void setCMD_CODE(Integer cMD_CODE) {
		CMD_CODE = cMD_CODE;
	}

	public List<String> getTarget() {
		return target;
	}

	public void setTarget(List<String> target) {
		this.target = target;
	}
}
