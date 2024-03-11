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
import nodo.erp.Hr.Dto.Position_Form;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.Position;
import nodo.erp.Hr.Service.Dep_Service;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Hr.Service.Position_Service;

@RequestMapping("/position")
@RequiredArgsConstructor
@Controller
public class Position_Controller {
	private final Position_Service position_Service;
	private final Emp_Service emp_Service;

	@GetMapping("/list")
	public String posilist(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		List<Position> paging = this.position_Service.getList();
		model.addAttribute("paging", paging);
		return "Hr/position_list";
	}

	@GetMapping("/create")
	public String posiCreate(Position_Form position_Form, Authentication authentication) {
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() == 1) {
			return "Hr/position_Form";
//		} else {
//			return "redirect:/";
//		}
	}

	@PostMapping("/create")
	public String posiCreate(@Valid Position_Form position_Form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "Hr/position_Form";
		}
		this.position_Service.create(position_Form.getPosicode(), position_Form.getPosiname());
		return "redirect:/position/list";
	}

	@GetMapping("/modify/{id}")
	public String posiModify(Position_Form position_Form, @PathVariable("id") Integer id) {
		Position posi = this.position_Service.getFindById(id);
		position_Form.setPosicode(posi.getPositioncode());
		position_Form.setPosiname(posi.getPositionname());
		return "Hr/Position_Form";
	}

	@PostMapping("/modify/{id}")
	public String posiModify(@Valid Position_Form position_Form, BindingResult bindResult, @PathVariable("id") Integer id) {
		Position posi = this.position_Service.getFindById(id);
		if (bindResult.hasErrors()) {
			return "Hr/Position_Form";
		}
		this.position_Service.modify(posi, position_Form.getPosicode(), position_Form.getPosiname());
		return "redirect:/position/list";
	}

	@GetMapping("/delete/{id}")
	public String depDelete(@PathVariable("id") Integer id, Authentication authentication) {
		Position posi = this.position_Service.getFindById(id);
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() == 1) {
			this.position_Service.delete(posi);
			return "redirect:/position/list";
//		} else {
//			return "redirect:/";
//		}
	}
	
}
