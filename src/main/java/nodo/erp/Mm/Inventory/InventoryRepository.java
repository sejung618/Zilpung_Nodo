package nodo.erp.Mm.Inventory;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Employee;


public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

	Page<Inventory> findAll(Pageable pageable);
	
	Page<Inventory> findByIndateContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByItem_ItmNameContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByItem_ItmCodeContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByEmployee_EmpnameContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByEmployee_EmpnumContaining(Pageable pageable, String kw);
	
	Page<Inventory> findByIndateContainingOrItem_ItmNameContainingOrItem_ItmCodeContainingOrEmployee_EmpnameContainingOrEmployee_EmpnumContaining(Pageable pageable, String kw1, String kw2, String kw3, String kw4, String kw5);
}