package nodo.erp.Sd.Reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpdateForm {

	@NotEmpty(message = "제품을 픽업해가셨다면 O로 바꿔주세요.")
	private String rvpick;
	
	private LocalDateTime rvptime;
	
	
}
