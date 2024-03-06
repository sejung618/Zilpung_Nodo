package nodo.erp.Sd.Orders;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrCreateForm {
	
	private String ornum;
	
	@NotEmpty(message = "발주신청 날짜 입력은 필수입니다")
	private String ordate;
	
	private String oritem;
	
	private String oricode;
	
	private String orcompany;
	
	private String orcode;
		
	private float vat;
	
	private String orcount;
	
	private String orprice;
	
	private String orcp;
	
	private String orvat;
	
	private String orvatsum;
	
}
