package nodo.erp.Sd.Purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 10)
	private String PC_Num; // 구매번호 - 날짜6자리 + "-" + 001 ~ 999
	
	@Column(length = 10)
	private String PC_Date; // 구매날짜
	
	@Column(length = 30)
	private String PC_Company; // 구매회사 명 - Account.AC_Company 가져와서 저장
	
	/*@ManyToOne
    @JoinColumn(name = "PC_Code", referencedColumnName = "AC_Code")
    private Account account;*/
	@Column(length = 20)
	private String PC_Code;
	
	@Column(length = 30)
	private String PC_Item; // 구매물품 - Account.AC_Item 가져와서 저장
	
	@Column(length = 11)
	private String PC_Icode; // 구매물품코드 - Account.AC_Icode 가져와서 저장
	
	@Column(length = 3)
	private Integer PC_Count; // 구매물품 수량
	
	@Column(length = 7)
	private Integer PC_Price; // 구매물품 가격 - Account.AC_Price 가져와서 저장
	
	@Column(length = 12)
	private Integer PC_CP; // 구매 공급가액 - 수량 * 가격
	
	@Column(length = 12)
	private Integer PC_VAT; // 부가세 가격 - PC_CP / 10
	
	@Column(length = 15)
	private Integer PC_VATSUM; // 총 액 - PC_CP + PC_VAT
	
}
