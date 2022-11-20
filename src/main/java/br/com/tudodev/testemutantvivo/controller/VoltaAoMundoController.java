package br.com.tudodev.testemutantvivo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tudodev.testemutantvivo.models.MelhorTempoHeroiDTO;
import br.com.tudodev.testemutantvivo.models.VoltaDTO;
import br.com.tudodev.testemutantvivo.service.VoltaAoMundoService;

@RestController
@RequestMapping("/")
public class VoltaAoMundoController {

	@Autowired
	VoltaAoMundoService serv;

	
	@GetMapping("resultadoGeral")
	public ResponseEntity<List<VoltaDTO>> resultado() {
		return ResponseEntity.status(200).body(serv.resultadoGeral());
	}
	
	@GetMapping("melhorVolta/{codHeroi}")
	public ResponseEntity<MelhorTempoHeroiDTO> melhorVolta(@PathVariable String codHeroi) {
		return ResponseEntity.status(200).body(serv.melhorVoltaHeroi(codHeroi));
	}
	
	@GetMapping("melhorVoltaCorrida")
	public ResponseEntity<MelhorTempoHeroiDTO> melhorVoltaCorrida() {
		return ResponseEntity.status(200).body(serv.melhorVoltaCorrida());
	}

	
	@GetMapping("velMedia/{codHeroi}")
	public ResponseEntity<Integer> velMedia(@PathVariable String codHeroi) {
		return ResponseEntity.status(200).body(serv.velMediaHeroi(codHeroi));
	}

	

	
	
}
