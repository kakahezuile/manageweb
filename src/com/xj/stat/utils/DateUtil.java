package com.xj.stat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.xj.utils.DateUtils;

public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	//FIXME SimpleDateFormat不是线程安全的，这样使用不正确
	public static SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd HH:mm");
	public static SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat dateformatDate = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateformatDateTime = new SimpleDateFormat("yy-MM-dd HH:mm");
	public static SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

	/**
	 * 按照一定格式将字符串转换成Date类型
	 * 
	 * @param date
	 *            待转字符串
	 * @param format
	 *            格式
	 * @return Date
	 */
	public static Date getDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = df.parse(date);
		} catch (Exception e) {
			logger.error("getDate(String, String)", e); //$NON-NLS-1$

			logger.error("wrong occured");
		}
		return d;
	}

	/**
	 * 按指定格式取得当前时间字符串
	 * 
	 * @param format
	 *            格式化样式 如：yyyyMMdd
	 * @return String
	 */
	public static String getDate(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 按指定格式把DATE类转化成字符串
	 */
	public static String getDateStr(Date d, String format) {
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		return formatDate.format(d);
	}

	/**
	 * 取得当前时间,格式：HH:mm
	 * 
	 * @return
	 */
	public static String getTime() {
		String time = dateformat1.format(new Date());
		return time.substring(11);
	}

	/**
	 * 取得当前时间,格式：yy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getDate2() {
		String time = dateformat1.format(new Date());
		return time.substring(11);
	}

	/**
	 * 取得当前的时间，格式为：yy-MM-dd HH:mm
	 *
	 * @return
	 */
	public static String getDateTime() {
		String time = dateformatDateTime.format(new Date());
		return time;
	}

	/**
	 * 取得当前日期,格式为：yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getDate() {
		String time = dateformatDate.format(new Date());
		return time;
	}

	/**
	 * 取得指定日期,格式为：yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getDate(Date date) {
		String time = dateformatDate.format(date);
		return time;
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * 取得当前的月份和时间
	 *
	 * @param d
	 * @return
	 */
	public static String getTime(Date d) {
		String time = dateformat.format(d);
		return time;
	}

	/**
	 * 取得当月的最后一天
	 *
	 * @param in_date
	 * @return
	 */
	public static String getLastDayinMonth(String in_date) {
		StringBuffer sb = new StringBuffer();
		int year = Integer.parseInt(in_date.substring(0, 4));
		int month = Integer.parseInt(in_date.substring(5, 7));
		int day = getLastDayinMonth(year, month);
		sb.append(year + "-");
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month + "-");
		if (day < 10) {
			sb.append("0");
		}
		sb.append(day);
		return sb.toString();
	}

	/**
	 * 取得本月的每一天
	 *
	 * @param in_date
	 * @return
	 */
	public static String getFirstDayinMonth(String in_date) {
		StringBuffer sb = new StringBuffer();

		sb.append(in_date.substring(0, 8));
		sb.append("01");
		return sb.toString();
	}

	/**
	 * 取得当月的最后一天
	 *
	 * @param in_year
	 * @param in_month
	 * @return
	 */
	// -------------------------------------------------------------------------------------------------------------
	public static int getLastDayinMonth(int in_year, int in_month) {
		Calendar cal = Calendar.getInstance();

		cal.set(in_year, in_month, 1);
		return (cal.getActualMaximum(Calendar.DATE));
	}

	/**
	 * 取得本周的第一天
	 *
	 * @return
	 */
	public static String getWeekFirst() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		cal.add(Calendar.DATE, -day_of_week);
		return dateformatDate.format(cal.getTime());
	}

	/**
	 * 取得本周的最后一天
	 *
	 * @return
	 */
	public static String getWeekLast() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		cal.add(Calendar.DATE, -day_of_week);
		cal.add(Calendar.DATE, 6);

		return dateformatDate.format(cal.getTime());
	}

	/**
	 * 取得明天
	 *
	 * @return
	 */
	public static String getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return dateformatDate.format(cal.getTime());
	}

	/**
	 * 取得几天前日期
	 *
	 * @param day
	 *            几天前日期参数
	 * @param format
	 *            格式化样式
	 * @return String
	 */
	public static String getBeforeDay(int day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -day);
		SimpleDateFormat formatTime = new SimpleDateFormat(format);
		return formatTime.format(cal.getTime());
	}

	/**
	 * 获取几天后的日期
	 *
	 * @param day
	 * @return
	 */
	public static Date getAfterDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * @param date1
	 *            需要比较的时间 不能为空(null),需要正确的日期格式
	 * @param date2
	 *            被比较的时间 为空(null)则为当前时间
	 * @param stype
	 *            返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return
	 */
	public static int compareDate(Date date1, Date date2, int stype) {
		int n = 0;
		String date1Str = dateformatDate.format(date1);
		String date2Str = dateformatDate.format(date2);
		// String[] u = { "��", "��", "��" };
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1Str));
			c2.setTime(df.parse(date2Str));
		} catch (Exception e3) {
			logger.error("wrong occured");
		}
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}
		n = n - 1;
		if (stype == 2) {
			n = (int) n / 365;
		}
		if (n == 0) {
			n = 1;
		}
		// System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
		return n;
	}

	/**
	 * 时间日期处理
	 *
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		if (date != null) {
			DateFormat fd = new SimpleDateFormat("MM-dd HH:mm");
			return fd.format(date);
		}
		return "";
	}

	/**
	 * 比较两个字符串型的时间，是否相等
	 *
	 */
	public static int compareDate(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (Exception e3) {
			logger.error("wrong occured");
		}
		int i = c1.compareTo(c2);
		// System.out.println(date1+" -- "+date2+" ������"+u[stype]+":"+n);
		return i;
	}

	/**
	 * 比较两个日期，返回相差分钟数 第二个参数比第一个参数大 则返回正数值
	 */
	public static int compareDate(Date date1, Date date2) {
		int i = 0;
		if (date1 == null || date2 == null) {
			return i;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1Str = df.format(date1);
		String date2Str = df.format(date2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1Str));
			c2.setTime(df.parse(date2Str));
		} catch (Exception e3) {
			logger.error("wrong occured");
		}
		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();
		i = Integer.parseInt((l2 - l1) / 1000 / 60 + "");

		return i;
	}

	/**
	 * 比较两个日期，返回相差分钟数 第二个参数比第一个参数大 则返回正数值
	 */
	public static int compareDate2(Date date1, Date date2) {
		int i = 0;
		if (date1 == null || date2 == null) {
			return i;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date1Str = df.format(date1);
		String date2Str = df.format(date2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1Str));
			c2.setTime(df.parse(date2Str));
		} catch (Exception e3) {
			logger.error("wrong occured");
		}
		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();
		i = Integer.parseInt((l2 - l1) / 1000 / 60 + "");

		return i;
	}

	/**
	 * 获取指定时间距离当前时间的天数
	 *
	 * @param date
	 * @return
	 */
	public static int getNowSpace(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1Str = df.format(new Date());
		String date2Str = df.format(date);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1Str));
			c2.setTime(df.parse(date2Str));
		} catch (Exception e3) {
			logger.error("wrong occured");
		}
		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();
		int day = Integer.parseInt((l2 - l1) / 1000 / 60 / 60 / 24 + "");

		return day;
	}

	/**
	 * 返回yyyyMMddHHmmss型字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getLongString(Date date) {
		String datestr = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		datestr = df.format(date);
		return datestr;
	}

	/**
	 * 比较与当前日期，返回相差小时/分/秒
	 *
	 * @param stype
	 *            返回值类型 0为多少小时，1为多少个分，2为多少秒
	 */
	public static long compareDate(String time, int stype) {
		long difference = 0;
		Date d = getDate(time, "yyyy-MM-dd HH:mm:ss");
		long s = d.getTime();
		long s1 = new Date().getTime();
		if (s >= s1)
			return difference;
		difference = (s1 - s) / 1000;
		switch (stype) {
		case 0:
			difference = difference / 60 / 60;
			break;
		case 1:
			difference = difference / 60;
			break;
		default:
			break;
		}
		return difference;
	}

	/**
	 * 比较两个字符串日期，返回相差小时/分/秒
	 *
	 * @param stype
	 *            返回值类型 0为多少小时，1为多少个分，2为多少秒
	 */
	public static long compareDate(String beginTime, String endTime, int stype) {
		long difference = 0;
		Date d = getDate(beginTime, "yyyy-MM-dd HH:mm:ss");
		Date d1 = getDate(endTime, "yyyy-MM-dd HH:mm:ss");
		long s = d.getTime();
		long s1 = d1.getTime();
		if (s >= s1)
			return difference;
		difference = (s1 - s) / 1000;
		switch (stype) {
		case 0:
			difference = difference / 60 / 60;
			break;
		case 1:
			difference = difference / 60;
			break;
		default:
			break;
		}
		return difference;
	}

	// YYYYMMDD HHMMSS
	public static Date stringToDate(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String day = str.substring(6, 8);
		String hour = str.substring(9, 11);
		String min = str.substring(11, 13);
		String sec = str.substring(13, 15);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
		cal.set(Calendar.MINUTE, Integer.parseInt(min));
		cal.set(Calendar.SECOND, Integer.parseInt(sec));
		return cal.getTime();
	}

	// YYYYMMDDHHMMSS
	public static Date stringToDate4(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String day = str.substring(6, 8);
		String hour = str.substring(8, 10);
		String min = str.substring(10, 12);
		String sec = str.substring(12, 14);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
		cal.set(Calendar.MINUTE, Integer.parseInt(min));
		cal.set(Calendar.SECOND, Integer.parseInt(sec));
		return cal.getTime();
	}

	// YYYYMMDD HHMMSS
	public static Date stringToDateShort(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String day = str.substring(6, 8);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		return cal.getTime();
	}

	// YYYY-MM-DD HH:MM
	public static Date stringToDate2(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(5, 7);
		String day = str.substring(8, 10);
		String hour = str.substring(11, 13);
		String min = str.substring(14, 16);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
		cal.set(Calendar.MINUTE, Integer.parseInt(min));
		return cal.getTime();
	}

	// YYYY-MM-DD
	public static Date stringToDateShort2(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(5, 7);
		String day = str.substring(8, 10);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	// YYYY-MM-DD
	public static Date stringToDateShort4(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(5, 7);
		String day = str.substring(8, 10);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// YYYYMMDD
	public static Date stringToDateShort3(String str) {
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String day = str.substring(6, 8);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		return cal.getTime();
	}

	public static String getTimeForMicorBlog(Date tempDate) {
		String datestr = "";
		long nowtimes = new Date().getTime();
		long oldtimes = tempDate.getTime();
		long num = nowtimes - oldtimes;
		long min = num / 1000 / 60;
		if (min > 4320) {
			datestr = "����ǰ";
		} else if (min > 1440) {
			datestr = min / 1440 + "��ǰ";
		} else if (min > 60) {
			datestr = min / 60 + "Сʱǰ";
		}

		else {
			datestr = min + "����ǰ";
		}

		return datestr;
	}

	/***
	 * 字符串转成日期（yyyy-MM-dd）,如果参数为空或参数格式不正确,则返回空。
	 *
	 * @param date
	 *            字符串日期
	 * @return
	 */
	public static Date formatByDate(String date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		Date dates = null;
		try {
			if (date != null)
				dates = fd.parse(date);
		} catch (ParseException e) {
			logger.error(e);
		}
		return dates;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：2011年03月07日
	 */
	public static String dateToFullShorString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String year = sdate.substring(0, 4) + "年";
			String month = sdate.substring(5, 7) + "月";
			String day = sdate.substring(8, 10) + "日";
			result = year + month + day;
		}
		return result;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：2011年03月
	 */
	public static String dateToHalfShorString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String year = sdate.substring(0, 4) + "年";
			String month = sdate.substring(5, 7) + "月";
			result = year + month;
		}
		return result;
	}

	public static String dateToMonthString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String month = sdate.substring(5, 7) + "月";
			result = month;
		}
		return result;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：03月07日
	 */
	public static String dateToShorDateString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String month = "";
			if (sdate.substring(5, 6).equals("0")) {
				month = sdate.substring(6, 7) + "月";
			} else {
				month = sdate.substring(5, 7) + "月";
			}
			String day = sdate.substring(8, 10) + "日";
			result = month + day;
		}
		return result;
	}

	public static String strToStr(String date) {
		String result = "";
		if (date != null) {
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			String time = date.substring(11, 16);
			result = month + "月" + day + "日" + time;
		}
		return result;
	}

	/**
	 * yyyyMMdd转换成MM月dd日
	 *
	 * @param date
	 * @return
	 */
	public static String StringToShorDateString(String date) {
		String result = "";
		if (date != null) {
			String month = "";
			if (date.substring(4, 5).equals("0")) {
				month = date.substring(5, 6) + "月";
			} else {
				month = date.substring(4, 6) + "月";
			}
			String day = date.substring(6, 8) + "日";
			result = month + day;
		}
		return result;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：11年03月07日
	 */
	public static String dateToShorString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String year = sdate.substring(2, 4) + "年";
			String month = sdate.substring(5, 7) + "月";
			String day = sdate.substring(8, 10) + "日";
			result = year + month + day;
		}
		return result;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：11年03月07日
	 */
	public static String stringToShorString(String date) {
		String result = "";
		if (date != null) {
			String year = date.substring(2, 4) + "年";
			String month = date.substring(5, 7) + "月";
			String day = date.substring(8, 10) + "日";
			result = year + month + day;
		}
		return result;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：11年03月07日 14:30
	 */
	public static String dateToString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String year = sdate.substring(2, 4) + "年";
			String month = sdate.substring(5, 7) + "月";
			String day = sdate.substring(8, 10) + "日";
			String hour = sdate.substring(11, 13);
			String minute = ":" + sdate.substring(14, 16);
			result = year + month + day + " " + hour + minute;
		}
		return result;
	}

	/**
	 * 根据String型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：11年03月07日 14:30
	 */
	public static String dateToString2(String date) {
		String result = "";
		if (date != null) {
			String sdate = date;
			String year = sdate.substring(2, 4) + "年";
			String month = sdate.substring(5, 7) + "月";
			String day = sdate.substring(8, 10) + "日";
			String hour = sdate.substring(11, 13);
			String minute = ":" + sdate.substring(14, 16);
			result = year + month + day + " " + hour + minute;
		}
		return result;
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：11年03月07日 14:30
	 */
	public static String stringToString(String date) {
		String result = "";
		if (date != null) {
			String year = date.substring(2, 4) + "年";
			String month = date.substring(5, 7) + "月";
			String day = date.substring(8, 10) + "日";
			String hour = date.substring(11, 13);
			String minute = ":" + date.substring(14, 16);
			result = year + month + day + " " + hour + minute;
		}
		return result;
	}

	/**
	 * 将日期类型数据转换成时间字符串 如果是今天(HH:mm) 不是今天(MM-dd)
	 *
	 * @param date
	 * @return
	 */
	public static String dateToVisitString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String result = "";
		Date nowdate = new Date();
		if (date != null) {
			String now = fd.format(nowdate);
			String sdate = fd.format(date);
			String month = sdate.substring(5, 7) + "-";
			String day = sdate.substring(8, 10);
			String hour = sdate.substring(11, 13);
			String minute = ":" + sdate.substring(14, 16);
			if (now.substring(0, 10).equals(sdate.substring(0, 10))) {
				result = hour + minute;
			} else {
				result = month + day;
			}
		}
		return result;
	}

	/**
	 * 获取给定日期的当月28日的日期
	 *
	 * @param date
	 * @return
	 */
	public static Date dateTo28(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String year = sdate.substring(0, 4) + "-";
			String month = sdate.substring(5, 7) + "-";
			String day = "28";
			result = year + month + day;
		}
		return stringToDateShort2(result);
	}

	/**
	 * 根据Date型日期返回日期字符串
	 *
	 * @param date
	 * @return 如：14:30
	 */
	public static String dateToHourString(Date date) {
		DateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String result = "";
		if (date != null) {
			String sdate = fd.format(date);
			String hour = sdate.substring(11, 13);
			String minute = ":" + sdate.substring(14, 16);
			result = hour + minute;
		}
		return result;
	}

	/***
	 * 根据指定的日期、格式返回字符串格式。格式示例（yyy-MM-dd,MM-dd HH:ss等）
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatTime(Date date, String format) {

		DateFormat fd = new SimpleDateFormat(format);
		String result = null;
		if (date != null) {
			result = fd.format(date);
		}
		return result;
	}

	public static String formateTimeMillSeconds(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SSS");
		return dateformat.format(date);
	}

	public static String formateTime(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateformat.format(date);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(Date date,
			String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	public static String showCurrentTime_Long() {
		// System.out.println(System.currentTimeMillis());
		return String.valueOf(System.currentTimeMillis());
	}

	public static String showCurrentTime_seconds() {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		// System.out.println(sf.format(date));
		return sf.format(date);
	}

	public static String showCurrentTime_millSeconds() {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss:SSS");
		Date date = new Date();
		// System.out.println(sf.format(date));
		return sf.format(date);
	}

	/**
	 * 取得当前时间,格式：yy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getDate3() {
		String time = dateformat2.format(new Date());
		return time;
	}

	/** ******************取得北京时间下的各种日期******************* */
	/**
	 * 返回一个北京时间字符串
	 * 
	 * @param date
	 *            当地时间Date类型
	 * @return String 格式化后的字符串 HH:mm yyyy/MM/dd
	 */
	public static String getTime1(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm yyyy/MM/dd",
				Locale.CHINA);
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		dateformat.setTimeZone(zone);
		return dateformat.format(date);
	}

	/**
	 * 取得当前北京时间星期
	 * 
	 * @return String 当前星期 如 星期一
	 */
	public static String getDayWeek() {
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		Calendar calendar = Calendar.getInstance(zone, Locale.CHINA);
		calendar.setTimeZone(zone);
		// calendar.setTime(new Date());
		String week[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 取得指定时间的星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayWeek(Date date) {
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		Calendar calendar = date2Calendar(date);
		calendar.setTimeZone(zone);
		String week[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 取得指定时间的星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayWeek2(Date date) {
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		Calendar calendar = date2Calendar(date);
		calendar.setTimeZone(zone);
		String week[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 取得指定时间的星期
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayWeek3(Date date) {
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		Calendar calendar = date2Calendar(date);
		calendar.setTimeZone(zone);
		int week[] = { 7, 1, 2, 3, 4, 5, 6 };
		return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 返回一个北京时间的Date类型
	 * 
	 * @return Date
	 */
	public static Date getBJDate() {
		Date d = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = TimeZone.getTimeZone("GMT+08:00");
		df.setTimeZone(zone);
		d = getDate(df.format(new Date()), "yyyy-MM-dd HH:mm:ss");
		return d;
	}

	/**
	 * Calendar转换成Date
	 * 
	 * @param cal
	 * @return Date
	 */
	public static Date calendar2Date(Calendar cal) {
		return cal.getTime();
	}

	/**
	 * Date转换成Calendar
	 * 
	 * @param date
	 * @return Calendar
	 */
	public static Calendar date2Calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 字符串转换成Calendar
	 * 
	 * @param time
	 *            待转字符串
	 * @param format
	 *            格式
	 * @return Calendar
	 */
	public static Calendar str2Calendar(String time, String format) {
		return date2Calendar(getDate(time, format));
	}

	/**
	 * 获得几天前的日期
	 * 
	 * @param day
	 * @return
	 */
	public static String getBeforeDay(int day) {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -day);
		return getDateStr(calendar2Date(cal), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取以小时为单位之前的时间
	 * 
	 * @param hour
	 *            小时
	 * @param style
	 *            获取的时间显示样式
	 * @return
	 */
	public static String getBeforeTimeByHour(int hour, String style) {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, -hour);
		return getDateStr(calendar2Date(cal), style);// "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取以小时为单位之前的时间(默认样式为 yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param hour
	 * @return
	 */
	public static String getBeforeTimeByHour(int hour) {
		return getBeforeTimeByHour(hour, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获得几个月前的日期
	 * 
	 * @param month
	 * @return
	 */
	public static String getBeforeMonth(int month) {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -month);
		return getDateStr(calendar2Date(cal), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	public static String date2Zodica(Calendar time) {
		return zodiacArr[time.get(Calendar.YEAR) % 12];
	}

	/**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return String
	 */
	public static String date2Constellation(Calendar time) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	/**
	 * 根据日期获取星座
	 * 
	 * @param date
	 *            参数为标准日期样式字符串
	 * @return String
	 */
	public static String date2Constellation(String date) {
		Calendar time = Calendar.getInstance();
		time = str2Calendar(date, "yyyyMMdd");
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	/**
	 * 根据出生日期计算年龄
	 * 
	 * @param birthDay
	 * @return 未来日期返回0
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) {

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}

		return age;
	}

	/**
	 * 根据出生日期计算年龄
	 * 
	 * @param strBirthDay
	 *            字符串型日期
	 * @param format
	 *            日期格式
	 * @return 未来日期返回0
	 * @throws Exception
	 */
	public static int getAge(String strBirthDay, String format) {

		DateFormat df = new SimpleDateFormat(format);
		Date birthDay;
		try {
			birthDay = df.parse(strBirthDay);
			return getAge(birthDay);
		} catch (ParseException e) {
			logger.error(e);
		}
		return 0;
	}

	/**
	 * 获取本月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return getDateStr(calendar2Date(cal), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取本月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay() {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 日期倒数一日,既得到本月最后一天
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return getDateStr(calendar2Date(cal), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取本月的最后一天
	 * 
	 * @return
	 */
	public static int getMonthLastDayInt(Date date) {
		Calendar cal = date2Calendar(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 日期倒数一日,既得到本月最后一天
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取本月的最后一天
	 * 
	 * @return
	 */
	public static Date getMonthLastDayDate(Date date) {
		Calendar cal = date2Calendar(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 日期倒数一日,既得到本月最后一天
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
		return calendar2Date(cal);
	}

	/**
	 * 获取本月的第一天
	 * 
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar cal = date2Calendar(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 日期倒数一日,既得到本月最后一天
		return calendar2Date(cal);
	}

	/**
	 * 获取该日期几天前的日期
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getTidyDate(Date date, int num) {
		Calendar cal = date2Calendar(date);
		cal.add(Calendar.DAY_OF_MONTH, num);
		return calendar2Date(cal);
	}

	/**
	 * 获取该日期是几号
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = date2Calendar(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取该日期的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = date2Calendar(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取该日期的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cal = date2Calendar(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取该日期的前一个月的字符串日期(2012-09-12)
	 * 
	 * @param date
	 * @return
	 */
	public static String getPreMonth(Date date) {
		Calendar cal = date2Calendar(date);
		cal.add(Calendar.MONTH, -1);
		return getDateStr(calendar2Date(cal), "yyyy-MM-dd");
	}

	/**
	 * 获取该日期的后一个月的字符串日期(2012-09-12)
	 * 
	 * @param date
	 * @return
	 */
	public static String getNextMonth(Date date) {
		Calendar cal = date2Calendar(date);
		cal.add(Calendar.MONTH, 1);
		return getDateStr(calendar2Date(cal), "yyyy-MM-dd");
	}

	/**
	 * 获取今天的是几号
	 * 
	 * @return
	 */
	public static int getDayOfMonth() {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static String getYear() {
		Date now = getBJDate();
		Calendar cal = date2Calendar(now);
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	/**
	 * 验证日期与当前日期是否为同一天
	 * 
	 * @param date
	 * @return
	 */
	public static String validateSameDay(Date date) {
		String result = "different";
		if (date != null) {
			Date now = getBJDate();
			Calendar nowcal = date2Calendar(now);
			int nowyear = nowcal.get(Calendar.YEAR);
			int nowmonth = nowcal.get(Calendar.MONTH);
			int nowday = nowcal.get(Calendar.DAY_OF_MONTH);
			Calendar datecal = date2Calendar(date);
			int dateyear = datecal.get(Calendar.YEAR);
			int datemonth = datecal.get(Calendar.MONTH);
			int dateday = datecal.get(Calendar.DAY_OF_MONTH);
			if (nowyear == dateyear && nowmonth == datemonth
					&& nowday == dateday) {
				result = "same";
			} else {
				result = "different";
			}
		}
		return result;
	}

	public static String getNowTimeyyyyMMdd() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}

	public static String getNowTime2() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(new Date());
	}

	// 获取当前时间
	public static String getNowTimeyyyy_MM_dd() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
		return df.format(new Date());
	}

	// 获取当前时间
	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	// 指定时间与当前时间对比
	public static long compareDateOfNowDate(String getDateStr) {
		long diff = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateStr = df.format(new Date());
		try {
			Date tempDate = df.parse(getDateStr);
			Date nowDate = df.parse(nowDateStr);
			diff = tempDate.getTime() - nowDate.getTime();
		} catch (ParseException e) {
			logger.error(e);
		}
		return diff;
	}

	// 指定时间与当前时间对比
	public static long compareDateOfNowDateyyyyMMdd(String getDateStr) {
		long diff = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String nowDateStr = df.format(new Date());
		try {
			Date tempDate = df.parse(getDateStr);
			Date nowDate = df.parse(nowDateStr);
			diff = tempDate.getTime() - nowDate.getTime();
		} catch (ParseException e) {
			logger.error(e);
		}
		return diff;
	}

	// 判断指定的时间是星期几
	public static int dayForWeek(String timeStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(timeStr));
		} catch (ParseException e) {
			logger.error(e);
		}
		int dayForWeek = 0;
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static int daysOfTwo(String dateStr, String nowDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(nowDate));
			int day1 = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.setTime(format.parse(dateStr));
			int day2 = calendar.get(Calendar.DAY_OF_YEAR);
			return day2 - day1;
		} catch (ParseException e) {
			logger.error(e);
		}
		return -1;
	}

	// 实现日期加几天的方法
	public static String addDay(String dateStr, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(dateStr));
			cd.add(Calendar.DATE, n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	// 年份是当前年份隐藏年，否则显示全部
	@SuppressWarnings("deprecation")
	public static String hideNowYear(String getDateStr) {
		String returnTime=getDateStr;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateStr = df.format(new Date());
		try {
			Date tempDate = df.parse(getDateStr);
			Date nowDate = df.parse(nowDateStr);
			if(tempDate.getYear()==nowDate.getYear()){
				SimpleDateFormat dff = new SimpleDateFormat("MM-dd");
				returnTime=dff.format(tempDate);
			}
		} catch (ParseException e) {
			logger.error(e);
		}
		return returnTime;
	}

	public static Date stringToDate16(String str) {

		String year = str.split("-")[0];
		String month = str.split("-")[1].split("-")[0];
		String day = str.split("-")[2];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
		return sqlDate;

	}

	/**
	 * 获取前几周 0 表示 本周  	1表示下周   	-1 表示上周 
	 * @param i
	 * @return 一周时间段的其实utm时间 与结束utm时间 
	 * @throws Exception 
	 */
	public static List<Integer> getWeek(int i) throws Exception{
		ArrayList<Integer> list = new  ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		int n = i;
		int j = cal.get(Calendar.DAY_OF_WEEK);
		if(j==1){
			n = i-1;
		}
		
		
		String monday;
		cal.add(Calendar.DATE, n*7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//		System.out.println("上周一："+monday);
//		上周一
		List<String> times = DateUtils.get24Time(monday);
		String[] split = times.get(0).split("#");
		String startTime = split[1];
		
//		上周日
		Long  time = cal.getTime().getTime()+1000*60*60*24*6;
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
//		System.out.println("上周日："+sunday);
		List<String> times2 = DateUtils.get24Time(sunday);
//		System.out.println(times2);
		String[] split1 = times2.get(times.size()-1).split("#");
		String endTime = split1[2];
		Integer start = new Integer(startTime);
		Integer end = new Integer(endTime);
		
		list.add(start);
		list.add(end-1);
		return list;
	}
	
	public static List<Integer> getYesterday() throws Exception{
		Calendar calendar = Calendar.getInstance();
		long timeInMillis = calendar.getTimeInMillis();
		SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");
		timeInMillis = timeInMillis - 1000*60*60*24;
		String format = dateFormat.format(new Date(timeInMillis));
		List<String> times = DateUtils.get24Time(format);
		
		List<Integer> list = getTimes(times);
		
		return list;
	}

	/**
	 * 获取今天的开始 结束时间
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> getTodayTimes() throws Exception{
		Calendar calendar = Calendar.getInstance();
		long timeInMillis = calendar.getTimeInMillis();
		SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");
		String format = dateFormat.format(new Date(timeInMillis));
		List<String> times = DateUtils.get24Time(format);
		
		List<Integer> list = getTimes(times);
		
		return list;
	}
	
	/**
	 * 获取上n月的开始结束时间
	 * @return
	 * @throws Exception 
	 */
	public  static List<Integer> getLastMonth(int i) throws Exception{
		Calendar calendar = Calendar.getInstance();
		int n = i;
		calendar.add(Calendar.MONTH, n);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		long timeInMillis = calendar.getTimeInMillis();
		SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(new Date(timeInMillis));
		
		int maximum = calendar.getActualMaximum(Calendar.DATE);
		
		calendar.set(Calendar.DATE, maximum); 
		long timeInMillis2 = calendar.getTimeInMillis();
		String end = dateFormat.format(new Date(timeInMillis2));
		
		List<String> startTimes = DateUtils.get24Time(start);
		List<String> endTimes = DateUtils.get24Time(end);
		
		Integer startMonth = getTimes(startTimes).get(0);
		Integer endMonth = getTimes(endTimes).get(1);
		
		List<Integer> list = new  ArrayList<Integer>();
		list.add(startMonth);
		list.add(endMonth);
		
			return  list;
	}
	
	
	
	
	/**
	 * 获取某一日期至今的时间
	 * @return 
	 * @param yyyy-MM-dd 格式日期
	 * @throws Exception
	 */
	public  static List<Integer> getOnlineTime(String startTime) throws Exception{
		String endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<String> start = DateUtils.get24Time(startTime);
		List<String> end = DateUtils.get24Time(endTime);
		Integer s = getTimes(start).get(0);
		Integer e = getTimes(end).get(1);
		
		List<Integer> list = new  ArrayList<Integer>();
		list.add(s);
		list.add(e);
		return  list;
	}
	
	
	/**
	 * 获取本月一号至今的时间
	 * @return 
	 * @param yyyy-MM-dd 格式日期
	 * @throws Exception
	 */
	public  static List<Integer> getThisMonthTime() throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date time = calendar.getTime();
		String startTime = new SimpleDateFormat("yyyy-MM-dd").format(time);
		
		String endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		
		List<String> start = DateUtils.get24Time(startTime);
		List<String> end = DateUtils.get24Time(endTime);
		Integer s = getTimes(start).get(0);
		Integer e = getTimes(end).get(1);
		
		List<Integer> list = new  ArrayList<Integer>();
		list.add(s);
		list.add(e);
		return  list;
	}
	
	private static List<Integer> getTimes(List<String> times) {
		String[] split = times.get(0).split("#");
		String[] split1 = times.get(times.size()-1).split("#");
		String startTime = split[1];
		String endTime = split1[2];
		
		List<Integer> list = new  ArrayList<Integer>();
		
		Integer start = new Integer(startTime);
		Integer end = new Integer(endTime);
		list.add(start);
		list.add(end-1);
		return list;
	}
	
	
	/**
	 * 获取前几周 0 表示 本周  	1表示下周   	-1 表示上周 
	 * @param i
	 * @return 一周时间段的其实utm时间 与结束utm时间 
	 * @throws Exception 
	 */
	public static List<Integer> getWeekTest(int i) throws Exception{
		ArrayList<Integer> list = new  ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		int n = i;
		int j = cal.get(Calendar.DAY_OF_WEEK);
		if(j==1){
			n = i-1;
		}
		
		String sunday;
		cal.add(Calendar.DATE, n*7);
		//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		cal.add(Calendar.DATE, 1);
		sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//		System.out.println("上周一："+monday);
//		上周一
		List<String> times = DateUtils.get24Time(sunday);
		String[] split = times.get(0).split("#");
		String startTime = split[1];
		
//		上周日
		Long  time = cal.getTime().getTime()+1000*60*60*24*6;
		String Saturday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
//		System.out.println("上周日："+sunday);
		List<String> times2 = DateUtils.get24Time(Saturday);
//		System.out.println(times2);
		String[] split1 = times2.get(times.size()-1).split("#");
		String endTime = split1[2];
		Integer start = new Integer(startTime);
		Integer end = new Integer(endTime);
		
		list.add(start);
		list.add(end-1);
		return list;
	}
}
