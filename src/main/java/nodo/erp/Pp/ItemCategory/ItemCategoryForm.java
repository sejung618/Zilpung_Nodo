package nodo.erp.Pp.ItemCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCategoryForm {
	
	private String IcCode;
	
	@NotBlank(message="품목그룹명은 필수항목입니다.")
	@Size(min=1, max=10, message="품목분류명은 1자 이상 10자 이하로 입력해주세요.")
	private String IcName;
}
