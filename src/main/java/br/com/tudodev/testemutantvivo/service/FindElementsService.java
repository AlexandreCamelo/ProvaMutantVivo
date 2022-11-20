package br.com.tudodev.testemutantvivo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import br.com.tudodev.testemutantvivo.models.Element;
import lombok.Data;

@Service
@Data
public class FindElementsService {
	private int[][] matrizBipmap;
	private final int QTDE_INSERCOES = 50;
	private final int SIZE_FIRST_DIM = 20;
	private final int SIZE_SECOND_DIM = 20;
	private final int MAX_SIZE_PARAM = 15;

	public FindElementsService() {
		preencherMatriz();
	}

	private void preencherMatriz() {
		matrizBipmap = new int[SIZE_FIRST_DIM][SIZE_SECOND_DIM];
		Random rdn = new Random();

		for (int i = 0; i < QTDE_INSERCOES; i++) {
			int p1 = rdn.nextInt(19);
			int p2 = rdn.nextInt(19);
			int elemento = rdn.nextInt(15);
			matrizBipmap[p1][p2] = elemento;
		}
	}

	public List<Element> elementosEncontrados(int[] an) {
		int[] newAn = an;
		
		if (newAn.length == 0)
			return new ArrayList<>();
		
		if(newAn.length > MAX_SIZE_PARAM) {
			newAn = ajustarTamanhoArray(an);
		}

		final int tam1MatBipmap = matrizBipmap.length;
		final int tam2MatBipmap = matrizBipmap[0].length;
		List<Element> listaRetorno = new ArrayList<>();
		int encontrado;

		for (int elemento : newAn) {
			encontrado = 0;

			for (int p1 = 0; p1 < tam1MatBipmap; p1++) {

				for (int p2 = 0; p2 < tam2MatBipmap; p2++) {
					int valor = matrizBipmap[p1][p2];

					if (valor == elemento) {
						encontrado++;
					}
				}

			}

			Element el = new Element();
			el.setElemento(elemento);
			el.setVezesEncontradas(encontrado);
			listaRetorno.add(el);
		}

		return listaRetorno;
	}

	private int[] ajustarTamanhoArray(int[] an) {
		if(an == null || an.length == 0) {
			return new int[] {};
		}
		
		int[] newArray = new int[MAX_SIZE_PARAM];
		
		for(int i = 0; i < MAX_SIZE_PARAM; i++) {
			newArray[i] = an[i];
		}
		
		return newArray;
	}

}