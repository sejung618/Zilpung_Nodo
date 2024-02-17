package nodo.erp.Hr;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@RequiredArgsConstructor
@Service
public class Hr_Service {

	private final Hr_Repository hr_Repository;

	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Hr_Dto_Emp> getList() {
		return this.hr_Repository.findAll();
	}

	public void create(String EmpName, String EmpSsn, String EmpAdd, String EmpPhone, 
			String EmpMail,Date EmpDate, String EmpSpot, String EmpPosition, String DepCode) {
		SimpleDateFormat formatv = new SimpleDateFormat("yyyy");
        String strv = formatv.format(EmpDate);
        SimpleDateFormat formate = new SimpleDateFormat("yy");
        String stre = formate.format(EmpDate);
        String Num = String.format("%05d", generateEmpId());
        
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
		q.setEmpId(generateEmpId());
		q.setEmpNum(stre + Num);
		q.setEmpVaca(vaca(strv));
		this.hr_Repository.save(q);
	}
	 private Integer generateEmpId() {
	        jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(e.EmpId) FROM Hr_Dto_Emp e");
	        Integer maxId = (Integer) query.getSingleResult();
	        return (maxId == null) ? 1 : maxId + 1;
	    }
	 
	 private int vaca(String str) {
		 // 현재 날짜 구하기       
		 LocalDate now = LocalDate.now();        
		 // 포맷 정의  
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");      
		 // 포맷 적용       
		 String formatedNow = now.format(formatter);      
		 int nowyear = Integer.parseInt(formatedNow);
		 int empyear = Integer.parseInt(str);
		 int y = nowyear - empyear;
		 return (int) (15+ y*0.5);
	 }


	
}
