package nodo.erp.Hr.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface Att_Repository extends JpaRepository<Attendance, Integer>{

	boolean existsByEmployeeAndDay(Employee employee, LocalDate checkInday);
	Optional<Attendance> findByDayAndEmployee(LocalDate checkInday,Employee employee);
	
	List<Attendance> findByEmployee(Employee employee);
	
	Page<Attendance> findAll(Pageable pageable);
	
}
