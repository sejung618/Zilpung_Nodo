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

	public void create(String spdate, String spdt, Integer spcamount, String spstate,
			Employee empnum, Account accode, Item itmcode) {
		Shipping sp = new Shipping();

		String yy = spdate.substring(2, 4);
		String mm = spdate.substring(5, 7);
		String dd = spdate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		String lo = "본사창고";

		sp.setSpnum(ymd + "-" + Num); // 출고일자
		sp.setSpdate(spdate); // 출고일자
		sp.setAccount(accode); // 거래처코드
		sp.setItem(itmcode); // 품목코드
		sp.setEmployee(empnum); // 담당사번
		sp.setSpdt(spdt); // 납기일자
		sp.setSpcamount(spcamount);// 출고수량
		sp.setSplocation(lo);// 출고위치
		sp.setSpstate(spstate); // 진행상태
		sp.setCreateDate(LocalDateTime.now());

		this.shippingRepository.save(sp);
	}

	private Integer generateWHNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(s.spnum,-3) AS int)) "
				+ "FROM Shipping s WHERE SUBSTRING(s.spnum, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}

	public Page<Shipping> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findAll(pageable);
	}

	public Page<Shipping> findBySpdate(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findBySpdateContaining(pageable, kw);
	}

	public Page<Shipping> findByAccompany(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findByAccount_AccompanyContaining(pageable, kw);
	}

	public Page<Shipping> findByItmName(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findByItem_ItmNameContaining(pageable, kw);
	}

	public Page<Shipping> searchAllCategories(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository
				.findBySpdateContainingOrAccount_AccompanyContainingOrItem_ItmNameContainingOrEmployee_EmpnameContainingOrEmployee_EmpnumContaining(pageable, kw, kw, kw,kw, kw);
	}

	public Page<Shipping> findByState(int page, String st) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findBySpstate(pageable, st);
	}

	
	
	public Page<Shipping> findByEmpname(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findByEmployee_EmpnameContaining(pageable, kw);
	}
	
	public Page<Shipping> findByEmpnum(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.shippingRepository.findByEmployee_EmpnumContaining(pageable, kw);
	}


	public Page<Shipping> findByStateAndItmName(int page, String state, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		
		// '미출고' 상태에서 품목명으로 검색
		return shippingRepository.findBySpstateAndItem_ItmNameContaining(state, kw, pageable);
	}

	// 디테일
	public Shipping getShipping(Integer spid) {
		Optional<Shipping> shipping = this.shippingRepository.findById(spid);
		if (shipping.isPresent()) {
			return shipping.get();
		} else {
			throw new DataNotFoundException("shipping not found");
		}
	}

	public void modify(Shipping sp, String spdate, String spdt, Integer spcamount, String spstate,
			Employee empnum, Account accode, Item itmcode) {

		String yy = spdate.substring(2, 4);
		String mm = spdate.substring(5, 7);
		String dd = spdate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateWHNum(ymd));
		String lo = "본사창고";
		
		sp.setSpnum(ymd + "-" + Num); // 일자번호
		sp.setSpdate(spdate); // 출고일자
		sp.setAccount(accode); // 거래처코드
		sp.setItem(itmcode); // 품목코드
		sp.setEmployee(empnum); // 담당사번
		sp.setSpdt(spdt); // 납기일자
		sp.setSpcamount(spcamount);// 출고수량
		sp.setSplocation(lo);// 출고위치
		sp.setSpstate(spstate); // 진행상태
		sp.setModifyDate(LocalDateTime.now());
		this.shippingRepository.save(sp);
	}

	public void delete(Shipping shipping) {
		this.shippingRepository.delete(shipping);
	}

}
