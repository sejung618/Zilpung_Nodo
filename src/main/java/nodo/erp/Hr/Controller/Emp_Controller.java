package nodo.erp.Hr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.Dto.Emp_Form;
import nodo.erp.Hr.Dto.Emp_Pass_Form;
import nodo.erp.Hr.Dto.Emp_modify_Form;
import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Service.Emp_Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

@RequestMapping("/Hr")
@RequiredArgsConstructor
@Controller
public class Emp_Controller {

	private final Emp_Service emp_Service;
	@Autowired
	private PasswordEncoder passwordEncoder;
		
//	@GetMapping("/list")
//	public String Emplist(Model model) {
//		List<Hr_Dto_Emp> EmpList = this.hr_Service.getList();
//		model.addAttribute("EmpList", EmpList);
//		return "Hr/Emp_List";
//	}
	//페이징
	 @GetMapping("/list")
	    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
	        Page<Employee> paging = this.emp_Service.getList(page);
	        model.addAttribute("paging", paging);
	        return "/Hr/Emp_List";
	    }

	@GetMapping("/create")
    public String EmpCreate(Model model, Emp_Form emp_Form) {
		List<Department> deplist = this.emp_Service.getdepList();
		model.addAttribute("deplist", deplist);
		
        return "Hr/Emp_Form";
    }
	

	
	@PostMapping("/create")
	public String EmpCreate(@Valid Emp_Form emp_Form, BindingResult bindingResult) {
		Department depart = this.emp_Service.getDepCode(emp_Form.getDepcode());
		if (bindingResult.hasErrors()) {
	        return "Hr/Emp_Form";
	    }
	    this.emp_Service.create(emp_Form.getEmpname(), emp_Form.getEmpssn(), 
	    		emp_Form.getEmpadd(), emp_Form.getEmpphone(), emp_Form.getEmpmail(),
	    		emp_Form.getEmpdate(), emp_Form.getEmpspot(), emp_Form.getEmpposition(), 
	    		depart);
	    
	    return "redirect:/Hr/list";
	}
	
    @GetMapping(value = "/detail/{id}")
    public String EmpDetail(Model model, @PathVariable("id") Integer id) {
    	Employee empDtail = this.emp_Service.getEmpDetail(id);
        model.addAttribute("empDtail", empDtail);
    	
    	return "Hr/Emp_detail";
    }				
	
    @GetMapping("/modify/{id}")
    public String EmpModify(Emp_modify_Form emp_modify_Form, @PathVariable("id") Integer id) {
    	Employee empDtail = this.emp_Service.getEmpDetail(id);
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
    	Employee hr_Dto_Emp = this.emp_Service.getEmpDetail(id);
    	
    	if(bindResult.hasErrors()) {
			return "Hr/Emp_modify_Form";
		}
		this.emp_Service.modify(hr_Dto_Emp, emp_modify_Form.getEmpname(), emp_modify_Form.getEmpadd(),emp_modify_Form.getEmpphone(),emp_modify_Form.getEmpmail());
		return "redirect:/Hr/list";
	}
    
    
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id) {
    	Employee emp = this.emp_Service.getEmpDetail(id);
//        if (!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
        this.emp_Service.delete(emp);
        return "redirect:/";
    }
    
    
    @GetMapping("/login")
    public String login() {
        return "Hr/login_form";
    }
    
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pass/{id}")
    public String EmpPassModify(Emp_Pass_Form emp_Pass_Form, @PathVariable("id") Integer id,Principal principal ) {
    	Employee emp = this.emp_Service.getEmpDetail(id);
        
    	if(!emp.getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        return "Hr/Emp_Pass_Form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pass/{id}")
	public String EmpPassModify(@PathVariable("id") Integer id ,@Valid Emp_Pass_Form emp_Pass_Form,  BindingResult bindingResult,Principal principal) {
    	if(bindingResult.hasErrors()) {
    		return "Hr/Emp_Pass_Form";
    	}			
    	Employee emp = this.emp_Service.getEmpDetail(id);
    	System.out.println(emp.getPassword());
    	System.out.println(emp_Pass_Form.getPass());
    	if (!passwordEncoder.matches(emp_Pass_Form.getPass(), emp.getPassword()))  {
    		bindingResult.rejectValue("pass", "passwordInCorrect", 
                    "비밀번호가 틀렸습니다.");
            return "Hr/Emp_Pass_Form";
        }
    	
    	if (!emp_Pass_Form.getNewpass().equals(emp_Pass_Form.getNewpass2())) {
    		bindingResult.rejectValue("newpass2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "Hr/Emp_Pass_Form";
        }
    	
    	if (!emp.getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
    	
		this.emp_Service.passmodify(emp, emp_Pass_Form.getNewpass());
		return "redirect:/";
	}
    
}