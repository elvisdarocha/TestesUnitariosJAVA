package br.com.udemy.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.udemy.builders.UsuarioBuilder;
import br.com.udemy.dao.LocacaoDAO;
import br.com.udemy.exception.FilmeSemEstoqueException;
import br.com.udemy.exception.LocadoraException;
import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;
import br.com.udemy.model.Usuario;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	@InjectMocks
	public static LocacaoService service;
	
	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	
	@Parameter(value=0)
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	public Usuario usuario;
	
	@Before
	public void setUp() {
		usuario = UsuarioBuilder.umUsuario().agora();
		
		MockitoAnnotations.initMocks(this);
		//service = new LocacaoService();
		//LocacaoDAO dao = new LocacaoDAOFake();
		/*dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);*/
	}
	
	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
	private static Filme filme7 = new Filme("Filme 7", 2, 4.0);
	
	@Parameters(name="Teste {index} = {2}}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 8.0, "2 filmes: Sem desconto" },
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 filmes: 25%" },
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 filmes: 50%" },
			{Arrays.asList(filme1, filme2, filme3, filme4,  filme5), 14.0 , "5 filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4,  filme5, filme6), 14.0 , "6 filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4,  filme5, filme6, filme7), 18.0 , "7 filmes: Sem desconto"}
		});
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		//Automaticamente gerado pelo Parameterized
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(valorLocacao));
	}
}
