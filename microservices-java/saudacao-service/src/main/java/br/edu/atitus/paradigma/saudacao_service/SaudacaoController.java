package br.edu.atitus.paradigma.saudacao_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("saudacao-service")
public class SaudacaoController {

	@GetMapping({"","/"})
	public ResponseEntity<String> getSaudacao(
			@RequestParam(required = false) String nome){
		String template = "%s %s!";
		if (nome == null) nome = "World";
		return ResponseEntity.ok(
				String.format(template, "Hello", nome));
	}
}
