package nodo.erp.Hr.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

//출퇴근 관리 테이블
@Getter
@Setter
@Entity
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Employee employee;

    
    private LocalDateTime checkInTime;	//출근시간
    private LocalDateTime checkOutTime;	//퇴근시간

}