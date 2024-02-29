package nodo.erp.Pp.Item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nodo.erp.Pp.ItemGroup.ItemGroup;

@Getter
@Setter
public class ItemForm {
	
	private String ItmCode;
	
	@NotBlank(message="품목명은 필수항목입니다.")
	@Size(min=1, max=30, message="품목명은 1자 이상 30자 이하로 입력해주세요.")
	private String ItmName;
	
	// ItemGroup에서 값 가져오기
	private ItemGroup ItmGroup;
	
	// ItemCategory에서 값 가져오기
	private String ItmCategory;
	
	@Null
	private String ItmStandard;
	
	@Null
	@PositiveOrZero(message = "입고단가는 0 이상의 값으로 입력해주세요.")
	private Integer ItmSprice;
	
	@Null
	@PositiveOrZero(message = "출고단가는 0 이상의 값으로 입력해주세요.")
	private Integer ItmRprice;
}
