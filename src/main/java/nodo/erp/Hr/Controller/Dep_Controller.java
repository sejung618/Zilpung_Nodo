package nodo.erp.Hr.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.CustomUserDetails;
import nodo.erp.Hr.Dto.Dep_Form;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Service.Dep_Service;
import nodo.erp.Hr.Service.Emp_Service;

@RequestMapping("/dep")
@RequiredArgsConstructor
@Controller
public class Dep_Controller {

	private final Dep_Service dep_Service;
	private final Emp_Service emp_Service;

	@GetMapping("/list")
	public String deplist(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		List<Department> paging = this.dep_Service.getList();
		model.addAttribute("paging", paging);
		return "Hr/Dep_List";
	}

	@GetMapping("/create")
	public String depCreate(Dep_Form dep_Form, Authentication authentication) {
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getSpot().getId() >= 3) {
			return "Hr/Dep_Form";
//		} else {
//			return "redirect:/";
//		}
	}

	@PostMapping("/create")
	public String depCreate(@Valid Dep_Form dmp_Form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "Hr/Dep_Form";
		}
		this.dep_Service.create(dmp_Form.getDepcode(), dmp_Form.getDepname());
		return "redirect:/dep/list";
	}

	@GetMapping("/modify/{id}")
	public String depModify(Dep_Form dep_Form, @PathVariable("id") Integer id) {
		Department dep = this.dep_Service.getFindById(id);
		dep_Form.setDepcode(dep.getDepcode());
		dep_Form.setDepname(dep.getDepname());
		return "Hr/Dep_Form";
	}

	@PostMapping("/modify/{id}")
	public String depModify(@Valid Dep_Form dep_Form, BindingResult bindResult, @PathVariable("id") Integer id) {
		Department dep = this.dep_Service.getFindById(id);
		if (bindResult.hasErrors()) {
			return "Hr/Dep_Form";
		}
		this.dep_Service.modify(dep, dep_Form.getDepcode(), dep_Form.getDepname());
		return "redirect:/dep/list";
	}

	@GetMapping("/delete/{id}")
	public String depDelete(@PathVariable("id") Integer id, Authentication authentication) {
		Department dep = this.dep_Service.getFindById(id);
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
		if (employee.getId() == 1) {
			this.dep_Service.delete(dep);
			return "redirect:/dep/list";
		} else {
			return "redirect:/";
		}
	}

}
