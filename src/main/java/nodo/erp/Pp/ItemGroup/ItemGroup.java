package nodo.erp.Pp.ItemGroup;

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
public class ItemGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IgId;
	
	@Column(unique = true, length = 6)
	private String IgCode;

	@Column(length = 10)
	private String IgName;
}

