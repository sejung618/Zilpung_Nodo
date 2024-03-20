package nodo.erp.Sd.Orders;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrRepository extends  JpaRepository<Orders, Integer>{

	List<Orders> findAll();
	
	//페이지 구현
	Page<Orders> findAll(Pageable pageable);
	
	//검색 옵션으로 페이지 구현(발주신청날짜, 발주제품명, 발주회사, 발주번호)
	Page<Orders> findByOrdateContaining(Pageable pageable, String kw);
	Page<Orders> findByOritemContaining(Pageable pageable, String kw);
	Page<Orders> findByOrcompanyContaining(Pageable pageable, String kw);
	Page<Orders> findByOrnumContaining(Pageable pageable, String kw);
	
	//전체 옵션에서 검색
	Page<Orders> findByOrdateContainingOrOritemContainingOrOrcompanyContainingOrOrnumContaining(Pageable pageable, String kw1, String kw2, String kw3, String kw4); 
}