package nodo.erp.Hr;
import lombok.Getter;

@Getter
public enum EmpRole {
	ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

	EmpRole(String value) {
        this.value = value;
    }

    private String value;
	
}


//권한 설정할때 필요!