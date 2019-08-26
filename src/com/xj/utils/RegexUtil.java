package com.xj.utils;

import java.util.regex.Pattern;

public class RegexUtil {
	
	public static boolean isNumber(String str){
		return str!=null?Pattern.compile("^\\d+$").matcher(str).find():false;
	}
	
	public static boolean isDecimals(String str){
		return str!=null?Pattern.compile("^-?\\d?\\.?\\d+$").matcher(str).find():false;
	}
	
	
	
	public static boolean isEmail(String email){
		String regex = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
		return email!=null?Pattern.compile(regex).matcher(email).find():false;
	}
	
	public static boolean isPhone(String phone){
		String regex = "^(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
		return phone!=null?Pattern.compile(regex).matcher(phone).find():false;
	}
	
	public static void main(String[] args) {
		System.out.println(RegexUtil.isDecimals("4"));
		System.out.println(RegexUtil.isDecimals("2.42"));
//		System.out.println(RegexUtil.isEmail("41254fasdfh35234@gmail.com.cn"));
//		System.out.println(isPhone("13381458187"));
	}
}
