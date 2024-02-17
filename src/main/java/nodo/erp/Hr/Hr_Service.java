package nodo.erp.Hr;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Hr_Service {

	private final Hr_Repository hr_Repository;

	
	public List<Hr_Dto_Emp> getList() {
		return this.hr_Repository.findAll();
	}

	public void create(String EmpName, String EmpSsn, String EmpAdd, String EmpPhone, 
			String EmpMail,String EmpDate, String EmpSpot, String EmpPosition, String DepCode) {
		Hr_Dto_Emp q = new Hr_Dto_Emp();
		q.setEmpName(EmpName);
		q.setEmpSsn(EmpSsn);
		q.setEmpAdd(EmpAdd);
		q.setEmpPhone(EmpPhone);
		q.setEmpMail(EmpMail);
		q.setEmpDate(EmpDate);
		q.setEmpSpot(EmpSpot);
		q.setEmpPosition(EmpPosition);
		q.setDepCode(DepCode);
		this.hr_Repository.save(q);
	}


	
}
