package nodo.erp.Sd.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class OrService {
	
	private final OrRepository orRepository;
	
	public List<Orders> getList(){
		return this.orRepository.findAll();
	}
	
	public Orders getOrders(Integer id) {
		Optional<Orders> orders = this.orRepository.findById(id);
		if(orders.isPresent()) {
			return orders.get();
		} else {
			throw new DataNotFoundException("Orders not found");
		}
	}
	
	public void create(String ornum, String ordate, String oritem, String oricode, String orcompany,
			Integer orcount, Integer orprice, Integer orcp, float vat, Integer orvat, Integer orvatsum, String orcode) {
		Orders or = new Orders();
		
		or.setOrnum(ornum);
	    or.setOrdate(ordate);
	    or.setOritem(oritem);
	    or.setOricode(oricode);
	    or.setOrcompany(orcompany);
	    or.setOrcount(orcount);
	    or.setOrprice(orprice);
	    or.setOrcp(orcp);
	    or.setVat((float)10.0);
	    or.setOrvat(orvat);
	    or.setOrvatsum(orvatsum);
	    or.setOrcode(orcode);
	    
	    this.orRepository.save(or);
	}

	public Page<Orders> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.orRepository.findAll(pageable);
	}
	
	public Page<Orders> findByOrdate(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.orRepository.findByOrdateContaining(pageable, kw);
	}
	
	public Page<Orders> findByOritem(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.orRepository.findByOritemContaining(pageable, kw);
	}
	
	public Page<Orders> findByOrcompany(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.orRepository.findByOrcompanyContaining(pageable, kw);
	}
	
	public Page<Orders> findByOrnum(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.orRepository.findByOrnumContaining(pageable, kw);
	}
	
	public Page<Orders> searchAll(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("Id"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		return this.orRepository.findByOrdateContainingOrOritemContainingOrOrcompanyContainingOrOrnumContaining(pageable, kw, kw, kw, kw);
	}
	
}
