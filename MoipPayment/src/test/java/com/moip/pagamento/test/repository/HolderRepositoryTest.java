package com.moip.pagamento.test.repository;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moip.pagamento.model.Holder;
import com.moip.pagamento.repository.HolderRepository;

/** Testes unitário da classe Holder
 * 
 * @author Murilo
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class HolderRepositoryTest {

	@Autowired
	private HolderRepository holderRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Testa a persistência de dados
	 */
	
	@Test
	public void createShouldPersistData() {	
		Holder holder = new Holder();
		holder.setBirthDate("19-04-2000");
		holder.setDocumentNumber("123123123");
		holder.setName("Lara");
		this.holderRepository.save(holder);
		Assertions.assertThat(holder.getBirthDate()).isNotNull();
		Assertions.assertThat(holder.getDocumentNumber()).isNotNull();
		Assertions.assertThat(holder.getName()).isNotNull();
	}
	
	/**
	 * Testa a remoção de dados
	 */
	
	@Test
	public void deleteShouldRemoveData() {
		Holder holder = new Holder();
		holder.setBirthDate("19-04-2000");
		holder.setDocumentNumber("123123123");
		holder.setName("Lara");
		this.holderRepository.save(holder);
		this.holderRepository.delete(holder);
		Assertions.assertThat(holderRepository.findById(holder.getId())).isEmpty();
	}
	
	/**
	 * Testa a atualização de dados
	 */
	
	@Test
	public void updateShouldChangeAndPersistData() {
		Holder holder = new Holder();
		holder.setBirthDate("19-04-2000");
		holder.setDocumentNumber("123123123");
		holder.setName("Lara");
		this.holderRepository.save(holder);
		holder.setName("newHolder");
		this.holderRepository.save(holder);
		Assertions.assertThat(holder.getName().equals("newHolder"));
	}
	
}
