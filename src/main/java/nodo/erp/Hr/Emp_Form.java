package nodo.erp.Hr;

import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emp_Form {

	@NotEmpty(message="이름은 필수항목입니다.")
    @Size(max=50)
    private String EmpName;

    @NotEmpty(message="주민번호는 필수항목입니다.")
    private String EmpSsn;
    
    @NotEmpty(message="주소은 필수항목입니다.")
    private String EmpAdd;
    
    private String EmpPhone;
    
    private String EmpMail;
    
    private Date EmpDate;
    
    private String EmpSpot;
    
    private String EmpPosition;
    
    private String DepCode;
}
