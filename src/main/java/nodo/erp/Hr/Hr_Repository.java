package nodo.erp.Hr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;



public interface Hr_Repository extends JpaRepository<Hr_Dto_Emp, Integer>{

	Page<Hr_Dto_Emp> findAll(Pageable pageable);
    Page<Hr_Dto_Emp> findAll(Specification<Hr_Dto_Emp> spec, Pageable pageable);
    
   
    
    Optional<Hr_Dto_Emp> findByEmpNum(String empNum);
}
