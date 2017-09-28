package br.com.udemy.builders;

import java.util.Arrays;
import br.com.udemy.model.Usuario;
import java.time.LocalDate;
import java.lang.Double;

import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;


public class LocacaoBuilder {
	private Locacao elemento;
	private LocacaoBuilder(){}

	public static LocacaoBuilder umLocacao() {
		LocacaoBuilder builder = new LocacaoBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(LocacaoBuilder builder) {
		builder.elemento = new Locacao();
		Locacao elemento = builder.elemento;

		
		elemento.setUsuario(UsuarioBuilder.umUsuario().agora());
		elemento.setFilmes(Arrays.asList(FilmeBuilder.umFilme().agora()));
		elemento.setDataLocacao(LocalDate.now());
		elemento.setDataRetorno(LocalDate.now().plusDays(1L));
		elemento.setValor(4.0);
	}

	public LocacaoBuilder comUsuario(Usuario param) {
		elemento.setUsuario(param);
		return this;
	}

	public LocacaoBuilder comListaFilmes(Filme... params) {
		elemento.setFilmes(Arrays.asList(params));
		return this;
	}

	public LocacaoBuilder comDataLocacao(LocalDate param) {
		elemento.setDataLocacao(param);
		return this;
	}

	public LocacaoBuilder comDataRetorno(LocalDate param) {
		elemento.setDataRetorno(param);
		return this;
	}
	
	public LocacaoBuilder atrasado() {
		elemento.setDataLocacao(LocalDate.now().minusDays(4));
		elemento.setDataRetorno(LocalDate.now().minusDays(2));
		return this;
	}

	public LocacaoBuilder comValor(Double param) {
		elemento.setValor(param);
		return this;
	}

	public Locacao agora() {
		return elemento;
	}
}

