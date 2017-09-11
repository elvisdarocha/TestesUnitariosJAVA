package br.com.udemy.service;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

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
	public static Filme filme;

	@BeforeClass
	public static void setUp() {
		service = new LocacaoService();
		usuario = new Usuario("usuario 1");
		filme = new Filme("Filme 1", 2, 5.0);
	}

	@Test
	public void testeLocacao() throws Exception {
		// cenario
		/* - configurado no setup
		 service = new LocacaoService();
		 usuario = new Usuario("usuario 1");
		 filme = new Filme("Filme 1", 2, 5.0);
		 */

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

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

	}
	
	//Testa Lancar excecao quando filme nao tem estoque
	//Caso de exception, o teste passa pois é esperado ( expected ) um exception
	@Test(expected=Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		filme = new Filme("Filme 1", 0, 5.0);
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_filmeSemEstoque_2() {
		filme = new Filme("Filme 1", 0, 5.0);
		try {
			service.alugarFilme(usuario, filme);
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
		
		filme = new Filme("Filme 1", 0, 5.0);
		service.alugarFilme(usuario, filme);
		
	}
}
