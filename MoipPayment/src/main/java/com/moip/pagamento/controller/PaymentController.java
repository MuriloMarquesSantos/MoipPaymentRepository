package com.moip.pagamento.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.moip.pagamento.model.Boleto;
import com.moip.pagamento.model.CreditCard;
import com.moip.pagamento.model.Payment;
import com.moip.pagamento.model.Payment.PaymentStatus;
import com.moip.pagamento.repository.BuyerRepository;
import com.moip.pagamento.repository.ClientRepository;
import com.moip.pagamento.repository.PaymentRepository;

/** Classe responsável por gerenciar as operações e requisições referentes aos Pagamentos
 * 
 * @author Murilo
 *
 */

@RestController
public class PaymentController {
	
	
	
	String resposta = "";
	
	//Construção da String de resposta para o usuário
	
	StringBuilder stringBuilder = new StringBuilder(resposta);
	
	@Autowired
	private PaymentRepository pr;
	
	@Autowired
	private CreditCardController ccc;
	
	@Autowired
	private BoletoController bc;
	
	/** Retorna uma lista com todos os Pagamentos do DB.
	 * 
	 * @return - Iterable<Payment> - Lista de todos os pagamentos do DB.
	 */
	
	@GetMapping(path="/payment", produces="application/json")
	public @ResponseBody Iterable<Payment> listaPayments() {
		Iterable<Payment> listaPayments = pr.findAll();
		return listaPayments;
	}
	
	/** Método que busca e retorna um pagamento de acordo com seu ID. Caso não seja
	 * encontrado, retorna o erro.
	 * 
	 * @param id - Id do pagamento buscado
	 * @return ResponseEntity - Com objeto buscado ou resposta negativa
	 */
	@GetMapping("/payment/{id}")
	public ResponseEntity getPayment(@PathVariable("id") Long id){
			List<Payment> listaPayment = pr.findAll();
			Payment paymentProcurado = null;
			
			for(Payment payment: listaPayment){
				if(payment.getId()==id) {
					paymentProcurado = payment;
				}
			}
			
			if(paymentProcurado == null) {
				return new ResponseEntity("No Payment found for ID " + id, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity(paymentProcurado, HttpStatus.OK);
	}
	
	/** Cadastra o pagamento. Define se o pagamento é boleto ou cartão de crédito.
	 * Se for Boleto, retorna um número pré-definido. Se for cartão de crédito,
	 * passará por algumas validações.
	 * 
	 * @param payment - Pagamento preenchido no corpo da request.
	 * @return String - Retorna uma String de resposta.
	 * @throws Exception
	 */
	
	@PostMapping(path="/payment")
	public String cadastrarPayment(@RequestBody @Valid Payment payment) throws Exception {

		this.stringBuilder.delete(0, stringBuilder.length());
		
		//Se o pagamento for boleto, o método cria um código e uma data e retorna para o usuário.
		
		if(payment.getPaymentMethod().getMethod().name().equals("BOLETO")) {
			payment.getPaymentMethod().setBoleto(this.criarBoleto());
			payment.setStatus(PaymentStatus.CREATED);
			try {
				stringBuilder.append("/ Pagamento cadastrado com sucesso!! //");
				return this.stringBuilder + "\nCódigo do boleto: " +
						pr.save(payment).getPaymentMethod().getBoleto().getCode() +
						" \nData de Vencimento: " +
						payment.getPaymentMethod().getBoleto().getExpirationDate();
			}
			catch(Exception e) {
				return "Erro";
			}
		}
		
		/**
		 * Verificar se: 1) Cartão já foi cadastrado Anteriormente 2)Amount é valido, 
		 * 3) Número do Cartão é valido e 4) CVV é válido
		 */
		
		else if(!(creditCardExists(payment.getPaymentMethod().getCreditCard())) &&
				validarAmount(payment.getAmount()) &&
				validarCartao(payment.getPaymentMethod().getCreditCard().getCardNumber(),
						      payment.getPaymentMethod().getCreditCard().getCvv())) {
	
				payment.setStatus(PaymentStatus.CREATED);
				stringBuilder.append("/ Pagamento cadastrado com sucesso!! //");
				return this.stringBuilder + " Status atual: " + pr.save(payment).getStatus().name();					
		}
		return "Erros encontrados: " + this.stringBuilder;	
		
	}
	
	/** Deleta um Pagamento do banco de dados
	 * 
	 * @param pagamento - Pagamento a ser deletado do DB.
	 * @return Pagamento - Pagamento deletado do DB.
	 */

	@DeleteMapping(path="/payment")
	public Payment deletarPayment(@RequestBody Payment payment) {
		pr.delete(payment);
		return payment;
	}
	
	/** Criação do Boleto, caso o método de pagamento desejado seja este
	 * 
	 * @return Boleto - Retorna o boleto com seu número preenchido
	 */
		
	public Boleto criarBoleto() {
		return bc.createBoleto("239398762934239398762934239398762934239398762934",
								"30-12-2018");
	}
	
	/** Consulta controlador de cartão de crédito para verificar se número é valido
	 * 
	 * @param cartaoNumero
	 * @return boolean - Resultado da validação (True or False)
	 */
	
	public boolean validarNumeroCartao(String cartaoNumero) {
		if(ccc.validarNumeroCartao(cartaoNumero)) {
			return true;
		} 
		stringBuilder.append(" /Cartão inválido. Por favor, o código do Cartão deve possuir 16 carácteres numéricos (0-9) / ");
		return false;
	}
	
	/** Consulta controlador de cartão de crédito para verificar se DV é válido
	 * 
	 * @param dv
	 * @return boolean - Resultado da validação (True or False)
	 */
	
	public boolean validarDV(String dv) {
		if(ccc.validarDV(dv)){
			return true;
			
		}
		stringBuilder.append(("/ Digito verificador inválido / "));
		return false;
	}
	
	/** Método que engloba as validações do cartão de crédito
	 * 
	 * @param cartaoNumero
	 * @param dv
	 * @return boolean - Resultado da validação (True or False)
	 */
	
	public boolean validarCartao(String cartaoNumero, String dv) {
		return(validarNumeroCartao(cartaoNumero) && validarDV(dv));
	}
	
	/** Verificar se o valor do pagamento é válido
	 * 
	 * @param amount
	 * @return boolean - Resultado do teste (true or false)
	 * @throws InvalidFormatException
	 */
	
	public boolean validarAmount(double amount) throws InvalidFormatException{
		if(amount < 1000000.00){
			return true;
			
		}
		stringBuilder.append(("/ Amount inválido, o valor máximo é R$1.000.000 / "));
		return false;
	}
	
	/** Verifica se o número de cartão já foi cadastrado anteriormente
	 * 
	 * @param creditCard
	 * @return
	 */
	
	public boolean creditCardExists(CreditCard creditCard) {
		if(ccc.creditCardExists(creditCard)) {
			stringBuilder.append(("Negado. Cartão já cadastrado anteriormente."));
			return true;
		}
		return false;
	}
	

	}
	


