package br.com.tudodev.testemutantvivo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.tudodev.testemutantvivo.controller.FindElementsController;
import br.com.tudodev.testemutantvivo.models.Element;

@SpringBootTest
public class FindElementsControllerTest {

	@Autowired
	FindElementsController findElem;
	
	final int[] DEFAULT_PARAM = {1,2,3,4,5,6,7,8,9};
	
	@Test
	@DisplayName("procurarElementos should be return status 200")
	void procurarElementosShouldBeReturnStatus200() {
		ResponseEntity<List<Element>> resposta = findElem.procurarElementos(DEFAULT_PARAM);
		Assertions.assertTrue(resposta.getStatusCode().is2xxSuccessful());
	}
	
	
	
	@Test
	@DisplayName("procurarElementos should return a list with the same size as the parameter")
	void procurarElementosShouldReturnAListWithTheSameSizeAsTheParameter() {
		int tamanhoParametro = DEFAULT_PARAM.length;
		int tamanhoResposta = 0;
		
		ResponseEntity<List<Element>> resposta = findElem.procurarElementos(DEFAULT_PARAM);
		tamanhoResposta = resposta.getBody().size();
		
		Assertions.assertTrue(tamanhoParametro == tamanhoResposta);
	}
	
	
}
