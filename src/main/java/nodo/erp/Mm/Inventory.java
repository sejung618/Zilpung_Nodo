package nodo.erp.Mm;

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
	private Integer id;
	
	@Column(length = 15)
	private String INDate;
	
	@Column(length = 10)
	private String INPName;
	
	@Column(length = 13)
	private String INPNum;
	
	@Column(length = 30)
	private String ININame;
	
	@Column(length = 11)
	private String INICode;
	
	@Column(length = 7)
	private Integer INQuantity;
	
	@Column(length = 50)
	private String INStandard;
	
	private LocalDateTime createDate; 
	
	private LocalDateTime modifyDate;
}
