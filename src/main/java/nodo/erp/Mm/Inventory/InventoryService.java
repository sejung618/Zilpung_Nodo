package nodo.erp.Mm.Inventory;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Employee;

@RequiredArgsConstructor
@Service
public class InventoryService {
	
	private final InventoryRepository inventoryRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Inventory> getList() {
		return this.inventoryRepository.findAll();
		
	}
	
	public void create(String INDate, String ININame, String INPName, Integer INQuantity, String INPNum, String INICode,
			String INStandard, Employee empname) {

		String yy = INDate.substring(2, 4);
		String mm = INDate.substring(5, 7);
		String dd = INDate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateInvNum(ymd));

		Inventory i = new Inventory();

		i.setINDate(ymd + "-" + Num);
		i.setININame(ININame);
		i.setINPName(INPName);
		i.setINQuantity(INQuantity);
		i.setINPNum(INPNum);
		i.setINICode(INICode);
		i.setINStandard(INStandard);
		i.setCreateDate(LocalDateTime.now());
		i.setEmployee(empname);
		this.inventoryRepository.save(i);
	}

	private Integer generateInvNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(i.INDate,-3) AS int)) "
						+ "FROM Inventory i WHERE SUBSTRING(i.INDate, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	
	//디테일
	public Inventory getInventory(Integer INid) {
		Optional<Inventory> inventory = this.inventoryRepository.findById(INid);
		if(inventory.isPresent()) {
			return inventory.get();
		} else {
			throw new DataNotFoundException("inventory not found");
		}
	}
	
	public void modify(Inventory inventory, String ININame, String INPName, Integer INQuantity, String INPNum, String INICode, String INStandard) {
		
		inventory.setININame(ININame);
		inventory.setINPName(INPName);
		inventory.setINQuantity(INQuantity);
		inventory.setINPNum(INPNum);
		inventory.setINICode(INICode);
		inventory.setINStandard(INStandard);
		inventory.setModifyDate(LocalDateTime.now());
		this.inventoryRepository.save(inventory);
	}
	
	
	public void delete(Inventory inventory) {
        this.inventoryRepository.delete(inventory);
    }
	
	
	
	public Page<Inventory> findByINDate(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.inventoryRepository.findByINDate(pageable, kw);
	}
	

	
	public Page<Inventory> findByININame(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.inventoryRepository.findByININame(pageable, kw);
	}
	
	public Page<Inventory> findByINICode(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.inventoryRepository.findByINICode(pageable, kw);
	}
	

	public Page<Inventory> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.inventoryRepository.findAll(pageable);
	}
	
	
	
	
//	public Page<Inventory> getList(int page, String kw) {
//		List<Sort.Order> sorts = new ArrayList<>();
//		sorts.add(Sort.Order.desc("createDate"));
//		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
//		Specification<Inventory> spec = search(kw);
//		return this.inventoryRepository.findAll(spec, pageable);
//	}
	
//	private Specification<Inventory> search(String kw) {
//        return new Specification<>() {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public Predicate toPredicate(Root<Inventory> i, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                query.distinct(true);  // 중복을 제거 
//                return cb.or(cb.like(i.get("INDate"), "%" + kw + "%"), 
//                        cb.like(i.get("INPName"), "%" + kw + "%"),     
//                        cb.like(i.get("INPNum"), "%" + kw + "%"),    
//                        cb.like(i.get("ININame"), "%" + kw + "%"),       
//                        cb.like(i.get("INICode"), "%" + kw + "%"));   
//            }
//        };
//    }
} 
