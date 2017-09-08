package br.com.udemy.service;

import java.time.LocalDate;

import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;
import br.com.udemy.model.Usuario;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(LocalDate.now());
		locacao.setValor(filme.getPrecoLocacao());
		
		//Entrega no dia seguinte
		LocalDate dataEntrega = LocalDate.now();
		dataEntrega = dataEntrega.plusDays(1);
		locacao.setDataRetorno(dataEntrega);
		
		return locacao;
	}
	
}
