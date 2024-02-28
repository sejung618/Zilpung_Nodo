package nodo.erp.Sd.Purchase;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurUpdateForm {

	@NotEmpty(message = "변경 수량 입력은 필수입니다.")
	private Integer PC_Count;
	
	private Integer PC_Price;
	
	private Integer PC_CP;
	
	private Integer PC_VAT;
	
	private Integer PC_VATSUM;
}
