package br.com.music.tests.entities;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.music.entities.Banda;





public class BandaTests {
	@Test
	public void BandaDeveriaCriarObjetoComDadosCorretosQuandoDadosValidos() {
		Banda banda = new Banda();
		Assertions.assertTrue(banda.equals(banda));
	}
}
