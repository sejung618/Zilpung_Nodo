package nodo.erp.Sd;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccUpdateForm {

	@NotEmpty(message = "상호명 입력은 필수입니다.")
	private String AC_Company;
	
	@NotEmpty(message="거래처 주소 입력은 필수입니다.")
	private String AC_Address;
	
	@NotEmpty(message="대표이사 이름 입력은 필수입니다.")
	private String AC_Name;
	
	@NotEmpty(message="대표이사 연락처 입력은 필수입니다.")
	private String AC_Phone;
	
	@NotEmpty(message="물품가격 입력은 필수입니다.")
	private String AC_Price;
}