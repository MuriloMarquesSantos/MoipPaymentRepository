package com.moip.pagamento.test.repository;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moip.pagamento.model.Buyer;
import com.moip.pagamento.repository.BuyerRepository;

/** Testes unitários da Classe Buyer
 * 
 * @author Murilo
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class BuyerRepositoryTest {

	@Autowired
	private BuyerRepository buyerRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	/**
	 * Testa a persistência de dados com a classe Buyer
	 */
	@Test
	public void createShouldPersistData() {	
		Buyer buyer = new Buyer();
		buyer.setCpf("432402304203");
		buyer.setNome("buyer");
		buyer.setEmail("buyer@buyer.com");
		this.buyerRepository.save(buyer);
		Assertions.assertThat(buyer.getId()).isNotNull();
		Assertions.assertThat(buyer.getCpf()).isNotNull();
		Assertions.assertThat(buyer.getNome()).isNotNull();
		Assertions.assertThat(buyer.getEmail()).isNotNull();	
	}
	
	/**
	 * Testa a remoção de dados
	 */
	
	@Test
	public void deleteShouldRemoveData() {
		Buyer buyer = new Buyer();
		buyer.setCpf("432402304203");
		buyer.setNome("buyer");
		buyer.setEmail("buyer@buyer.com");
		this.buyerRepository.save(buyer);
		this.buyerRepository.delete(buyer);
		Assertions.assertThat(buyerRepository.findById(buyer.getId())).isEmpty();
	}
	
	/**
	 * Testa o update de dados
	 */
	
	@Test
	public void updateShouldChangeAndPersistData() {
		Buyer buyer = new Buyer();
		buyer.setCpf("432402304203");
		buyer.setNome("buyer");
		buyer.setEmail("buyer@buyer.com");
		this.buyerRepository.save(buyer);
		buyer.setNome("newBuyer");
		this.buyerRepository.save(buyer);
		Assertions.assertThat(buyer.getNome().equals("newBuyer"));
	}
	
}
