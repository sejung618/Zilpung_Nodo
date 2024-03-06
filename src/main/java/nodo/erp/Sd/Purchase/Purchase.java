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
	private String pcnum; // 구매번호 - 날짜6자리 + "-" + 001 ~ 999
	
	@Column(length = 10)
	private String pcdate; // 구매날짜
	
	@Column(length = 30)
	private String pccompany; // 구매회사 명 - Account.AC_Company 가져와서 저장
	
	/*@ManyToOne
    @JoinColumn(name = "PC_Code", referencedColumnName = "AC_Code")
    private Account account;*/
	@Column(length = 20)
	private String pccode;
	
	@Column(length = 30)
	private String pcitem; // 구매물품 - Account.AC_Item 가져와서 저장
	
	@Column(length = 11)
	private String pcicode; // 구매물품코드 - Account.AC_Icode 가져와서 저장
	
	@Column(length = 3)
	private Integer pccount; // 구매물품 수량
	
	@Column(length = 7)
	private Integer pcprice; // 구매물품 가격 - Account.AC_Price 가져와서 저장
	
	@Column(length = 12)
	private Integer pccp; // 구매 공급가액 - 수량 * 가격
	
	@Column(length = 12)
	private Integer pcvat; // 부가세 가격 - PC_CP / 10
	
	@Column(length = 15)
	private Integer pcvatsum; // 총 액 - PC_CP + PC_VAT
	
}
