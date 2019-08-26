package com.xj.bean;

import java.util.List;

/**
 * 点赞列表
 * @author Administrator
 *
 */
public class SinglePraise {
	
	private String avatar;
	
	private String nickname;
	
	private Integer characterValues;
	
	private Double characterPercent;
	
	private List<LifePraiseContent> list;

	public List<LifePraiseContent> getList() {
		return list;
	}

	public void setList(List<LifePraiseContent> list) {
		this.list = list;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
