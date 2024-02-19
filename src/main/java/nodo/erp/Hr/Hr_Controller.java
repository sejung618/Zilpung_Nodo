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
	
//	@GetMapping("/list")
//	public String Emplist(Model model) {
//		List<Hr_Dto_Emp> EmpList = this.hr_Service.getList();
//		model.addAttribute("EmpList", EmpList);
//		return "Hr/Emp_List";
//	}
	//페이징
	 @GetMapping("/list")
	    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
	        Page<Hr_Dto_Emp> paging = this.hr_Service.getList(page);
	        model.addAttribute("paging", paging);
	        return "Hr/Emp_List";
	    }

	@GetMapping("/create")
    public String EmpCreate(Emp_Form emp_Form) {
        return "Hr/Emp_Form";
    }
	
	
	@PostMapping("/create")
	public String EmpCreate(@Valid Emp_Form emp_Form, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "Hr/Emp_Form";
	    }
	    this.hr_Service.create(emp_Form.getEmpname(), emp_Form.getEmpssn(), 
	    		emp_Form.getEmpadd(), emp_Form.getEmpphone(), emp_Form.getEmpmail(),
	    		emp_Form.getEmpdate(), emp_Form.getEmpspot(), emp_Form.getEmpposition(), 
	    		emp_Form.getDepcode());
	    
	    return "redirect:/Hr/list";
	}
	
    @GetMapping(value = "/detail/{id}")
    public String EmpDetail(Model model, @PathVariable("id") Integer id) {
    	Hr_Dto_Emp empDtail = this.hr_Service.getEmpDetail(id);
        model.addAttribute("empDtail", empDtail);
    	
    	return "Hr/Emp_detail";
    }				
	
   	
    
    @GetMapping("/login")
    public String login() {
        return "Hr/login_form";
    }
    
}