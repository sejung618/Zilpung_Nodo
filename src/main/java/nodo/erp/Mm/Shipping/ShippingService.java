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
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Repository.Emp_Repository;
import nodo.erp.Mm.Inventory.Inventory;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemRepository;
import nodo.erp.Sd.AccService;
import nodo.erp.Sd.Account;



@RequiredArgsConstructor
@Service
public class ShippingService {
	
	private final ShippingRepository shippingRepository;
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(String spdate, String spdt, Integer spcamount, String splocation, String spstate, Employee empnum, Account accode, Item itmcode) {
		Shipping sp = new Shipping();
		
		String yy = spdate.substring(2, 4);
		String mm = spdate.substring(5, 7);
		String dd = spdate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		
		sp.setSpnum(ymd + "-" + Num); //출고일자
		sp.setSpdate(spdate); //출고일자
		sp.setAccount(accode); //거래처코드
		sp.setItem(itmcode); //품목코드
		sp.setEmployee(empnum);	//담당사번
		sp.setSpdt(spdt);		//납기일자
		sp.setSpcamount(spcamount);//출고수량
		sp.setSplocation(splocation);//출고위치
		sp.setSpstate(spstate); //진행상태
		sp.setCreateDate(LocalDateTime.now());
		
		this.shippingRepository.save(sp);
	}
	
	private Integer generateWHNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(s.spnum,-3) AS int)) "
						+ "FROM Shipping s WHERE SUBSTRING(s.spnum, 1, 6) = :ymd");
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
                return cb.or(cb.like(s.get("spdate"), "%" + kw + "%"), 
//                        cb.like(s.get("SPPName"), "%" + kw + "%"),     
//                        cb.like(s.get("SPPNum"), "%" + kw + "%"),    
//                        cb.like(s.get("SPAName"), "%" + kw + "%"),       
//                        cb.like(s.get("SPACode"), "%" + kw + "%"),
//                        cb.like(s.get("SPIName"), "%" + kw + "%"),
//                        cb.like(s.get("SPICode"), "%" + kw + "%"),
                        cb.like(s.get("spstate"), "%" + kw + "%"),
                        cb.like(s.get("splocation"), "%" + kw + "%"));   
            }
        };
    }
	
	//디테일
	public Shipping getShipping(Integer spid) {
		Optional<Shipping> shipping = this.shippingRepository.findById(spid);
		if(shipping.isPresent()) {
			return shipping.get();
		} else {
			throw new DataNotFoundException("shipping not found");
		}
	}
	
	public void modify(Shipping sp, String spdate, String spdt, Integer spcamount, String splocation, String spstate, Employee empnum, Account accode, Item itmcode) {
		
		String yy = spdate.substring(2, 4);
		String mm = spdate.substring(5, 7);
		String dd = spdate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		
		sp.setSpnum(ymd + "-" + Num); //일자번호
		sp.setSpdate(spdate); //출고일자
		sp.setAccount(accode); //거래처코드
		sp.setItem(itmcode); //품목코드
		sp.setEmployee(empnum);	//담당사번
		sp.setSpdt(spdt);		//납기일자
		sp.setSpcamount(spcamount);//출고수량
		sp.setSplocation(splocation);//출고위치
		sp.setSpstate(spstate); //진행상태
		sp.setModifyDate(LocalDateTime.now());
		this.shippingRepository.save(sp);
	}
	
	public void delete(Shipping shipping) {
        this.shippingRepository.delete(shipping);
    }
	
	public Page<Shipping> State(int page, Shipping state) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findBySpstate(pageable, state);
	}
	
}
