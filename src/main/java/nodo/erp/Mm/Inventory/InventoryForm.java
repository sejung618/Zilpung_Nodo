package nodo.erp.Mm.Inventory;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InventoryForm {
	@NotEmpty(message="일자는 필수항목입니다.") 
	private String INDate;
	
	@NotNull(message="품목코드는 필수항목입니다.")
	private Integer ItmCode;
	
	@NotNull(message="수량은 필수항목입니다.")
	private Integer INQuantity;
	
	@NotNull(message="담당자사번은 필수항목입니다.")
	private Integer empnum;

}