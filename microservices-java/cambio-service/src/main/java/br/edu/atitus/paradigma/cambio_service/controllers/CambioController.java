package br.edu.atitus.paradigma.cambio_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.paradigma.cambio_service.entities.CambioEntity;
import br.edu.atitus.paradigma.cambio_service.repositories.CambioRepository;

@RestController
@RequestMapping("cambio-service")
public class CambioController {
	
	private final CambioRepository cambioRepository;
	//private final CotacaoBCBClient cotacaoBCB;

	public CambioController(CambioRepository cambioRepository
			//, CotacaoBCBClient cotacaoBCB
			) {
		super();
		this.cambioRepository = cambioRepository;
		//this.cotacaoBCB = cotacaoBCB;
	}
	
	@Value("${server.port}")
	private int porta;
	
	@GetMapping("/{valor}/{origem}/{destino}")
	public ResponseEntity<CambioEntity> getCambio(
			@PathVariable double valor,
			@PathVariable String origem,
			@PathVariable String destino) throws Exception {
		
		CambioEntity cambio = cambioRepository.findByOrigemAndDestino(origem, destino)
				.orElseThrow(() -> new Exception("Câmbio não encontrado para esta origem e destino"));
		
		//Aqui temos o exemplo para buscar a cotação na API  do Banco Central
		//   Esse exemplo só vai funcionar corretamente caso a conversão seja de Dólar para Real (USD -> BRL)
		//   Outras conversões seriam necessário melhorar o código
		//   Vamos deixar desabilitado - servirá apenas como exemplo
		//ListaCotacaoResponse cotacao = cotacaoBCB.getCotacao("USD", "10-10-2024");
		//cambio.setFator(cotacao.getValue().get(0).getCotacaoVenda());
		
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
