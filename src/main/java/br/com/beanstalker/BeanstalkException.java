package br.com.beanstalker;

public class BeanstalkException extends Exception {

	public BeanstalkException(Throwable e) {
		super(e);
	}

	public BeanstalkException(String message) {
		super(message);
	}
}