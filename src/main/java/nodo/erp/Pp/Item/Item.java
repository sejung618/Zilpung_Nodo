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
	private Integer itmId;
	
	@Column(unique = true)
	private String itmCode;

	private String itmName;
	
	@ManyToOne
	private ItemGroup itmGroup;
//	private String ItmGroup;
	
	@ManyToOne
	private ItemCategory itmCategory;
//	private String ItmCategory;
	
	private String itmStandard;
	
	private Integer itmSprice;
	
	private Integer itmRprice;
	
	private LocalDateTime createDate;
	
}