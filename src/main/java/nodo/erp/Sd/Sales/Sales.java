package nodo.erp.Sd.Sales;

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
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 11)
	private String SA_Num;
	
	@Column(length = 15)
	private String SA_Method; // 판매방법(온라인판매, 오프라인판매, 예약)
	
	@Column(length = 10)
	private String SA_Date;
	
	@Column(length = 30)
	private String SA_Item;
	
	@Column(length = 11)
	private String SA_Icode;
	
	@Column(length = 3)
	private Integer SA_Count;
	
	@Column(length = 7)
	private Integer SA_Price;
	
	@Column(length = 12)
	private Integer SA_CP;
	
	@Column(length = 12)
	private Integer SA_VAT;
	
	@Column(length = 15)
	private Integer SA_Sum;
}
