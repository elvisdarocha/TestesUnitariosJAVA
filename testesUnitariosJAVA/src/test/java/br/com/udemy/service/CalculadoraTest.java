package br.com.udemy.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.udemy.exception.NaoPodeDividirPorZeroException;
import br.com.udemy.runners.ParallelRunner;

@RunWith(ParallelRunner.class)
public class CalculadoraTest {
	
	public static StringBuffer ordem = new StringBuffer();
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		 calc = new Calculadora();
		 System.out.println("inicializando...");
		 ordem.append("1");
	}
	
	@After
	public void tearDown() {
		 System.out.println("finalizando...");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println(ordem.toString());
	}
	
	@Test
	public void deveSomarDoisValores() throws InterruptedException {
		//cenario
		int a = 5;
		int b = 3;
		Thread.sleep(1000);
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
	
	@Test
	public void deveDividir() {
		String a = "6";
		String b = "3";
		
		int resultado = calc.divide(a, b);
		Assert.assertEquals(2, resultado);
	}

}
