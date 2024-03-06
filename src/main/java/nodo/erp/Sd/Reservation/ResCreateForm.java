package nodo.erp.Sd.Reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCreateForm {

	private String rvnum;
	
	private LocalDateTime rvdate;
	
	@NotEmpty(message = "제품 정보 입력은 필수입니다")
	private String rvitem;
	
	private String rvicode;
	
	private String rvcount;
	
	private String rvprice;
	
	private String rvcp;
	
	private String rvvat;
	
	private String rvsum;
	
	private String rvpick;
	
	private String rvptime;
}
