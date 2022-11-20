package br.com.tudodev.testemutantvivo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tudodev.testemutantvivo.models.Element;
import br.com.tudodev.testemutantvivo.service.FindElementsService;

@SpringBootTest
public class FindElementsServiceTest {
	
	
	@Autowired 
	FindElementsService findElem;
	
	@Test
	@DisplayName("Return of elementosEncontrados must be different from null")
	void elementosEncontradoShouldBeDifferentFromNull() {
		List<Element> elementos =  findElem.elementosEncontrados(new int[]{1,2,3,4,5});
		Assertions.assertTrue(elementos != null);
	}
	
	@Test
	@DisplayName("elementosEncontrados's size should be greater than zero")
	void elementosEncontradoSizeShouldBeGreaterThanZero() {
		List<Element> elementos =  findElem.elementosEncontrados(new int[]{1});
		Assertions.assertTrue(elementos.size() > 0);
	}
	
	@Test
	@DisplayName("Elem parameter must be less than or equal to stipulated")
	void elementosEncontradoParamShouldBeLessThanOrEqualToStipulated() {
		int testSize = findElem.getMAX_SIZE_PARAM() + 10;
		int[] parametro = new int[testSize];
		
		for(int i = 0; i < testSize; i++) {
			parametro[i] = i;
		}
		
		List<Element> elementos =  findElem.elementosEncontrados(parametro);
		Assertions.assertTrue(elementos.size() <= findElem.getMAX_SIZE_PARAM());
	}
	
	

}
