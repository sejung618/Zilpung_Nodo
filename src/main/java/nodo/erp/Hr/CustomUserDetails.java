package nodo.erp.Hr;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final String empname; // 예시로 사용자의 이름을 추가
    private final Integer id;
    
    public CustomUserDetails(String empnum, String password
    		, Collection<? extends GrantedAuthority> authorities
    		, String empname, Integer id) {
        super(empnum, password, authorities);
        this.empname = empname;
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }
    
    public Integer getEmpid() {
        return id;
    }
}