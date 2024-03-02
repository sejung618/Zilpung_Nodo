package nodo.erp.Pp.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	
//	Item findByItmId(Integer ItmId);
//	List<Item> findByItmName(String ItmName);
	Page<Item> findAll(Pageable pageable);
	
//	Page<Item> findByItmName(Pageable pageable, String kw);
//	Page<Item> findByItmGroup(Pageable pageable, String kw);
//	Page<Item> findByItmCategory(Pageable pageable, String kw);	
	
}
