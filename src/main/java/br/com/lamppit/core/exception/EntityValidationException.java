package br.com.lamppit.core.exception;

public class EntityValidationException extends RuntimeException {

	private static final long serialVersionUID = -8492138078970673348L;

	public EntityValidationException(String mensagem) {
		super(mensagem);
	}
}
