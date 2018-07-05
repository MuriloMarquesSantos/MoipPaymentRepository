package com.moip.pagamento.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moip.pagamento.model.CreditCard;
import com.moip.pagamento.repository.CreditCardRepository;

/** Classe responsável por controlar operações e requisições referentes aos Cartões
 * 
 * @author Murilo
 *
 */

@RestController
@RequestMapping("/creditcard")
public class CreditCardController {
	
	@Autowired
	private CreditCardRepository cr;
	
	/** Retorna todos os cartões de crédito do DB.
	 * 
	 * @return Iterable<CreditCard> - Lista de cartões de crédito no DB.
	 */
	@GetMapping(produces="application/json")
	public @ResponseBody Iterable<CreditCard> listaCreditCards() {
		Iterable<CreditCard> listaCreditCards = cr.findAll();
		return listaCreditCards;
	}
	
	/** Cadastra um cartão de Crédito no DB.
	 * 
	 * @param creditCard - Cartão de crédito a ser armazenado
	 * @return CreditCard - Cartão de Crédito armazenado
	 */
	
	@PostMapping()
	public CreditCard cadastrarCreditCard(@RequestBody @Valid CreditCard creditCard) {
		

		return cr.save(creditCard);
	}
	
	/** Deleta um cartão de Crédito no DB.
	 * 
	 * @param creditCard - Cartão de crédito a ser deletado
	 * @return CreditCard - Cartão de Crédito deletado
	 */
	
	@DeleteMapping()
	public CreditCard deletarCreditCard(@RequestBody CreditCard creditCard) {
		cr.delete(creditCard);
		return creditCard;
	}
	
	/**Verificar se o cartão de crédito já existe no DB
	 * 
	 * @param creditCard
	 * @return
	 */
	
	public boolean creditCardExists(CreditCard creditCard) {
		List<CreditCard> crl = cr.findAll();
		
		for (CreditCard card : crl) {
			if(card.getCardNumber().equals(creditCard.getCardNumber())) {
				return true;
			}
		}
		return false;
	}
	
	/** Verifica se o número do cartão é válido
	 * 
	 * @param cartaoNumero
	 * @return boolean - Resultado do teste (true or false)
	 */
	public boolean validarNumeroCartao(String cartaoNumero) {
		if(cartaoNumero.length()== 16 && cartaoNumero.matches("[0-9]*")) {
			return true;
		} 
		return false;
	}
	
	/** Verifica se digito verificar é válido
	 * 
	 * @param dv
	 * @return boolean - Resultado do teste (true or false)
	 */
	
	public boolean validarDV(String dv) {
		if(dv.length()== 3 && dv.matches("[0-9]*")){
			return true;
			
		}
		return false;
	}
}
