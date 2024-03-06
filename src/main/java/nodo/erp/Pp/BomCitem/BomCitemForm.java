package nodo.erp.Pp.BomCitem;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BomCitemForm {
	
	// 소모품목: Item에서 ItmId 가져오기
	private Integer bcItemId;
		
	// 소모량
	@PositiveOrZero(message = "소모량은 1 이상의 값으로 입력해주세요.")
	private Integer bcCount;
		
	// 생산품: Bom에서 bomId 가져오기
	private Integer bomId;

}
