package br.com.udemy.service;

import static br.com.udemy.matchers.MatchersProprios.caiNumaSegunda;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.udemy.builders.FilmeBuilder;
import br.com.udemy.builders.UsuarioBuilder;
import br.com.udemy.dao.LocacaoDAO;
import br.com.udemy.exception.FilmeSemEstoqueException;
import br.com.udemy.exception.LocadoraException;
import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;
import br.com.udemy.model.Usuario;

public class LocacaoServiceSemPowerMockitoTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@InjectMocks 
	@Spy
	public LocacaoService service ;
	public Usuario usuario;
	public List<Filme> filmes;
	
	@Mock
	private SPCService spcService;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService emailService;

	@BeforeClass
	public static void setUpClass() {
	}
	
	@Before
	public void setUp() {
		usuario = UsuarioBuilder.umUsuario().agora();
		filmes = new ArrayList<>();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		
		LocalDate now = LocalDate.of(2017, Month.OCTOBER, 6);
		Mockito.doReturn(now).when(service).obterDataAgora();
		
		// cenario
		/* - configurado no setup
		 service = new LocacaoService();
		 usuario = new Usuario("usuario 1");
		 filme = new Filme("Filme 1", 2, 5.0);
		 */

		// acao
		filmes.add(FilmeBuilder.umFilme().comValor(5.0).agora());
		Locacao locacao = service.alugarFilme(usuario, filmes);


		Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
		Assert.assertThat(locacao.getDataLocacao(), CoreMatchers.is(now));
		Assert.assertThat(locacao.getDataRetorno(), CoreMatchers.is(now.plusDays(1)));

		//PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
		
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabadoUtilizandoMatcherProprio() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		LocalDate now = LocalDate.of(2017, Month.OCTOBER, 7);
		Mockito.doReturn(now).when(service).obterDataAgora();
		
		filmes.add(FilmeBuilder.umFilme().agora());
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean retornoNaSegunda = DayOfWeek.from(retorno.getDataRetorno()).equals(DayOfWeek.MONDAY);
		Assert.assertTrue(retornoNaSegunda);
		
		Assert.assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		filmes.add(FilmeBuilder.umFilme().agora());
		Class<LocacaoService> clazz = LocacaoService.class;
		Method calcularValorLocacao = clazz.getDeclaredMethod("calcularValorLocacao", List.class);
		calcularValorLocacao.setAccessible(true);
		Double valorLocacao = (Double) calcularValorLocacao.invoke(service, filmes);
		
		Assert.assertThat(valorLocacao, CoreMatchers.is(4.0));
	}
	
}
