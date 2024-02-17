package nodo.erp.Hr;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import java.util.Set;
import jakarta.persistence.ManyToMany;

@Getter
@Setter
@Entity
public class Hr_Dto_Emp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer EmpId;

	@Column(length = 50)
	private String EmpName;	//이름@
	
	@Column(length = 50)
	private String EmpSsn;	//주민번호@

	@Column(length = 50)
	private String EmpAdd;	//주소@
	
	@Column(length = 50)
	private String EmpPhone;	//폰번호@
	
	@Column(length = 50)
	private String EmpMail;		//이메일@
	
	private String EmpDate;	//입사일자@
	
	@Column(length = 50)
	private String EmpSpot;		//@직위(인턴 사원 대리 과장 부장 이사 대표)
	
	@Column(length = 50)
	private String EmpPosition; //@직책 (팀장 부팀장)
	
	@Column(length = 50)
	private String DepCode;		//@부서번호
	
	@Column(length = 50)
	private String EmpVaca;		//연차갯수

}
