package nodo.erp.Pp.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	Page<Item> findAll(Pageable pageable);
	Page<Item> findAll(Specification<Item> sItem, Pageable pageable);
	
//	Page<Item> findByItmGroup(Pageable pageable, String kw);
//	Page<Item> findByItmCategory(Pageable pageable, String kw);	
//	Page<Item> findByItmName(Pageable pageable, String kw);
	
}
