package nodo.erp.Hr.Service;

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
import nodo.erp.Hr.Entity.Dep_Entity;
import nodo.erp.Hr.Entity.Emp_Entity;
import nodo.erp.Hr.Repository.Dep_Repository;
import nodo.erp.Hr.Repository.Emp_Repository;
import nodo.erp.Sd.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class Emp_Service {

	private final Emp_Repository hr_Repository;
	
	private final Dep_Repository dep_Repository;

	@PersistenceContext
    private EntityManager entityManager;
	private final PasswordEncoder passwordEncoder;
	
	public List<Emp_Entity> getList() {
		return this.hr_Repository.findAll();
	}
	
	public List<Dep_Entity> getdepList() {
		return this.dep_Repository.findAll();
	}
	
	 public Page<Emp_Entity> getList(int page) {
	        Pageable pageable = PageRequest.of(page, 10);
	        return this.hr_Repository.findAll(pageable);
	    }
	 
	 


	public void create(String empname, String empssn, String empadd, String empphone, 
			String empmail,Date empdate, String empspot, String empposition, Dep_Entity depart) {
		SimpleDateFormat formatv = new SimpleDateFormat("yyyy");
        String strv = formatv.format(empdate);
        SimpleDateFormat formate = new SimpleDateFormat("yy");
        String stre = formate.format(empdate);
        String Num = String.format("%05d", generateEmpId());
        
        Emp_Entity q = new Emp_Entity();
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
        //q.setPassword(passwordEncoder.encode(stre + Num));
		q.setPassword(passwordEncoder.encode("0000"));
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

	 public Emp_Entity getEmpDetail(Integer id) {  
	        Optional<Emp_Entity> empDetail = this.hr_Repository.findById(id);
	        if (empDetail.isPresent()) {
	            return empDetail.get();
	        } else {
	            throw new DataNotFoundException("question not found");
	        }
	    }
	

	 
	 public Dep_Entity getDepCode(String depcode) {
	        Optional<Dep_Entity> hr_Dto_Dep = this.dep_Repository.findByDepcode(depcode);
	        if (hr_Dto_Dep.isPresent()) {
	            return hr_Dto_Dep.get();
	        } else {
	            throw new DataNotFoundException("depcode not found");
	        }
	    }
	 
	 
	 public void modify(Emp_Entity hr_Dto_emp,String empname, String empadd,String empphone,String empmail) {
		 Emp_Entity m = this.hr_Repository.findById(hr_Dto_emp.getId()).orElse(null);
		m.setEmpname(empname);
		m.setEmpadd(empadd);
		m.setEmpphone(empphone);
		m.setEmpmail(empmail);
		this.hr_Repository.save(m);
	}
	 
	 public void passmodify(Emp_Entity hr_Dto_emp ,String pass) {
		 Emp_Entity m = this.hr_Repository.findById(hr_Dto_emp.getId()).orElse(null);
		m.setEmpname(pass);
		this.hr_Repository.save(m);
	}
	 
	 public void delete(Emp_Entity hr_Dto_Emp) {
	        this.hr_Repository.delete(hr_Dto_Emp);
	    }
	 
	 
}
