package br.com.udemy.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**Testa a order dos testes caso um metodo dependa do resultado de outro*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderTest {
	
	public static int contator = 0;
	
	@Test
	public void inicia() {
		contator = 1;
	}
	
	@Test
	public void verifica() {
		Assert.assertEquals(1, contator);
	}
	
	@Test
	public void testGeral() {
		this.inicia();
		this.verifica();
	}

}
