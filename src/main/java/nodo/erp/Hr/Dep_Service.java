package nodo.erp.Hr;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class Dep_Service {

	private final Dep_Repository dep_Repository;
	
	
	public List<Hr_Dto_Dep> getList() {
		return this.dep_Repository.findAll();
	}
	
	 public Page<Hr_Dto_Dep> getdepList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.dep_Repository.findAll(pageable);
	    }
	 
	 
	 public void create(String depcode, String depname) {
	        Hr_Dto_Dep q = new Hr_Dto_Dep();
			q.setDepcode(depcode);
			q.setDepname(depname);
			
			this.dep_Repository.save(q);
		}
	 
	 public Hr_Dto_Dep getFindByIdDep(Integer id) {  
	        Optional<Hr_Dto_Dep> getFindByIdDep = this.dep_Repository.findById(id);
	        if (getFindByIdDep.isPresent()) {
	            return getFindByIdDep.get();
	        } else {
	            throw new DataNotFoundException("question not found");
	        }
	    }

	public void modify(Hr_Dto_Dep hr_Dto_Dep,String depcode, String depname) {
		hr_Dto_Dep.setDepcode(depcode);
		hr_Dto_Dep.setDepname(depname);
			
			this.dep_Repository.save(hr_Dto_Dep);
	}
	 
	public void delete(Hr_Dto_Dep hr_Dto_Dep) {
        this.dep_Repository.delete(hr_Dto_Dep);
    }
	
}
