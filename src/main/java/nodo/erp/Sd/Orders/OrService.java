package nodo.erp.Sd.Orders;

import java.util.List;
import java.util.Optional;

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
	
	public void create(String OR_Num, String OR_Date, String OR_Item, String OR_Icode, String OR_Company,
			Integer OR_Count, Integer OR_Price, Integer OR_CP, float VAT, Integer OR_VAT, Integer OR_VATSUM, String OR_Code) {
		Orders or = new Orders();
		
		or.setOR_Num(OR_Num);
	    or.setOR_Date(OR_Date);
	    or.setOR_Item(OR_Item);
	    or.setOR_Icode(OR_Icode);
	    or.setOR_Company(OR_Company);
	    or.setOR_Count(OR_Count);
	    or.setOR_Price(OR_Price);
	    or.setOR_CP(OR_CP);
	    or.setVAT((float)10.0);
	    or.setOR_VAT(OR_VAT);
	    or.setOR_VATSUM(OR_VATSUM);
	    or.setOR_Code(OR_Code);
	    
	    this.orRepository.save(or);
	}

}
