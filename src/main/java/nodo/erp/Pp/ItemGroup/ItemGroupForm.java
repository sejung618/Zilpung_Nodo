package nodo.erp.Pp.ItemGroup;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemGroupForm {
	
	@Column(length = 6)
	private String IgCode;
	
	@NotBlank(message="품목그룹명은 필수항목입니다.")
	@Size(max=20)
	private String IgName;
}
