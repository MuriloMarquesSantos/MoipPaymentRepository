package com.moip.pagamento.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moip.pagamento.model.PaymentMethod;
import com.moip.pagamento.repository.PaymentMethodRepository;

/** Classe responsável por controlar as operações referentes a PaymentMethod
 * 
 * @author Murilo
 *
 */

@RestController
@RequestMapping("/paymentmethod")
public class PaymentMethodController {
	
	@Autowired
	private PaymentMethodRepository pmr;
	
	/** Retorna uma lista de todos os PaymentMethod
	 * 
	 * @return Iterable<PaymentMethod> - Lista com todos os PaymentMethod do DB.
	 */
	
	@GetMapping(produces="application/json")
	public @ResponseBody Iterable<PaymentMethod> listaPaymentMethods() {
		Iterable<PaymentMethod> listaPaymentMethods = pmr.findAll();
		return listaPaymentMethods;
	}
	
	/** Cadastra um novo PaymentMethod
	 * 
	 * @param paymentMethod
	 * @return PaymentMethod - PaymentMethod cadastrado
	 */
	
	@PostMapping()
	public PaymentMethod cadastrarPaymentMethod(@RequestBody @Valid PaymentMethod paymentMethod) {
		return pmr.save(paymentMethod);
	}
	
	/** Deleta um PaymentMethod do sistema
	 * 
	 * @param paymentMethod
	 * @return PaymentMethod - PaymentMethod deletado do DB.
	 */
	
	@DeleteMapping()
	public PaymentMethod deletarPayment(@RequestBody PaymentMethod paymentMethod) {
		pmr.delete(paymentMethod);
		return paymentMethod;
	}
}
