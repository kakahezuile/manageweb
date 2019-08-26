package com.xj.httpclient.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.xj.annotation.NotUpdataAnnotation;
import com.xj.utils.BeanUtil;

public class DaoUtils {
	
	public static Object[] reflect(Object obj) {
		Object obResult[] = new Object[2];
		StringBuilder sql = new StringBuilder("");
		List<Object> list = new ArrayList<Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		int type = 0;
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			// 字段名
			// System.out.print(fields[j].getName() + ":");
			if (fields[j].isAnnotationPresent(NotUpdataAnnotation.class))
				continue;
			if ("serialVersionUID".equals(fields[j].getName()))
				continue;
			try {
				if (fields[j].get(obj) != null) {
					if (type == 0) {
						type = 1;
					} else {
						sql.append(" , ");
					}
					if ("version".equalsIgnoreCase(BeanUtil.changeName(fields[j].getName()))) {
						sql.append(" ").append(BeanUtil.changeName(fields[j].getName())).append(" = version+1 ");
					} else {
						sql.append(" ").append(BeanUtil.changeName(fields[j].getName())).append(" = ? ");
						list.add(fields[j].get(obj));
					}
				}
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		obResult[0] = new String(sql.toString());
		sql.setLength(0);
		obResult[1] = list;
		return obResult;
	}
}
