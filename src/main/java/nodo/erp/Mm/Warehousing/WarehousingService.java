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
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Mm.Shipping.Shipping;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Sd.Account;


@RequiredArgsConstructor
@Service
public class WarehousingService {

	private final WarehousingRepository warehousingRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(String whdate, String whdt, Integer whcamount, String whstate, Employee empnum, Account accode, Item itmcode) {
		Warehousing wh = new Warehousing();
		
		String yy = whdate.substring(2, 4);
		String mm = whdate.substring(5, 7);
		String dd = whdate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		String lo = "본사창고";
		
		
		wh.setWhnum(ymd + "-" + Num);
		wh.setWhdate(whdate);
		wh.setAccount(accode); //거래처코드
		wh.setItem(itmcode); //품목코드
		wh.setEmployee(empnum);	//담당사번
		wh.setWhdt(whdt);
		wh.setWhcamount(whcamount);
		wh.setWhlocation(lo);
		wh.setWhstate(whstate);
		wh.setCreateDate(LocalDateTime.now());
		this.warehousingRepository.save(wh);
	}
	
	private Integer generateWHNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(w.whnum,-3) AS int)) "
						+ "FROM Warehousing w WHERE SUBSTRING(w.whnum, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	
	
	public Page<Warehousing> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.warehousingRepository.findAll(pageable);
	}
	
	
	
	public Page<Warehousing> findBySpdate(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.warehousingRepository.findByWhdateContaining(pageable, kw);
	}
	
	public Page<Warehousing> findByAccompany(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.warehousingRepository.findByAccount_AccompanyContaining(pageable, kw);
	}
	
	public Page<Warehousing> findByItmName(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.warehousingRepository.findByItem_ItmNameContaining(pageable, kw);
	}
	
	
	public Page<Warehousing> searchAllCategories(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.warehousingRepository.findByWhdateContainingOrAccount_AccompanyContainingOrItem_ItmNameContainingOrEmployee_EmpnameContainingOrEmployee_EmpnumContaining(pageable, kw, kw, kw, kw, kw);
	}
	
	public Page<Warehousing> findByState(int page, String st) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page,  10, Sort.by(sorts));
		return this.warehousingRepository.findByWhstate(pageable, st);
	}
	
	public Page<Warehousing> findByEmpname(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.warehousingRepository.findByEmployee_EmpnameContaining(pageable, kw);
	}
	
	public Page<Warehousing> findByEmpnum(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.warehousingRepository.findByEmployee_EmpnumContaining(pageable, kw);
	}

	
	
	
//	private Specification<Warehousing> search(String kw) {
//        return new Specification<>() {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public Predicate toPredicate(Root<Warehousing> w, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                query.distinct(true);  // 중복을 제거 
//                return cb.or(cb.like(w.get("whdate"), "%" + kw + "%"), 
////                        cb.like(w.get("WHPName"), "%" + kw + "%"),     
////                        cb.like(w.get("WHPNum"), "%" + kw + "%"),    
////                        cb.like(w.get("WHAName"), "%" + kw + "%"),       
////                        cb.like(w.get("WHACode"), "%" + kw + "%"),
////                        cb.like(w.get("WHIName"), "%" + kw + "%"),
////                        cb.like(w.get("WHICode"), "%" + kw + "%"),
//                        cb.like(w.get("whstate"), "%" + kw + "%"),
//                        cb.like(w.get("whlocation"), "%" + kw + "%"));   
//            }
//        };
//    }
	
	//디테일
	public Warehousing getWarehousing(Integer whid) {
		Optional<Warehousing> warehousing = this.warehousingRepository.findById(whid);
		if(warehousing.isPresent()) {
			return warehousing.get();
		} else {
			throw new DataNotFoundException("warehousing not found");
		}
	}
	
	public void modify(Warehousing wh, String whdate, String whdt, Integer whcamount, String whstate, Employee empnum, Account accode, Item itmcode) {
		
		String yy = whdate.substring(2, 4);
		String mm = whdate.substring(5, 7);
		String dd = whdate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		String lo = "본사창고";
		
		
		wh.setWhnum(ymd + "-" + Num);
		wh.setWhdate(whdate);
		wh.setAccount(accode); //거래처코드
		wh.setItem(itmcode); //품목코드
		wh.setEmployee(empnum);	//담당사번
		wh.setWhdt(whdt);
		wh.setWhcamount(whcamount);
		wh.setWhlocation(lo);
		wh.setWhstate(whstate);
		wh.setModifyDate(LocalDateTime.now());
		this.warehousingRepository.save(wh);
	}
	
	public void delete(Warehousing warehousing) {
        this.warehousingRepository.delete(warehousing);
    }
	
	public List<String[]> listWarehousing() {
        List<Warehousing> list = warehousingRepository.findAll(); 
        List<String[]> listStrings = new ArrayList<>();
        listStrings.add(new String[]{"일련번호", "입고일자", "납기일자", "거래처코드", "거래처명", "담당사번", "담당자", "품목코드", "품목명", "규격", "수량", "입고위치", "진행상태"});
        for (Warehousing wh: list) {
            String[] rowData = new String[30];
            rowData[0] = wh.getWhnum();
            rowData[1] = wh.getWhdate();
            rowData[2] = wh.getWhdt();
            rowData[3] = wh.getAccount().getAccode();
            rowData[4] = wh.getAccount().getAccompany();
            rowData[5] = wh.getEmployee().getEmpnum();
            rowData[6] = wh.getEmployee().getEmpname();
            rowData[7] = wh.getItem().getItmCode();
            rowData[8] = wh.getItem().getItmName();
            rowData[9] = wh.getItem().getItmStandard();
            rowData[10] = wh.getWhcamount().toString();
            rowData[11] = wh.getWhlocation();
            rowData[12] = wh.getWhstate();
            listStrings.add(rowData);
        }
        return listStrings;
    }
}
