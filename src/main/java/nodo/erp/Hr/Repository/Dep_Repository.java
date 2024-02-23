package nodo.erp.Hr.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Department;


public interface Dep_Repository extends JpaRepository<Department, Integer> {
	Optional<Department> findByDepcode(String depcode);
	
	Page<Department> findAll(Pageable pageable);
    Page<Department> findAll(Specification<Department> spec, Pageable pageable);
    
	
}
