package com.xj.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UtilDate {

	 public static void main(String args[]) {
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();
	        System.out.println("=========LastWeek Days=========");
	        cal.add(Calendar.WEEK_OF_MONTH, -1);
	        for (int i = 0; i < 7; i++) {
	            cal.add(Calendar.DATE, -1 * cal.get(Calendar.DAY_OF_WEEK) + 2 + i);
	            System.out.println(sf.format(cal.getTime()));
	        }
	        System.out.println("=========ThisMonth Days=========");
	        cal = Calendar.getInstance();
	        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
	        cal.add(Calendar.MONTH, 1);
	        cal.add(Calendar.DATE, -1);
	        String lastDay = sf.format(cal.getTime());
	        String aDay = "";
	        int i = 1;
	        while (!lastDay.equals(aDay)) {
	            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
	            aDay = sf.format(cal.getTime());
	            System.out.println(sf.format(cal.getTime()));
	            i++;
	        }
	        System.out.println("=========LastMonth Days=========");
	        cal = Calendar.getInstance();
	        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
	        cal.add(Calendar.DATE, -1);
	        lastDay = sf.format(cal.getTime());
	        i = 1;
	        while (!lastDay.equals(aDay)) {
	            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), i);
	            aDay = sf.format(cal.getTime());
	            System.out.println(sf.format(cal.getTime()));
	            i++;
	        }
	    }
}
