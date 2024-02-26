package nodo.erp.Hr.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.CustomUserDetails;
import nodo.erp.Hr.Entity.Attendance;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Service.Att_Service;
import nodo.erp.Hr.Service.Emp_Service;

@RequestMapping("/Attendance")
@RequiredArgsConstructor
@Controller
public class Att_Controller {

	private final Att_Service att_Service;
	private final Emp_Service emp_Service;

//	@GetMapping("/list")
//	public String Attlist(Model model) {
//		List<Attendance> AttList = this.att_Service.getList();
//		model.addAttribute("AttList", AttList);
//		return "Hr/Att_list";
//	}
	@GetMapping("/list")
	public String Attlist(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			// 로그인한 사용자에게만 허용
			List<Attendance> AttList = this.att_Service.getList();
			model.addAttribute("AttList", AttList);
			return "Hr/Att_list";
		} else {
			// 로그인하지 않은 사용자에게는 다른 페이지로 리다이렉션 또는 에러 처리
			return "redirect:/Hr/login";
		}

	}

	@PostMapping("/checkin")
	public String checkIn(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());
			att_Service.checkin(employee);
			return "redirect:/Attendance/list";
		} else {
			return "redirect:/Hr/login";
		}
	}

	@PostMapping("/checkout")
	public String checkOut(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());
			att_Service.checkout(employee);
			return "redirect:/Attendance/list";
		} else {
			return "redirect:/Hr/login";
		}
	}

}
