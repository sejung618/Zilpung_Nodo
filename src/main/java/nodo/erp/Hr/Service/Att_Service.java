package nodo.erp.Hr.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Repository.Att_Repository;
import nodo.erp.Hr.Repository.Emp_Repository;

@RequiredArgsConstructor
@Service
public class Att_Service {
	
	@Autowired
	private final Att_Repository att_Repository;

	public List<Attendance> getList() {
		return this.att_Repository.findAll();
	}

	public void checkin(Employee employee) {
		// 현재 날짜 가져오기.
        boolean alreadyCheckedIn = att_Repository.existsByEmployeeAndDay(employee, LocalDate.now());
		// 해당 날짜와 아이디로 이미 체크인한 기록이 있는지 확인
//		System.out.println(alreadyCheckedIn);
		if (alreadyCheckedIn) {
			// 이미 체크인한 경우 알람 또는 예외 처리 등을 수행
//			System.out.println("Already checked in for today!");
			throw new DataNotFoundException("Already checked in for today!");
		} else {
			// 체크인 기록 저장
			Attendance attendance = new Attendance();
			attendance.setDay(LocalDate.now());
			attendance.setEmployee(employee);
			attendance.setCheckInTime(LocalTime.now());
			this.att_Repository.save(attendance);
			
		}

	}
	
    public void checkout(Employee employee) {
        // 현재 날짜 가져오기.

        // 해당 날짜와 아이디로 출근한 기록 찾기
        Optional<Attendance> attendanceOptional = att_Repository.findByDayAndEmployee(LocalDate.now(), employee);

        if (attendanceOptional.isPresent()) {
            // 출근한 기록이 존재하면 퇴근 시간 설정
            Attendance attendance = attendanceOptional.get();
            attendance.setCheckOutTime(LocalTime.now());
            this.att_Repository.save(attendance);
        } else {
            // 출근한 기록이 없으면 에러 처리 또는 예외 발생
            throw new DataNotFoundException("No check-in record found for today!");
        }
    }
}

	
//	public class AlreadyCheckedInException extends RuntimeException {
//	    public AlreadyCheckedInException(String message) {
//	        super(message);
//	    }
//	}