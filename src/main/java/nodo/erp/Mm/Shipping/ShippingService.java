package nodo.erp.Mm.Shipping;

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
import nodo.erp.Mm.Warehousing.Warehousing;


@RequiredArgsConstructor
@Service
public class ShippingService {
	
	private final ShippingRepository shippingRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(String SPDate, String SPAName, String SPACode, String SPIName, String SPICode, String SPPName, String SPPNum, String SPDT, Integer SPCAmount, String SPLocation, String SPState) {
		Shipping wh = new Shipping();
		
		String yy = SPDate.substring(2, 4);
		String mm = SPDate.substring(5, 7);
		String dd = SPDate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		
		wh.setSPDate(ymd + "-" + Num); //출고일자
		wh.setSPAName(SPAName); //거래처명
		wh.setSPACode(SPACode); //거래처코드
		wh.setSPIName(SPIName); //품목명
		wh.setSPICode(SPICode); //품목코드
		wh.setSPPName(SPPName); //담당자
		wh.setSPPNum(SPPNum);	//담당사번
		wh.setSPDT(SPDT);		//납기일자
		wh.setSPCAmount(SPCAmount);//출고수량
		wh.setSPLocation(SPLocation);//출고위치
		wh.setSPState(SPState); //진행상태
		wh.setCreateDate(LocalDateTime.now());
		this.shippingRepository.save(wh);
	}
	
	private Integer generateWHNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(s.SPDate,-3) AS int)) "
						+ "FROM Shipping s WHERE SUBSTRING(s.SPDate, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	public Page<Shipping> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		Specification<Shipping> spec = search(kw);
		return this.shippingRepository.findAll(spec, pageable);
	}
	
	private Specification<Shipping> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Shipping> s, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                return cb.or(cb.like(s.get("SPDate"), "%" + kw + "%"), 
                        cb.like(s.get("SPPName"), "%" + kw + "%"),     
                        cb.like(s.get("SPPNum"), "%" + kw + "%"),    
                        cb.like(s.get("SPAName"), "%" + kw + "%"),       
                        cb.like(s.get("SPACode"), "%" + kw + "%"),
                        cb.like(s.get("SPIName"), "%" + kw + "%"),
                        cb.like(s.get("SPICode"), "%" + kw + "%"),
                        cb.like(s.get("SPState"), "%" + kw + "%"),
                        cb.like(s.get("SPLocation"), "%" + kw + "%"));   
            }
        };
    }
	
	//디테일
	public Shipping getShipping(Integer SPid) {
		Optional<Shipping> shipping = this.shippingRepository.findById(SPid);
		if(shipping.isPresent()) {
			return shipping.get();
		} else {
			throw new DataNotFoundException("shipping not found");
		}
	}
	
	public void modify(Shipping sp, String SPAName, String SPACode, String SPIName, String SPICode, String SPPName, String SPPNum, String SPDT, Integer SPCAmount, String SPLocation, String SPState) {
		
		sp.setSPAName(SPAName);
		sp.setSPACode(SPACode);
		sp.setSPIName(SPIName);
		sp.setSPICode(SPICode);
		sp.setSPPName(SPPName);
		sp.setSPPNum(SPPNum);
		sp.setSPDT(SPDT);
		sp.setSPCAmount(SPCAmount);
		sp.setSPLocation(SPLocation);
		sp.setSPState(SPState);
		sp.setModifyDate(LocalDateTime.now());
		this.shippingRepository.save(sp);
	}
	
	public void delete(Shipping shipping) {
        this.shippingRepository.delete(shipping);
    }
}