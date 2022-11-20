package br.com.tudodev.testemutantvivo.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Element implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer elemento;
	private Integer vezesEncontradas;
}
