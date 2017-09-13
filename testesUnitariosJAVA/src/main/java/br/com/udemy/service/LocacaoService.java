package br.com.udemy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.DoubleStream;

import br.com.udemy.exception.FilmeSemEstoqueException;
import br.com.udemy.exception.LocadoraException;
import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;
import br.com.udemy.model.Usuario;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException, FilmeSemEstoqueException  {
		
		if(usuario == null)
			throw new LocadoraException("Usuario vazio");
		
		if(filmes == null || filmes.isEmpty())
			throw new LocadoraException("Filme vazio");
	
		for (Filme filme : filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}
		
		
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(LocalDate.now());
		Double somaLocacao = filmes.stream().flatMapToDouble(f -> DoubleStream.of(f.getPrecoLocacao())).sum();
		locacao.setValor(somaLocacao);
		
		//Entrega no dia seguinte
		LocalDate dataEntrega = LocalDate.now();
		dataEntrega = dataEntrega.plusDays(1);
		locacao.setDataRetorno(dataEntrega);
		
		return locacao;
	}
	
}
