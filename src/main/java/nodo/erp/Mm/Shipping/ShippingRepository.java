package nodo.erp.Mm.Shipping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
	Page<Shipping> findAll(Pageable pageable);

//	Page<Shipping> findAll(Specification<Shipping> spec, Pageable pageable);

	Page<Shipping> findBySpdateContaining(Pageable pageable, String kw);

	Page<Shipping> findByAccount_AccompanyContaining(Pageable pageable, String kw);

	Page<Shipping> findByItem_ItmNameContaining(Pageable pageable, String kw);

	Page<Shipping> findBySpdateContainingOrAccount_AccompanyContainingOrItem_ItmNameContaining(Pageable pageable, String kw1, String kw2, String kw3);

	Page<Shipping> findBySpstate(Pageable pageable, String st);
}
