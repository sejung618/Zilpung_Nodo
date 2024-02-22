package nodo.erp.Mm;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehousingForm {
	@NotEmpty(message="입고일자는 필수항목입니다.") 
	private String WHDate;		//입고일자
	
	@NotEmpty(message="담당자는 필수항목입니다.") 
	private String WHPName;		//담당자
	
	@NotEmpty(message="담당사번는 필수항목입니다.") 
	private String WHPNum;		//담당사번
	
	@NotEmpty(message="거래처명은 필수항목입니다.") 
	private String WHAName;		//거래처명
	
	@NotEmpty(message="거래처코드는 필수항목입니다.") 
	private String WHACode;		//거래처코드
	
	@NotEmpty(message="품목명은 필수항목입니다.") 
	private String WHIName;		//품목명
	
	@NotEmpty(message="품목코드는 필수항목입니다.") 
	private String WHICode;		//품목코드
	
	@NotNull(message="입고수량은 필수항목입니다.") 
	private Integer WHCAmount;		//입고수량
	
	@NotEmpty(message="진행상태는 필수항목입니다.") 
	private String WHState;		//진행상태
	
	@NotEmpty(message="입고위치는 필수항목입니다.")
	private String WHLocation;		//입고위치
	
	@NotEmpty(message="납기일자는 필수항목입니다.") 
	private String WHDT;		//납기일자
}
