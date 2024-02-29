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
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
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

	private final Dep_Repository dep_Repository;

	@PersistenceContext
	private EntityManager entityManager;
	private final PasswordEncoder passwordEncoder;

	public List<Employee> getList() {
		return this.emp_Repository.findAll();
	}

	public List<Department> getdepList() {
		return this.dep_Repository.findAll();
	}

	public Page<Employee> getList(int page, String kw, String searchType,String sort) {
		List<Sort.Order> sorts = new ArrayList<>();
		System.out.println(sort);

		sorts.add(Sort.Order.desc(sort)); // asc오름차순
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		Specification<Employee> spec = search(kw, searchType);
		return this.emp_Repository.findAll(spec, pageable);
	}

	public void create(String empname, String empssn, String empadd, String empphone, String empmail, LocalDate empdate,
			String empspot, String empposition, Department depart) {
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
		q.setEmpspot(empspot);
		q.setEmpposition(empposition);
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

	public Employee getEmpDetail(Integer id) {
		Optional<Employee> empDetail = this.emp_Repository.findById(id);
		if (empDetail.isPresent()) {
			return empDetail.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public Department getDepCode(String depcode) {
		Optional<Department> hr_Dto_Dep = this.dep_Repository.findByDepcode(depcode);
		if (hr_Dto_Dep.isPresent()) {
			return hr_Dto_Dep.get();
		} else {
			throw new DataNotFoundException("depcode not found");
		}
	}

	public void modify(Employee hr_Dto_emp, String empname, String empadd, String empphone, String empmail) {
		Employee m = this.emp_Repository.findById(hr_Dto_emp.getId()).orElse(null);
		m.setEmpname(empname);
		m.setEmpadd(empadd);
		m.setEmpphone(empphone);
		m.setEmpmail(empmail);
		this.emp_Repository.save(m);
	}

	public void passmodify(Employee hr_Dto_emp, String pass) {
		Employee m = this.emp_Repository.findById(hr_Dto_emp.getId()).orElse(null);
		m.setPassword(passwordEncoder.encode(pass));
		this.emp_Repository.save(m);
	}

	public void delete(Employee hr_Dto_Emp) {
		this.emp_Repository.delete(hr_Dto_Emp);
	}


	public static Specification<Employee> search(String keyword, String searchType) {
		return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			Join<Employee, Department> d = root.join("depart", JoinType.LEFT);
			if (searchType.equals("empnum")) {
				return criteriaBuilder.like(root.get("empnum"), "%" + keyword + "%");
			} else if (searchType.equals("empname")) {
				return criteriaBuilder.like(root.get("empname"), "%" + keyword + "%");
			} else if (searchType.equals("empssn")) {
				return criteriaBuilder.like(d.get("depname"), "%" + keyword + "%");
			} else if (searchType.equals("empposition")) {
				return criteriaBuilder.like(root.get("empposition"), "%" + keyword + "%");
			} else if (keyword.matches("\\d{4}-\\d{2}")) {
				YearMonth yearMonth = YearMonth.parse(keyword, DateTimeFormatter.ofPattern("yyyy-MM"));
				LocalDate startDate = yearMonth.atDay(1);
				LocalDate endDate = yearMonth.plusMonths(1).atDay(1);
				return criteriaBuilder.between(root.get("empdate"), startDate, endDate);
			} else if(keyword.matches("\\d{4}-\\d{2}-\\d{2}")) {
				LocalDate searchDate = LocalDate.parse(keyword, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				return criteriaBuilder.equal(root.get("empdate"), searchDate);
			}

			else {
				return criteriaBuilder.or(criteriaBuilder.like(root.get("empnum"), "%" + keyword + "%"),
						criteriaBuilder.like(root.get("empname"), "%" + keyword + "%"),
						criteriaBuilder.like(d.get("depname"), "%" + keyword + "%"),
						criteriaBuilder.like(root.get("empposition"), "%" + keyword + "%"));
			}

		};
	}

}
