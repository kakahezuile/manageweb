package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class XjTest {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer id;
	
	private String strOne;
	
	private String strTwo;
	
	private Integer createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStrOne() {
		return strOne;
	}

	public void setStrOne(String strOne) {
		this.strOne = strOne;
	}

	public String getStrTwo() {
		return strTwo;
	}

	public void setStrTwo(String strTwo) {
		this.strTwo = strTwo;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	
	
}
