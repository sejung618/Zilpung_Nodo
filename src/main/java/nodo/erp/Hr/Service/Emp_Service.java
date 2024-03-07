package nodo.erp.Hr.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.Position;
import nodo.erp.Hr.Entity.Spot;
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

	private final Emp_Repository emp_Repository;
	@PersistenceContext
	private EntityManager entityManager;
	private final PasswordEncoder passwordEncoder;

	public List<Employee> getList() {
		return this.emp_Repository.findAll();
	}


	public Page<Employee> getList(int page, String num, String name, String month,String spot,String posi,String depart) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id")); // asc오름차순
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		Specification<Employee> spec = search(num, name,month,spot,posi,depart);
		return this.emp_Repository.findAll(spec, pageable);
	}
	
	public Employee getfindById(Integer id) {
		Optional<Employee> empDetail = this.emp_Repository.findById(id);
		if (empDetail.isPresent()) {
			return empDetail.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String empname, String empssn, String empadd, String empphone, String empmail, LocalDate empdate,
			Spot spot, Position position, Department depart) {
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy");
		String strv = formatter1.format(empdate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
		String stre = formatter.format(empdate);
		String Num = String.format("%05d", generateEmpId());

		Employee q = new Employee();
		q.setEmpname(empname);
		q.setEmpssn(empssn);
		q.setEmpadd(empadd);
		q.setEmpphone(empphone);
		q.setEmpmail(empmail);
		q.setEmpdate(empdate);
		q.setSpot(spot);
		q.setPosition(position);
		q.setDepart(depart);
//		q.setId(generateEmpId());
		q.setEmpnum(stre + Num);
		// q.setEmpvaca(vaca(strv));
		// q.setPassword(passwordEncoder.encode(stre + Num));
		q.setPassword(passwordEncoder.encode("0000"));

		this.emp_Repository.save(q);
	}

	private Integer generateEmpId() {
		jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(e.Id) FROM Employee e");
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
		return (int) (15 + y * 0.5);
	}


	

	public void modify(Employee employee, String empname, String empadd, String empphone, String empmail) {
		Employee m = this.emp_Repository.findById(employee.getId()).orElse(null);
		m.setEmpname(empname);
		m.setEmpadd(empadd);
		m.setEmpphone(empphone);
		m.setEmpmail(empmail);
		this.emp_Repository.save(m);
	}
	
	public void Pa(Employee employee, Spot spot, Position position, Department depart) {
		Employee m = this.emp_Repository.findById(employee.getId()).orElse(null);
		m.setSpot(spot);
		m.setPosition(position);
		m.setDepart(depart);
		this.emp_Repository.save(m);
	}

	public void passmodify(Employee employee, String pass) {
		Employee m = this.emp_Repository.findById(employee.getId()).orElse(null);
		m.setPassword(passwordEncoder.encode(pass));
		this.emp_Repository.save(m);
	}

	public void delete(Employee employee) {
		this.emp_Repository.delete(employee);
	}

	//검색 메소드
//	public static Specification<Employee> search(String keyword, String searchType) {
//		return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
//			Join<Employee, Department> d = root.join("depart", JoinType.LEFT);
//			Join<Employee, Spot> s = root.join("spot", JoinType.LEFT);
//			Join<Employee, Position> p = root.join("position", JoinType.LEFT);
//			if (searchType.equals("empnum")) {
//				return criteriaBuilder.like(root.get("empnum"), "%" + keyword + "%");
//			} else if (searchType.equals("empname")) {
//				return criteriaBuilder.like(root.get("empname"), "%" + keyword + "%");
//			} else if (searchType.equals("depname")) {
//				return criteriaBuilder.like(d.get("depname"), "%" + keyword + "%");
//			} else if (searchType.equals("empposition")) {
//				return criteriaBuilder.like(p.get("positionname"), "%" + keyword + "%");
//			} else if (keyword.matches("\\d{4}-\\d{2}")) {
//				YearMonth yearMonth = YearMonth.parse(keyword, DateTimeFormatter.ofPattern("yyyy-MM"));
//				LocalDate startDate = yearMonth.atDay(1);
//				LocalDate endDate = yearMonth.plusMonths(1).atDay(1);
//				return criteriaBuilder.between(root.get("empdate"), startDate, endDate);
//			} else if(keyword.matches("\\d{4}-\\d{2}-\\d{2}")) {
//				LocalDate searchDate = LocalDate.parse(keyword, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//				return criteriaBuilder.equal(root.get("empdate"), searchDate);
//			}
//
//			else {
//				return criteriaBuilder.or(criteriaBuilder.like(root.get("empnum"), "%" + keyword + "%"),
//						criteriaBuilder.like(root.get("empname"), "%" + keyword + "%"),
//						criteriaBuilder.like(d.get("depname"), "%" + keyword + "%"),
//						criteriaBuilder.like(p.get("positionname"), "%" + keyword + "%"),
//						criteriaBuilder.like(root.get("empadd"), "%" + keyword + "%"),
//						criteriaBuilder.like(root.get("empphone"), "%" + keyword + "%"),
//						criteriaBuilder.like(root.get("empmail"), "%" + keyword + "%"),
//						criteriaBuilder.like(s.get("spotname"), "%" + keyword + "%")
//						);
//			}
//
//		};
//	}
	
	   public static Specification<Employee> search(String num, String name, String month, String spot, String posi, String depart) {
	       return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
	           Join<Employee, Department> d = root.join("depart", JoinType.LEFT);
	           Join<Employee, Spot> s = root.join("spot", JoinType.LEFT);
	           Join<Employee, Position> p = root.join("position", JoinType.LEFT);

	           Predicate predicate1 = criteriaBuilder.like(root.get("empnum"), "%" + num + "%");
	           Predicate predicate2 = criteriaBuilder.like(root.get("empname"), "%" + name + "%");
	           Predicate predicate3 = null;
	           Predicate predicate4 = null;
	           Predicate predicate5 = null;
	           Predicate predicate6 = null;

	           if (month.matches("\\d{4}-\\d{2}")) {
	               YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));
	               LocalDate startDate = yearMonth.atDay(1);
	               LocalDate endDate = yearMonth.atEndOfMonth();
	               predicate3 = criteriaBuilder.between(root.get("empdate"), startDate, endDate);
	           } 
	           if (spot != null && !spot.isEmpty()) {
	               predicate4 = criteriaBuilder.like(s.get("spotname"), "%" + spot + "%");
	           }
	           if (posi != null && !posi.isEmpty()) {
	               predicate5 = criteriaBuilder.like(p.get("positionname"), "%" + posi + "%");
	           }
	           if (depart != null && !depart.isEmpty()) {
	               predicate6 = criteriaBuilder.like(d.get("depname"), "%" + depart + "%");
	           }

	           // 각 Predicate가 null이 아닌 경우에만 포함시킵니다.
	           List<Predicate> predicates = new ArrayList<>();
	           predicates.add(predicate1);
	           predicates.add(predicate2);
	           if (predicate3 != null) {
	               predicates.add(predicate3);
	           }
	           if (predicate4 != null) {
	               predicates.add(predicate4);
	           }
	           if (predicate5 != null) {
	               predicates.add(predicate5);
	           }
	           if (predicate6 != null) {
	               predicates.add(predicate6);
	           }

	           return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	       };
	   }

}
