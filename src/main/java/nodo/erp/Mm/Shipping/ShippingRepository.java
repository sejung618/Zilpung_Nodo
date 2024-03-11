package nodo.erp.Mm.Shipping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
	Page<Shipping> findAll(Pageable pageable);

//	Page<Shipping> findAll(Specification<Shipping> spec, Pageable pageable);

	Page<Shipping> findBySpdateContaining(Pageable pageable, String kw);

	Page<Shipping> findByAccount_AccompanyContaining(Pageable pageable, String kw);

	Page<Shipping> findByItem_ItmNameContaining(Pageable pageable, String kw);
	
	Page<Shipping> findByEmployee_EmpnameContaining(Pageable pageable, String kw);
	
	Page<Shipping> findByEmployee_EmpnumContaining(Pageable pageable, String kw);

	Page<Shipping> findBySpdateContainingOrAccount_AccompanyContainingOrItem_ItmNameContainingOrEmployee_EmpnameContainingOrEmployee_EmpnumContaining(Pageable pageable,
			String kw1, String kw2, String kw3, String kw4, String kw5);

	Page<Shipping> findBySpstate(Pageable pageable, String st);

	Page<Shipping> findBySpstateAndItem_ItmNameContaining(String state, String kw, Pageable pageable);

}
