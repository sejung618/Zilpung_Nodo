package nodo.erp.Sd.Purchase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurRepository extends JpaRepository<Purchase, Integer>{

	List<Purchase> findAll();
	
	//페이지 구현
	Page<Purchase> findAll(Pageable pageable);
	
	//검색 옵션으로 페이지 구현(구매번호, 구매날짜, 구매회사, 제품명)
	Page<Purchase> findByPcnumContaining(Pageable pageable, String kw);
	Page<Purchase> findByPcdateContaining(Pageable pageable, String kw);
	Page<Purchase> findByPccompanyContaining(Pageable pageable, String kw);
	Page<Purchase> findByPcitemContaining(Pageable pageable, String kw);
	
	//전체 옵션에서 검색
	Page<Purchase> findByPcnumContainingOrPcdateContainingOrPccompanyContainingOrPcitemContaining(Pageable pageable, String kw1, String kw2, String kw3, String kw4);	
}