package com.xj.bean;

import java.io.Serializable;

/**
 
 * @author dongquan
 *
 */
public class TableDesc implements Serializable{
	
	private static final long serialVersionUID = 3314864554929370984L;

	private String field; //字段名称
	private String type; //对应的java类型
	private boolean allowNull;// 允许为空
	private Key key; // 键值类型
	private String defaultValue;
	private boolean autoIncrement; //是否自动递增
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAllowNull() {
		return allowNull;
	}
	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
