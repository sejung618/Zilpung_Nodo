package nodo.erp.Mm;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingForm {
	
	@NotEmpty(message="출고일자는 필수항목입니다.") 
	private String SPDate;		//출고일자
	
	@NotEmpty(message="담당자는 필수항목입니다.") 
	private String SPPName;		//담당자
	
	@NotEmpty(message="담당사번는 필수항목입니다.") 
	private String SPPNum;		//담당사번
	
	@NotEmpty(message="거래처명은 필수항목입니다.") 
	private String SPAName;		//거래처명
	
	@NotEmpty(message="거래처코드는 필수항목입니다.") 
	private String SPACode;		//거래처코드
	
	@NotEmpty(message="품목명은 필수항목입니다.") 
	private String SPIName;		//품목명
	
	@NotEmpty(message="품목코드는 필수항목입니다.") 
	private String SPICode;		//품목코드
	
	@NotNull(message="출고수량은 필수항목입니다.") 
	private Integer SPCAmount;		//출고수량
	
	@NotEmpty(message="진행상태는 필수항목입니다.") 
	private String SPState;		//진행상태
	
	@NotEmpty(message="출고위치는 필수항목입니다.")
	private String SPLocation;		//출고위치
	
	@NotEmpty(message="납기일자는 필수항목입니다.") 
	private String SPDT;		//납기일자
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
	

}
