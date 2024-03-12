package nodo.erp.Mm.Warehousing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import nodo.erp.Mm.Shipping.Shipping;

public interface WarehousingRepository extends JpaRepository<Warehousing, Integer> {
	Page<Warehousing> findAll(Pageable pageable);
//	Page<Warehousing> findAll(Specification<Warehousing> spec, Pageable pageable);

	Page<Warehousing> findByWhdateContaining(Pageable pageable, String kw);

	Page<Warehousing> findByAccount_AccompanyContaining(Pageable pageable, String kw);

	Page<Warehousing> findByItem_ItmNameContaining(Pageable pageable, String kw);

	Page<Warehousing> findByEmployee_EmpnameContaining(Pageable pageable, String kw);

	Page<Warehousing> findByEmployee_EmpnumContaining(Pageable pageable, String kw);

	Page<Warehousing> findByWhdateContainingOrAccount_AccompanyContainingOrItem_ItmNameContainingOrEmployee_EmpnameContainingOrEmployee_EmpnumContaining(
			Pageable pageable, String kw1, String kw2, String kw3, String kw4, String kw5);

	Page<Warehousing> findByWhstate(Pageable pageable, String st);
}
