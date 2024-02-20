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
import nodo.erp.DataNotFoundException;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class Hr_Service {

	private final Hr_Repository hr_Repository;
	
	private final Dep_Repository dep_Repository;

	@PersistenceContext
    private EntityManager entityManager;
	private final PasswordEncoder passwordEncoder;
	
	public List<Hr_Dto_Emp> getList() {
		return this.hr_Repository.findAll();
	}

	public void create(String empname, String empssn, String empadd, String empphone, 
			String empmail,Date empdate, String empspot, String empposition, Hr_Dto_Dep depart) {
		SimpleDateFormat formatv = new SimpleDateFormat("yyyy");
        String strv = formatv.format(empdate);
        SimpleDateFormat formate = new SimpleDateFormat("yy");
        String stre = formate.format(empdate);
        String Num = String.format("%05d", generateEmpId());
        
        Hr_Dto_Emp q = new Hr_Dto_Emp();
		q.setEmpname(empname);
		q.setEmpssn(empssn);
		q.setEmpadd(empadd);
		q.setEmpphone(empphone);
		q.setEmpmail(empmail);
		q.setEmpdate(empdate);
		q.setEmpspot(empspot);
		q.setEmpposition(empposition);
		q.setDepart(depart);
		q.setId(generateEmpId());
		q.setEmpnum(stre + Num);
		q.setEmpvaca(vaca(strv));
        q.setPassword(passwordEncoder.encode(stre + Num));
		this.hr_Repository.save(q);
	}
	 private Integer generateEmpId() {
	        jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(e.Id) FROM Hr_Dto_Emp e");
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

	 public Hr_Dto_Emp getEmpDetail(Integer id) {  
	        Optional<Hr_Dto_Emp> EmpDetail = this.hr_Repository.findById(id);
	        if (EmpDetail.isPresent()) {
	            return EmpDetail.get();
	        } else {
	            throw new DataNotFoundException("question not found");
	        }
	    }
	
	 public Page<Hr_Dto_Emp> getList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.hr_Repository.findAll(pageable);
	    }
	 
	 public Hr_Dto_Dep getDepCode(String depcode) {
	        Optional<Hr_Dto_Dep> hr_Dto_Dep = this.dep_Repository.findByDepcode(depcode);
	        if (hr_Dto_Dep.isPresent()) {
	            return hr_Dto_Dep.get();
	        } else {
	            throw new DataNotFoundException("depcode not found");
	        }
	    }
	 
}
