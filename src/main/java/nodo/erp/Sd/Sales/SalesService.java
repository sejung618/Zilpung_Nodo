package nodo.erp.Sd.Sales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Pp.Item.ItemRepository;
import nodo.erp.Pp.Item.ItemService;
import nodo.erp.Sd.Reservation.ReserRepository;
import nodo.erp.Sd.Reservation.ReserService;

@RequiredArgsConstructor
@Service
public class SalesService {

	private final SalesRepository SR;
	private final ReserRepository RR;
	private final ItemRepository IR;
	
	
	private final ReserService RS;
	private final ItemService IS;
	
	
	@PersistenceContext
	private EntityManager entity;
	
	public List<Sales> getList() {
		return SR.findAll();
	}
	
	public Sales getSales(Integer id) {
		Optional<Sales> sales = this.SR.findById(id);
		if(sales.isPresent()) {
			return sales.get();
		} else {
			throw new DataNotFoundException("Sales not found");
		}
	}
	
	
	public void create(String sanum, String samethod, LocalDate sadate, String saitem, 
			String saicode, Integer sacount, Integer saprice, Integer sacp, Integer savat, Integer sasum) {
		
		Sales sales = new Sales();
		
		
		String yy = String.valueOf(sadate.getYear()).substring(2);
	    String mm = String.format("%02d", sadate.getMonthValue());
	    String dd = String.format("%02d", sadate.getDayOfMonth());
	    String ymd = yy + mm + dd;

	    // 해당 날짜에 대한 가장 큰 일련번호를 조회합니다.
	    String num = String.format("%04d", generateMaxNum(ymd));
		
		sales.setSanum(ymd + "-" + num);
		sales.setSamethod(samethod);
		sales.setSadate(LocalDate.now());
		sales.setSaitem(saitem);
		sales.setSaicode(saicode);
		sales.setSacount(sacount);
		sales.setSaprice(saprice);
		sales.setSacp(sacp);
		sales.setSavat(savat);
		sales.setSasum(sasum);
		
		/*if("예약".equals(samethod)) {
			
		}*/
		
		this.SR.save(sales);
	}
	
	private int generateMaxNum(String ymd) {
	    TypedQuery<Integer> query = entity.createQuery(
	            "SELECT MAX(CAST(SUBSTRING(s.sanum, 9) AS int)) " +
	                    "FROM Sales s WHERE SUBSTRING(s.sanum, 1, 6) = :ymd", Integer.class);
	    query.setParameter("ymd", ymd);

	    Integer maxNum = query.getSingleResult();
	    return (maxNum == null) ? 1 : maxNum+1;
	}
	
	
	
	public void delete(Integer id) {
		Sales sales = new Sales();
		sales.setId(id);
		this.SR.delete(sales);
	}
	
	public Page<Sales> getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.SR.findAll(pageable);
	}
	
	public Page<Sales> findBySanum(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.SR.findBySanumContaining(pageable, kw);
	}
	
	public Page<Sales> findBySaitem(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.SR.findBySaitemContaining(pageable, kw);
	}
	
	public Page<Sales> findBySamethod(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.SR.findBySamethodContaining(pageable, kw);
	}
	
	public Page<Sales> searchAll(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.SR.findBySanumContainingOrSaitemContainingOrSamethodContaining(pageable, kw, kw, kw);
	}
	
}
