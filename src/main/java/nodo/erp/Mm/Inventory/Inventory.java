package nodo.erp.Mm.Inventory;

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
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;		//번호
	
	@Column(length = 15)
	private String INDate;		//일자
	
	@Column(length = 10)
	private String INPName;		//담당자
	
	@Column(length = 13)
	private String INPNum;		//담당사번
	
	@Column(length = 30)
	private String ININame;		//품목명
	
	@Column(length = 11)
	private String INICode;		//품목코드
	
	@Column(length = 7)
	private Integer INQuantity;		//수량
	
	@Column(length = 50)
	private String INStandard;		//규격
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
}
