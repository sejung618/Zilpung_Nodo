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
	
	public List<Account> getList() {
		return this.accRepository.findAll();
	}
	
	public Account getAccount(String AC_Code) {
		Optional<Account> account = this.accRepository.findById(AC_Code);
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
	
	@PersistenceContext
	private EntityManager entityManager;
	private Integer generateAC_Code(String ac) {
	    jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(acc.AC_Code, 10, 14) AS int)) From Account acc WHERE SUBSTRING(acc.AC_Code, 4, 7) = :ac");
	    query.setParameter("ac", ac);
	    Integer maxNum = (Integer) query.getSingleResult();
	    
	    return (maxNum == null) ? 1 : maxNum + 1;
	}
	public void update(String AC_Code,String AC_Company, String AC_Address, String AC_Name, String AC_Phone, 
			String AC_Item, String AC_Icode, float VAT, String AC_Date, int AC_Price, String AC_Num) {
		Account acc = new Account();
		
		acc.setAC_Code(AC_Code);
		
		acc.setAC_Company(AC_Company);
		acc.setAC_Address(AC_Address);
		acc.setVAT(VAT);
		acc.setAC_Date(AC_Date);
		acc.setAC_Icode(AC_Icode);
		acc.setAC_Item(AC_Item);
		acc.setAC_Name(AC_Name);
		acc.setAC_Num(AC_Num);
		acc.setAC_Phone(AC_Phone);
		acc.setAC_Price(AC_Price);
		
		
		this.accRepository.save(acc);
	}
	
	public void delete(String AC_Code) {
		Account acc = new Account();
		
		acc.setAC_Code(AC_Code);
		
		this.accRepository.delete(acc);
	}
}