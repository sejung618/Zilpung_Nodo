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
    private String empname;

    @NotEmpty(message="주민번호는 필수항목입니다.")
    private String empssn;
    
    @NotEmpty(message="주소은 필수항목입니다.")
    private String empadd;
    
    private String empphone;
    
    private String empmail;
    
    private Date empdate;
    
    private String empspot;
    
    private String empposition;
    
    private String depcode;
}
