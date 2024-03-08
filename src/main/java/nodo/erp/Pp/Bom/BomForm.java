package nodo.erp.Pp.Bom;

import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BomForm {
	
	// Bom 고유 코드
	private String bomCode;

	// 생산품목: Item에서 ItmId 가져오기
	private Integer bpItemId;
			
	// 생산량
	@PositiveOrZero(message = "생산량은 1 이상의 값으로 입력해주세요.")
	private Integer bpCount;
				
	// 소모품목 리스트: BomCitem에서 bcId 가져오기
	private List<Integer> bcIdList;
		
	// 소요시간
	@PositiveOrZero(message = "소요시간은 1 이상의 값으로 입력해주세요.")
	private Integer bpHour;
	
}
