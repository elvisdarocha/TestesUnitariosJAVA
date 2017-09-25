package br.com.udemy.matchers;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DiaSemanaMatcher extends TypeSafeMatcher<LocalDate> {
	
	private DayOfWeek diaDaSemana;
	
	public DiaSemanaMatcher(DayOfWeek diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(diaDaSemana.toString());
	}

	@Override
	protected boolean matchesSafely(LocalDate data) {
		return DayOfWeek.from(data).equals(diaDaSemana);
	}

}
