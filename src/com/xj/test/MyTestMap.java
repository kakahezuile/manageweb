package com.xj.test;

import java.util.HashMap;
import java.util.Map;

public class MyTestMap<K,V> {
	
	private Map<K, V> map = new HashMap<K, V>();
	
	private K key;
	
	private V value;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	public void put(K k , V v){
		map.put(k, v);
	}
	
	public V get(K k){
		return map.get(k);
	}
}
