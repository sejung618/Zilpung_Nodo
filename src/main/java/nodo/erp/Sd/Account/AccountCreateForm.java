package nodo.erp.Sd.Account;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateForm {

	private String AC_Code;
	
	@NotEmpty(message="상호명은 입력 필수입니다.")
	private String AC_Company;
	
	@NotEmpty(message="거래처 주소 입력은 필수입니다.")
	private String AC_Address;
	
	@NotEmpty(message="회사 전화번호 입력은 필수입니다.")
	private String AC_Num;
	
	@NotEmpty(message="대표이사 이름 입력은 필수입니다.")
	private String AC_Name;
	
	@NotEmpty(message="대표이사 연락처 입력은 필수입니다.")
	private String AC_Phone;
	
	@NotEmpty(message="물품명 입력은 필수입니다.")
	private String AC_Item;
	
	@NotEmpty(message="물품코드 입력은 필수입니다.")
	private String AC_Icode;
	
	@NotEmpty(message="물품가격 입력은 필수입니다.")
	private String AC_Price;
	
	@NotEmpty(message="첫 거래날짜 입력은 필수입니다.")
	private String AC_Date;
	
	private float VAT;
	
}
