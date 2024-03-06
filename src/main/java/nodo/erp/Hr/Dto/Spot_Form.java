package nodo.erp.Hr.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spot_Form {
	@NotEmpty(message="직위코드는 필수항목입니다.")
    @Size(max=50)
    private String spotcode;

    @NotEmpty(message="직위이름은 필수항목입니다.")
    private String spotname;
}