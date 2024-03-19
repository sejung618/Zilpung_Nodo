package nodo.erp.Hr.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.VacationApply;
import nodo.erp.Hr.Repository.Att_Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
@Service
public class Att_Service {
	
	@Autowired
	private final Att_Repository att_Repository;

	public List<Attendance> getList() {
		return this.att_Repository.findAll();
	}
	
	public Page<Attendance> getList(int page, String kw1, String kw2) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Attendance> spec = search(kw1, kw2);
        return this.att_Repository.findAll(spec,pageable);
    }
	
	public Page<Attendance> getdetailList(Employee employee,int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
		return this.att_Repository.findByEmployee(employee,pageable);
//		 List<Attendance> Detail = this.att_Repository.findByEmployee(employee);
//		 if (Detail != null) {
//	            return this.att_Repository.findByEmployee(employee);
//	        } else {
//	        	checkin(employee);
//	        	return null;
////	            throw new DataNotFoundException("employee not found");
//	        }
	}
	
	public Attendance getfindById(Integer id) {
		Optional<Attendance> attendance = this.att_Repository.findById(id);
		if (attendance.isPresent()) {
			return attendance.get();
		} else {
			throw new DataNotFoundException("attendance not found");
		}
	}

//	public void checkin(Employee employee) {
//		// 해당 날짜와 아이디로 이미 체크인한 기록이 있는지 확인
//        boolean alreadyCheckedIn = att_Repository.existsByEmployeeAndDay(employee, LocalDate.now());
//		if (alreadyCheckedIn) {
//			// 이미 체크인한 경우 알람 또는 예외 처리 등을 수행
//			
//		} else {
//			// 체크인 기록 저장
//			Attendance attendance = new Attendance();
//			attendance.setDay(LocalDate.now());
//			attendance.setEmployee(employee);
////			attendance.setCheckInTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
//			attendance.setCheckInTime(LocalTime.of(9,0)); // 9시로 출근시간 고정
//			this.att_Repository.save(attendance);
//			
//		}
//
//	}
	public boolean checkin(Employee employee) {
	    // 해당 날짜와 아이디로 이미 체크인한 기록이 있는지 확인
	    boolean alreadyCheckedIn = att_Repository.existsByEmployeeAndDay(employee, LocalDate.now());
	    if (alreadyCheckedIn) {
	        // 이미 체크인한 경우 알람 또는 예외 처리 등을 수행
	        return true;
	    } else {
	        // 체크인 기록 저장
	        Attendance attendance = new Attendance();
	        attendance.setDay(LocalDate.now());
	        attendance.setEmployee(employee);
	        attendance.setCheckInTime(LocalTime.of(9,0)); // 9시로 출근시간 고정
	        this.att_Repository.save(attendance);
	        return false;
	    }
	}
	
	
    public boolean checkout(Employee employee) {
        // 해당 날짜와 아이디로 출근한 기록 찾기
        Optional<Attendance> attendanceOptional = att_Repository.findByDayAndEmployee(LocalDate.now(), employee);

        if (attendanceOptional.isPresent()) {
            // 출근한 기록이 존재하면 퇴근 시간 설정
            Attendance attendance = attendanceOptional.get();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            attendance.setCheckOutTime(now);
            Duration working = Duration.between(attendance.getCheckInTime(), now);
            long hours = working.toHours();
            long minutes = working.toMinutes() % 60;
            attendance.setWorkingtime(String.format("%d시간 %d분", hours, minutes));
            this.att_Repository.save(attendance);
            return false;
        } else {
            // 출근한 기록이 없으면 에러 처리 또는 예외 발생
        	return true;
        }
    }
    
    
    
    
    
 // 검색 메소드
 	public static Specification<Attendance> search(String keyword1, String keyword2) {
 	    return (Root<Attendance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
 	        Join<Attendance, Employee> d = root.join("employee", JoinType.LEFT);
 	        Predicate predicate1 = criteriaBuilder.or(criteriaBuilder.like(d.get("empnum"), "%" + keyword2 + "%"),
 									criteriaBuilder.like(d.get("empname"), "%" + keyword2 + "%"));
 	        Predicate predicate2 = null; // predicate2를 먼저 초기화합니다.

 	        if (keyword1.matches("\\d{4}-\\d{2}-\\d{2}")) {
 	            LocalDate searchDate = LocalDate.parse(keyword1); // 문자열을 LocalDate로 변환
 	            predicate2 = criteriaBuilder.equal(root.get("day"), searchDate);
 	        } else {
 	            predicate2 = criteriaBuilder.like(d.get("empnum"), "%" + keyword1 + "%"); // predicate2를 초기화합니다.
 	        }

 	        return criteriaBuilder.and(predicate1, predicate2); // 두 개의 Predicate를 결합하여 반환합니다.
 	    };
 	}
}

	

