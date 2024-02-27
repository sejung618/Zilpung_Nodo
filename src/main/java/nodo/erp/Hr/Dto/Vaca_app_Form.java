package nodo.erp.Hr.Dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vaca_app_Form {
	
	private LocalDate startdate;	//휴가 시작날짜
	private LocalDate enddate;		//휴가 끝날짜
	private String leavetype;

}
