package nodo.erp.Mm.Shipping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShippingRepository extends JpaRepository<Shipping, Integer>{
	Page<Shipping> findAll(Pageable pageable);
	Page<Shipping> findAll(Specification<Shipping> spec, Pageable pageable);
	
	Page<Shipping> findBySpstate(Pageable pageable, Shipping state);
}
