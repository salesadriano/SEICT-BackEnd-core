package br.com.lamppit.core.exception;

public class BusinessValidation extends RuntimeException {
	private static final long serialVersionUID = -164254041492766687L;

	public BusinessValidation(String message) {
		super(message);
	}

}
