package nodo.erp.Sd.Purchase;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurCreateForm {

	private String pcnum;
	
	@NotEmpty(message = "구매날짜는 필수입니다. 달력을 누르고 날짜를 선택하세요")
	private String pcdate;
	
	private String pccompany;
	
	@NotEmpty(message = "회사 코드를 선택해주세요")
	private String pccode;
	
	private String pcitem;
	
	private String pcicode;
	
	@NotEmpty(message = "구매 수량입력은 필수입니다.")
	private String pccount;
	
	private String pcprice;
	
	private String pccp;
	
	private String pcvat;
	
	private String pcvatsum;
	
}
