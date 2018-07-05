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

import com.moip.pagamento.model.Holder;
import com.moip.pagamento.model.Holder;
import com.moip.pagamento.repository.HolderRepository;

/** Classe responsável por controlar operações e requisições referentes aos Holders
 * 
 * @author Murilo
 *
 */

@RestController
@RequestMapping("/holder")
public class HolderController {

	@Autowired
	private HolderRepository hr;
	
	/** Retorna uma lista com todos os Holders do DB.
	 * 
	 * @return - Iterable<Holder> - Lista de todos os holders do DB.
	 */
	
	@GetMapping(produces="application/json")
	public @ResponseBody Iterable<Holder> listaHolders() {
		Iterable<Holder> listaHolders = hr.findAll();
		return listaHolders;
	}
	
	/** Cadastra um Holder no banco de dados.
	 * 
	 * @param holder
	 * @return Holder - Holder Cadastrado no DB.
	 */
	
	@PostMapping()
	public Holder cadastrarHolder(@RequestBody @Valid Holder holder) {
		return hr.save(holder);
	}
	
	/** Deleta um holder do banco de dados
	 * 
	 * @param holder - Holder a ser deletado do DB.
	 * @return Holder - Holder deletado do DB.
	 */
	
	@DeleteMapping()
	public Holder deletarHolder(@RequestBody Holder holder) {
		hr.delete(holder);
		return holder;
	}
}
