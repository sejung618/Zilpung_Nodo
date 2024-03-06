package nodo.erp.Mm.Shipping;

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
public class Shipping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer spid;		//번호
	
	private String spnum;		//일자번호(yymmdd)
	
	@Column(length = 15)
	private String spdate;		//출고일자
	
	@Column(length = 20)
	private String spdt;		//납기일자
	
	@Column(length = 7)
	private Integer spcamount;		//출고수량
	
	@Column(length = 30)
	private String splocation;		//출고위치
	
	@Column(length = 20)
	private String spstate;		//진행상태
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Employee employee; //사원번호, 사원이름
	
	@ManyToOne
	private Account account; //거래처명, 거래처코드
	
	@ManyToOne
	private Item item; //품목코드, 품목명, 규격
}
