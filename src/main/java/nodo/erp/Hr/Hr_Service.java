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

    private Specification<Hr_Dto_Emp> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Hr_Dto_Emp> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
//                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
//                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
//                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("EmpName"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("EmpAdd"), "%" + kw + "%")      // 내용 
//                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
//                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
//                        cb.like(u2.get("username"), "%" + kw + "%")
                        );   // 답변 작성자 
            }
        };
    }
	
	public List<Hr_Dto_Emp> getList() {
		return this.hr_Repository.findAll();
	}

	public void create(String EmpName, String EmpSsn, String EmpAdd, String EmpPhone, 
			String EmpMail,Date EmpDate, String EmpSpot, String EmpPosition, String DepCode) {
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

	public Page<Hr_Dto_Emp> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("EmpId")); // 여기서 쓰인 desc는 내림차순을 의미하고, asc는 오름차순을 의미한다.
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Hr_Dto_Emp> spec = search(kw);
		return this.hr_Repository.findAll(spec, pageable);
	}
	
}
