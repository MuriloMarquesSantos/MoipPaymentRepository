package com.moip.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moip.pagamento.model.Holder;

/** Interface que permite que utilizemos métodos de persistência simplificados do
 * JpaRepository
 * 
 * @author Murilo
 *
 */

public interface HolderRepository extends JpaRepository<Holder, Long> {

}
