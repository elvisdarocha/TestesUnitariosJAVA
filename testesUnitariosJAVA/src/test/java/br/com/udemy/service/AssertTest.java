package br.com.udemy.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.udemy.model.Usuario;

public class AssertTest {
	
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		Assert.assertNull(null);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals(0.51234, 0.512, 0.001);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		//Assert.assertEquals(i, i2);
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertEquals("bola", "bola");
		//Assert.assertEquals("bola", "Bola");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u = new Usuario("U");
		Usuario u2 = new Usuario("U");
		
		Assert.assertEquals(u, u2);
		
		//Assert.assertSame(u, u2);
		Assert.assertSame(u2, u2);
		
	}

}
