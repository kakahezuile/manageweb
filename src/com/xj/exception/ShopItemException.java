package com.xj.exception;

public class ShopItemException extends Exception {
	
	private Object object;
	
	
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShopItemException(){
		super();
	}
	
	public ShopItemException(String message){
		super(message);
	}
	
	public ShopItemException(Object object){
		super();
		this.object = object;
	}
}
