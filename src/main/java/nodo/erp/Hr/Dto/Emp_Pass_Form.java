package nodo.erp.Hr.Dto;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emp_Pass_Form {

	@NotEmpty(message="비빌번호는 필수항목입니다.")
    private String pass;
	
	@NotEmpty(message="새 비빌번호는 필수항목입니다.")
    private String newpass;
	
	@NotEmpty(message="비밀번호 확인은 필수항목입니다.")
    private String newpass2;

}
