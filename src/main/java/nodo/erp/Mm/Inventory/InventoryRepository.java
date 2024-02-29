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
	
	Page<Inventory> findByINDate(Pageable pageable, String kw);
	
	Page<Inventory> findByININame(Pageable pageable, String kw);
	
	Page<Inventory> findByINICode(Pageable pageable, String kw);
	
}

