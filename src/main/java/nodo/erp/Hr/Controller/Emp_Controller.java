package nodo.erp.Hr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
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
import nodo.erp.Hr.Service.Dep_Service;
import nodo.erp.Hr.Service.Emp_Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/list")
	public String list(Model model, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "st", defaultValue = "") String st,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "sort", defaultValue = "id") String sort) {
		Page<Employee> paging = this.emp_Service.getList(page, kw, st, sort);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("st", st);
		model.addAttribute("sort", sort);
		return "/Hr/Emp_List";
	}

	@GetMapping("/create")
	public String EmpCreate(Model model, Emp_Form emp_Form, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
		if (employee.getId() == 1) {
			List<Department> deplist = this.dep_Service.getList();
			model.addAttribute("deplist", deplist);
			return "Hr/Emp_Form";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/create")
	public String EmpCreate(@Valid Emp_Form emp_Form, BindingResult bindingResult, Model model) {
		Department depart = this.dep_Service.getFindById(emp_Form.getDepid());
		if (bindingResult.hasErrors()) {
			List<Department> deplist = this.dep_Service.getList();
			model.addAttribute("deplist", deplist);
			return "Hr/Emp_Form";
		}
		this.emp_Service.create(emp_Form.getEmpname(), emp_Form.getEmpssn(), emp_Form.getEmpadd(),
				emp_Form.getEmpphone(), emp_Form.getEmpmail(), emp_Form.getEmpdate(), emp_Form.getEmpspot(),
				emp_Form.getEmpposition(), depart);

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
		return "redirect:/Hr/list";
	}

	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
		if (employee.getId() == 1) {
			Employee emp = this.emp_Service.getfindById(id);
			this.emp_Service.delete(emp);
			return "redirect:/Hr/list";
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/login")
	public String login() {
		return "Hr/login_form";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/pass/{id}")
	public String EmpPassModify(Emp_Pass_Form emp_Pass_Form, @PathVariable("id") Integer id, Principal principal) {
		Employee emp = this.emp_Service.getfindById(id);
		if (!emp.getEmpnum().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		return "Hr/Emp_Pass_Form";
	}

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
	public String personnelAppointments(Model model) {
		List<Employee> emplist = this.emp_Service.getList();
		List<Department> deplist = this.dep_Service.getList();
		model.addAttribute("emplist", emplist);
		model.addAttribute("deplist", deplist);

		return "Hr/Emp_Pa_Form";
	}

	@PostMapping("/pa")
	public String personnelAppointments(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "empspot") String empspot, 
			@RequestParam(value = "empposition") String empposition,
			@RequestParam(value = "depid") Integer depid) {
		Employee emp = this.emp_Service.getfindById(id);
		Department depart = this.dep_Service.getFindById(depid);
		this.emp_Service.Pa(emp, empspot, empposition, depart);
		return "redirect:/Hr/list";
	}

}