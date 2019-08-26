package com.xj.httpclient.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MyDateUtiles {
	public static Integer[] getTime(String status) {
		Integer time[] = new Integer[2];
		if ("day".equals(status)) {
			time[0] = (int) (getDayStartTime() / 1000l);
			time[1] = (int) (getDayEndTime() / 1000l);
		} else if ("week".equals(status)) {
			time[0] = (int) (getWeekStartTime() / 1000l);
			time[1] = (int) (getWeekEndTime() / 1000l);
		} else if ("month".equals(status)) {
			time[0] = (int) (getMonthStartTime() / 1000l);
			time[1] = (int) (getMonthEndTime() / 1000l);
		}
		return time;
	}
	
	public static Integer[] getTime(String status , Integer date , String type){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer time[] = new Integer[2];
		try {
			if("next".equals(type)){
				if("day".equals(status)){
					String dateTime = getSpecifiedDayAfter(date, 1);
					time[0] = (int)(format.parse(dateTime+ " 00:00:01").getTime() / 1000l);
					time[1] = (int)(format.parse(dateTime+ " 23:59:59").getTime() / 1000l);
				}else if("week".equals(status)){
					time = getWeek(type, date);
				}else if("month".equals(status)){
					time = getMonth(type, date, 1);
				}
			}else if("last".equals(type)){
				if("day".equals(status)){
					String dateTime = getSpecifiedDayBefore(date, 1);
					time[0] = (int)(format.parse(dateTime+ " 00:00:01").getTime() / 1000l);
					time[1] = (int)(format.parse(dateTime+ " 23:59:59").getTime() / 1000l);
				}else if("week".equals(status)){
					time = getWeek(type, date);
				}else if("month".equals(status)){
					time = getMonth(type, date, 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return time;
	}
	
	public static Long getDayStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}

	public static Long getDayEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime().getTime();
	}

	public static Long getWeekStartTime() {
		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 1);
		return cal.getTime().getTime();
	}
	
	public static Long getWeekStartTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 1);
		return cal.getTime().getTime();
	}
	
	public static Integer getWeekStartTime(Date date , String type , Integer num) {
		Long time = getWeekStartTime(date);
		Integer resultTime = (int)(time / 1000l);
		if(num != null && num > 0){
			Integer result[] = null;
			for(int i = 0 ; i < num ; i++){
				result = getWeek(type, resultTime);
				resultTime = result[0];
			}
		}
		
		return resultTime;
	}
	public static Integer getWeekEndTime(Date date , String type , Integer num) {
		Long time = getWeekEndTime(date);
		Integer resultTime = (int)(time / 1000l);
		if(num != null && num > 0){
			Integer result[] = null;
			for(int i = 0 ; i < num ; i++){
				result = getWeek(type, resultTime);
				resultTime = result[1];
			}
		}
		
		return resultTime;
	}

	public static Long getWeekEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 6);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime().getTime();
	}
	
	public static Long getWeekEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 6);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime().getTime();
	}

	public static Long getMonthStartTime() {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 1);
		return cal.getTime().getTime();
	}

	public static Long getMonthEndTime() {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);//
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime().getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	

	/**
	 * 获得指定日期的前N天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(Integer specifiedDay , Integer daySum) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new Date(specifiedDay * 1000l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - daySum);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后N天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(Integer specifiedDay , Integer daySum) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new Date(specifiedDay * 1000l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + daySum);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}
	
	
    
    public static void main(String[] args) {
    	int time = getWeekStartTime(new Date(), "last", 1);
    	Date d = new Date();
    	d.setTime(time * 1000l);
    	System.out.println(d);
    	Long tim = getWeekEndTime(d);
    	d.setTime(tim);
    	System.out.println(d);
	}
    
    public static Integer[] getWeek(String status , Integer date){ // 根据日期获取 前一周或后一周
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Integer time[] = new Integer[2];
    	String date1 = "";
    	String date2 = "";
    	try {
    		if("last".equals(status)){ // 前一周
        		date1 = getSpecifiedDayBefore(date, 7);
        	}else if("next".equals(status)){ // 后一周
        		date1 = getSpecifiedDayAfter(date, 7); 		
        	}
    		time[0] = (int)(format.parse(date1+" 00:00:01").getTime() / 1000l);
    		date2 = getSpecifiedDayAfter(time[0], 6);
    		time[1] = (int)(format.parse(date2+" 23:59:59").getTime() / 1000l);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return time;
    }
    
    public static Integer[] getMonth(String status , Integer date , Integer sum){
    	Integer time[] = new Integer[2];
    	Calendar cal = Calendar.getInstance();// 获取当前日期
    	Date d = new Date(date * 1000l);
    	cal.setTime(d);
    	cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
    	if("last".equals(status)){
    		cal.add(Calendar.MONTH, -sum);
    	}else if("next".equals(status)){
    		cal.add(Calendar.MONTH, sum);
    	}
    	cal.set(Calendar.HOUR_OF_DAY, 0);
		
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MILLISECOND, 1);
		time[0] = (int)(cal.getTime().getTime() / 1000l);
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);//
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		time[1] = (int)(cal.getTime().getTime() / 1000l);
		
	
    	return time;
    }
    
    /**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(String months) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
       
        try {
			calendar.setTime(df.parse(months));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        Date theDate = calendar.getTime();
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 当月第一天
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }
    
    /**
     * 当月最后一天
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }



  

}
