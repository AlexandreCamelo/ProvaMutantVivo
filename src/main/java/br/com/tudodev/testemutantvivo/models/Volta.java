package br.com.tudodev.testemutantvivo.models;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Volta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private LocalTime hora;
	private String codHeroi;
	private String nomeHeroi;
	private Integer volta;
	private LocalTime tempoVolta;
	private Long tempoVoltaNano;
	private Integer velMedia;
	private LocalTime tempoTotal;

}
