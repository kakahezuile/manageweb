package com.xj.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.xj.exception.TypeConvertException;

public class BeanUtil {
	
	public static String getSetMethodName(String name) {
		return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public static String getSetter(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}

	public static String getGetter(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);

	}
	
	public static String sqlTyptToJavaType(String sqlType){
		
		String type = "";

		if (sqlType.startsWith("int") || sqlType.startsWith("mediumint")
				|| sqlType.startsWith("smallint")) {
			type = "Integer";
		}else if(sqlType.startsWith("bigint")){
			type = "Long";
		} else if (sqlType.startsWith("varchar") || sqlType.startsWith("text")
				|| sqlType.startsWith("char") || sqlType.startsWith("mediumtext")
				|| sqlType.startsWith("longtext")||sqlType.startsWith("enum")) {
			type = "String";
		} else if (sqlType.startsWith("decimal")) {
			type = "java.math.BigDecimal";
		} else if (sqlType.startsWith("date") || sqlType.startsWith("time")
				|| sqlType.startsWith("datetime")) {
			type = "java.util.Date";
		} else if (sqlType.startsWith("float")) {
			type = "Float";
		} else if (sqlType.startsWith("double")) {
			type = "Double";
		} else if (sqlType.startsWith("blob")) {
			type = "Byte[]";
		} else if(sqlType.startsWith("tinyint")){
			type = "Boolean";
		}
		
		if("".equals(type)){
			throw new TypeConvertException("不能将数据库类型 >>> "+sqlType+" <<< 转换为java类型");
		}
		return type;
	}
	
	public static Object convertType(Object val,Class<?> targetType){
		String name = targetType.getSimpleName();
		Object result = null;
		if("Integer".equals(name)||"int".equals(name)){
			result = Integer.parseInt(val+"");
		}else if("String".equals(name)){
			result = val+"";
		}else if("long".equalsIgnoreCase(name)){
			if(val==null || "null".equals(val)){
				result = 0;
			}else{
				result = Long.parseLong(val+"");
			}
		}else if("float".equalsIgnoreCase(name)){
			result = Float.parseFloat(val+"");
		}else if("double".equalsIgnoreCase(name)){
			if(val==null || StringUtil.isEmpty(val.toString())){
				result = 0;
			}else{
				result = Double.parseDouble(val+"");
			}
		}else if("boolean".equalsIgnoreCase(name)){
			result = Boolean.parseBoolean(val+"");
		}
		if(result==null){
			throw new TypeConvertException("不能将 "+val+"转换为 "+targetType+" 类型");
		}
		return result;
	}

	/**
	 * 把表名 替换成 类名 
	 * 把 类名替换成 表名
	 * 如：表名  cif_member    转换后： CifMember
	 * 		  CifMember     转换后 : cif_member
	 * 			Member      转换后：member
	 * 			member      转换后：Member
	 *          memberId    转换后：member_id
	 * @param name
	 * @return
	 */
	public static String changeName(String name){
		StringBuilder result = new StringBuilder();
		if(name.contains("_")){
			String[] names = name.split("_");
			for(String n : names){
				result.append(n.substring(0, 1).toUpperCase()+n.substring(1));
			}
		}else{
			int sum=0;
			StringBuilder s = new StringBuilder();
			char[] c=name.toCharArray();
			for(int i=0;i<c.length;i++){
				if(c[i]>='A'&& c[i]<='Z'){
					s.append("_");
					sum++;
				}
				s.append(c[i]);
			}
			char ch = name.charAt(0);
			if(ch>='A'&&ch<='Z'&&sum<=1){
				result.append(name.toLowerCase());
			}else if(ch>='a'&&ch<='z'&&sum<1){
				result.append(name.substring(0,1).toUpperCase()+name.substring(1));
			}else if(ch>='a'&&ch<='z'&&sum>=1){
				result.append(s.substring(0, s.length()).toLowerCase());
			}else if(ch>='0'&&ch<='9'&&sum>=1){
				result.append(s.substring(0, s.length()).toLowerCase());
			}else{
				result.append(s.substring(1, s.length()).toLowerCase());
			}
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(changeName("shortId"));
	}
	
	/**
	 * 将对象中的值 一一映射到 map中
	 * 作用：方便数据的回显
	 * @param obj
	 * @param map
	 * @throws Exception 
	 */
	public static void objToMap(Object obj,Map<String,Object> map) throws Exception{
		if(obj==null||map==null){
			throw new NullPointerException();
		}
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			String fieldName = field.getName();
			if("serialVersionUID".equals(fieldName)) continue;
			Method method = clazz.getDeclaredMethod(getGetter(fieldName));
			Object value = method.invoke(obj);
			map.put(fieldName, value);
		}
	}
	
	public static boolean isEmpty(List<? extends Object> list){		
		return (list == null || list.size()==0);
	}
	
	public static String getRandomNum(){
		StringBuilder s = new StringBuilder();
		Random random = new Random();
		for(int i=0;i<5;i++){
			s.append(random.nextInt(10));
		}
		return s.toString();
	}
	
	public static String changePhone(String phone){
		return phone.substring(0, 3)+"****"+phone.substring(7);
	}
	
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}