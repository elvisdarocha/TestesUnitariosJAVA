package br.com.udemy.dao;

import java.util.List;

import br.com.udemy.model.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
