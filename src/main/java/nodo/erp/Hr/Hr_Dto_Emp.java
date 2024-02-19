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
	private Integer id;

	@Column(length = 50)
	private String empnum;	//사번@
	
	@Column(length = 50)
	private String empname;	//이름@
	
	@Column(length = 50)
	private String empssn;	//주민번호@

	@Column(length = 50)
	private String empadd;	//주소@
	
	@Column(length = 50)
	private String empphone;	//폰번호@
	
	@Column(length = 50)
	private String empmail;		//이메일@
	
	private Date empdate;	//입사일자@
	
	@Column(length = 50)
	private String empspot;		//@직위(인턴 사원 대리 과장 부장 이사 대표)
	
	@Column(length = 50)
	private String empposition; //@직책 (팀장 부팀장)
	
	@ManyToOne
	private Hr_Dto_Dep depcode;		//@부서번호
	
	private Integer empvaca;		//연차갯수
	
	private String password;

}
