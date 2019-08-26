package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class SensitiveWords {
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	private Integer sensitiveWordsId;
	
	private String sensitiveWords;
	
	private Integer createTime;
	
	private Integer updateTime;

	public Integer getSensitiveWordsId() {
		return sensitiveWordsId;
	}

	public void setSensitiveWordsId(Integer sensitiveWordsId) {
		this.sensitiveWordsId = sensitiveWordsId;
	}

	public String getSensitiveWords() {
		return sensitiveWords;
	}

	public void setSensitiveWords(String sensitiveWords) {
		this.sensitiveWords = sensitiveWords;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
}
