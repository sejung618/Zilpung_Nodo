package nodo.erp.Sd.Purchase;

import java.util.List;
import java.util.Optional;

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
	
    public void create(String PC_Num, String PC_Date, String PC_Code,
                              String PC_Company, String PC_Item, String PC_Icode, Integer PC_Count,
                               Integer PC_Price, Integer PC_CP, Integer PC_VAT, Integer PC_VATSUM) {
    	Purchase pur = new Purchase();
        
    	String yy = PC_Date.substring(2, 4);
    	String mm = PC_Date.substring(5, 7);
    	String dd = PC_Date.substring(8, 10);
    	String ymd = yy + mm + dd;
    	String Num = String.format("%03d", generatePCNum(ymd));
    	
    	pur.setPC_Num(ymd + "-" + Num);
    	pur.setPC_Date(PC_Date);
    	pur.setPC_Company(PC_Company);
    	pur.setPC_Code(PC_Code);
        pur.setPC_Item(PC_Item);
        pur.setPC_Icode(PC_Icode);
        pur.setPC_Count(PC_Count);
        pur.setPC_Price(PC_Price);
        pur.setPC_CP(PC_CP);
        pur.setPC_VAT(PC_VAT);
        pur.setPC_VATSUM(PC_VATSUM);

        this.purRepositroy.save(pur);
    }
	
	private Integer generatePCNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery
				("SELECT MAX(CAST(SUBSTRING(i.PC_Date,-3) AS int)) "
						+ "FROM Purchase i WHERE SUBSTRING(i.PC_Date, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}
	
	public void update(Purchase purchase, Integer PC_Count, Integer PC_Price, Integer PC_CP, Integer PC_VAT, Integer PC_VATSUM) {
		Purchase pur = this.purRepositroy.findById(purchase.getId()).orElse(null);
		
		pur.setPC_Count(PC_Count);
		pur.setPC_Price(PC_Price);
		pur.setPC_CP(PC_Count * PC_Price);
		pur.setPC_VAT(PC_CP / 10);
        pur.setPC_VATSUM(PC_CP + PC_VAT);
        
        this.purRepositroy.save(pur);
	}
	
	
	 public void delete(Integer id) {
		 Purchase pur = new Purchase();
		 pur.setId(id);
		 this.purRepositroy.delete(pur);
	 }
	
	
}
