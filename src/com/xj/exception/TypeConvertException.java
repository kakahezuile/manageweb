package com.xj.exception;

public class TypeConvertException extends RuntimeException{

	private static final long serialVersionUID = -6976129326390209243L;

	public TypeConvertException() {
		super();
	}

	public TypeConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeConvertException(String message) {
		super(message);
	}

	public TypeConvertException(Throwable cause) {
		super(cause);
	}
	
}
