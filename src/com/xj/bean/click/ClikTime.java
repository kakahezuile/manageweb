package com.xj.bean.click;

import java.util.List;

public class ClikTime {
	
	private ClickDate _id;
	private List<ClickDetail> info;
	public ClickDate get_id() {
		return _id;
	}
	public void set_id(ClickDate _id) {
		this._id = _id;
	}
	public List<ClickDetail> getInfo() {
		return info;
	}
	public void setInfo(List<ClickDetail> info) {
		this.info = info;
	}
	
}
