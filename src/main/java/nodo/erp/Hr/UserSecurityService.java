package nodo.erp.Hr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final Hr_Repository hr_Repository;

    @Override
    public UserDetails loadUserByUsername(String empnum) throws UsernameNotFoundException {
        Optional<Hr_Dto_Emp> _siteUser = this.hr_Repository.findByEmpnum(empnum);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Hr_Dto_Emp siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(empnum)) {
            authorities.add(new SimpleGrantedAuthority(EmpRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(EmpRole.USER.getValue()));
        }
        return new CustomUserDetails(siteUser.getEmpnum(), 
        		siteUser.getPassword(), authorities, siteUser.getEmpname(), siteUser.getId());
    }
}