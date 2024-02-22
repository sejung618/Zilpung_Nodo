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
import nodo.erp.Mm.Inventory.Inventory;


@RequiredArgsConstructor
@Service
public class WarehousingService {

	private final WarehousingRepository warehousingRepository;
	
	public void create(String WHDate, String WHAName, String WHACode, String WHIName, String WHICode, String WHPName, String WHPNum, String WHDT, Integer WHCAmount, String WHLocation, String WHState) {
		Warehousing wh = new Warehousing();
		
		wh.setWHDate(WHDate);
		wh.setWHAName(WHAName);
		wh.setWHACode(WHACode);
		wh.setWHIName(WHIName);
		wh.setWHICode(WHICode);
		wh.setWHPName(WHPName);
		wh.setWHPNum(WHPNum);
		wh.setWHDT(WHDT);
		wh.setWHCAmount(WHCAmount);
		wh.setWHLocation(WHLocation);
		wh.setWHState(WHState);
		this.warehousingRepository.save(wh);
	}
	
	public Page<Warehousing> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		Specification<Warehousing> spec = search(kw);
		return this.warehousingRepository.findAll(spec, pageable);
	}
	
	private Specification<Warehousing> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Warehousing> w, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                return cb.or(cb.like(w.get("WHDate"), "%" + kw + "%"), 
                        cb.like(w.get("WHPName"), "%" + kw + "%"),     
                        cb.like(w.get("WHPNum"), "%" + kw + "%"),    
                        cb.like(w.get("WHAName"), "%" + kw + "%"),       
                        cb.like(w.get("WHACode"), "%" + kw + "%"),
                        cb.like(w.get("WHIName"), "%" + kw + "%"),
                        cb.like(w.get("WHICode"), "%" + kw + "%"),
                        cb.like(w.get("WHState"), "%" + kw + "%"),
                        cb.like(w.get("WHLocation"), "%" + kw + "%"));   
            }
        };
    }
	
	//디테일
	public Warehousing getWarehousing(Integer id) {
		Optional<Warehousing> warehousing = this.warehousingRepository.findById(id);
		if(warehousing.isPresent()) {
			return warehousing.get();
		} else {
			throw new DataNotFoundException("warehousing not found");
		}
	}
	
	public void modify(Warehousing wh, String WHDate, String WHAName, String WHACode, String WHIName, String WHICode, String WHPName, String WHPNum, String WHDT, Integer WHCAmount, String WHLocation, String WHState) {
			
		wh.setWHDate(WHDate);
		wh.setWHAName(WHAName);
		wh.setWHACode(WHACode);
		wh.setWHIName(WHIName);
		wh.setWHICode(WHICode);
		wh.setWHPName(WHPName);
		wh.setWHPNum(WHPNum);
		wh.setWHDT(WHDT);
		wh.setWHCAmount(WHCAmount);
		wh.setWHLocation(WHLocation);
		wh.setWHState(WHState);
		this.warehousingRepository.save(wh);
	}
	
	public void delete(Warehousing warehousing) {
        this.warehousingRepository.delete(warehousing);
    }
}
