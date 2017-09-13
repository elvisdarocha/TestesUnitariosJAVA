package br.com.udemy.exception;

public class FilmeSemEstoqueException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FilmeSemEstoqueException() {
		super();
	}

	public FilmeSemEstoqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilmeSemEstoqueException(String message) {
		super(message);
	}

	public FilmeSemEstoqueException(Throwable cause) {
		super(cause);
	}

}
