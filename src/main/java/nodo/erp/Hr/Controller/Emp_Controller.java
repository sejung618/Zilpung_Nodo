package nodo.erp.Hr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.CustomUserDetails;
import nodo.erp.Hr.Dto.Emp_Form;
import nodo.erp.Hr.Dto.Emp_Pass_Form;
import nodo.erp.Hr.Dto.Emp_modify_Form;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.Position;
import nodo.erp.Hr.Entity.Spot;
import nodo.erp.Hr.Service.Dep_Service;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Hr.Service.Position_Service;
import nodo.erp.Hr.Service.Spot_Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

@RequestMapping("/Hr")
@RequiredArgsConstructor
@Controller
public class Emp_Controller {

	private final Emp_Service emp_Service;
	private final Dep_Service dep_Service;
	private final Spot_Service spot_service;
	private final Position_Service posi_service;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('HR')")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "num", defaultValue = "") String num,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "month", defaultValue = "") String month,
			@RequestParam(value = "spot", defaultValue = "") String spot,
			@RequestParam(value = "posi", defaultValue = "") String posi,
			@RequestParam(value = "depart", defaultValue = "") String depart) {
		Page<Employee> paging = this.emp_Service.getList(page, num, name, month, spot, posi, depart);
		model.addAttribute("paging", paging);
		model.addAttribute("num", num);
		model.addAttribute("name", name);
		model.addAttribute("month", month);
		model.addAttribute("spot", spot);
		model.addAttribute("posi", posi);
		model.addAttribute("depart", depart);
		return "/Hr/Emp_List";
	}

	@GetMapping("/create")
	@PreAuthorize("hasRole('HR')")
	public String EmpCreate(Model model, Emp_Form emp_Form, Authentication authentication) {
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() == 1) {
		List<Department> deplist = this.dep_Service.getList();
		List<Spot> spotlist = this.spot_service.getList();
		List<Position> posilist = this.posi_service.getList();
		model.addAttribute("deplist", deplist);
		model.addAttribute("spotlist", spotlist);
		model.addAttribute("posilist", posilist);
		return "Hr/Emp_Form";
//		} else {
//			return "redirect:/";
//		}
	}

	@PostMapping("/create")
	public String EmpCreate(@Valid Emp_Form emp_Form, BindingResult bindingResult, Model model) {
		Department depart = null;
		Spot spot = null;
		Position posi = null;
		if (emp_Form.getDepid() != null) {
			depart = this.dep_Service.getfindById(emp_Form.getDepid());
		}
		if (emp_Form.getSpotid() != null) {
			spot = this.spot_service.getFindById(emp_Form.getSpotid());
		}
		if (emp_Form.getPosiid() != null) {
			posi = this.posi_service.getFindById(emp_Form.getPosiid());
		}
		if (bindingResult.hasErrors()) {
			List<Department> deplist = this.dep_Service.getList();
			List<Spot> spotlist = this.spot_service.getList();
			List<Position> posilist = this.posi_service.getList();
			model.addAttribute("deplist", deplist);
			model.addAttribute("spotlist", spotlist);
			model.addAttribute("posilist", posilist);
			return "Hr/Emp_Form";
		}
		this.emp_Service.create(emp_Form.getEmpname(), emp_Form.getEmpssn(), emp_Form.getEmpadd(),
				emp_Form.getEmpphone(), emp_Form.getEmpmail(), emp_Form.getEmpdate(), spot, posi, depart);

		return "redirect:/Hr/list";
	}

	@GetMapping(value = "/detail/{id}")
	public String EmpDetail(Model model, @PathVariable("id") Integer id) {
		Employee emp = this.emp_Service.getfindById(id);
		model.addAttribute("empDtail", emp);

		return "Hr/Emp_detail";
	}

	@GetMapping("/modify/{id}")
	public String EmpModify(Emp_modify_Form emp_modify_Form, @PathVariable("id") Integer id) {
		Employee emp = this.emp_Service.getfindById(id);
		emp_modify_Form.setEmpname(emp.getEmpname());
		emp_modify_Form.setEmpadd(emp.getEmpadd());
		emp_modify_Form.setEmpphone(emp.getEmpphone());
		emp_modify_Form.setEmpmail(emp.getEmpmail());
		return "Hr/Emp_modify_Form";
	}

	@PostMapping("/modify/{id}")
	public String EmpModify(@PathVariable("id") Integer id, @Valid Emp_modify_Form emp_modify_Form,
			BindingResult bindResult) {
		Employee employee = this.emp_Service.getfindById(id);

		if (bindResult.hasErrors()) {
			return "Hr/Emp_modify_Form";
		}
		this.emp_Service.modify(employee, emp_modify_Form.getEmpname(), emp_modify_Form.getEmpadd(),
				emp_modify_Form.getEmpphone(), emp_modify_Form.getEmpmail());
		return "redirect:/Hr/detail/{id}";
	}

//	@GetMapping("/delete/{id}")
//	public String EmpDelete(@PathVariable("id") Integer id, Authentication authentication,RedirectAttributes redirectAttributes) {
//		if(authentication!=null && authentication.isAuthenticated()) {
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() <= 10) {
//			Employee emp = this.emp_Service.getfindById(id);
//			this.emp_Service.delete(emp);
//			return "redirect:/Hr/list";
//		} else {
//			return ;
//		}
//		}else {
//			return "redirect:/Hr/login";
//		}
//	}
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<String> questionDelete(@PathVariable("id") Integer id, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());

			if (employee.getSpot().getSpotcode() >= 108) {
				Employee emp = this.emp_Service.getfindById(id);
				this.emp_Service.delete(emp);
				return ResponseEntity.ok().build(); // 성공 시 응답 코드 200 반환
			} else {
				// 실패 시 실패 메시지와 함께 응답 코드 400 반환
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한이 없습니다.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 인증되지 않은 경우
		}
	}

//	HttpStatus.CONTINUE: 100 - 클라이언트가 요청을 계속해야 함을 나타냅니다.
//	HttpStatus.SWITCHING_PROTOCOLS: 101 - 서버가 프로토콜을 변경하기 위해 클라이언트 요청을 수락했음을 나타냅니다.
//	HttpStatus.OK: 200 - 요청이 성공적으로 처리되었음을 나타냅니다.
//	HttpStatus.CREATED: 201 - 새로운 리소스가 성공적으로 생성되었음을 나타냅니다.
//	HttpStatus.ACCEPTED: 202 - 요청이 받아들여졌지만 처리가 완료되지 않았음을 나타냅니다.
//	HttpStatus.NO_CONTENT: 204 - 요청이 성공했지만 응답 본문에 내용이 없음을 나타냅니다.
//	HttpStatus.BAD_REQUEST: 400 - 요청이 잘못되었음을 나타냅니다.
//	HttpStatus.UNAUTHORIZED: 401 - 인증이 필요함을 나타냅니다.
//	HttpStatus.FORBIDDEN: 403 - 요청이 거부되었음을 나타냅니다.
//	HttpStatus.NOT_FOUND: 404 - 요청한 리소스를 찾을 수 없음을 나타냅니다.
//	HttpStatus.METHOD_NOT_ALLOWED: 405 - 요청된 메서드가 허용되지 않음을 나타냅니다.
//	HttpStatus.INTERNAL_SERVER_ERROR: 500 - 서버에서 내부 오류가 발생했음을 나타냅니다.
//	HttpStatus.NOT_IMPLEMENTED: 501 - 요청된 기능이 구현되지 않았음을 나타냅니다.
//	HttpStatus.SERVICE_UNAVAILABLE: 503 - 서버가 현재 요청을 처리할 수 없음을 나타냅니다.

	@GetMapping("/login")
	public String login() {
		return "Hr/login_form";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/pass/{id}")
	public String EmpPassModify(Emp_Pass_Form emp_Pass_Form, @PathVariable("id") Integer id, Authentication authentication) {
		Employee emp = this.emp_Service.getfindById(id);
		if (!emp.getEmpnum().equals(authentication.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		return "Hr/Emp_Pass_Form";
	}
	
//	@GetMapping("/pass/{id}")
//	public ResponseEntity<?> EmpPassModify(Emp_Pass_Form emp_Pass_Form, @PathVariable("id") Integer id, Principal principal) {
//	    Employee emp = this.emp_Service.getfindById(id);
//	    if (!emp.getEmpnum().equals(principal.getName())) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정권한이 없습니다.");
//	    }
//	    return ResponseEntity.ok("Hr/Emp_Pass_Form");
//	}



	@PreAuthorize("isAuthenticated()")
	@PostMapping("/pass/{id}")
	public String EmpPassModify(@PathVariable("id") Integer id, @Valid Emp_Pass_Form emp_Pass_Form,
			BindingResult bindingResult, Principal principal) {
		Employee emp = this.emp_Service.getfindById(id);
		if (!emp.getEmpnum().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		if (bindingResult.hasErrors()) {
			return "Hr/Emp_Pass_Form";
		}
		if (!passwordEncoder.matches(emp_Pass_Form.getPass(), emp.getPassword())) {
			bindingResult.rejectValue("pass", "passwordInCorrect", "비밀번호가 틀렸습니다.");
			return "Hr/Emp_Pass_Form";
		}

		if (!emp_Pass_Form.getNewpass().equals(emp_Pass_Form.getNewpass2())) {
			bindingResult.rejectValue("newpass2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "Hr/Emp_Pass_Form";
		}

		this.emp_Service.passmodify(emp, emp_Pass_Form.getNewpass());
		return "redirect:/";
	}

	@GetMapping("/pa")
	@PreAuthorize("hasRole('HR')")
	public String personnelAppointments(Model model) {
		List<Employee> emplist = this.emp_Service.getList();
		List<Department> deplist = this.dep_Service.getList();
		List<Spot> spotlist = this.spot_service.getList();
		List<Position> posilist = this.posi_service.getList();
		model.addAttribute("emplist", emplist);
		model.addAttribute("deplist", deplist);
		model.addAttribute("spotlist", spotlist);
		model.addAttribute("posilist", posilist);

		return "Hr/Emp_Pa_Form";
	}

	@PostMapping("/pa")
	@PreAuthorize("hasRole('HR')")
	public String personnelAppointments(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "spotid") Integer spotid, @RequestParam(value = "positionid") Integer positionid,
			@RequestParam(value = "depid") Integer depid) {
		Employee emp = this.emp_Service.getfindById(id);
		Spot spot = null;
		Position posi = null;
		Department depart = null;
		if (spotid != null) {
			spot = this.spot_service.getFindById(spotid);
		}
		if (positionid != null) {
			posi = this.posi_service.getFindById(positionid);
		}
		if (depid != null) {
			depart = this.dep_Service.getfindById(depid);
		}
		this.emp_Service.Pa(emp, spot, posi, depart);
		return "redirect:/Hr/list";
	}

}