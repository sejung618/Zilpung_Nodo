package nodo.erp.Hr;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final String empname; // 예시로 사용자의 이름을 추가
    private final Integer id;
    private final Integer depart;
    
    private final Integer position;
    
    public CustomUserDetails(String empnum, String password
    		, Collection<? extends GrantedAuthority> authorities
    		, String empname, Integer id, Integer depart,Integer position) {
        super(empnum, password, authorities);
        this.empname = empname;
        this.id = id;
        this.depart = depart;
        
        this.position = position;
    }

    public String getEmpname() {
        return empname;
    }
    
    public Integer getEmpid() {
        return id;
    }
    
    public Integer getDepart() {
        return depart;
    }
    
    public Integer getPosition() {
    	return position;
    }
}