package br.com.udemy.service;

import static br.com.udemy.matchers.MatchersProprios.caiNumaSegunda;
import static br.com.udemy.matchers.MatchersProprios.ehHoje;
import static br.com.udemy.matchers.MatchersProprios.ehHojeComDiferencaDias;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.udemy.exception.FilmeSemEstoqueException;
import br.com.udemy.exception.LocadoraException;
import br.com.udemy.matchers.DiaSemanaMatcher;
import br.com.udemy.matchers.MatchersProprios;
import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;
import br.com.udemy.model.Usuario;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	public static LocacaoService service;
	public static Usuario usuario;
	public List<Filme> filmes = new ArrayList<>();

	@BeforeClass
	public static void setUpClass() {
		service = new LocacaoService();
		usuario = new Usuario("Usuario 1");
	}
	
	@Before
	public void setUp() {
		//System.out.println("setup");
	}
	
	@After
	public void tearDown() {
		//System.out.println("tearDown");
	}
	
	@AfterClass
	public static void tearDownClass() {
		//System.out.println("tearDown");
	}


	@Test
	public void deveAlugarFilme() throws Exception {
		
		Assume.assumeFalse(DayOfWeek.from(LocalDate.now()).equals(DayOfWeek.SATURDAY));
		
		// cenario
		/* - configurado no setup
		 service = new LocacaoService();
		 usuario = new Usuario("usuario 1");
		 filme = new Filme("Filme 1", 2, 5.0);
		 */

		// acao
		filmes.add(new Filme("Filme 1", 2, 5.0));
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertEquals(5.0, locacao.getValor(), 0.01);
		Assert.assertTrue(locacao.getDataLocacao().isEqual(LocalDate.now()));
		Assert.assertTrue(locacao.getDataRetorno().isEqual(LocalDate.now().plusDays(1)));

		// Quando Utilizado Assert That, quando o sistema encontra um erro
		// o processo para e nao valida o restante
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));
		Assert.assertThat(locacao.getDataLocacao(), CoreMatchers.is(LocalDate.now()));
		Assert.assertThat(locacao.getDataRetorno(), CoreMatchers.is(LocalDate.now().plusDays(1)));

		// Com o Error Collector, o sistema roda todas as validacoes
		error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));
		error.checkThat(locacao.getDataLocacao(), CoreMatchers.is(LocalDate.now()));
		error.checkThat(locacao.getDataRetorno(), CoreMatchers.is(LocalDate.now().plusDays(1)));

		
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1L));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
	}
	
	//Testa Lancar excecao quando filme nao tem estoque
	//Caso de exception, o teste passa pois é esperado ( expected ) um exception
	@Test(expected=FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception{
		filmes.add(new Filme("Filme 1", 0, 5.0));
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemEstoque() {
		filmes.add(new Filme("Filme 1", 0, 5.0));
		try {
			service.alugarFilme(usuario, filmes);
			//Trava para que nao passe o false positivo
			Assert.fail("Deveria ter lancado uma excecao");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme sem estoque"));
		}
	}
	
	@Test
	public void testLocacao_filmeSemEstoque_3() throws Exception{
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		filmes.add(new Filme("Filme 1", 0, 5.0));
		service.alugarFilme(usuario, filmes);
		
		//Apos lancada a execao, o junit para de executar o metodo
		System.out.println("Nao vai passar aqui");
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("Deveria ter lancado uma excecao");
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
		} 
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException {
		try {
			service.alugarFilme(usuario, null);
			Assert.fail("Deveria ter lancado uma excecao");
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme vazio"));
		} 
	}
	
	@Test
	public void devePagar75PorcentoNoFilme3() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		filmes.add(new Filme("Filme 1", 2, 4.0));
		filmes.add(new Filme("Filme 2", 2, 4.0));
		filmes.add(new Filme("Filme 3", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(11.0));
	}
	
	@Test
	public void devePagar50PorcentoNoFilme4() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		filmes.add(new Filme("Filme 1", 2, 4.0));
		filmes.add(new Filme("Filme 2", 2, 4.0));
		filmes.add(new Filme("Filme 3", 2, 4.0));
		filmes.add(new Filme("Filme 4", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(13.0));
	}
	
	@Test
	public void devePagar25PorcentoNoFilme5() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		filmes.add(new Filme("Filme 1", 2, 4.0));
		filmes.add(new Filme("Filme 2", 2, 4.0));
		filmes.add(new Filme("Filme 3", 2, 4.0));
		filmes.add(new Filme("Filme 4", 2, 4.0));
		filmes.add(new Filme("Filme 5", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.0));
	}
	
	@Test
	public void deveAlugarDeGracaOFilme6() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		filmes.add(new Filme("Filme 1", 2, 4.0));
		filmes.add(new Filme("Filme 2", 2, 4.0));
		filmes.add(new Filme("Filme 3", 2, 4.0));
		filmes.add(new Filme("Filme 4", 2, 4.0));
		filmes.add(new Filme("Filme 5", 2, 4.0));
		filmes.add(new Filme("Filme 6", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1+0
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.0));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Assume.assumeTrue(DayOfWeek.from(LocalDate.now()).equals(DayOfWeek.SATURDAY));
		
		filmes.add(new Filme("Filme 1", 1, 5.0));
		
		//acao
		
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		
		boolean retornoNaSegunda = DayOfWeek.from(retorno.getDataRetorno()).equals(DayOfWeek.MONDAY);
		
		//verificacao
		Assert.assertTrue(retornoNaSegunda);
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado_UtilizandoMatcherProprio() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		Assume.assumeTrue(DayOfWeek.from(LocalDate.now()).equals(DayOfWeek.SATURDAY));
		
		filmes.add(new Filme("Filme 1", 1, 5.0));
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean retornoNaSegunda = DayOfWeek.from(retorno.getDataRetorno()).equals(DayOfWeek.MONDAY);
		Assert.assertTrue(retornoNaSegunda);
		
		Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(DayOfWeek.MONDAY));
		
		Assert.assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		
	}
}
