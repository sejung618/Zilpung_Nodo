package nodo.erp.Mm.Warehousing;

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


@RequiredArgsConstructor
@Service
public class WarehousingService {

	private final WarehousingRepository warehousingRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(String WHDate, String WHAName, String WHACode, String WHIName, String WHICode, String WHPName, String WHPNum, String WHDT, Integer WHCAmount, String WHLocation, String WHState) {
		Warehousing wh = new Warehousing();
		
		String yy = WHDate.substring(2, 4);
		String mm = WHDate.substring(5, 7);
		String dd = WHDate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		
		wh.setWHDate(ymd + "-" + Num);
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
		wh.setCreateDate(LocalDateTime.now());
		this.warehousingRepository.save(wh);
	}
	
	private Integer generateWHNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(w.WHDate,-3) AS int)) "
						+ "FROM Warehousing w WHERE SUBSTRING(w.WHDate, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
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
	public Warehousing getWarehousing(Integer WHid) {
		Optional<Warehousing> warehousing = this.warehousingRepository.findById(WHid);
		if(warehousing.isPresent()) {
			return warehousing.get();
		} else {
			throw new DataNotFoundException("warehousing not found");
		}
	}
	
	public void modify(Warehousing wh, String WHAName, String WHACode, String WHIName, String WHICode, String WHPName, String WHPNum, String WHDT, Integer WHCAmount, String WHLocation, String WHState) {
		
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
		wh.setModifyDate(LocalDateTime.now());
		this.warehousingRepository.save(wh);
	}
	
	public void delete(Warehousing warehousing) {
        this.warehousingRepository.delete(warehousing);
    }
}
