package nodo.erp.Sd;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nodo.erp.Mm.Inventory.Inventory;

public interface AccRepository extends JpaRepository<Account, Integer> {

	List<Account> findAll();
	
	// 페이지 구현
	Page<Account> findAll(Pageable pageable);
	
	
	 //검색 옵션으로 페이지 구현(회사 주소, 회사명, 품목명, 첫 거래날짜)
	Page<Account> findByAcaddressContaining(Pageable pageable, String kw);
	Page<Account> findByAcitemContaining(Pageable pageable , String kw);
	Page<Account> findByAcdateContaining(Pageable pageable, String kw);
	Page<Account> findByAccompanyContaining(Pageable pageable, String kw);
	
	//전체 옵션에서 검색
	Page<Account> findByAcaddressContainingOrAcitemContainingOrAcdateContainingOrAccompanyContaining(Pageable pageable, String kw1, String kw2, String kw3, String kw4);

	//Page<Account> findByAcaddressContaining(org.springframework.data.domain.Pageable pageable, String kw);
	
	
	/*
		//전체옵션
		Page<Inventory> findByIndateContainingOrItem_ItmNameContainingOrItem_ItmCodeContaining(Pageable pageable, String kw1, String kw2, String kw3);
	 */
}