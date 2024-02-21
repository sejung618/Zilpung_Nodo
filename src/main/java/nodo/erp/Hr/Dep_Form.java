package nodo.erp.Hr;

import java.sql.Date;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dep_Form {
	
	@NotEmpty(message="이름은 필수항목입니다.")
    @Size(max=50)
    private String depcode;

    @NotEmpty(message="주민번호는 필수항목입니다.")
    private String depname;

}
