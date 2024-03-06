package nodo.erp.Pp.BomCitem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Pp.Bom.Bom;
import nodo.erp.Pp.Item.Item;

@Getter
@Setter
@Entity
public class BomCitem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bcId;
	
	@ManyToOne
	// Item에서 정보 가져오기: 소모품
	private Item bcItem;
	
	// 소모량
	private Integer bcCount;
	
	@ManyToOne
	private Bom bom;
	
}
