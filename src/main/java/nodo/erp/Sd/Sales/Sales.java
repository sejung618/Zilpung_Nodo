package nodo.erp.Sd.Sales;

import java.time.LocalDate;
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
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 11)
	private String sanum;
	
	@Column(length = 15)
	private String samethod; // 판매방법(온라인판매, 오프라인판매, 예약)
	
	@Column
	private LocalDate sadate;
	
	@Column(length = 30)
	private String saitem;
	
	@Column(length = 11)
	private String saicode;
	
	@Column(length = 3)
	private Integer sacount;
	
	@Column(length = 7)
	private Integer saprice;
	
	@Column(length = 12)
	private Integer sacp;
	
	@Column(length = 12)
	private Integer savat;
	
	@Column(length = 15)
	private Integer sasum;
}
