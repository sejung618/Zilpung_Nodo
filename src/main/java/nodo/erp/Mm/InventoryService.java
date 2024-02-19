package nodo.erp.Mm;

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
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class InventoryService {
	
	private final InventoryRepository inventoryRepository;
	
	public List<Inventory> getList() {
		return this.inventoryRepository.findAll();
		
	}
	
	public void create(String INDate, String ININame, String INPName, Integer INQuantity, String INPNum, String INICode, String INStandard) {
		Inventory i = new Inventory();
		i.setINDate(INDate);
		i.setININame(ININame);
		i.setINPName(INPName);
		i.setINQuantity(INQuantity);
		i.setINPNum(INPNum);
		i.setINICode(INICode);
		i.setINStandard(INStandard);
		i.setCreateDate(LocalDateTime.now());
		this.inventoryRepository.save(i);
	}
	
	public Page<Inventory> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		Specification<Inventory> spec = search(kw);
		return this.inventoryRepository.findAll(spec, pageable);
	}
	
	//디테일
	public Inventory getInventory(Integer id) {
		Optional<Inventory> inventory = this.inventoryRepository.findById(id);
		if(inventory.isPresent()) {
			return inventory.get();
		} else {
			throw new DataNotFoundException("inventory not found");
		}
	}
	
	public void modify(Inventory inventory, String INDate, String ININame, String INPName, Integer INQuantity, String INPNum, String INICode, String INStandard) {
		inventory.setINDate(INDate);
		inventory.setINICode(INICode);
		inventory.setININame(ININame);
		inventory.setINPName(INPName);
		inventory.setINPNum(INPNum);
		inventory.setINQuantity(INQuantity);
		inventory.setINStandard(INStandard);
		inventory.setModifyDate(LocalDateTime.now());
		this.inventoryRepository.save(inventory);
	}
	
	public void delete(Inventory inventory) {
        this.inventoryRepository.delete(inventory);
    }
	
	private Specification<Inventory> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Inventory> i, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                return cb.or(cb.like(i.get("INDate"), "%" + kw + "%"), 
                        cb.like(i.get("INPName"), "%" + kw + "%"),     
                        cb.like(i.get("INPNum"), "%" + kw + "%"),    
                        cb.like(i.get("ININame"), "%" + kw + "%"),       
                        cb.like(i.get("INICode"), "%" + kw + "%"));   
            }
        };
    }
} 
