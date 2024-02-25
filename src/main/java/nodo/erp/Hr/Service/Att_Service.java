package nodo.erp.Hr.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Repository.Att_Repository;
import nodo.erp.Hr.Repository.Emp_Repository;

@RequiredArgsConstructor
@Service
public class Att_Service {

	private final Att_Repository att_Repository;
	
	
	
	public List<Attendance> getList() {
		return this.att_Repository.findAll();
	}
	
	 public void checkin(Employee empid) {
		 Attendance q = new Attendance();
		q.setEmployee(empid);	
		q.setCheckInTime(LocalDateTime.now());
			
			this.att_Repository.save(q);
		}
	
	
}
