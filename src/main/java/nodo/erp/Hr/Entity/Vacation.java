package nodo.erp.Hr.Entity;

import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

//사원별 휴가 테이블
@Getter
@Setter
@Entity
public class Vacation {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @ManyToOne
	    private Employee employee;

	    private int totalVacation;   	//총 연차갯수
	    private int remainingVacation; 	//남은 갯수
	    private int usedVacation;		//사용 갯수
	    private Year year;				//년도

}