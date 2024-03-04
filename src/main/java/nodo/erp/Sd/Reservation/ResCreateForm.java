package nodo.erp.Sd.Reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCreateForm {

	private String RV_Num;
	
	private LocalDateTime RV_Date;
	
	@NotEmpty(message = "제품 정보 입력은 필수입니다")
	private String RV_Item;
	
	private String RV_Icode;
	
	private String RV_Count;
	
	private String RV_Price;
	
	private String RV_CP;
	
	private String RV_VAT;
	
	private String RV_Sum;
	
	private String RV_Pick;
	
	private String RV_PTime;
}
