package nodo.erp.Mm.Warehousing;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Hr.Entity.Employee;

@Getter
@Setter
@Entity
public class Warehousing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer WHid;		//번호
	
	@Column(length = 15)
	private String WHDate;		//입고일자
	
	@Column(length = 10)
	private String WHPName;		//담당자
	
	@Column(length = 13)
	private String WHPNum;		//담당사번
	
	@Column(length = 30)
	private String WHAName;		//거래처명
	
	@Column(length = 30)
	private String WHACode;		//거래처코드
	
	@Column(length = 30)
	private String WHIName;		//품목명
	
	@Column(length = 11)
	private String WHICode;		//품목코드
	
	@Column(length = 7)
	private Integer WHCAmount;		//입고수량
	
	@Column(length = 20)
	private String WHState;		//진행상태
	
	@Column(length = 30)
	private String WHLocation;		//입고위치
	
	@Column(length = 20)
	private String WHDT;		//납기일자
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Employee employee;
	

}
