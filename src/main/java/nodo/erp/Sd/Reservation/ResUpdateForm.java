package nodo.erp.Sd.Reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpdateForm {

	
	private String rvpick;
	
	private LocalDateTime rvptime;
	
	
}
