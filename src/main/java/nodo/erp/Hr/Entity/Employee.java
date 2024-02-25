package nodo.erp.Hr.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.ManyToOne;

//사원 관리 테이블
@Getter
@Setter
@Entity
public class Employee {
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
	
	private LocalDate empdate;	//입사일자@
	
	@Column(length = 50)
	private String empspot;		//@직위(인턴 사원 대리 과장 부장 이사 대표)
	
	@Column(length = 50)
	private String empposition; //@직책 (팀장 부팀장)
	
	@ManyToOne
	private Department depart;		//@부서
	
	private String password;

}
