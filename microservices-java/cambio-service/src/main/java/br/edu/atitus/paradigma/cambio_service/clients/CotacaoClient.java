package br.edu.atitus.paradigma.cambio_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "olinda-bcb", url = "https://olinda.bcb.gov.br")
public interface CotacaoClient {
	
	@GetMapping("/olinda/servico/PTAX/versao/v1/odata/CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)?@moeda='{moeda}'&@dataCotacao='{dataCotacao}'&$format=json")
	CotacaoResponse getCotacao(
			@PathVariable String moeda, 
			@PathVariable String dataCotacao);

}
