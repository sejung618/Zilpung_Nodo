package nodo.erp.Sd.Sales;

import java.time.LocalDate;

import org.aspectj.weaver.ast.Not;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesCreateForm {

	private String sanum;
	
	private LocalDate sadate;
	
	@NotEmpty(message = "판매방법 선택은 필수입니다.")
	private String samethod;
	
	@NotEmpty(message = "제품 선택은 필수입니다.")
	private String saitem;
	
	private String saicode;
	
	private String sacount;
	
	private String saprice;
	
	private String sacp;
	
	private String savat;
	
	private String sasum;
	
	
}
