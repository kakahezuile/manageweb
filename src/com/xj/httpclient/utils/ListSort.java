package com.xj.httpclient.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSort<E> {

	/**
	 * @param list 要排序的集合
	 * @param method 要排序的实体的属性所对应的get方法
	 * @param sort desc 为正序
	 */
	public void Sort(List<E> list, final String method, final String sort) {
		Collections.sort(list, new Comparator<E>() {
			public int compare(E a, E b) {
				int ret = 0;
				try {
					Method m1 = a.getClass().getMethod(method);
					Method m2 = b.getClass().getMethod(method);
					if (sort != null && "desc".equals(sort)) {
						ret = m2.invoke(((E) b)).toString().compareTo(m1.invoke(((E) a)).toString());
					} else {
						ret = m1.invoke(((E) a)).toString().compareTo(m2.invoke(((E) b)).toString());// 正序排序
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return ret;
			}
		});
	}
}
