package br.com.tudodev.testemutantvivo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tudodev.testemutantvivo.models.MelhorTempoHeroiDTO;
import br.com.tudodev.testemutantvivo.models.Volta;
import br.com.tudodev.testemutantvivo.models.VoltaDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class VoltaAoMundoService {

	private final String PATH_FILE = "voltaAoMundo.log";

	public List<Volta> leArquivo() {
		List<Volta> listaDeVoltas = new ArrayList<>();

		try (FileReader fr = new FileReader(PATH_FILE)) {
			BufferedReader br = new BufferedReader(fr);
			DateTimeFormatter formatoHora = DateTimeFormatter
					.ofPattern("HH:m:ss.SSS");

			String linha = br.readLine(); // primeira linha ignorada
			while ((linha = br.readLine()) != null) {
				Volta volta = new Volta();
				String[] dadosLinha = linha.split(";");

				volta.setHora(LocalTime.parse(dadosLinha[0], formatoHora));
				volta.setCodHeroi(pegaCodHeroi(dadosLinha[1]));
				volta.setNomeHeroi(pegaNomeHeroi(dadosLinha[1]));
				volta.setVolta(Integer.parseInt(dadosLinha[2]));
				volta.setTempoVolta(LocalTime
						.parse(formataTempoVolta(dadosLinha[3]), formatoHora));
				volta.setTempoVoltaNano(Long.parseLong(
						String.valueOf(volta.getTempoVolta().getNano())));
				volta.setVelMedia(formataVelMedia(dadosLinha[4]));
				listaDeVoltas.add(volta);
			}

			return listaDeVoltas;

		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<VoltaDTO> resultadoGeral() {
		List<Volta> result = leArquivo();
		return posicaoChegada(result);
	}

	public List<VoltaDTO> posicaoChegada(List<Volta> voltas) {
		List<VoltaDTO> listaFinal = new ArrayList<>();

		List<Volta> listaOrdenada = voltas.stream()
				.filter(volta -> volta.getVolta() == 4).toList();

		for (Volta v : listaOrdenada) {
			v.setTempoTotal(tempoTotalPorHeroi(voltas, v.getCodHeroi()));
		}

		List<Volta> listaChegada = listaOrdenada.stream()
				.sorted(Comparator.comparing(Volta::getHora)).toList();

		ModelMapper modelMapper = new ModelMapper();
		VoltaDTO voltaDTO = new VoltaDTO();

		for (Volta v : listaChegada) {
			voltaDTO = modelMapper.map(v, VoltaDTO.class);
			listaFinal.add(voltaDTO);
		}

		return listaFinal;
	}

	private LocalTime tempoTotalPorHeroi(List<Volta> voltas, String codHeroi) {
		LocalTime tempo = voltas.get(0).getTempoVolta();

		List<Volta> listaVoltasHerois = voltas.stream()
				.filter(volta -> volta.getCodHeroi().equals(codHeroi)).toList();

		for (int i = 1; i < listaVoltasHerois.size(); i++) {
			tempo = tempo.plusSeconds(
					listaVoltasHerois.get(i).getTempoVolta().getSecond());
			tempo = tempo.plusMinutes(
					listaVoltasHerois.get(i).getTempoVolta().getMinute());
			tempo = tempo.plusMinutes(
					listaVoltasHerois.get(i).getTempoVolta().getHour());
		}

		return tempo;
	}

	// Utilizando StringBuilder, pois, por algum motivo, o 'String.split'
	// não estava dividindo as Strings pelo caractere "-"
	public String pegaCodHeroi(String codNomeHeroi) {
		try {
			StringBuilder sbCod = new StringBuilder(codNomeHeroi);
			return sbCod.substring(0, 3);
		} catch (NumberFormatException e) {
			log.warn("O código do herói " + codNomeHeroi
					+ " não foi formatado. Talvez alguns resultados sejam comprometidos.");
			e.printStackTrace();
			return "";
		}
	}

	// Utilizando StringBuilder, pois, por algum motivo, o 'String.split'
	// não estava dividindo as String pelo caractere "-"
	private String pegaNomeHeroi(String codNomeHeroi) {
		StringBuilder sbNome = new StringBuilder(codNomeHeroi);
		return sbNome.substring(4);
	}

	private String formataTempoVolta(String tempoVolta) {
		return "00:0" + tempoVolta;
	}

	private Integer formataVelMedia(String velMedia) {
		String vel = velMedia.replaceAll(",", "");
		if (vel.length() < 5) {
			vel = vel + "0";
		}

		try {
			int intVel = Integer.parseInt(vel);
			return intVel;
		} catch (NumberFormatException e) {
			log.warn(
					"A velocidade média não foi formatada. Talvez alguns resultados sejam comprometidos.");
			e.printStackTrace();
			return 0;
		}
	}

	public ResponseEntity<MelhorTempoHeroiDTO> melhorVoltaHeroi(
			String codHeroi) {
		try {
			log.warn("O código do herói é: " + codHeroi);
			List<Volta> todasAsVoltas = leArquivo();
			List<Volta> listaPorHeroi = todasAsVoltas.stream()
					.filter(h -> h.getCodHeroi().equals(codHeroi)).toList();
			List<Volta> melhores = listaPorHeroi.stream()
					.sorted(Comparator.comparing(Volta::getTempoVolta))
					.toList();
			ModelMapper modelMapper = new ModelMapper();
			MelhorTempoHeroiDTO melhorTempoHeroiDTO = modelMapper
					.map(melhores.get(0), MelhorTempoHeroiDTO.class);
			return ResponseEntity.status(200).body(melhorTempoHeroiDTO);
		} catch (ArrayIndexOutOfBoundsException e) {
			log.warn("O herói de código " + codHeroi + " não foi encontrado.");
			e.printStackTrace();
			return ResponseEntity.status(404).build();
		}
	}

	public MelhorTempoHeroiDTO melhorVoltaCorrida() {
		List<Volta> todasAsVoltas = leArquivo();
		List<Volta> melhores = todasAsVoltas.stream()
				.sorted(Comparator.comparing(Volta::getTempoVolta)).toList();
		MelhorTempoHeroiDTO melhorTempoHeroiDTO = new MelhorTempoHeroiDTO();
		ModelMapper modelMapper = new ModelMapper();
		melhorTempoHeroiDTO = modelMapper.map(melhores.get(0),
				MelhorTempoHeroiDTO.class);
		return melhorTempoHeroiDTO;
	}

	public ResponseEntity<Integer> velMediaHeroi(String codHeroi) {
		int contador = 0;
		int velMedia = 0;
		List<Volta> todasAsVoltas = leArquivo();
		List<Volta> listaPorHeroi = todasAsVoltas.stream()
				.filter(h -> h.getCodHeroi().equals(codHeroi)).toList();

		for (Volta v : listaPorHeroi) {
			contador++;
			velMedia += v.getVelMedia();
		}

		try {

			if (contador == 0)
				return ResponseEntity.status(400).body(0);

			return ResponseEntity.status(200).body(velMedia / contador);

		} catch (ArithmeticException e) {
			return ResponseEntity.status(400).body(0);
		}
	}

}
