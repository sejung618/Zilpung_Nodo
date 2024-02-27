package nodo.erp.Mm.Warehousing;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.CustomUserDetails;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Service.Emp_Service;


@RequestMapping("/warehousing")
@RequiredArgsConstructor
@Controller
public class WarehousingController {
	
	private final WarehousingService warehousingService;
	private final Emp_Service emp_Service;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Warehousing> paging = this.warehousingService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "Mm/warehousing_list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String warehousingCreate(WarehousingForm warehousingForm){
		return "Mm/warehousing_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String warehousingCreate(@Valid WarehousingForm warehousingForm, BindingResult br, Authentication authentication) {
		if (br.hasErrors()) {
			return "Mm/warehousing_form"; 
		}
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee employee = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());
		
		this.warehousingService.create(warehousingForm.getWHDate(),warehousingForm.getWHAName(), warehousingForm.getWHACode(), warehousingForm.getWHIName(), warehousingForm.getWHICode(), warehousingForm.getWHPName(), warehousingForm.getWHPNum(), warehousingForm.getWHDT(), warehousingForm.getWHCAmount(), warehousingForm.getWHLocation(), warehousingForm.getWHState(), employee);
		return "redirect:/warehousing/list";
	}
	
	@GetMapping(value = "/detail/{WHid}")
	public String detail(Model model, @PathVariable("WHid") Integer WHid) {
		Warehousing warehousing = this.warehousingService.getWarehousing(WHid);
		model.addAttribute("warehousing", warehousing);
		return "Mm/warehousing_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{WHid}")
	public String warehousingModify(WarehousingUpdateForm wf, @PathVariable("WHid") Integer WHid, Principal principal) {
		Warehousing warehousing = this.warehousingService.getWarehousing(WHid);
		
		if(!warehousing.getEmployee().getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
		
		wf.setWHAName(warehousing.getWHAName());
		wf.setWHACode(warehousing.getWHACode());
		wf.setWHIName(warehousing.getWHIName());
		wf.setWHICode(warehousing.getWHICode());
		wf.setWHPName(warehousing.getWHPName());
		wf.setWHPNum(warehousing.getWHPNum());
		wf.setWHDT(warehousing.getWHDT());
		wf.setWHCAmount(warehousing.getWHCAmount());
		wf.setWHLocation(warehousing.getWHLocation());
		wf.setWHState(warehousing.getWHState());
		return "Mm/warehousing_update";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{WHid}")
    public String warehousingModify(@Valid WarehousingUpdateForm wf, BindingResult br, @PathVariable("WHid") Integer WHid, Principal principal) {
        if (br.hasErrors()) {
            return "Mm/warehousing_update";
        }
        Warehousing warehousing = this.warehousingService.getWarehousing(WHid); {
    	if(!warehousing.getEmployee().getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.warehousingService.modify(warehousing, wf.getWHAName(),wf.getWHACode(),wf.getWHIName(),wf.getWHICode(),wf.getWHPName(),wf.getWHPNum(),wf.getWHDT(),wf.getWHCAmount(),wf.getWHLocation(),wf.getWHState());
        return String.format("redirect:/warehousing/list", WHid);
    
        }
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{WHid}")
    public String warehousingDelete(@PathVariable("WHid") Integer WHid, 
            Principal principal) {
		Warehousing warehousing = this.warehousingService.getWarehousing(WHid);
      
		if(!warehousing.getEmployee().getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
		
        this.warehousingService.delete(warehousing);
        return "redirect:/warehousing/list";
    }
}
