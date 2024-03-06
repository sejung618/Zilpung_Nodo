package nodo.erp.Pp.Bom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BomRepository extends JpaRepository<Bom, Integer> {
	
	Page<Bom> findAll(Pageable pageable);
	
}
