package com.xj.stat.bean.parameter;

import com.xj.utils.DateUtils;

public class CurrentTimeRange {

	private Integer communityId;	// 小区id
	private Integer todayBegin;		// 今天开始时刻
	private Integer todayEnd;		// 今天结束时刻
	private Integer thisWeekBegin;	// 本周开始时刻
	private Integer thisWeekEnd;	// 本周结束时刻
	private Integer thisMonthBegin;	// 本月开始时刻
	private Integer thisMonthEnd;	// 本月结束时刻

	public CurrentTimeRange(Integer communityId) {
		this.communityId = communityId;
		
		this.todayBegin = Long.valueOf(DateUtils.getDayBegin().getTime() / 1000L).intValue();
		this.todayEnd = Long.valueOf(DateUtils.getDayEnd().getTime() / 1000L).intValue();
		this.thisWeekBegin = Long.valueOf(DateUtils.getWeekBegin().getTime() / 1000L).intValue();
		this.thisWeekEnd = Long.valueOf(DateUtils.getWeekEnd().getTime() / 1000L).intValue();
		this.thisMonthBegin = Long.valueOf(DateUtils.getMonthBegin().getTime() / 1000L).intValue();
		this.thisMonthEnd = Long.valueOf(DateUtils.getMonthEnd().getTime() / 1000L).intValue();
	}
	
	public CurrentTimeRange() {
		super();
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getTodayBegin() {
		return todayBegin;
	}

	public void setTodayBegin(Integer todayBegin) {
		this.todayBegin = todayBegin;
	}

	public Integer getTodayEnd() {
		return todayEnd;
	}

	public void setTodayEnd(Integer todayEnd) {
		this.todayEnd = todayEnd;
	}

	public Integer getThisWeekBegin() {
		return thisWeekBegin;
	}

	public void setThisWeekBegin(Integer thisWeekBegin) {
		this.thisWeekBegin = thisWeekBegin;
	}

	public Integer getThisWeekEnd() {
		return thisWeekEnd;
	}

	public void setThisWeekEnd(Integer thisWeekEnd) {
		this.thisWeekEnd = thisWeekEnd;
	}

	public Integer getThisMonthBegin() {
		return thisMonthBegin;
	}

	public void setThisMonthBegin(Integer thisMonthBegin) {
		this.thisMonthBegin = thisMonthBegin;
	}

	public Integer getThisMonthEnd() {
		return thisMonthEnd;
	}

	public void setThisMonthEnd(Integer thisMonthEnd) {
		this.thisMonthEnd = thisMonthEnd;
	}

}
