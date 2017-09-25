package br.com.udemy.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

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
		Double somaLocacao = 0d;
		
		for(int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			
			switch (i) {
				case 2: valorFilme =  valorFilme * 0.75; break;
				case 3: valorFilme =  valorFilme * 0.5; break;
				case 4: valorFilme =  valorFilme * 0.25; break;
				case 5: valorFilme =  0d; break;
			}
			
			/*if(i  == 2) {
			valorFilme =  valorFilme * 0.75;
			}
			if(i  == 3) {
				valorFilme =  valorFilme * 0.5;
			}
			if(i  == 4) {
				valorFilme =  valorFilme * 0.25;
			}
			if(i  == 5) {
				valorFilme =  0d;
			}*/
			
			somaLocacao += valorFilme;
		}
		//filmes.stream().flatMapToDouble(f -> DoubleStream.of(f.getPrecoLocacao())).sum();
		locacao.setValor(somaLocacao);
		
		//Entrega no dia seguinte
		LocalDate dataEntrega = LocalDate.now();
		dataEntrega = dataEntrega.plusDays(1);
		if(DayOfWeek.from(dataEntrega).equals(DayOfWeek.SUNDAY))
			dataEntrega = dataEntrega.plusDays(1);
		locacao.setDataRetorno(dataEntrega);
		
		return locacao;
	}
	
}
