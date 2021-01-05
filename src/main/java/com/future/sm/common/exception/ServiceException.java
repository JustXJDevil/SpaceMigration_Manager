package com.future.sm.common.exception;
/**
 * 自定义异常可以更好地地位错误，带来更好的体验
 * @author Administrator
 *
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable t) {
		super(t);
	}

}
