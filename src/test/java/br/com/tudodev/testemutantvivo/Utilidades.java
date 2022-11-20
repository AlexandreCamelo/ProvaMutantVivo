package br.com.tudodev.testemutantvivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tudodev.testemutantvivo.service.VoltaAoMundoService;

@Component
public class Utilidades {

	@Autowired
	VoltaAoMundoService voltaServ;
	
	
	public String pegaCodHeroiQualquer() {
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
