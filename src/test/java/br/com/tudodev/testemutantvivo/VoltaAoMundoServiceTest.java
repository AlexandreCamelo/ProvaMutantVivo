package br.com.tudodev.testemutantvivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tudodev.testemutantvivo.models.MelhorTempoHeroiDTO;
import br.com.tudodev.testemutantvivo.models.Volta;
import br.com.tudodev.testemutantvivo.models.VoltaDTO;
import br.com.tudodev.testemutantvivo.service.VoltaAoMundoService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class VoltaAoMundoServiceTest {

	@Autowired
	VoltaAoMundoService voltaServ;

	private List<Volta> listaDeVoltas;
	private String codHeroiQualquer = "";

	@BeforeAll
	public void preencheListaDeVoltas() {
		listaDeVoltas = voltaServ.leArquivo();
	}

	@BeforeAll
	public void preencheCodHeroi() {
		codHeroiQualquer = pegaCodHeroiQualquer();
	}
	
	
	
	@Test
	@DisplayName("leArquivo should be return ListGreater than zero")
	void leArquivoShouldBeReturnListGreaterThanZero() {
		// List<Volta> voltas = voltaServ.leArquivo();
		Assertions.assertTrue(listaDeVoltas.size() > 0);
	}

	@Test
	@DisplayName("resultadoGeral should be return ListGreater than zero")
	void resultadoGeralShouldBeReturnListGreaterThanZero() {
		List<VoltaDTO> voltas = voltaServ.resultadoGeral();
		Assertions.assertTrue(voltas.size() > 0);
	}

	@Test
	@DisplayName("posicaoChegada should be return ListGreater than zero")
	void posicaoChegadaShouldBeReturnListGreaterThanZero() {
		List<VoltaDTO> voltas = voltaServ.posicaoChegada(listaDeVoltas);
		Assertions.assertTrue(voltas.size() > 0);
	}

	@Test
	@DisplayName("posicaoChegada should return list in ascending order")
	void posicaoChegadaShouldReturnListInAscendingOrder() {
		List<String> listaOrdemMetodo = new ArrayList<>();
		List<String> listaOrdemTeste = new ArrayList<>();

		List<VoltaDTO> listaRetornada = voltaServ.posicaoChegada(listaDeVoltas);
		listaRetornada.forEach(v -> listaOrdemMetodo.add(v.getCodHeroi()));

		List<Volta> listaTeste = listaDeVoltas.stream()
				.filter(volta -> volta.getVolta() == 4).toList();
		listaTeste = listaTeste.stream()
				.sorted(Comparator.comparing(Volta::getHora)).toList();
		listaTeste.forEach(v -> listaOrdemTeste.add(v.getCodHeroi()));

		Assertions.assertTrue(listaOrdemMetodo.equals(listaOrdemTeste));
	}

	@Test
	@DisplayName("melhorVoltaHeroi should return value equal to this test")
	void melhorVoltaHeroiShouldReturnValueEqualToThisTest() {
		
		MelhorTempoHeroiDTO tempoRetornadoMetodo = voltaServ
				.melhorVoltaHeroi(codHeroiQualquer);

		
		List<Volta> listaPorHeroi = listaDeVoltas.stream()
				.filter(h -> h.getCodHeroi().equals(codHeroiQualquer)).toList();
		List<Volta> melhores = listaPorHeroi.stream()
				.sorted(Comparator.comparing(Volta::getTempoVolta)).toList();

		ModelMapper modelMapper = new ModelMapper();
		MelhorTempoHeroiDTO tempoRetornadoTeste = modelMapper.map(melhores.get(0),
				MelhorTempoHeroiDTO.class);

		Assertions.assertTrue(tempoRetornadoMetodo.equals(tempoRetornadoTeste));
	}

	
	
	@Test
	@DisplayName("melhorVoltaCorrida should return value equal to this test")
	void melhorVoltaCorridaShouldReturnValueEqualToThisTest() {
		MelhorTempoHeroiDTO melhorVoltaMetodo = voltaServ.melhorVoltaCorrida();
		
		List<Volta> melhores = listaDeVoltas.stream()
				.sorted(Comparator.comparing(Volta::getTempoVolta)).toList();
		ModelMapper modelMapper = new ModelMapper();
		MelhorTempoHeroiDTO melhorVoltaTeste = modelMapper.map(melhores.get(0),
				MelhorTempoHeroiDTO.class);
		
		
		Assertions.assertTrue(melhorVoltaMetodo.equals(melhorVoltaTeste));
	}
	
	
	@Test
	@DisplayName("velMediaHeroi should be grater than zero")
	void velMediaHeroiShouldBeGraterThanZero() {
		int velocidadeMediaMetodo = 0;
		velocidadeMediaMetodo = voltaServ.velMediaHeroi(codHeroiQualquer);
		Assertions.assertTrue(velocidadeMediaMetodo > 0);
	}
	
	
	@Test
	@DisplayName("velMediaHeroi should return value equal to this test")
	void velMediaHeroiShouldReturnValueEqualToThisTest() {
		int velocidadeMediaMetodo = 0;
		int velocidadeMediaTeste = 0;
		int contador = 0;
		int velMedia = 0;
		
		
		velocidadeMediaMetodo = voltaServ.velMediaHeroi(codHeroiQualquer);
		
		
		
		List<Volta> todasAsVoltas = listaDeVoltas;
		List<Volta> listaPorHeroi = todasAsVoltas.stream()
				.filter(h -> h.getCodHeroi().equals(codHeroiQualquer)).toList();

		for (Volta v : listaPorHeroi) {
			contador++;
			velMedia += v.getVelMedia();
		}

		if (contador == 0)
			velocidadeMediaTeste = 0;

		velocidadeMediaTeste = velMedia / contador;
		
		Assertions.assertTrue(velocidadeMediaMetodo == velocidadeMediaTeste);
	}
	
	
	
	
	private String pegaCodHeroiQualquer() {
		Random r = new Random();
		int linhaaleatoria = r.nextInt(5) + 1;
		int contador = 0;

		try (FileReader fr = new FileReader(voltaServ.getPATH_FILE())) {
			BufferedReader br = new BufferedReader(fr);
			String linha = br.readLine(); // primeira linha ignorada
			while ((linha = br.readLine()) != null) {
				contador++;
				String[] dadoslinha = linha.split(";");

				if (contador == linhaaleatoria) {
					return voltaServ.pegaCodHeroi(dadoslinha[1]);
				}
			}
			
			return "0";

		} catch (IOException e) {
			e.printStackTrace();
			return "0";
		}
	}

}
