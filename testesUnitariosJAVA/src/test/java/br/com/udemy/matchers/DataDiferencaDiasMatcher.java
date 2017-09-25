package br.com.udemy.matchers;

import java.time.LocalDate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<LocalDate> {

	private Long quantidadeDeDias;
	
	public DataDiferencaDiasMatcher(Long quantidadeDeDias) {
		this.quantidadeDeDias = quantidadeDeDias;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText(LocalDate.now().plusDays(quantidadeDeDias).toString());
	}

	@Override
	protected boolean matchesSafely(LocalDate data) {
		LocalDate hoje = LocalDate.now();
		hoje = hoje.plusDays(quantidadeDeDias);
		return data.equals(hoje);
	}

}
