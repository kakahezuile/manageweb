package com.xj.bean;

/**
 * 生活圈头部及内容列表模型
 */
public class LifeCircleBean {

	private String avatar; // 头像
	private String nickname; // 昵称
	private Integer characterValues; // 人品值
	private Double characterPercent; // 打败小区百分比
	private Page<LifeCircleVO> list;

	public Page<LifeCircleVO> getList() {
		return list;
	}

	public void setList(Page<LifeCircleVO> list) {
		this.list = list;
	}

	public Double getCharacterPercent() {
		return characterPercent;
	}

	public void setCharacterPercent(Double characterPercent) {
		this.characterPercent = characterPercent;
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
}
