package nodo.erp.Sd;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurCreateForm {

	private String PC_Num;
	
	@NotEmpty(message = "구매날짜는 필수입니다. 달력을 누르고 날짜를 선택하세요")
	private String PC_Date;
	
	private String PC_Company;
	
	@NotEmpty(message = "회사 코드를 선택해주세요")
	private Account account;
	
	private String PC_Item;
	
	private String PC_Icode;
	
	@NotEmpty(message = "구매 수량입력은 필수입니다.")
	private String PC_Count;
	
	private String PC_Price;
	
	private String PC_CP;
	
	private String PC_VAT;
	
	private String PC_VATSUM;
	
}
