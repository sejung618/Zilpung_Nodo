package nodo.erp.Mm;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryForm {
	@NotEmpty(message="재고번호는 필수항목입니다.") 
	private String INDate;
	
	@NotEmpty(message="품목명은 필수항목입니다.")
	private String ININame;
	
	@NotEmpty(message="품목코드는 필수항목입니다.")
	private String INICode;
	
	@NotNull(message="수량은 필수항목입니다.")
	private Integer INQuantity;
	
	@NotEmpty(message="담당자는 필수항목입니다.")
	private String INPName;
	
	@NotEmpty(message="담당자사번은 필수항목입니다.")
	private String INPNum;
	
	private String INStandard;
}
