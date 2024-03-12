package nodo.erp.Mm.Shipping;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingForm {
	
	@NotEmpty(message="출고일자는 필수항목입니다.") 
	private String spdate;		//출고일자
	
	@NotNull(message="거래처코드는 필수항목입니다.") 
	private Integer accode;		//거래처코드
	
	@NotNull(message="품목코드는 필수항목입니다.") 
	private Integer itmcode;		//품목코드
	
	@NotNull(message="출고수량은 필수항목입니다.") 
	private Integer spcamount;		//출고수량
	
	@NotEmpty(message="진행상태는 필수항목입니다.") 
	private String spstate;		//진행상태
	
	@NotEmpty(message="출고위치는 필수항목입니다.")
	private String splocation;		//출고위치
	
	@NotEmpty(message="납기일자는 필수항목입니다.") 
	private String spdt;		//납기일자

	

}
