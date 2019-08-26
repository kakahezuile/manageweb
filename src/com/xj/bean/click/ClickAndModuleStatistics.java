package com.xj.bean.click;

/**
 * 包含模块点击量和模块自身业务的统计结果
 */
public class ClickAndModuleStatistics {

	private Object click;
	private Object module;

	public Object getClick() {
		return click;
	}

	public void setClick(Object click) {
		this.click = click;
	}

	public Object getModule() {
		return module;
	}

	public void setModule(Object module) {
		this.module = module;
	}
}