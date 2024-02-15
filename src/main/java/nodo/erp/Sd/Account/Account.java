package nodo.erp.Sd.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
	@Id
	private String AC_Code; // 거래처 코드(AC + 연도(ex 2024 = 24) + 월(01~12) + 순번(001~999)

	
	@Column(length = 30)
	private String AC_Company; // 회사 상호명
	
	@Column(length = 12)
	private String AC_Num; // 회사 전화번호
	
	@Column(length = 100)
	private String AC_Address;
	
	@Column(length = 20)
	private String AC_Name;
	
	@Column(length = 13)
	private String AC_Phone;
	
	@Column(length = 30)
	private String AC_Item;
	
	@Column(length = 11)
	private String AC_Icode;
	
	@Column(length = 7)
	private Integer AC_Price;
	
	@Column(columnDefinition = "NUMERIC(4,2) NOT NULL")
	private float VAT = 10.0f;
	
	@Column(length = 10)
	private String AC_Date;
	
}
