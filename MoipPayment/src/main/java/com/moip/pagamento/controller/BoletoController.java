package com.moip.pagamento.controller;

import org.springframework.web.bind.annotation.RestController;

import com.moip.pagamento.model.Boleto;

/** Classe responsável por controlar operações e requisições referentes aos Boletos
 * 
 * @author Murilo
 *
 */

@RestController
public class BoletoController {
	
	public Boleto createBoleto(String code, String expDate) {
		Boleto boleto = new Boleto();
		boleto.setCode(code);
		boleto.setExpirationDate(expDate);
		
		return boleto;
		
	}

}
