package com.xj.stat.bean.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeriodModuleClick {

	private String begin;
	private String end;
	private String totalCount ;
	private List<ModuleClick> modules;  //多模块
	private Map<String,ModuleClick> map;
	
	
	public Map<String, ModuleClick> getMap() {
		return map;
	}

	public void setMap(Map<String, ModuleClick> map) {
		this.map = map;
	}

	public PeriodModuleClick() {
	}
	
	public PeriodModuleClick(String begin, String end, List<ModuleClick> modules) {
		this.begin = begin;
		this.end = end;
		this.setModules(modules);
	}
	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public List<ModuleClick> getModules() {
		return modules;
	}

	public void setModules(List<ModuleClick> modules) {
		this.modules = modules;
	}

	public void addModule(ModuleClick moduleClick) {
		if (null == modules) {
			modules = new ArrayList<ModuleClick>();
		}
		
		modules.add(moduleClick);
	}
}