package nodo.erp.Pp.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public List<Item> getList() {
		return this.itemRepository.findAll();
	}
	
	public Page<Item> getList(int page, String st, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Item> sItem = search(st, kw);
		return this.itemRepository.findAll(sItem, pageable);
	}
	
	public static Specification<Item> search(String search_terms, String search_kw) {
		return (Root<Item> item, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			if (search_terms.equals("ItmGroup")) {
				return cb.like(item.get("ItmGroup"), "%" + search_kw + "%");
			} else if (search_terms.equals("ItmCategory")) {
				return cb.like(item.get("ItmCategory"), "%" + search_kw + "%");
			} else if (search_terms.equals("ItmName")) {
				return cb.like(item.get("ItmName"), "%" + search_kw + "%");
			} else {
				return cb.or(cb.like(item.get("ItmGroup"), "%" + search_kw + "%"),
						cb.like(item.get("ItmCategory"), "%" + search_kw + "%"),
						cb.like(item.get("ItmName"), "%" + search_kw + "%") );
			}
		};
	}
	
	public Item getItem(Integer ItmId) {
		Optional<Item> item= this.itemRepository.findById(ItmId);
		if (item.isPresent()) {
	        return item.get();
	    } else {
	        throw new DataNotFoundException("item not found");
	    }
	}
	
	public void create(String ItmCode, String ItmName, String ItmGroup, String ItmCategory, String ItmStandard, Integer ItmSprice, Integer ItmRprice) {
	    Item itm = new Item();
	    itm.setItmCode(ItmCode);
	    itm.setItmName(ItmName);
	    itm.setItmGroup(ItmGroup);
	    itm.setItmCategory(ItmCategory);
	    itm.setItmStandard(ItmStandard);
	    itm.setItmSprice(ItmSprice);
	    itm.setItmRprice(ItmRprice);
	    itm.setCreateDate(LocalDateTime.now());
	    this.itemRepository.save(itm);
	}
	
	public void modify(Item itm, String ItmName, String ItmGroup, String ItmCategory, String ItmStandard, Integer ItmSprice, Integer ItmRprice) {
		itm.setItmName(ItmName);
	    itm.setItmCategory(ItmCategory);
	    itm.setItmStandard(ItmStandard);
	    itm.setItmSprice(ItmSprice);
	    itm.setItmRprice(ItmRprice);
	    this.itemRepository.save(itm);
	}
	
	public void delete(Item itm) {
	    this.itemRepository.delete(itm);
	}
}