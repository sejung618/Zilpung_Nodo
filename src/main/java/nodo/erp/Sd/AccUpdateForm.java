package nodo.erp.Sd;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccUpdateForm {

	@NotEmpty(message = "상호명 입력은 필수입니다.")
	private String accompany;
	
	@NotEmpty(message="거래처 주소 입력은 필수입니다.")
	private String acaddress;
	
	@NotEmpty(message="대표이사 이름 입력은 필수입니다.")
	private String acname;
	
	@NotEmpty(message="대표이사 연락처 입력은 필수입니다.")
	private String acphone;
	
	@NotNull(message="물품가격 입력은 필수입니다.")
	private Integer acprice;
}