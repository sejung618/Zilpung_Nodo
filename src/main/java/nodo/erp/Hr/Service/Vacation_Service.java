package nodo.erp.Hr.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.VacationApply;
import nodo.erp.Hr.Repository.Dep_Repository;
import nodo.erp.Hr.Repository.Vaca_App_Reository;

@RequiredArgsConstructor
@Service
public class Vacation_Service {

	private final Vaca_App_Reository vaca_App_Reository;

	public void create(Employee employee,LocalDate startdate, LocalDate enddate, String leavetype) {
		VacationApply q = new VacationApply();
		q.setEmployee(employee);
		q.setStartdate(startdate);
		q.setEnddate(enddate);
		q.setLeavetype(leavetype);
		long period = ChronoUnit.DAYS.between(startdate, enddate);
	    q.setPeriod(period+1);
		this.vaca_App_Reository.save(q);
	}

	
	public List<VacationApply> getList() {
		return this.vaca_App_Reository.findAll();
	}
	
	public Page<VacationApply> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.vaca_App_Reository.findAll(pageable);
    }
}
