package nodo.erp.Sd.Sales;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

	List<Sales> findAll();
	
	Page<Sales> findAll(Pageable pageable);
	
	Page<Sales> findBySanumContaining(Pageable pageable, String kw);
	Page<Sales> findBySaitemContaining(Pageable pageable, String kw);
	Page<Sales> findBySamethodContaining(Pageable pageable, String kw);
	
	Page<Sales> findBySanumContainingOrSaitemContainingOrSamethodContaining(Pageable pageable, String kw1, String kw2, String kw3);
	
	
}
