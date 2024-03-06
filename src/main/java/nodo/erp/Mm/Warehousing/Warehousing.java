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
import nodo.erp.Pp.Item.Item;
import nodo.erp.Sd.Account;

@Getter
@Setter
@Entity
public class Warehousing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer whid;		//번호
	
	private String whnum;		//일자번호(yymmdd)
	
	@Column(length = 15)
	private String whdate;		//입고일자
	
	@Column(length = 20)
	private String whdt;		//납기일자
	
	@Column(length = 7)
	private Integer whcamount;		//입고수량
	
	@Column(length = 30)
	private String whlocation;		//입고위치
	
	@Column(length = 20)
	private String whstate;		//진행상태
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Employee employee; //사원번호, 사원이름
	
	@ManyToOne
	private Account account; //거래처명, 거래처코드
	
	@ManyToOne
	private Item item; //품목코드, 품목명, 규격
}
