package br.com.tudodev.testemutantvivo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tudodev.testemutantvivo.models.Element;
import br.com.tudodev.testemutantvivo.service.FindElementsService;

@RestController
@RequestMapping("/")
public class FindElementsController {

	
	@Autowired
	FindElementsService elemService;
	
	@PostMapping("findElements")
	public ResponseEntity<List<Element>> procurarElementos(@RequestBody int[] el) {
		return ResponseEntity.status(200).body(elemService.elementosEncontrados(el));
	}

}

