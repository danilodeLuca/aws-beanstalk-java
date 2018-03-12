package br.com.beanstalker;

public class BeanStalkException extends Exception {

	public BeanStalkException(Throwable e) {
		super(e);
	}

	public BeanStalkException(String message) {
		super(message);
	}
}