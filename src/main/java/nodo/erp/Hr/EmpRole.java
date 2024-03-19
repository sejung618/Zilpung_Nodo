package nodo.erp.Hr;
import lombok.Getter;

@Getter
public enum EmpRole {
	ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    HR("ROLE_HR"),
    MM("ROLE_MM"),
    PP("ROLE_PP"),
    SD("ROLE_SD"),
    ;
	
	
	EmpRole(String value) {
        this.value = value;
    }

    private String value;
	
}


//권한 설정할때 필요!