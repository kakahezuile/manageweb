package com.xj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

	public static final int SECOND_OF_DAY = 60 * 60 * 24;//一天的秒数
	public static final int MILLISECOND_OF_DAY = 1000 * 60 * 60 * 24;//一天的毫秒数
	
	/**
	 * 偏移类型：天
	 */
	public static final int OFFSET_DAY = 1;
	/**
	 * 偏移类型：周
	 */
	public static final int OFFSET_WEEK = 2;
	/**
	 * 偏移类型：月
	 */
	public static final int OFFSET_MONTH = 3;

	//SimpleDateFormat不是线程安全的,所以需要放在ThreadLocal中来保证安全性
	private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	private static final ThreadLocal<SimpleDateFormat> timeFormat = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * 将日期格式字符串转化成日期，比如：2015-12-02
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return dateFormat.get().parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将时间戳格式字符串转化成日期，比如：2015-12-02 15:56:23
	 * @param timeStamp
	 * @return
	 */
	public static Date parseTimeStamp(String timeStamp) {
		try {
			return timeFormat.get().parse(timeStamp);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (null == date) {
			throw new NullPointerException();
		}
		return dateFormat.get().format(date);
	}

	/**
	 * 格式化时间戳
	 * @param timeStamp
	 * @return
	 */
	public static String formatTimeStamp(Date timeStamp) {
		if (null == timeStamp) {
			throw new NullPointerException();
		}
		return timeFormat.get().format(timeStamp);
	}

	/**
	 * @param date 格式：yyyy-MM-dd 如 2014-03-18
	 * @return
	 *         0点-1点#1395072000#1395075600 1点-2点#1395075600#1395079200 .........
	 *         22点-23点#1395151200#1395154800 23点-24点#1395154800#1395158400
	 */
	public static List<String> get24Time(String date) throws Exception {
		if (StringUtil.isEmpty(date))
			return null;
		Calendar c = Calendar.getInstance();
		String[] ymd = date.split("-");
		if (ymd.length != 3)
			return null;

		c.set(Calendar.YEAR, Integer.parseInt(ymd[0]));
		c.set(Calendar.MONTH, Integer.parseInt(ymd[1]) - 1);
		c.set(Calendar.DATE, Integer.parseInt(ymd[2]));

		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		List<Long> sec = new ArrayList<Long>();

		for (int i = 0; i <= 24; i++) {
			c.set(Calendar.HOUR_OF_DAY, i);
			sec.add(c.getTimeInMillis() / 1000);
		}

		List<String> result = new ArrayList<String>();

		StringBuilder str = new StringBuilder();

		for (int i = 0; i < sec.size() - 1; i++) {
			str.append(i + "点-" + (i + 1) + "点");
			str.append("#");
			str.append(sec.get(i));
			str.append("#");
			str.append(sec.get(i + 1));
			result.add(str.toString());
			str.setLength(0);
		}

		return result;
	}

	public static int getcurrentdate() {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		return Integer.parseInt(y + "" + (m <= 9 ? "0" + m : m) + (d <= 9 ? "0" + d : d));
	}

	public static String currentDate() {
		return dateFormat.get().format(Calendar.getInstance().getTime());
	}

	public static String before5Date() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 4);
		
		return dateFormat.get().format(c.getTime());
	}

	/**
	 * 返回两个日期之前的,包括两个日期的集合,如果begin和end在同一天,将只返回改天
	 * @param begin
	 * @param end
	 * @return
	 */
	public static List<Date> findBetweenDates(Date begin, Date end) {
		if (begin.after(end)) {
			Date temp = begin;
			begin = end;
			end = temp;
		}
		
		List<Date> dates = new ArrayList<Date>();
		if (formatDate(begin).equals(formatDate(end))) {
			dates.add(end);
			return dates;
		}
		
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(begin);
		
		while (end.after(beginCalendar.getTime())) {
			dates.add(beginCalendar.getTime());
			
			beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
			
			if (formatDate(end).equals(formatDate(beginCalendar.getTime()))) {
				break;
			}
		}
		dates.add(end);
		
		return dates;
	}

	public static List<Date> findBetweenDates(String begin, String end) {
		if (StringUtil.isEmpty(begin) || StringUtil.isEmpty(end))
			return null;
		
		return findBetweenDates(parseDate(begin), parseDate(end));
	}

	public static List<Date> findBetweenDates(Long begin, Long end) {
		if (null == begin || null == end)
			return null;
		
		return findBetweenDates(new Date(begin), new Date(end));
	}

	public static List<String> getBetweenDates(Date begin, Date end) {
		if (begin.after(end)) {
			Date temp = begin;
			begin = end;
			end = temp;
		}
		
		List<String> dates = new ArrayList<String>();
		if (formatDate(begin).equals(formatDate(end))) {
			dates.add(formatDate(end));
			return dates;
		}
		
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(begin);
		
		while (end.after(beginCalendar.getTime())) {
			dates.add(formatDate(beginCalendar.getTime()));
			
			beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
			
			if (formatDate(end).equals(formatDate(beginCalendar.getTime()))) {
				break;
			}
		}
		dates.add(formatDate(end));
		
		return dates;
	}

	public static List<String> getBetweenDates(String begin, String end) {
		if (StringUtil.isEmpty(begin) || StringUtil.isEmpty(end))
			return null;
		
		return getBetweenDates(parseDate(begin), parseDate(end));
	}

	public static List<String> getBetweenDates(Long begin, Long end) {
		if (null == begin || null == end)
			return null;
		
		return getBetweenDates(new Date(begin), new Date(end));
	}

	public static Integer getThisMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 上周开始时间
	 * @return
	 */
	public static long getLastWeekStartTime() {
		Calendar lastWeek = Calendar.getInstance();//获得当前日期
		lastWeek.set(Calendar.WEEK_OF_MONTH, lastWeek.get(Calendar.WEEK_OF_MONTH) -1);
		if (lastWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			Long time = lastWeek.getTime().getTime() - 1000 * 60 * 60 * 24;
			lastWeek.setTimeInMillis(time);
		}
		
		lastWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		lastWeek.set(Calendar.HOUR_OF_DAY, 0);
		lastWeek.set(Calendar.MINUTE, 0);
		lastWeek.set(Calendar.SECOND, 0);
		lastWeek.set(Calendar.MILLISECOND, 0);
		
		return lastWeek.getTime().getTime();
	}
	
	/**
	 * 获取上月开始时间
	 * @return
	 */
	public static long getLastMonthStartTime() {
		Calendar lastMonth = Calendar.getInstance();
		lastMonth.set(Calendar.MONTH, lastMonth.get(Calendar.MONTH) - 1);
		lastMonth.set(Calendar.DAY_OF_MONTH, 1);
		lastMonth.set(Calendar.HOUR_OF_DAY, 0);
		lastMonth.set(Calendar.MINUTE, 0);
		lastMonth.set(Calendar.SECOND, 0);
		lastMonth.set(Calendar.MILLISECOND, 0);
		
		return lastMonth.getTime().getTime();
	}
	
	/**
	 * 获取指定日期的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		
		return day.getTime();
	}
	
	/**
	 * 获取指定日期的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(String date) {
		return getDayBegin(parseDate(date));
	}
	
	/**
	 * 获取指定日期的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Long date) {
		return getDayBegin(new Date(date));
	}

	/**
	 * 获取今天的开始时刻
	 * @return
	 */
	public static Date getDayBegin() {
		return getDayBegin(new Date());
	}
	
	/**
	 * 获取指定日期的结束时刻
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		
		return day.getTime();
	}
	
	/**
	 * 获取指定日期的结束时刻
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(String date) {
		return getDayEnd(parseDate(date));
	}
	
	/**
	 * 获取指定日期的结束时刻
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Long date) {
		return getDayEnd(new Date(date));
	}
	
	/**
	 * 获取今天的结束时刻
	 * @return
	 */
	public static Date getDayEnd() {
		return getDayEnd(new Date());
	}

	/**
	 * 获取昨天开始毫秒数
	 * @return
	 */
	public static long getYesterdayStartTime() {
		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) -1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MILLISECOND, 0);
		
		return yesterday.getTime().getTime();
	}
	
	/**
	 * 获取指定日期所在周的周一开始时刻
	 * @param date
	 * @return
	 */
	public static Date getWeekBegin(Date date) {
		Calendar week = Calendar.getInstance();
		week.setTime(date);
		
		if (week.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {//周日是一周的开始,所以要将周日的时间设置成周六的,然后再计算周一
			Long time = week.getTime().getTime() - 1000 * 60 * 60 * 24;
			week.setTimeInMillis(time);
		}
		
		week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		week.set(Calendar.HOUR_OF_DAY, 0);
		week.set(Calendar.MINUTE, 0);
		week.set(Calendar.SECOND, 0);
		week.set(Calendar.MILLISECOND, 0);
		
		return week.getTime();
	}
	
	/**
	 * 获取指定日期所在周的周一开始时刻
	 * @param date
	 * @return
	 */
	public static Date getWeekBegin(String date) {
		return getWeekBegin(parseDate(date));
	}
	
	/**
	 * 获取指定日期所在周的周一开始时刻
	 * @param date
	 * @return
	 */
	public static Date getWeekBegin(Long date) {
		return getWeekBegin(new Date(date));
	}
	
	/**
	 * 获取今天所在周的周一开始时刻
	 * @return
	 */
	public static Date getWeekBegin() {
		return getWeekBegin(new Date());
	}
	
	/**
	 * 获取指定日期所在周的周日结束时刻
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(Date date) {
		Calendar week = Calendar.getInstance();
		week.setTime(date);
		
		if (week.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return getDayEnd(date);
		}
		
		week.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		week.set(Calendar.HOUR_OF_DAY, 23);
		week.set(Calendar.MINUTE, 59);
		week.set(Calendar.SECOND, 59);
		week.set(Calendar.MILLISECOND, 999);
		week.setTimeInMillis(week.getTimeInMillis() + 1000 * 60 * 60 * 24);
		
		return week.getTime();
	}
	
	/**
	 * 获取指定日期所在周的周日结束时刻
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(String date) {
		return getWeekEnd(parseDate(date));
	}
	
	/**
	 * 获取指定日期所在周的周日结束时刻
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(Long date) {
		return getWeekEnd(new Date(date));
	}
	
	/**
	 * 获取今天所在周的周日结束时刻
	 * @return
	 */
	public static Date getWeekEnd() {
		return getWeekEnd(new Date());
	}
	
	/**
	 * 获取指定日期所在月的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(Date date) {
		Calendar month = Calendar.getInstance();
		month.setTime(date);
		
		month.set(Calendar.DAY_OF_MONTH, 1);
		month.set(Calendar.HOUR_OF_DAY, 0);
		month.set(Calendar.MINUTE, 0);
		month.set(Calendar.SECOND, 0);
		month.set(Calendar.MILLISECOND, 0);
		
		return month.getTime();
	}
	
	/**
	 * 获取指定日期所在月的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(String date) {
		return getMonthBegin(parseDate(date));
	}
	
	/**
	 * 获取指定日期所在月的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(Long date) {
		return getMonthBegin(new Date(date));
	}
	
	/**
	 * 获取今天所在月的开始时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin() {
		return getMonthBegin(new Date());
	}
	
	/**
	 * 获取指定日期所在月的最后时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar month = Calendar.getInstance();
		month.setTime(date);
		month.add(Calendar.MONTH, 1);
		month.set(Calendar.DAY_OF_MONTH, 1);
		month.set(Calendar.HOUR_OF_DAY, 0);
		month.set(Calendar.MINUTE, 0);
		month.set(Calendar.SECOND, 0);
		month.set(Calendar.MILLISECOND, 0);
		month.setTimeInMillis(month.getTimeInMillis() - 1);
		
		return month.getTime();
	}
	
	/**
	 * 获取指定日期所在月的最后时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(String date) {
		return getMonthEnd(parseDate(date));
	}
	
	/**
	 * 获取指定日期所在月的最后时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Long date) {
		return getMonthEnd(new Date(date));
	}
	
	/**
	 * 获取今天所在月的最后时刻
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd() {
		return getMonthEnd(new Date());
	}

	/**
	 * 获取相对于今天的偏移一定量的时间后的日期
	 * @param amount
	 * @param type
	 * 	@see com.xj.utils.DateUtils#OFFSET_DAY
	 * 	@see com.xj.utils.DateUtils#OFFSET_WEEK
	 * 	@see com.xj.utils.DateUtils#OFFSET_MONTH
	 * @return
	 */
	public static Date getOffsetDate(int amount, int type) {
		Calendar today = Calendar.getInstance();
		
		if (OFFSET_DAY == type) {
			today.add(Calendar.DATE, amount);
		} else if (OFFSET_WEEK == type) {
			today.add(Calendar.WEEK_OF_MONTH, amount);
		} else if (OFFSET_MONTH == type) {
			today.add(Calendar.MONTH, amount);
		}
		
		return today.getTime();
	}
}