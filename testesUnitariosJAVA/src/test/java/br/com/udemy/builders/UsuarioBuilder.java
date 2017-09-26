package br.com.udemy.builders;

import br.com.udemy.model.Usuario;

public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder criarComUmUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario("Usuario 1");
		return builder;
	}
	
	public Usuario pegarUsuarioAtual() {
		return usuario;
	}
}
