package nodo.erp.Pd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ItemGroup {
	@Id
	@Column(length = 6)
	private String IgCode;

	@Column(unique = true, length = 10)
	private String IgName;
}
