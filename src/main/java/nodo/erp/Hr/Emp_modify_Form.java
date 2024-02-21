package nodo.erp.Hr;

import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emp_modify_Form {
	
	@NotEmpty(message="이름은 필수항목입니다.")
    @Size(max=50)
    private String empname;

}
