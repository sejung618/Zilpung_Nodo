package nodo.erp.Hr.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

//휴가 신청 테이블
@Getter
@Setter
@Entity
public class VacationApply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Employee employee;

	private LocalDate startdate;	//휴가 시작날짜
	private LocalDate enddate;		//휴가 끝날짜
	private long period;		//후가 기간
	private String leavetype;		//휴가 종류
}