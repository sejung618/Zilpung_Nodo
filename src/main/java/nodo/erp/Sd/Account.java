package nodo.erp.Sd;

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
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, length = 20)
    private String accode; 
    
	@Column(length = 30)
	private String accompany; // 회사명
	
	@Column(length = 12)
	private String acnum; // 회사 전화번호
	
	@Column(length = 100)
	private String acaddress; // 회사 주소
	
	@Column(length = 20)
	private String acname; // 대표이사 명
	
	@Column(length = 13)
	private String acphone; // 대표이사 연락처
	
	@Column(length = 30)
	private String acitem; // 물품명
	
	@Column(length = 11)
	private String acicode; // 물품코드
	
	@Column(length = 7)
	private Integer acprice; // 가격
	
	@Column(columnDefinition = "NUMERIC(4, 2) NOT NULL default 10.0")
	private float vat; // 부가세율 10.0 고정
	
	@Column(length = 10)
	private String acdate; // 첫 거래날짜

}