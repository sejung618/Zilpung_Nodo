package nodo.erp.Mm.Warehousing;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehousingForm {
	@NotEmpty(message="입고일자는 필수항목입니다.") 
	private String whdate;		//입고일자
	
	@NotNull(message="거래처코드는 필수항목입니다.") 
	private Integer accode;		//거래처코드
	
	@NotNull(message="품목코드는 필수항목입니다.") 
	private Integer itmcode;		//품목코드
	
	@NotNull(message="입고수량은 필수항목입니다.") 
	private Integer whcamount;		//입고수량
	
	@NotEmpty(message="진행상태는 필수항목입니다.") 
	private String whstate;		//진행상태
	
	
	@NotEmpty(message="납기일자는 필수항목입니다.") 
	private String whdt;		//납기일자
	
}
