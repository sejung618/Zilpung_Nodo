package nodo.erp.Pp.Bom;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Pp.Item.Item;

@Getter
@Setter
@Entity
public class Bom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bomId;
	
	@Column(unique = true)
	private String bomCode;
	
	@ManyToOne
	// Item에서 정보 가져오기: 생산품
	private Item bpItem;
	
	// 생산량
	private Integer bpCount;
	
	// 소모품목수
//	@OneToMany
	private Integer bcListSize;
	
	// 소요시간
	private Integer bpHour;
	
	private LocalDateTime createDate;

}
