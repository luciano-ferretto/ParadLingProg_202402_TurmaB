package br.edu.atitus.paradigma.cambio_service.clients;

import java.util.List;

public class ListaCotacaoResponse {

	private List<CotacaoResponse> value;

	public List<CotacaoResponse> getValue() {
		return value;
	}

	public void setValue(List<CotacaoResponse> value) {
		this.value = value;
	}
	
}
