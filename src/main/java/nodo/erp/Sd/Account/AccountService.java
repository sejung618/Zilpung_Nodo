package nodo.erp.Sd.Account;

import java.util.List;

import org.springframework.stereotype.Service;

import nodo.erp.Sd.Account.Account;
import lombok.RequiredArgsConstructor;
import nodo.erp.Mm.DataNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
	private final AccountRepository accountRepository;
	
	public List<Account> getList(){
		return this.accountRepository.findAll();
	}
	
	public Account getAccount(String AC_Code) {
		Optional<Account> account = this.accountRepository.findById(AC_Code);
		if(account.isPresent()) {
			return account.get();
		} else {
			throw new DataNotFoundException("Account not found");
		}
	}
	
	public void create(String AC_Code, String AC_Company, String AC_Address, String AC_Name, String AC_Phone, 
			String AC_Item, String AC_Icode, float VAT, String AC_Date, int AC_Price, String AC_Num) {
		Account acc = new Account();
		
		String a = AC_Date.substring(2, 4);
		String b = AC_Date.substring(5, 7);
		String c="";
		
		for(int i = 1; i <= 9999; i++) {
			c = "";
			if(i < 10) {
				c = "000" + i;
			} else if (i < 100) {
				c = "00" + i;
			} else if (i < 1000) {
				c = "0" +  i;
			} else  {
				c = String.valueOf(c);
			}
		} // for 문 문제있음
		
		System.out.println("c:"+c);
		
		acc.setAC_Code("AC" + a + b + c);
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
		
		this.accountRepository.save(acc);
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
		
		
		this.accountRepository.save(acc);
	}
	
	public void delete(String AC_Code) {
		Account acc = new Account();
		
		acc.setAC_Code(AC_Code);
		
		this.accountRepository.delete(acc);
	}
}
