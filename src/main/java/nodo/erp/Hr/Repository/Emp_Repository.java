package nodo.erp.Hr.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Emp_Entity;

import java.util.Optional;
import java.util.List;



public interface Emp_Repository extends JpaRepository<Emp_Entity, Integer>{

	Page<Emp_Entity> findAll(Pageable pageable);
    Page<Emp_Entity> findAll(Specification<Emp_Entity> spec, Pageable pageable);
    
   
    
    Optional<Emp_Entity> findByEmpnum(String empnum);
}
