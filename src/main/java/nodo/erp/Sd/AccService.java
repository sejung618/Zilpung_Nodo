package nodo.erp.Sd;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Sd.Account;

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
	public void create(String AC_Code, String AC_Company, String AC_Address, String AC_Name, String AC_Phone,
			String AC_Item, String AC_Icode, float VAT, String AC_Date, int AC_Price, String AC_Num) {
		
		Account acc = new Account();
		String ym = AC_Date.substring(2, 7);
		String am = String.format("%04d", generateAC_Code(ym));

		acc.setAC_Code("ACC" + ym + "-" + am);
		acc.setAC_Company(AC_Company);
		acc.setAC_Address(AC_Address);
		acc.setVAT((float)10.0); // = acc.setVAT(10.0f);
		acc.setAC_Date(AC_Date);
		acc.setAC_Icode(AC_Icode);
		acc.setAC_Item(AC_Item);
		acc.setAC_Name(AC_Name);
		acc.setAC_Num(AC_Num);
		acc.setAC_Phone(AC_Phone);
		acc.setAC_Price(AC_Price);
		
		this.accRepository.save(acc);
	}
	

	
	private Integer generateAC_Code(String ym) {
	    jakarta.persistence.Query query = entityManager.createQuery(
	            "SELECT MAX(CAST(SUBSTRING(i.AC_Code,-4) AS int)) "
	                    + "FROM Account i WHERE SUBSTRING(i.AC_Code, 4, 5) = :ym");
	    query.setParameter("ym", ym);
	    Integer maxNum = (Integer) query.getSingleResult();
	    System.out.println(maxNum);

	    return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	public void update(Account account,String AC_Company, String AC_Address, String AC_Name, String AC_Phone, 
			 Integer AC_Price) {
		Account acc = this.accRepository.findById(account.getId()).orElse(null);
		acc.setAC_Company(AC_Company);
		acc.setAC_Address(AC_Address);
		acc.setAC_Name(AC_Name);
		acc.setAC_Phone(AC_Phone);
		acc.setAC_Price(AC_Price);
		
		this.accRepository.save(acc);
	}
	
	public void delete(Integer id) {
		Account acc = new Account();
		acc.setId(id);
		this.accRepository.delete(acc);
	}
}