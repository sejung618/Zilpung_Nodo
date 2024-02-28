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
import nodo.erp.Sd.Account;

@Getter
@Setter
@Entity
public class Shipping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer SPid;		//번호
	
	@Column(length = 15)
	private String SPDate;		//출고일자
	
	@Column(length = 30)
	private String SPAName;		//거래처명
	
	@Column(length = 30)
	private String SPACode;		//거래처코드
	
	@Column(length = 30)
	private String SPIName;		//품목명
	
	@Column(length = 11)
	private String SPICode;		//품목코드
	
	@Column(length = 10)
	private String SPPName;		//담당자
	
	@Column(length = 13)
	private String SPPNum;		//담당사번
	
	@Column(length = 20)
	private String SPDT;		//납기일자
	
	@Column(length = 7)
	private Integer SPCAmount;		//출고수량
	
	@Column(length = 30)
	private String SPLocation;		//출고위치
	
	@Column(length = 20)
	private String SPState;		//진행상태
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private Account account;
}
