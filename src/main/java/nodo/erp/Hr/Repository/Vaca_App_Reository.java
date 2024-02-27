package nodo.erp.Hr.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.VacationApply;

public interface Vaca_App_Reository extends JpaRepository<VacationApply, Integer>{

	Page<VacationApply> findAll(Pageable pageable);
    Page<VacationApply> findAll(Specification<VacationApply> spec, Pageable pageable);
    
	
}
