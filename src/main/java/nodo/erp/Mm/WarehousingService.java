package nodo.erp.Mm;

import java.util.ArrayList;
import java.util.List;

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


@RequiredArgsConstructor
@Service
public class WarehousingService {

	private final WarehousingRepository warehousingRepository;
	
	public void create(String WHDate,  String WHAName, String WHIName, String WHDT, Integer WHCAmount, String WHState) {
		Warehousing wh = new Warehousing();
		
		wh.setWHDate(WHDate);
		wh.setWHAName(WHAName);
		wh.setWHIName(WHIName);
		wh.setWHDT(WHDT);
		wh.setWHCAmount(WHCAmount);
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
                        cb.like(w.get("WHAName"), "%" + kw + "%"),     
                        cb.like(w.get("WHIName"), "%" + kw + "%"),    
                        cb.like(w.get("WHDT"), "%" + kw + "%"),       
                        cb.like(w.get("WHState"), "%" + kw + "%"));   
            }
        };
    }
}
