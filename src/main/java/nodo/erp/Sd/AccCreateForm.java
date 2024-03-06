package nodo.erp.Sd;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccCreateForm {
	
	private String accode;
	
	private float vat;
	
	@NotEmpty(message = "상호명 입력은 필수입니다.")
	private String accompany;
	
	@NotEmpty(message="거래처 주소 입력은 필수입니다.")
	private String acaddress;
	
	@NotEmpty(message="회사 전화번호 입력은 필수입니다.")
	private String acnum;
	
	@NotEmpty(message="대표이사 이름 입력은 필수입니다.")
	private String acname;
	
	@NotEmpty(message="대표이사 연락처 입력은 필수입니다.")
	private String acphone;
	
	@NotEmpty(message="물품명 입력은 필수입니다.")
	private String acitem;
	
	@NotEmpty(message="물품코드 입력은 필수입니다.")
	private String acicode;
	
	@NotEmpty(message="물품가격 입력은 필수입니다.")
	private String acprice;
	
	@NotEmpty(message="첫 거래날짜 입력은 필수입니다.")
	private String acdate;
}