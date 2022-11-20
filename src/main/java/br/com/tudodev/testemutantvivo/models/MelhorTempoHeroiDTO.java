package br.com.tudodev.testemutantvivo.models;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MelhorTempoHeroiDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String codHeroi;
	private String nomeHeroi;
	private Integer volta;
	private LocalTime tempoVolta;
	
}
