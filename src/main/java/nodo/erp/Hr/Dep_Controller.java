package nodo.erp.Hr;

import java.util.List;

import org.springframework.data.domain.Page;
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

@RequestMapping("/dep")
@RequiredArgsConstructor
@Controller
public class Dep_Controller {
	
	private final Hr_Service hr_Service;

	 @GetMapping("/list")
	    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
	        Page<Hr_Dto_Dep> paging = this.hr_Service.getdepList(page);
	        model.addAttribute("paging", paging);
	        return "Hr/Dep_List";
	    }
	 
//	 @GetMapping("/create")
//	    public String EmpCreate(Model model, Emp_Form emp_Form) {
//			List<Hr_Dto_Dep> deplist = this.hr_Service.getdepList();
//			model.addAttribute("deplist", deplist);
//			
//	        return "Hr/Emp_Form";
//	    }
//	
//	 @PostMapping("/create")
//		public String EmpCreate(@Valid Emp_Form emp_Form, BindingResult bindingResult) {
//			Hr_Dto_Dep depart = this.hr_Service.getDepCode(emp_Form.getDepcode());
//			if (bindingResult.hasErrors()) {
//		        return "Hr/Emp_Form";
//		    }
//		    this.hr_Service.create(emp_Form.getEmpname(), emp_Form.getEmpssn(), 
//		    		emp_Form.getEmpadd(), emp_Form.getEmpphone(), emp_Form.getEmpmail(),
//		    		emp_Form.getEmpdate(), emp_Form.getEmpspot(), emp_Form.getEmpposition(), 
//		    		depart);
//		    
//		    return "redirect:/Hr/list";
//		}
//	 
//	 @GetMapping("/modify/{id}")
//	    public String EmpModify(Emp_modify_Form emp_modify_Form, @PathVariable("id") Integer id) {
//	    	Hr_Dto_Emp empDtail = this.hr_Service.getEmpDetail(id);
////	        if(!question.getAuthor().getUsername().equals(principal.getName())) {
////	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
////	        }
//	    	emp_modify_Form.setEmpname(empDtail.getEmpname());
//	    	emp_modify_Form.setEmpadd(empDtail.getEmpadd());
//	    	emp_modify_Form.setEmpphone(empDtail.getEmpphone());
//	    	emp_modify_Form.setEmpmail(empDtail.getEmpmail());
//	        return "Hr/Emp_modify_Form";
//	    }
//	    
//	    @PostMapping("/modify/{id}")
//		public String EmpModify(@PathVariable("id") Integer id ,@Valid Emp_modify_Form emp_modify_Form,  BindingResult bindResult) {
//	    	Hr_Dto_Emp hr_Dto_Emp = this.hr_Service.getEmpDetail(id);
//	    	
//	    	if(bindResult.hasErrors()) {
//				return "Hr/Emp_modify_Form";
//			}
//			this.hr_Service.modify(hr_Dto_Emp, emp_modify_Form.getEmpname(), emp_modify_Form.getEmpadd(),emp_modify_Form.getEmpphone(),emp_modify_Form.getEmpmail());
//			return "redirect:/Hr/list";
//		}
//	    
//	    @GetMapping("/delete/{id}")
//	    public String questionDelete(@PathVariable("id") Integer id) {
//	    	Hr_Dto_Emp emp = this.hr_Service.getEmpDetail(id);
//
//	        this.hr_Service.delete(emp);
//	        return "redirect:/";
//	    }
//	    
}

