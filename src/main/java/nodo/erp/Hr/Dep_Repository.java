package nodo.erp.Hr;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Dep_Repository extends JpaRepository<Hr_Dto_Dep, Integer> {
	Optional<Hr_Dto_Dep> findByDepcode(String depcode);
	
	Page<Hr_Dto_Dep> findAll(Pageable pageable);
    Page<Hr_Dto_Dep> findAll(Specification<Hr_Dto_Dep> spec, Pageable pageable);
    
	
}
