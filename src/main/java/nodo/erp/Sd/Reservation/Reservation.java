package nodo.erp.Sd.Reservation;

import java.time.LocalDateTime;

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
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 11)
	private String RV_Num; //예약날짜 6자리 + '-' + 0001 ~ 9999

	@Column
    private LocalDateTime RV_Date; // 예약 날짜와 시간
	
	@Column(length = 30)
	private String RV_Item;
	
	@Column(length = 11)
	private String RV_Icode;
	
	@Column(length = 3)
	private Integer RV_Count;
	
	@Column(length = 7)
	private Integer RV_Price;
	
	@Column(length = 12)
	private Integer RV_CP;
	
	@Column(length = 12)
	private Integer RV_VAT;
	
	@Column(length = 15)
	private Integer RV_Sum;
	
	@Column(length = 1)
	private String RV_Pick;
	
	@Column
    private LocalDateTime RV_PTime; // 픽업 여부를 O로 설정한 그 순간의 시간
	
}
