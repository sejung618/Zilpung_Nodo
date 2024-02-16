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
	private final Hr_Repository hr_Repository;
	
//	@GetMapping("/list")
//	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "kw", defaultValue = "") String kw) {
//		Page<Hr_Dto_Emp> paging = this.hr_Service.getList(page, kw);
//		model.addAttribute("paging", paging);
//		model.addAttribute("kw", kw);
//		return "Emp_list";
//	}
	
	@GetMapping("/list")
    public String list(Model model) {
        List<Hr_Dto_Emp> EmpList = this.hr_Repository.findAll();
        model.addAttribute("EmpList", EmpList);
        return "Hr/Emp_List";
    }
	
//	@GetMapping("/create")
//	public String questionCreate(Hr_Form questionForm) {
//		return "Hr/Emp_Add";
//	}
//	
//	
//	@PostMapping("/create")
//	public String inventoryCreate(@Valid Hr_Form inf, BindingResult br) {
//		if (br.hasErrors()) {
//			return "Hr/Emp_Add"; 
//		}
//		this.hr_Service.create(inf.getEmpName(),inf.getEmpAdd());
//		return "redirect:/inventory/list";
//	}
	
	 @GetMapping("/create")
	    public String EmpCreate(Model model) {
	        model.addAttribute("Hr_Form", new Hr_Form()); // 모델에 Hr_Form 추가
	        return "Hr/Emp_Add";
	    }

	    @PostMapping("/create")
	    public String EmpCreate(@Valid Hr_Form inf, BindingResult br, Model model) {
	        if (br.hasErrors()) {
	            model.addAttribute("Hr_Form", inf); // 에러가 발생하면 모델에 Hr_Form 추가
	            return "Hr/Emp_Add";
	        }
	        this.hr_Service.create(inf.getEmpName(), inf.getEmpAdd());
	        return "redirect:/";
	    }

}
