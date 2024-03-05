package nodo.erp.Mm.Inventory;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Pp.Item.Item;

@Getter
@Setter
@Entity
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer inid;		//번호
	

	private String indate;		//일자
	
	
	private String innum;		//일자번호(yymmdd)
	
	
	private Integer inquantity;		//수량
	
	
	private LocalDateTime createDate; 		
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private Employee employee; //사원번호, 사원이름
	
	@ManyToOne
	private Item item; //품목코드, 품목명, 규격
}