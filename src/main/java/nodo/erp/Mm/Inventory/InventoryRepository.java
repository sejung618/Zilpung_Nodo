package nodo.erp.Mm.Inventory;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	Inventory findByINQuantity(Integer INQuantity);
	List<Inventory> findByININameLike(String inname);
	Page<Inventory> findAll(Pageable pageable);
	
//	Page<Inventory> findAll(Specification<Inventory> spec, Pageable pageable);
	
	Page<Inventory> findByINDateContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByININameContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByINICodeContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByINDateContainingOrININameContainingOrINICodeContaining(Pageable pageable, String kw1, String kw2, String kw3);
}

