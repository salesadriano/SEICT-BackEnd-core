package br.com.lamppit.core.exception;

public class EntityNotFound extends RuntimeException {

	private static final long serialVersionUID = -2388800837369352727L;

	public EntityNotFound(String message) {
		super(message);
	}

}
