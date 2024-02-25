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

	@GetMapping("/list")
	public String Attlist(Model model) {
		List<Attendance> AttList = this.att_Service.getList();
		model.addAttribute("AttList", AttList);
		return "Hr/Att_list";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/checkin")
	public String checkIn(Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee siteUser = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());
		att_Service.checkin(siteUser);
		return "redirect:/Attendance/list";
	}

}
