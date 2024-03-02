package nodo.erp.Pp.Item;

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
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ItmId;
	
	@Column(unique = true, length = 15)
	private String ItmCode;

	@Column(length = 30)
	private String ItmName;
	
	private String ItmGroup;
	
	@Column(length = 10)
	private String ItmCategory;
	
	@Column(length = 15)
	private String ItmStandard;
	
	@Column(length = 10)
	private Integer ItmSprice;
	
	@Column(length = 10)
	private Integer ItmRprice;
	
	private LocalDateTime createDate;
	
}