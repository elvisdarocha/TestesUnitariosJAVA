package br.com.udemy.service;

import static br.com.udemy.matchers.MatchersProprios.caiNumaSegunda;
import static br.com.udemy.matchers.MatchersProprios.ehHoje;
import static br.com.udemy.matchers.MatchersProprios.ehHojeComDiferencaDias;

import java.util.Date;
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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.com.udemy.builders.FilmeBuilder;
import br.com.udemy.builders.UsuarioBuilder;
import br.com.udemy.dao.LocacaoDAO;
import br.com.udemy.exception.FilmeSemEstoqueException;
import br.com.udemy.exception.LocadoraException;
import br.com.udemy.matchers.DataDiferencaDiasMatcher;
import br.com.udemy.matchers.MatchersProprios;
import br.com.udemy.model.Filme;
import br.com.udemy.model.Locacao;
import br.com.udemy.model.Usuario;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class, LocacaoServicePowerMockitoTest.class, DataDiferencaDiasMatcher.class})
public class LocacaoServicePowerMockitoTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@InjectMocks
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
		service = PowerMockito.spy(service);
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabadoUtilizandoMatcherProprio() throws LocadoraException, FilmeSemEstoqueException {
		//cenario
		LocalDate now = LocalDate.of(2017, Month.OCTOBER, 7);
		PowerMockito.mockStatic(LocalDate.class); 
		PowerMockito.when(LocalDate.now()).thenReturn(now);
		
		filmes.add(FilmeBuilder.umFilme().agora());
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean retornoNaSegunda = DayOfWeek.from(retorno.getDataRetorno()).equals(DayOfWeek.MONDAY);
		Assert.assertTrue(retornoNaSegunda);
		
		Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(DayOfWeek.MONDAY));
		
		Assert.assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		
		LocalDate now = LocalDate.of(2017, Month.OCTOBER, 6);
		PowerMockito.mockStatic(LocalDate.class); 
		PowerMockito.when(LocalDate.now()).thenReturn(now);
		
		Date hoje = new Date();
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(hoje);
		
		// cenario
		/* - configurado no setup
		 service = new LocacaoService();
		 usuario = new Usuario("usuario 1");
		 filme = new Filme("Filme 1", 2, 5.0);
		 */

		// acao
		filmes.add(FilmeBuilder.umFilme().comValor(5.0).agora());
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
		
		//PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
		
	}
	
	@Test
	public void deveAlugarFilmeSemCalcularValor() throws Exception {
		
		//cenario
		filmes.add(FilmeBuilder.umFilme().agora());
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		//locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(1.0));
		PowerMockito.verifyPrivate(service, Mockito.atLeast(1)).invoke( "calcularValorLocacao", filmes);
		//PowerMockito.verifyPrivate(service, Mockito.calls(2)).invoke( "calcularValorLocacao", filmes);
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		filmes.add(FilmeBuilder.umFilme().agora());
		Double valorLocacao = Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
		Assert.assertThat(valorLocacao, CoreMatchers.is(4.0));
	}
	
}
