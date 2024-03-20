package nodo.erp.Sd.Reservation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findAll();
	
	//페이지 구현
	Page<Reservation> findAll(Pageable pageable);
	
	// 검색 옵션으로 페이지
	Page<Reservation> findByRvitemContaining(Pageable pageable, String kw);
	
	Page<Reservation> findByRvnumContaining(Pageable pageable, String kw);
	
	Page<Reservation> findByRvnumContainingOrRvitemContaining(Pageable pageable, String kw1, String kw2); 
	
	 
}
