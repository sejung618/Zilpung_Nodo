package nodo.erp.Mm.Warehousing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface WarehousingRepository extends JpaRepository<Warehousing, Integer>{
	Page<Warehousing> findAll(Pageable pageable);
	Page<Warehousing> findAll(Specification<Warehousing> spec, Pageable pageable);
}
