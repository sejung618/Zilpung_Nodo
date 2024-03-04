package nodo.erp.Sd.Orders;

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
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 13)
	private String OR_Num; //PC_Num 가져오고 앞에 "OR-"만 붙이면 됨
	
	@Column(length = 10)
	private String OR_Date;
	
	@Column(length = 30)
	private String OR_Item;
	
	@Column(length = 11)
	private String OR_Icode;
	
	@Column(length = 30)
	private String OR_Company;
	
	@Column(length = 20)
	private String OR_Code;
	
	@Column(length = 3)
	private Integer OR_Count; // 발주수량 - Purchase.PC_Count 참조
	
	@Column(length = 7)
	private Integer OR_Price; // 구매물품 가격 - Account.AC_Price 가져와서 저장
	
	@Column(length = 12)
	private Integer OR_CP; // 구매 공급가액 - 수량 * 가격
	
	@Column(columnDefinition = "NUMERIC(4, 2) NOT NULL default 10.0")
	private float VAT; // 부가세율 10.0 고정
	
	@Column(length = 12)
	private Integer OR_VAT; // 부가세 가격 - PC_CP / 10
	
	@Column(length = 15)
	private Integer OR_VATSUM; // 총 액 - PC_CP + PC_VAT
	 

}
