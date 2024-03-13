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
import nodo.erp.Hr.Repository.Position_Repository;
import nodo.erp.Hr.Repository.Spot_Repository;

@RequiredArgsConstructor
@Service
public class Dep_Service {

	private final Dep_Repository dep_Repository;
	
	
	public List<Department> getList() {
		return this.dep_Repository.findAll();
	}
	
	 public Page<Department> getList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.dep_Repository.findAll(pageable);
	    }
	 
	 
	 public void create(Integer depcode, String depname) {
	        Department q = new Department();
			q.setDepcode(depcode);
			q.setDepname(depname);
			
			this.dep_Repository.save(q);
		}
	 
	 public Department getfindById(Integer id) {  
	        Optional<Department> departmant = this.dep_Repository.findById(id);
	        if (departmant.isPresent()) {
	            return departmant.get();
	        } else {
	            throw new DataNotFoundException("Department not found");
	        }
	    }

	public void modify(Department department,Integer depcode, String depname) {
		department.setDepcode(depcode);
		department.setDepname(depname);
			
			this.dep_Repository.save(department);
	}
	 
	public void delete(Department department) {
        this.dep_Repository.delete(department);
    }
	
}
