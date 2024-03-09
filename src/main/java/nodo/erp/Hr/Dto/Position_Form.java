package nodo.erp.Hr.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position_Form {
	@NotNull(message="직책코드는 필수항목입니다.")
    private Integer posicode;

    @NotEmpty(message="직책이름은 필수항목입니다.")
    private String posiname;

}
