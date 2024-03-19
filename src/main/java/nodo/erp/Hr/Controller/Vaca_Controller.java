package nodo.erp.Hr.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
import nodo.erp.Hr.Dto.Vaca_app_Form;
import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.Event;
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
			Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());

			this.vacation_Service.create(employee, vaca_app_Form.getStartdate(), vaca_app_Form.getEnddate(),
					vaca_app_Form.getLeavetype());

			return "redirect:/vacation/detail";

		} else {
			return "redirect:/Hr/login";
		}

	}

	@GetMapping("/list")
	@PreAuthorize("hasRole('HR')")
	public String list(Model model, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw1", defaultValue = "") String kw1,
			@RequestParam(value = "kw2", defaultValue = "") String kw2) {
		Page<VacationApply> paging = this.vacation_Service.getList(page, kw1, kw2);
		model.addAttribute("paging", paging);
		model.addAttribute("kw1", kw1);
		model.addAttribute("kw2", kw2);
		return "/Hr/vaca_app_list";
	}
	
	@GetMapping("/detail")
	public String detail(Model model, Authentication authentication,
			@RequestParam(value = "page", defaultValue = "0") int page) {
		if (authentication != null && authentication.isAuthenticated()) {
			// 로그인한 사용자에게만 허용
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
			Page<VacationApply> paging = this.vacation_Service.getdetailList(employee,page);
			model.addAttribute("paging", paging);
			
			return "Hr/vaca_app_detail";
		} else {
			// 로그인하지 않은 사용자에게는 다른 페이지로 리다이렉션 또는 에러 처리
			return "redirect:/Hr/login";
		}

	}
	
	@GetMapping("/modify/{id}")
	public String vacaModify(Vaca_app_Form vaca_app_Form, @PathVariable("id") Integer id) {
		VacationApply vaca = this.vacation_Service.getfindById(id);
		vaca_app_Form.setStartdate(vaca.getStartdate());
		vaca_app_Form.setEnddate(vaca.getEnddate());
		vaca_app_Form.setLeavetype(vaca.getLeavetype());
		return "Hr/vaca_app_Form";
	}
	
	@PostMapping("/modify/{id}")
	public String vacaModify(@Valid Vaca_app_Form vaca_app_Form, BindingResult bindResult, @PathVariable("id") Integer id) {
		VacationApply vaca = this.vacation_Service.getfindById(id);
		if (bindResult.hasErrors()) {
			return "Hr/vaca_app_Form";
		}
		this.vacation_Service.modify(vaca, vaca_app_Form.getStartdate(), vaca_app_Form.getEnddate(),vaca_app_Form.getLeavetype());
		return "redirect:/vacation/detail";
	}
	
	@GetMapping("/delete/{id}")
	public String depDelete(@PathVariable("id") Integer id, Authentication authentication) {
		VacationApply VacationApply = this.vacation_Service.getfindById(id);
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() == 1) {
			this.vacation_Service.delete(VacationApply);
			return "redirect:/vacation/list";
//		} else {
//			return "redirect:/";
//		}
	}

}
