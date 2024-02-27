package nodo.erp.Mm.Shipping;

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
import nodo.erp.Mm.Warehousing.Warehousing;
import nodo.erp.Mm.Warehousing.WarehousingUpdateForm;


@RequestMapping("/shipping")
@RequiredArgsConstructor
@Controller
public class ShippingController {
	
	private final ShippingService shippingService;
	private final Emp_Service emp_Service;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Shipping> paging = this.shippingService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "Mm/shipping_list";
	} 
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String shippingCreate(ShippingForm shippingForm){
		return "Mm/shipping_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String shippingCreate(@Valid ShippingForm shippingForm, BindingResult br, Authentication authentication) {
		if (br.hasErrors()) {
			return "Mm/shipping_form"; 
		}
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee employee = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());
		
		this.shippingService.create(shippingForm.getSPDate(),shippingForm.getSPAName(), shippingForm.getSPACode(), shippingForm.getSPIName(), shippingForm.getSPICode(), shippingForm.getSPPName(), shippingForm.getSPPNum(), shippingForm.getSPDT(), shippingForm.getSPCAmount(), shippingForm.getSPLocation(), shippingForm.getSPState(), employee);
		return "redirect:/shipping/list";
	}
	
	@GetMapping(value = "/detail/{SPid}")
	public String detail(Model model, @PathVariable("SPid") Integer SPid) {
		Shipping shipping = this.shippingService.getShipping(SPid);
		model.addAttribute("shipping", shipping);
		return "Mm/shipping_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{SPid}")
	public String shippingModify(ShippingUpdateForm sf, @PathVariable("SPid") Integer SPid, Principal principal) {
		Shipping shipping = this.shippingService.getShipping(SPid);
		
		if(!shipping.getEmployee().getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
		
		sf.setSPAName(shipping.getSPAName());
		sf.setSPACode(shipping.getSPACode());
		sf.setSPIName(shipping.getSPIName());
		sf.setSPICode(shipping.getSPICode());
		sf.setSPPName(shipping.getSPPName());
		sf.setSPPNum(shipping.getSPPNum());
		sf.setSPDT(shipping.getSPDT());
		sf.setSPCAmount(shipping.getSPCAmount());
		sf.setSPLocation(shipping.getSPLocation());
		sf.setSPState(shipping.getSPState());
		return "Mm/shipping_update";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{SPid}")
    public String shippingModify(@Valid ShippingUpdateForm sf, BindingResult br, @PathVariable("SPid") Integer SPid, Principal principal) {
        if (br.hasErrors()) {
            return "Mm/shipping_update";
        }
        Shipping shipping = this.shippingService.getShipping(SPid); {
    	if(!shipping.getEmployee().getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.shippingService.modify(shipping, sf.getSPAName(), sf.getSPACode(), sf.getSPIName(), sf.getSPICode(), sf.getSPPName(), sf.getSPPNum(), sf.getSPDT(), sf.getSPCAmount(), sf.getSPLocation(), sf.getSPState());
        return String.format("redirect:/shipping/list", SPid);
    
        }
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{SPid}")
    public String shippingDelete(@PathVariable("SPid") Integer SPid, 
            Principal principal) {
		Shipping shipping = this.shippingService.getShipping(SPid);
      
		if(!shipping.getEmployee().getEmpnum().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
		
        this.shippingService.delete(shipping);
        return "redirect:/shipping/list";
    }
	
	
}
