package com.mp.commonsdk.utils;

import java.io.Serializable;

public class Entry<K,V> implements Serializable{
	private static final long serialVersionUID = 4054845297443258156L;
	public K key;
	public V value;
	
	public Entry(){}
	
	public Entry(K key,V value){
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	public V getValue() {
		return value;
	}
	public void setKey(K key) {
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "{key:"+key+",value:"+value+"}";
	}
}
