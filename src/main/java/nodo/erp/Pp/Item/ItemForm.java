package nodo.erp.Pp.Item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {
	
	private String ItmCode;
	
	@NotBlank(message="품목명은 필수항목입니다.")
	@Size(min=1, max=30, message="품목명은 1자 이상 30자 이하로 입력해주세요.")
	private String ItmName;
	
	@NotBlank(message="품목그룹을 선택하세요.")
	// ItemGroup에서 값 가져오기
	private Integer ItmGroup;
	
	@NotBlank(message="분류를 선택하세요.")
	// ItemCategory에서 값 가져오기
	private Integer ItmCategory;
	
	private String ItmStandard;
	
	@PositiveOrZero(message = "입고단가는 0 이상의 값으로 입력해주세요.")
	private Integer ItmSprice;
	
	@PositiveOrZero(message = "출고단가는 0 이상의 값으로 입력해주세요.")
	private Integer ItmRprice;
	
}
