package br.edu.atitus.paradigma.cambio_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.paradigma.cambio_service.clients.CotacaoClient;
import br.edu.atitus.paradigma.cambio_service.clients.CotacaoResponse;
import br.edu.atitus.paradigma.cambio_service.entities.CambioEntity;
import br.edu.atitus.paradigma.cambio_service.repositories.CambioRepository;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

	private final CambioRepository cambioRepository;
	private final CotacaoClient cotacaoBCB;

	public CambioController(CambioRepository cambioRepository, CotacaoClient cotacaoBCB) {
		super();
		this.cambioRepository = cambioRepository;
		this.cotacaoBCB = cotacaoBCB;
	}

	@Value("${server.port}")
	private int porta;

	@GetMapping("/{valor}/{origem}/{destino}")
	public ResponseEntity<CambioEntity> getCambio(@PathVariable double valor, @PathVariable String origem,
			@PathVariable String destino) throws Exception {

		CambioEntity cambio = cambioRepository.findByOrigemAndDestino(origem, destino)
				.orElseThrow(() -> new Exception("Câmbio não encontrado para esta origem e destino"));

		// Busca a cotação de origem
		CotacaoResponse cotacaoOrigem = cotacaoBCB.getCotacao(origem, "10-10-2024");
		if (destino.equals("BRL")) { 
			// Se o destino é o Real (BRL) então a cotação retornada é o nosso Fator de conversão
			double fator = cotacaoOrigem.getValue().get(0).getCotacaoVenda();
			cambio.setFator(fator);
		} else {
			// Se o destino NÃO é o Real (BRL), então temos que fazer o cálculo, 
			//    já que a API do Banco Central retorna sempre a cotação em relação a moeda brasileira
			CotacaoResponse cotacaoDestino = cotacaoBCB.getCotacao(destino, "10-10-2024");
			double fator = cotacaoOrigem.getValue().get(0).getCotacaoVenda()
								/ cotacaoDestino.getValue().get(0).getCotacaoVenda();
			cambio.setFator(fator);
		}

		cambio.setValorConvertido(valor * cambio.getFator());
		cambio.setAmbiente("Cambio-Service run in port: " + porta);
		
		return ResponseEntity.ok(cambio);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", " ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cleanMessage);
	}

}
