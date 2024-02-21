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
import nodo.erp.Hr.Hr_Service;
import nodo.erp.Hr.Hr_Dto_Dep;

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
		Hr_Dto_Dep depart = this.hr_Service.getDepCode(emp_Form.getDepcode());
		if (bindingResult.hasErrors()) {
	        return "Hr/Emp_Form";
	    }
	    this.hr_Service.create(emp_Form.getEmpname(), emp_Form.getEmpssn(), 
	    		emp_Form.getEmpadd(), emp_Form.getEmpphone(), emp_Form.getEmpmail(),
	    		emp_Form.getEmpdate(), emp_Form.getEmpspot(), emp_Form.getEmpposition(), 
	    		depart);
	    
	    return "redirect:/Hr/list";
	}
	
    @GetMapping(value = "/detail/{id}")
    public String EmpDetail(Model model, @PathVariable("id") Integer id) {
    	Hr_Dto_Emp empDtail = this.hr_Service.getEmpDetail(id);
        model.addAttribute("empDtail", empDtail);
    	
    	return "Hr/Emp_detail";
    }				
	
    @GetMapping("/modify/{id}")
    public String EmpModify(Emp_modify_Form emp_modify_Form, @PathVariable("id") Integer id) {
    	Hr_Dto_Emp empDtail = this.hr_Service.getEmpDetail(id);
//        if(!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
    	emp_modify_Form.setEmpname(empDtail.getEmpname());
    	emp_modify_Form.setEmpadd(empDtail.getEmpadd());
    	emp_modify_Form.setEmpphone(empDtail.getEmpphone());
    	emp_modify_Form.setEmpmail(empDtail.getEmpmail());
        return "Hr/Emp_modify_Form";
    }
    
    @PostMapping("/modify/{id}")
	public String EmpModify(@PathVariable("id") Integer id ,@Valid Emp_modify_Form emp_modify_Form,  BindingResult bindResult) {
    	Hr_Dto_Emp hr_Dto_Emp = this.hr_Service.getEmpDetail(id);
    	
    	if(bindResult.hasErrors()) {
			return "Hr/Emp_modify_Form";
		}
		this.hr_Service.modify(hr_Dto_Emp, emp_modify_Form.getEmpname(), emp_modify_Form.getEmpadd(),emp_modify_Form.getEmpphone(),emp_modify_Form.getEmpmail());
		return "redirect:/Hr/list";
	}
    
    
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id) {
    	Hr_Dto_Emp emp = this.hr_Service.getEmpDetail(id);
//        if (!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
        this.hr_Service.delete(emp);
        return "redirect:/";
    }
    
    
    
    
    
    @GetMapping("/login")
    public String login() {
        return "Hr/login_form";
    }
    
}