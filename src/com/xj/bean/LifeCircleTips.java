package com.xj.bean;

import java.util.List;


/**
 * 生活圈头部
 * @author Administrator
 *
 */
public class LifeCircleTips {
	
	private String nickname; // 昵称
	
	private String avatar; // 头像
	
	private Integer characterValues; // 人品值
	
	private Double characterPercent; // 打败百分比
	
	private List<NewTips> list;
	
	private Integer time;
	
	private String emobId;

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public List<NewTips> getList() {
		return list;
	}

	public void setList(List<NewTips> list) {
		this.list = list;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getCharacterValues() {
		return characterValues;
	}

	public void setCharacterValues(Integer characterValues) {
		this.characterValues = characterValues;
	}

	public Double getCharacterPercent() {
		return characterPercent;
	}

	public void setCharacterPercent(Double characterPercent) {
		this.characterPercent = characterPercent;
	}
	
}
