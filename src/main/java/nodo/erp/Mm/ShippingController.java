package nodo.erp.Mm;

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
import nodo.erp.Mm.Warehousing.Warehousing;
import nodo.erp.Mm.Warehousing.WarehousingUpdateForm;


@RequestMapping("/shipping")
@RequiredArgsConstructor
@Controller
public class ShippingController {
	
	private final ShippingService shippingService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Shipping> paging = this.shippingService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "Mm/shipping_list";
	} 
	
	@GetMapping("/create")
	public String shippingCreate(ShippingForm shippingForm){
		return "Mm/shipping_form";
	}
	
	@PostMapping("/create")
	public String shippingCreate(@Valid ShippingForm shippingForm, BindingResult br) {
		if (br.hasErrors()) {
			return "Mm/shipping_form"; 
		}
		this.shippingService.create(shippingForm.getSPDate(),shippingForm.getSPAName(), shippingForm.getSPACode(), shippingForm.getSPIName(), shippingForm.getSPICode(), shippingForm.getSPPName(), shippingForm.getSPPNum(), shippingForm.getSPDT(), shippingForm.getSPCAmount(), shippingForm.getSPLocation(), shippingForm.getSPState());
		return "redirect:/shipping/list";
	}
	
	@GetMapping(value = "/detail/{SPid}")
	public String detail(Model model, @PathVariable("SPid") Integer SPid) {
		Shipping shipping = this.shippingService.getShipping(SPid);
		model.addAttribute("shipping", shipping);
		return "Mm/shipping_detail";
	}
	
	@GetMapping("/modify/{SPid}")
	public String shippingModify(ShippingUpdateForm sf, @PathVariable("SPid") Integer SPid) {
		Shipping shipping = this.shippingService.getShipping(SPid);
		
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
	
	@PostMapping("/modify/{SPid}")
    public String shippingModify(@Valid ShippingUpdateForm sf, BindingResult br, @PathVariable("SPid") Integer SPid) {
        if (br.hasErrors()) {
            return "Mm/shipping_update";
        }
        Shipping shipping = this.shippingService.getShipping(SPid); {
       
        this.shippingService.modify(shipping, sf.getSPAName(), sf.getSPACode(), sf.getSPIName(), sf.getSPICode(), sf.getSPPName(), sf.getSPPNum(), sf.getSPDT(), sf.getSPCAmount(), sf.getSPLocation(), sf.getSPState());
        return String.format("redirect:/shipping/list", SPid);
    
        }
	}
	
	@GetMapping("/delete/{SPid}")
    public String shippingDelete(@PathVariable("SPid") Integer SPid) {
		Shipping shipping = this.shippingService.getShipping(SPid);
      
        this.shippingService.delete(shipping);
        return "redirect:/shipping/list";
    }
	
	
}
