package nodo.erp.Hr.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Spot;

public interface Spot_Repository extends JpaRepository<Spot, Integer>{
	
	Page<Spot> findAll(Pageable pageable);
    Page<Spot> findAll(Specification<Spot> spec, Pageable pageable);
}
