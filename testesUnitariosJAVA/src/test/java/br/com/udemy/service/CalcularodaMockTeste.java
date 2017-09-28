package br.com.udemy.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class CalcularodaMockTeste {

	@Test
	public void teste() {
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		Mockito.when(calculadora.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		Assert.assertThat(calculadora.somar(1, 3), CoreMatchers.is(5));
	}
	
}
