package com.moip.pagamento.test.controller;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moip.pagamento.controller.PaymentController;
import com.moip.pagamento.model.Boleto;
import com.moip.pagamento.model.Buyer;
import com.moip.pagamento.model.CreditCard;

import junit.framework.Assert;

/**
 * 
 * @author Classe teste para o controlador de pagamentos, testa alguns dos principais
 * métodos.
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentControllerTest {

	
	private PaymentController paymentController;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createBoleto() {	
		Boleto boleto = new Boleto();
		paymentController = new PaymentController();
		paymentController.criarBoleto();
		boleto.setCode("1231231244124124");
		boleto.setExpirationDate("10-10-2040");
		Assertions.assertThat(boleto.getCode()).isNotNull();
	}
	
	@Test
	public void cardNumberValidation() {
		CreditCard creditCard = new CreditCard();
		paymentController = new PaymentController();
		creditCard.setCardNumber("1231230920393456");
		paymentController.validarNumeroCartao(creditCard.getCardNumber());
		Assertions.assertThat(paymentController.validarNumeroCartao(creditCard.getCardNumber()));
	}
	
	@Test
	public void codeVValition() {
		paymentController = new PaymentController();
		CreditCard creditCard = new CreditCard();
		creditCard.setCvv("123");
		Assertions.assertThat(paymentController.validarDV(creditCard.getCvv()));
	}
	
}
