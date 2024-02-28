package nodo.erp.Pp.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Pp.ItemGroup.ItemGroup;

@Getter
@Setter
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ItmId;
	
	@Column(unique = true, length = 11)
	private String ItmCode;

	@Column(length = 30)
	private String ItmName;
	
	@ManyToOne
	@JoinColumn(name="id")
	private ItemGroup itemGroup;
	
	@Column(length = 15)
	private String ItmCategory;
}