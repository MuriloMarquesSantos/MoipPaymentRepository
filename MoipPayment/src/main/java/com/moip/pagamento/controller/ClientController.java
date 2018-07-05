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

import com.moip.pagamento.model.Client;
import com.moip.pagamento.repository.ClientRepository;

/** Classe responsável por controlar as operações e requisições referentes ao Cliente
 * 
 * @author Murilo
 *
 */

@RestController
@RequestMapping("/client")
public class ClientController {
	@Autowired
	private ClientRepository cr;
	
	/** Método para retornar uma lista com todos os clientes do banco de dados
	 * 
	 * @return Iterable<Client> - Todos os clientes do DB.
	 */
	
	@GetMapping(produces="application/json")
	public @ResponseBody Iterable<Client> listaClients() {
		Iterable<Client> listaClients = cr.findAll();
		return listaClients;
	}
	
	/** Método para cadastrar um cliente
	 * 
	 * @param client - Cliente a ser armazenado
	 * @return Cliente - Cliente armazenado
	 */
	
	@PostMapping()
	public Client cadastrarClient(@RequestBody @Valid Client client) {
		return cr.save(client);
	}
	
	/** Método para deletar um cliente do banco de dados
	 * 
	 * @param client - Cliente a ser deletado
	 * @return Client - Cliente deletado do DB.
	 */
		
	@DeleteMapping()
	public Client deletarClient(@RequestBody Client client) {
		cr.delete(client);
		return client;
	}
}
