package nodo.erp.Pp.Item;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Pp.ItemCategory.ItemCategory;
import nodo.erp.Pp.ItemGroup.ItemGroup;

@Getter
@Setter
@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ItmId;
	
	@Column(unique = true)
	private String ItmCode;

	private String ItmName;
	
	@ManyToOne
	private ItemGroup ItmGroup;
//	private String ItmGroup;
	
	@ManyToOne
	private ItemCategory ItmCategory;
//	private String ItmCategory;
	
	private String ItmStandard;
	
	private Integer ItmSprice;
	
	private Integer ItmRprice;
	
	private LocalDateTime createDate;
	
}