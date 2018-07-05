package com.moip.pagamento.test.repository;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moip.pagamento.model.Client;
import com.moip.pagamento.repository.ClientRepository;

/** Testes unitários da classe Client
 * 
 * @author Murilo
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository clientRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Testa a persistência de dados
	 */
	
	@Test
	public void createShouldPersistData() {	
		Client client = new Client();
		client.setName("Google");
		this.clientRepository.save(client);
		Assertions.assertThat(client.getId()).isNotNull();
		Assertions.assertThat(client.getName()).isNotNull();
		}
	
	/**
	 * Testa a remoção de dados
	 */
	
	@Test
	public void deleteShouldRemoveData() {
		Client client = new Client();
		client.setName("Google");
		this.clientRepository.save(client);
		this.clientRepository.delete(client);
		Assertions.assertThat(clientRepository.findById(client.getId())).isEmpty();
	}
	
	/**
	 * Testa a atualização de dados
	 */
	
	
	@Test
	public void updateShouldChangeAndPersistData() {
		Client client = new Client();
		client.setName("Google");
		this.clientRepository.save(client);
		client.setName("Facebook");
		this.clientRepository.save(client);
		Assertions.assertThat(client.getName().equals("Facebook"));
	}
	
}
