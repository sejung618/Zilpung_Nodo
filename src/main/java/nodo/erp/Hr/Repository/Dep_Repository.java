package nodo.erp.Hr.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Dep_Entity;


public interface Dep_Repository extends JpaRepository<Dep_Entity, Integer> {
	Optional<Dep_Entity> findByDepcode(String depcode);
	
	Page<Dep_Entity> findAll(Pageable pageable);
    Page<Dep_Entity> findAll(Specification<Dep_Entity> spec, Pageable pageable);
    
	
}
