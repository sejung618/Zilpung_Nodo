package nodo.erp.Hr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/Hr")
@RequiredArgsConstructor
@Controller
public class Hr_Controller {

	private final Hr_Service hr_Service;
	
	@GetMapping("/list")
	public String Emplist(Model model) {
		List<Hr_Dto_Emp> EmpList = this.hr_Service.getList();
		model.addAttribute("EmpList", EmpList);
		return "Hr/Emp_List";
	}

	@GetMapping("/create")
    public String EmpCreate(Emp_Form emp_Form) {
        return "Hr/Emp_Form";
    }
	
	/*
	 * @PostMapping("/create") public String EmpCreate(@Valid Hr_Form hr_Form,
	 * BindingResult bindingResult, Model model) { 
	 * if (bindingResult.hasErrors()) {
	 * model.addAttribute("Hr_Form", hr_Form); 
	 * return "Hr/Emp_Add"; }
	 */
	
	@PostMapping("/create")
	public String EmpCreate(@Valid Emp_Form emp_Form, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "Hr/Emp_Form";
	    }
	    
	    this.hr_Service.create(emp_Form.getEmpName(), emp_Form.getEmpSsn(), 
	    		emp_Form.getEmpAdd(), emp_Form.getEmpPhone(), emp_Form.getEmpMail(),
	    		emp_Form.getEmpDate(), emp_Form.getEmpSpot(), emp_Form.getEmpPosition(), 
	    		emp_Form.getDepCode());
	    
	    return "redirect:/Hr/list";
	}
	
}