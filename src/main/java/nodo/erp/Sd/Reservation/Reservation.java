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
	private String rvnum; //예약날짜 6자리 + '-' + 0001 ~ 9999

	@Column
    private LocalDateTime rvdate; // 예약 날짜와 시간
	
	@Column(length = 30)
	private String rvitem;
	
	@Column(length = 50)
	private String rvicode;
	
	@Column(length = 3)
	private Integer rvcount;
	
	@Column(length = 7)
	private Integer rvprice;
	
	@Column(length = 12)
	private Integer rvcp;
	
	@Column(length = 12)
	private Integer rvvat;
	
	@Column(length = 15)
	private Integer rvsum;
	
	@Column(length = 1)
	private String rvpick;
	
	@Column
    private LocalDateTime rvptime; // 픽업 여부를 O로 설정한 그 순간의 시간
	
}
