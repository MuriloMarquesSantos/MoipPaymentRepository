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

import com.moip.pagamento.model.Buyer;
import com.moip.pagamento.repository.BuyerRepository;

/**Classe responsável por controlar operações e requisições referentes ao Buyer
 * 
 * @author Murilo
 *
 */

@RestController
public class BuyerController {
	
	@Autowired
	private BuyerRepository br;
	
	/**Método para retornar todos os compradores armazaneados
	 * 
	 * @return Iterable<Buyer> - Lista de compradores
	 */
	
	@GetMapping(path="/buyer", produces="application/json")
	public @ResponseBody Iterable<Buyer> listaBuyers() {
		Iterable<Buyer> listaBuyers = br.findAll();
		return listaBuyers;
	}
	
	/**Método para encontrar um comprador de acordo com seu ID
	 * 
	 * @param id - Id do Buyer procurado
	 * @return ResponseEntity - Juntamente com a resposta negativa, ou objeto encontrado
	 */
	@GetMapping("/buyer/{id}")
	public ResponseEntity getBuyer(@PathVariable("id") Long id){
		System.out.println("Entrei aqui");
			List<Buyer> listaBuyer = br.findAll();
			Buyer buyerProcurado = null;
			
			for(Buyer buyer: listaBuyer){
				if(buyer.getId()==id) {
					buyerProcurado = buyer;
				}
			}
			
			if(buyerProcurado == null) {
				return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity(buyerProcurado, HttpStatus.OK);
	}
	
	/** Método para cadastrar um novo comprador
	 * 
	 * @param buyer
	 * @return Buyer - Retorna o comprador salvo
	 */
	
	@PostMapping()
	public Buyer cadastrarBuyer(@RequestBody @Valid Buyer buyer) {
		return br.save(buyer);
	}
	
	/** Método para deletar um comprador do banco de dados
	 * 
	 * @param buyer
	 * @return Buyer - Retornar o comprador deletado.
	 */
	
	
	@DeleteMapping()
	public Buyer deletarBuyer(@RequestBody Buyer buyer) {
		br.delete(buyer);
		return buyer;
	}
}
