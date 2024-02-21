package nodo.erp.Hr;


import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emp_modify_Form {
	
	@NotEmpty(message="이름은 필수항목입니다.")
    private String empname;
	
	@NotEmpty(message="주소는 필수항목입니다.")
    private String empadd;

	@NotEmpty(message="주소는 필수항목입니다.")
    private String empphone;
	
	@NotEmpty(message="주소는 필수항목입니다.")
    private String empmail;
}
