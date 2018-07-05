package com.moip.pagamento.test.repository;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moip.pagamento.model.Buyer;
import com.moip.pagamento.model.Client;
import com.moip.pagamento.model.Payment;
import com.moip.pagamento.model.Payment.PaymentStatus;
import com.moip.pagamento.model.PaymentMethod;
import com.moip.pagamento.repository.PaymentRepository;

/** Testes unitários da classe Payment
 * 
 * @author Murilo
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryTest {

	@Autowired
	private PaymentRepository paymentRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Testa a persistência de dados
	 */
	
	@Test
	public void createShouldPersistData() {	
		Payment payment = new Payment(2000.00, new PaymentMethod(),PaymentStatus.CREATED,new Buyer(), new Client());
		this.paymentRepository.save(payment);
		Assertions.assertThat(payment.getId()).isNotNull();
		Assertions.assertThat(payment.getAmount()).isNotNull();
		Assertions.assertThat(payment.getBuyer()).isNotNull();
		Assertions.assertThat(payment.getAmount()).isNotNull();
		Assertions.assertThat(payment.getPaymentMethod()).isNotNull();
		Assertions.assertThat(payment.getStatus()).isNotNull();
	}
	
	/**
	 * Testa a remoção de dados
	 */
	
	@Test
	public void deleteShouldRemoveData() {
		Payment payment = new Payment(2000.00, new PaymentMethod(),PaymentStatus.CREATED,new Buyer(), new Client());
		this.paymentRepository.save(payment);
		this.paymentRepository.delete(payment);
		Assertions.assertThat(paymentRepository.findById(payment.getId())).isEmpty();
	}
	
	/**
	 * Testa a atualização de dados
	 */
	
	@Test
	public void updateShouldChangeAndPersistData() {
		Payment payment = new Payment(2000.00, new PaymentMethod(),PaymentStatus.CREATED,new Buyer(), new Client());
		this.paymentRepository.save(payment);
		payment.setAmount(3000.00);
		this.paymentRepository.save(payment);
		Assertions.assertThat(payment.getAmount()==3000.00);
	}
	
	/**
	 * Testa a violação da constraint "NOT NULL" do atributo PaymentMethod
	 */
	
	@Test
	public void createWhenMethodIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		Payment payment = new Payment(2000.00,PaymentStatus.CREATED,new Buyer(), new Client());
		this.paymentRepository.save(payment);
				
	}
}
