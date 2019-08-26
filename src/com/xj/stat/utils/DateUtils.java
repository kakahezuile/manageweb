package com.xj.stat.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	
	//private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 *  @param date 格式：yyyy-MM-dd 如 2014-03-18
	 *  @return
	 *  
	 *	0点-1点#1395072000#1395075600
	 *	1点-2点#1395075600#1395079200
	 *			.........
	 *	22点-23点#1395151200#1395154800
	 *	23点-24点#1395154800#1395158400
	 */
	public static List<String> get24Time(String date) throws Exception{
		if(StringUtil.isEmpty(date)) return null;
		Calendar c = Calendar.getInstance();
		String[] ymd = date.split("-");
		if(ymd.length!=3) return null;
		
		c.set(Calendar.YEAR, Integer.parseInt(ymd[0]));
		c.set(Calendar.MONTH, Integer.parseInt(ymd[1])-1); 
		c.set(Calendar.DATE, Integer.parseInt(ymd[2]));
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		List<Long> sec = new ArrayList<Long>();
		
		for(int i=0;i<=24;i++){
			c.set(Calendar.HOUR_OF_DAY,i);
			sec.add(c.getTimeInMillis()/1000);
		}
		
		List<String> result = new ArrayList<String>();
		
		StringBuilder str = new StringBuilder();
		
		for(int i=0;i<sec.size()-1;i++){
			str.append(i+"点-"+(i+1)+"点");
			str.append("#");
			str.append(sec.get(i));
			str.append("#");
			str.append(sec.get(i+1));
			result.add(str.toString());
			str.setLength(0);
		}
		
		return result;
	}
	
	public static int getcurrentdate(){
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH)+1; 
		int d = c.get(Calendar.DATE);
		return Integer.parseInt(y+""+(m<=9?"0"+m:m)+(d<=9?"0"+d:d));
	}
	
	public static String currentDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(Calendar.getInstance().getTime());
	}
	
	public static String before5Date(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-4);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(c.getTime());
	}
	
	
	
	
	
	// 来自   http://blog.sina.com.cn/s/blog_4d6be6f301010e85.html
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	
	
	public static String tofiexd(double d){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(d);
	}
	
	public static void main(String[] args) throws Exception {
		
		
//		List<String> list = getDayBetween("2014-03-01","2014-03-21");
//		
//		for(int i=list.size()-1;i>=0;i--){
//			System.out.println(list.get(i));
//		}
		
		/*for(String s : list){
			System.out.println(s);
			//System.out.println(Arrays.toString(getDayOfBeginAndEndLong(s)));
		}*/
		
		//System.out.println(currentDate());
		//System.out.println(before5Date());
		
//		List<String> list = get24Time("2014-12-12");
//		for(String str : list){
//			System.out.println(str);
//		}
		System.out.println(getThisMonth());
		
	}
	
	public static Integer getThisMonth(){
		Calendar calendar = Calendar.getInstance();
		Integer month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}
	
	public static int getCurrentUnixTime(){
		return (int)(System.currentTimeMillis()/1000l);
	}
}
