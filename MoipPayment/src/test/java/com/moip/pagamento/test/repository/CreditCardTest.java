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
import com.moip.pagamento.model.CreditCard;
import com.moip.pagamento.model.Holder;
import com.moip.pagamento.repository.CreditCardRepository;

/** Testes unitários da classe CreditCard.
 * 
 * @author Murilo
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class CreditCardTest {

	@Autowired
	private CreditCardRepository creditCardRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Testa e persistência de dados
	 */
	
	@Test
	public void createShouldPersistData() {	
		CreditCard creditCard = new CreditCard();
		creditCard.setBrand("visa");
		creditCard.setCardNumber("1091092342345621");
		creditCard.setCvv("123");
		creditCard.setHolder(new Holder());
		this.creditCardRepository.save(creditCard);
		Assertions.assertThat(creditCard.getId()).isNotNull();
		Assertions.assertThat(creditCard.getBrand()).isNotNull();
		Assertions.assertThat(creditCard.getCardNumber()).isNotNull();
		Assertions.assertThat(creditCard.getCvv()).isNotNull();
		Assertions.assertThat(creditCard.getHolder()).isNotNull();
		}
	
	/**
	 * Testa a remoção  de dados
	 */
	
	@Test
	public void deleteShouldRemoveData() {
		CreditCard creditCard = new CreditCard();
		creditCard.setBrand("visa");
		creditCard.setCardNumber("1091092342345621");
		creditCard.setCvv("123");
		creditCard.setHolder(new Holder());
		this.creditCardRepository.save(creditCard);
		this.creditCardRepository.delete(creditCard);
		Assertions.assertThat(creditCardRepository.findById(creditCard.getId())).isEmpty();
	}
	
	/**
	 * Testa a atualização de dados
	 */
	
	@Test
	public void updateShouldChangeAndPersistData() {
		CreditCard creditCard = new CreditCard();
		creditCard.setBrand("visa");
		creditCard.setCardNumber("1091092342345621");
		creditCard.setCvv("123");
		creditCard.setHolder(new Holder());
		this.creditCardRepository.save(creditCard);
		creditCard.setBrand("Master");
		this.creditCardRepository.save(creditCard);
		Assertions.assertThat(creditCard.getBrand().equals("Master"));
	}
	
	/**
	 * Testa a violação da Constraint "NOT NULL" do atributo "creditCardNumber"
	 */
	
	@Test
	public void createWhenNumberIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		CreditCard creditCard = new CreditCard();
		creditCard.setBrand("visa");
		creditCard.setCvv("123");
		creditCard.setHolder(new Holder());
		this.creditCardRepository.save(creditCard);
				
	}
}
