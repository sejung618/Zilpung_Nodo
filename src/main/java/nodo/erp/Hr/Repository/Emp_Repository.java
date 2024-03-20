package nodo.erp.Hr.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Hr.Entity.Employee;

import java.util.List;
import java.util.Optional;




public interface Emp_Repository extends JpaRepository<Employee, Integer>{

	Page<Employee> findAll(Pageable pageable);
    Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);
    List<Employee> findAll(Specification<Employee> spec);
    
   
    
    Optional<Employee> findByEmpnum(String empnum);
}
