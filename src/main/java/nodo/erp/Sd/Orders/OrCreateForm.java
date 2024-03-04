package nodo.erp.Sd.Orders;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrCreateForm {
	
	private String OR_Num;
	
	@NotEmpty(message = "발주신청 날짜 입력은 필수입니다")
	private String OR_Date;
	
	private String OR_Item;
	
	private String OR_Icode;
	
	private String OR_Company;
	
	private String OR_Code;
		
	private float VAT;
	
	private String OR_Count;
	
	private String OR_Price;
	
	private String OR_CP;
	
	private String OR_VAT;
	
	private String OR_VATSUM;
	
}
