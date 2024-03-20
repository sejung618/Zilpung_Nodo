package nodo.erp.Sd.Purchase;

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
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import jakarta.persistence.criteria.Predicate;

@RequiredArgsConstructor
@Service
public class PurService {

	private final PurRepository purRepositroy;
	
	 
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Purchase> getList() {
		return this.purRepositroy.findAll();
	}
	
	public Purchase getPurchase(Integer id) {
		Optional<Purchase> purchase = this.purRepositroy.findById(id);
		if(purchase.isPresent()) {
			return purchase.get();
		} else {
			throw new DataNotFoundException("Purchase not found");
		}
	}
	
    public void create(String pcnum, String pcdate, String pccode,
                              String pccompany, String pcitem, String pcicode, Integer pccount,
                               Integer pcprice, Integer pccp, Integer pcvat, Integer pcvatsum) {
    	Purchase pur = new Purchase();
        
    	String yy = pcdate.substring(2, 4);
    	String mm = pcdate.substring(5, 7);
    	String dd = pcdate.substring(8, 10);
    	String ymd = yy + mm + dd;
    	String Num = String.format("%03d", generatePCNum(ymd));
    	
    	pur.setPcnum(ymd + "-" + Num);
    	pur.setPcdate(pcdate);
    	pur.setPccompany(pccompany);
    	pur.setPccode(pccode);
        pur.setPcitem(pcitem);
        pur.setPcicode(pcicode);
        pur.setPccount(pccount);
        pur.setPcprice(pcprice);
        pur.setPccp(pccp);
        pur.setPcvat(pcvat);
        pur.setPcvatsum(pcvatsum);

        this.purRepositroy.save(pur);
    }
	
	private Integer generatePCNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(i.pcnum,-2) AS int)) "
						+ "FROM Purchase i WHERE replace( SUBSTRING(i.pcnum, 1, 6), '-','' ) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	
	 public void delete(Integer id) {
		 Purchase pur = new Purchase();
		 pur.setId(id);
		 this.purRepositroy.delete(pur);
	 }
	
	 public Page<Purchase> getList(int page, String kw) {
		 List<Sort.Order> sorts = new ArrayList<>();
			sorts.add(Sort.Order.desc("Id"));
			Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
			return this.purRepositroy.findAll(pageable);
	 }
	 
	 public Page<Purchase> findByPcnum(int page, String kw){
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("Id"));
		 Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		 return this.purRepositroy.findByPcnumContaining(pageable, kw);
	 }
	 
	 public Page<Purchase> findByPcdate(int page, String kw){
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("Id"));
		 Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		 return this.purRepositroy.findByPcdateContaining(pageable, kw);
	 }
	 
	 public Page<Purchase> findByPccompany(int page, String kw){
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("Id"));
		 Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		 return this.purRepositroy.findByPccompanyContaining(pageable, kw);
	 }
	
	 public Page<Purchase> findByPcitem(int page, String kw){
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("Id"));
		 Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		 return this.purRepositroy.findByPcitemContaining(pageable, kw);
	 }
	
	 public Page<Purchase> searchAll(int page, String kw){
		 List<Sort.Order> sorts = new ArrayList<>();
		 sorts.add(Sort.Order.desc("Id"));
		 Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
		 return this.purRepositroy.findByPcnumContainingrPcdateContainingOrPccompanyContainingOrPcitemContaining(pageable, kw, kw, kw, kw);
	 }
	
	 
	 
}
