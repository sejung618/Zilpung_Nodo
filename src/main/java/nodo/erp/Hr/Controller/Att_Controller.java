package nodo.erp.Hr.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	public String list(Model model, Authentication authentication,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value = "kw1", defaultValue = "") String kw1,
			@RequestParam(value = "kw2", defaultValue = "") String kw2) {
			Page<Attendance> paging  = this.att_Service.getList(page,kw1,kw2);
			model.addAttribute("paging", paging);
			model.addAttribute("kw1", kw1);
			model.addAttribute("kw2", kw2);
			return "Hr/Att_list";
	}
	
	@GetMapping("/detail")
	public String detail(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			// 로그인한 사용자에게만 허용
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
			 List<Attendance> AttList = this.att_Service.getdetailList(employee);
			model.addAttribute("AttList", AttList);
			return "Hr/Att_detail";
		} else {
			// 로그인하지 않은 사용자에게는 다른 페이지로 리다이렉션 또는 에러 처리
			return "redirect:/Hr/login";
		}

	}

//	@PostMapping("/checkin")
//	public String checkIn(Authentication authentication) {
//		if (authentication != null && authentication.isAuthenticated()) {
//			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//			Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//			att_Service.checkin(employee);
//			return "redirect:/Attendance/detail";
//		} else {
//			return "redirect:/Hr/login";
//		}
//	}
	@PostMapping("/checkin")
	public ResponseEntity<String> checkIn(Authentication authentication) {
	    if (authentication != null && authentication.isAuthenticated()) {
	        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	        Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
	        boolean alreadyCheckedIn = att_Service.checkin(employee);
	        if (alreadyCheckedIn) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already checked in for today!");
	        } else {
	            return ResponseEntity.ok("Check-in successful");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
	    }
	}

	@PostMapping("/checkout")
	public String checkOut(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
			att_Service.checkout(employee);
			return "redirect:/Attendance/detail";
		} else {
			return "redirect:/Hr/login";
		}
	}
	


	
}
