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
import nodo.erp.Hr.Dto.Spot_Form;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Entity.Spot;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Hr.Service.Spot_Service;

@RequestMapping("/spot")
@RequiredArgsConstructor
@Controller
public class Spot_Controller {

	private final Spot_Service spot_Service;
	private final Emp_Service emp_Service;
	
	@GetMapping("/list")
	public String spotlist(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		List<Spot> paging = this.spot_Service.getList();
		model.addAttribute("paging", paging);
		return "Hr/Spot_list";
	}

	@GetMapping("/create")
	public String spotCreate(Spot_Form spot_Form, Authentication authentication) {
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() == 1) {
			return "Hr/Spot_Form";
//		} else {
//			return "redirect:/";
//		}
	}

	@PostMapping("/create")
	public String spotCreate(@Valid Spot_Form spot_Form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "Hr/Spot_Form";
		}
		this.spot_Service.create(spot_Form.getSpotcode(), spot_Form.getSpotname());
		return "redirect:/spot/list";
	}

	@GetMapping("/modify/{id}")
	public String spotModify(Spot_Form spot_Form, @PathVariable("id") Integer id) {
		Spot spot = this.spot_Service.getFindById(id);
		spot_Form.setSpotcode(spot.getSpotcode());
		spot_Form.setSpotname(spot.getSpotname());
		return "Hr/Spot_Form";
	}

	@PostMapping("/modify/{id}")
	public String spotModify(@Valid Spot_Form spot_Form, BindingResult bindResult, @PathVariable("id") Integer id) {
		Spot spot = this.spot_Service.getFindById(id);
		if (bindResult.hasErrors()) {
			return "Hr/Spot_Form";
		}
		this.spot_Service.modify(spot, spot_Form.getSpotcode(), spot_Form.getSpotname());
		return "redirect:/spot/list";
	}

	@GetMapping("/delete/{id}")
	public String spotDelete(@PathVariable("id") Integer id, Authentication authentication) {
		Spot spot = this.spot_Service.getFindById(id);
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Employee employee = this.emp_Service.getfindById(customUserDetails.getEmpid());
//		if (employee.getId() == 1) {
			this.spot_Service.delete(spot);
			return "redirect:/spot/list";
//		} else {
//			return "redirect:/";
//		}
	}
}
