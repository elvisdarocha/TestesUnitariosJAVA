package br.com.udemy.exception;

public class LocadoraException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LocadoraException() {
		super();
	}

	public LocadoraException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocadoraException(String message) {
		super(message);
	}

	public LocadoraException(Throwable cause) {
		super(cause);
	}

}
