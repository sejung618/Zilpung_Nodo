package nodo.erp.Sd;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import groovyjarjarantlr4.v4.runtime.atn.SemanticContext.Predicate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class AccService {

	private final AccRepository accRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Account> getList() {
		
		return this.accRepository.findAll();
	}
	
	public Account getAccount(Integer id) {
		Optional<Account> account = this.accRepository.findById(id); 
		if(account.isPresent()) {
			return account.get();
		} else {
			throw new DataNotFoundException("Account not found");
		}
	}
	public void create(String accode, String accompany, String acaddress, String acname, String acphone,
			String acitem, String acicode, float vat, String acdate, int acprice, String acnum) {
		
		Account acc = new Account();
		String ym = acdate.substring(2, 7);
		String am = String.format("%04d", generateAC_Code(ym));

		
		acc.setAccode("ACC" + ym + "-" + am);
		acc.setAccompany(accompany);
		acc.setAcaddress(acaddress);
		acc.setVat((float)10.0); // = acc.setVAT(10.0f);
		acc.setAcdate(acdate);
		acc.setAcicode(acicode);
		acc.setAcitem(acitem);
		acc.setAcname(acname);
		acc.setAcnum(acnum);
		acc.setAcphone(acphone);
		acc.setAcprice(acprice);
		
		this.accRepository.save(acc);
	}
	

	
	private Integer generateAC_Code(String ym) {
	    jakarta.persistence.Query query = entityManager.createQuery(
	            "SELECT MAX(CAST(SUBSTRING(i.accode,-4) AS int)) "
	                    + "FROM Account i WHERE SUBSTRING(i.accode, 4, 5) = :ym");
	    query.setParameter("ym", ym);
	    Integer maxNum = (Integer) query.getSingleResult();

	    return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	public void update(Account account,String accompany, String acaddress, String acname, String acphone, 
			 Integer acprice) {
		Account acc = this.accRepository.findById(account.getId()).orElse(null);
		acc.setAccompany(accompany);
		acc.setAcaddress(acaddress);
		acc.setAcname(acname);
		acc.setAcphone(acphone);
		acc.setAcprice(acprice);
		
		this.accRepository.save(acc);
	}
	
	public void delete(Integer id) {
		Account acc = new Account();
		acc.setId(id);
		this.accRepository.delete(acc);
	}
	
	private Specification<Account> search(String ac) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			@Override
			public jakarta.persistence.criteria.Predicate toPredicate(Root<Account> a, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
				return cb.or(cb.like(a.get("accompany"), "%" + ac + "%"),
						cb.like(a.get("acname"),  "%" + ac + "%"),
						cb.like(a.get("acitem"), "%" + ac + "%"));
			}
		};
	}
	
	
	public Page<Account> getList(int page, String kw){
		Pageable pageable = PageRequest.of(page, 10);//, Sort.by("Id").ascending());
		return this.accRepository.findAll(pageable);
	}
	
	public Page<Account> findByAcaddress(int page, String kw){
		Pageable pageable = PageRequest.of(page, 10);//, Sort.by("Id").ascending());
		return this.accRepository.findByAcaddressContaining(pageable, kw);
	}
	
	public Page<Account> findByAcitem(int page, String kw){
		Pageable pageable = PageRequest.of(page, 10);
		return this.accRepository.findByAcitemContaining(pageable, kw);
	}
	
	public Page<Account> findByAcdate(int page, String kw){
		Pageable pageable = PageRequest.of(page, 10);//, Sort.by("Id").ascending());
		return this.accRepository.findByAcdateContaining(pageable, kw);
	}
	
	public Page<Account> findByAccompany(int page, String kw){
		Pageable pageable = PageRequest.of(page, 10);//, Sort.by("Id").ascending());
		return this.accRepository.findByAccompanyContaining(pageable, kw);
	}
	
	
	public Page<Account> searchAll(int page, String kw){
		Pageable pageable = PageRequest.of(page, 10);//, Sort.by("Id").ascending());
		return this.accRepository.findByAcaddressContainingOrAcitemContainingOrAcdateContainingOrAccompanyContaining(pageable, kw, kw, kw, kw);
	}
	
}