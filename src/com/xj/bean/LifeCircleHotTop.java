package com.xj.bean;

import java.util.List;

public class LifeCircleHotTop {
	
	private Integer characterValues;
	
	private Integer top;
	
	private List<LifeCircleTopVO> list ;

	public Integer getCharacterValues() {
		return characterValues;
	}

	public void setCharacterValues(Integer characterValues) {
		this.characterValues = characterValues;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public List<LifeCircleTopVO> getList() {
		return list;
	}

	public void setList(List<LifeCircleTopVO> list) {
		this.list = list;
	}
}
