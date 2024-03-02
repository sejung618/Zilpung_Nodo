package nodo.erp.Hr.Controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.CustomUserDetails;
import nodo.erp.Hr.Dto.Dep_Form;
import nodo.erp.Hr.Dto.Vaca_app_Form;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.VacationApply;
import nodo.erp.Hr.Service.Dep_Service;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Hr.Service.Vacation_Service;

@RequestMapping("/vacation")
@RequiredArgsConstructor
@Controller
public class Vaca_Controller {

	private final Vacation_Service vacation_Service;
	private final Emp_Service emp_Service;

	@GetMapping("/create")
	public String vacaCreate(Vaca_app_Form vaca_app_Form, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			return "Hr/vaca_app_Form";
		} else {
			return "redirect:/Hr/login";
		}
	}

	@PostMapping("/create")
	public String vacaCreate(@Valid Vaca_app_Form vaca_app_Form, BindingResult bindingResult,
			Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			if (bindingResult.hasErrors()) {
				return "Hr/vaca_app_Form";
			}
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());

			this.vacation_Service.create(employee, vaca_app_Form.getStartdate(), vaca_app_Form.getEnddate(),
					vaca_app_Form.getLeavetype());

			return "redirect:/vacation/list";

		} else {
			return "redirect:/Hr/login";
		}

	}

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw1", defaultValue = "") String kw1,
			@RequestParam(value = "kw2", defaultValue = "") String kw2) {
		Page<VacationApply> paging = this.vacation_Service.getList(page, kw1, kw2);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw1);
		model.addAttribute("st", kw2);
		return "/Hr/vaca_app_list";
	}

}
