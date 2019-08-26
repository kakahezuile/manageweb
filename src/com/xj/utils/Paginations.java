package com.xj.utils;

import java.util.List;

@SuppressWarnings("rawtypes")
public class Paginations {

	// 缺省每页显示的记录数
	public static final int DEFAULT_PAGESIZE = 8;
	private Integer nowPage;//当前页
	private Integer pages;//总页数
	private Integer from;//开始记录
	private Integer to;//结束记录
	private Integer pageSize;//每页显示的记录信息
	private Integer total;//总记录
	private List rows;//当前页信息
	private int endMark=0;
	
	// 封装分页信息操作的构造器 page代表当前页,total 代表总记录数 ,pageSize代表每页显示的记录数
	public Paginations(Integer nowPage, Integer total, int pageSize) {
		super();
		// 总记录数
		if (total < 0) {
			this.total = 0;
		} else {
			this.total = total;
		}
		// 每页显示的记录数
		if (pageSize < 1) {
			this.pageSize = DEFAULT_PAGESIZE;
		} else {
			this.pageSize = pageSize;
		}
		// 计算总页数
		this.pages = this.total % this.pageSize == 0 ? this.total/ this.pageSize : this.total / this.pageSize + 1;
		// 获取当前页
		if (nowPage <= 0) {
			this.nowPage = 1;
		} else if (nowPage == this.pages) {
			this.nowPage = this.pages;
		} else if(nowPage > this.pages){
			this.nowPage = this.pages;
			this.endMark++;
		} else {
			this.nowPage = nowPage;
		}
		
		if(this.endMark!=0){
			this.from = 0;
			this.to = 0;
		}else{
			// 计算出开始记录数
			if(total>0){
				this.from = (this.nowPage - 1) * this.pageSize;
			}else{
				this.from = 0;
			}
			// 结束记录数
			if(total<pageSize){
				this.to = total;
			}else{
				this.to = this.pageSize;
			}
		}
	}

	public Integer getNowPage() {
		return nowPage;
	}

	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
