package br.com.udemy.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.udemy.exception.NaoPodeDividirPorZeroException;

public class CalculadoraTeste {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		 calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.somar(a, b);
		
		//verificacao
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//c
		int a = 8;
		int b = 5;
		
		//a
		int resultado = calc.subtrair(a, b);
		
		//v
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//c
		int a = 10;
		int b = 5;
		//a
		int resultado = calc.dividir(a, b);
		//v
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected=NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		//c
		int a = 10;
		int b = 0;
		//a
		calc.dividir(a, b);
	}

}
