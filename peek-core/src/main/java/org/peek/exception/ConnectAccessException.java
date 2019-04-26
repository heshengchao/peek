package org.peek.exception;

public class  ConnectAccessException extends RuntimeException{
	
	private static final long serialVersionUID = -230841661673892941L;
	public ConnectAccessException() {
		super();
    }
    public ConnectAccessException(String message) {
    	super(message);
    }
    public ConnectAccessException(String message,Throwable e) {
    	super(message,e);
    }
}