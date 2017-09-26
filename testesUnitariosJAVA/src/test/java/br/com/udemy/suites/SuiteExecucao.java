package br.com.udemy.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.udemy.service.CalculadoraTeste;
import br.com.udemy.service.CalculoValorLocacaoTeste;
import br.com.udemy.service.LocacaoServiceTest;

//@RunWith(Suite.class)
/*e melhor executar o projeto inteiro com o JUnit, ao inves de fazer uma suite
 * Pq quando incluir uma nova classe, sempre e necessario atulizar a suite
 * e as ferramentas de build vao executar duplicado os testes*/
@SuiteClasses({
	CalculadoraTeste.class,
	CalculoValorLocacaoTeste.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {

	@BeforeClass
	public static void setupClass() {
		System.out.println("Setup Class");
	}
	
	@AfterClass
	public static void teardownClass() {
		System.out.println("TearDown Class");
	}
}
