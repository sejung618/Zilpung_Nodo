package nodo.erp.Hr.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Repository.Dep_Repository;

@RequiredArgsConstructor
@Service
public class Dep_Service {

	private final Dep_Repository dep_Repository;
	
	
	public List<Department> getList() {
		return this.dep_Repository.findAll();
	}
	
	 public Page<Department> getdepList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.dep_Repository.findAll(pageable);
	    }
	 
	 
	 public void create(String depcode, String depname) {
	        Department q = new Department();
			q.setDepcode(depcode);
			q.setDepname(depname);
			
			this.dep_Repository.save(q);
		}
	 
	 public Department getFindByIdDep(Integer id) {  
	        Optional<Department> getFindByIdDep = this.dep_Repository.findById(id);
	        if (getFindByIdDep.isPresent()) {
	            return getFindByIdDep.get();
	        } else {
	            throw new DataNotFoundException("question not found");
	        }
	    }

	public void modify(Department hr_Dto_Dep,String depcode, String depname) {
		hr_Dto_Dep.setDepcode(depcode);
		hr_Dto_Dep.setDepname(depname);
			
			this.dep_Repository.save(hr_Dto_Dep);
	}
	 
	public void delete(Department hr_Dto_Dep) {
        this.dep_Repository.delete(hr_Dto_Dep);
    }
	
}
