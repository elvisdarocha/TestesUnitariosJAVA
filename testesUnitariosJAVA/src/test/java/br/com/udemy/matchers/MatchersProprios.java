package br.com.udemy.matchers;

import java.time.DayOfWeek;

public class MatchersProprios {
	
	public static DiaSemanaMatcher caiEm(DayOfWeek diaDaSemana) {
		return new DiaSemanaMatcher(diaDaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(DayOfWeek.MONDAY);
	}
	
	public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(Long quantidadeDeDias) {
		return new DataDiferencaDiasMatcher(quantidadeDeDias);
	}
	
	public static DataDiferencaDiasMatcher ehHoje() {
		return new DataDiferencaDiasMatcher(0L);
	}
	

}
