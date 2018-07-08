package br.com.infra.exception;

public class ErroSistema extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ErroSistema(final String mensagem) {
		super(mensagem);
	}
	
	public ErroSistema(final String mensagem, final Throwable throwable) {
		super(mensagem, throwable);
	}

}
