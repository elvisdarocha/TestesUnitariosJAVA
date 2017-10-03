package br.com.udemy.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.udemy.builders.UsuarioBuilder;


public class CalcularodaMockTeste {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Spy
	private EmailService email;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	/**Diferenca entre mock e spy
	 * Mock quando nao configurado o when, retorna o valor padrao(null, 0, ect)
	 * Spy quando nao configurado o when, executa o metodo*/
	public void deveMostrarDiferencaEntreMockESpy() {
		Mockito.when(calcMock.somar(1, 3)).thenReturn(5);
		Mockito.when(calcMock.somar(1, 7)).thenCallRealMethod();
		//Mockito.doNothing().when(calcSpy).imprime();
		/*Quando utilizado o spy, o metodo é executado quando chamado o when, imprindo no console*/
		Mockito.when(calcSpy.somar(1, 3)).thenReturn(5);
		//Para nao executar o codigo do metodo, utilizar o do return
		Mockito.doReturn(5).when(calcSpy.somar(1, 3));
		
		System.out.println("Mock:" + calcMock.somar(1, 3));
		System.out.println("Spy:" + calcSpy.somar(1, 3));
		
		System.out.print("Mock: ");
		calcMock.imprime();
		System.out.print("Spy: ");
		calcSpy.imprime();
	}

	@Test
	public void teste() {
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		
		Mockito.when(calculadora.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);
		
		Assert.assertThat(calculadora.somar(1, 3), CoreMatchers.is(5));
		//System.out.println(argCapt.getAllValues());
		
	}
	
}
