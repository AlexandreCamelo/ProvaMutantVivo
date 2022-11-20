package br.com.tudodev.testemutantvivo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.tudodev.testemutantvivo.controller.VoltaAoMundoController;
import br.com.tudodev.testemutantvivo.models.MelhorTempoHeroiDTO;
import br.com.tudodev.testemutantvivo.models.VoltaDTO;
import br.com.tudodev.testemutantvivo.service.VoltaAoMundoService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class VoltaAoMundoControllerTest {

	@Autowired
	VoltaAoMundoController voltaCtrl;
	
	@Autowired
	VoltaAoMundoService voltaServ;

	
	@Autowired
	Utilidades utilidades;

	private ResponseEntity<List<VoltaDTO>> respostaResultados;
	private ResponseEntity<MelhorTempoHeroiDTO> respostaMelhorVolta;
	private ResponseEntity<MelhorTempoHeroiDTO> respostaMelhorVoltaCorrida;
	private ResponseEntity<Integer> respostaVelMedia;
	private String codHeroiQualquer;


	@BeforeAll
	void preencheRespostaMelhorVolta() {
		codHeroiQualquer = utilidades.pegaCodHeroiQualquer();
		respostaResultados = voltaCtrl.resultado();
		respostaMelhorVolta = voltaCtrl.melhorVolta(codHeroiQualquer);
		respostaMelhorVoltaCorrida = voltaCtrl.melhorVoltaCorrida();
		respostaVelMedia = voltaCtrl.velMedia(codHeroiQualquer);
	}

	@Test
	@DisplayName("resultado status should be 200")
	void resultadoStatusShouldBe200() {
		Assertions.assertTrue(
				respostaResultados.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("resultado body must be greater than zero")
	void resultadoBodyMustBeGreaterThanZero() {
		Assertions.assertTrue(respostaResultados.getBody().size() > 0);
	}

	
	
	@Test
	@DisplayName("melhorVolta status should be 200")
	void melhorVoltaStatusShouldBe200() {
		Assertions.assertTrue(
				respostaMelhorVolta.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("melhorVolta body must contain correct informations")
	void melhorVoltaBodyMustContainCorrectInformations() {
		Assertions.assertTrue(respostaMelhorVolta.getBody().getVolta() > 0
				&& respostaMelhorVolta.getBody().getTempoVolta() != null);
	}

	@Test
	@DisplayName("melhorVoltaCorrida status should be 200")
	void melhorVoltaCorridaStatusShouldBe200() {
		Assertions.assertTrue(
				respostaMelhorVoltaCorrida.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("melhorVoltaCorrida body must contain correct informations")
	void melhorVoltaCorridaBodyMustContainCorrectInformations() {
		Assertions.assertTrue(respostaMelhorVoltaCorrida.getBody().getVolta() > 0
				&& respostaMelhorVoltaCorrida.getBody().getTempoVolta() != null);
	}

	@Test
	@DisplayName("velMedia status should be 200")
	void velMediaStatusShouldBe200() {
		Assertions.assertTrue(
				respostaVelMedia.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("velMedia should be greater than zero")
	void velMediaShouldBeGreaterThanZero() {
		Assertions.assertTrue(respostaVelMedia.getBody() > 0);
	}
}
