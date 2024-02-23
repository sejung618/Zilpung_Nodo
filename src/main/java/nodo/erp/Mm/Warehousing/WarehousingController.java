package nodo.erp.Mm.Warehousing;

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


@RequestMapping("/warehousing")
@RequiredArgsConstructor
@Controller
public class WarehousingController {
	
	private final WarehousingService warehousingService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Warehousing> paging = this.warehousingService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "Mm/warehousing_list";
	}

	@GetMapping("/create")
	public String warehousingCreate(WarehousingForm warehousingForm){
		return "Mm/warehousing_form";
	}
	
	@PostMapping("/create")
	public String warehousingCreate(@Valid WarehousingForm warehousingForm, BindingResult br) {
		if (br.hasErrors()) {
			return "Mm/warehousing_form"; 
		}
		this.warehousingService.create(warehousingForm.getWHDate(),warehousingForm.getWHAName(), warehousingForm.getWHACode(), warehousingForm.getWHIName(), warehousingForm.getWHICode(), warehousingForm.getWHPName(), warehousingForm.getWHPNum(), warehousingForm.getWHDT(), warehousingForm.getWHCAmount(), warehousingForm.getWHLocation(), warehousingForm.getWHState());
		return "redirect:/warehousing/list";
	}
	
	@GetMapping(value = "/detail/{WHid}")
	public String detail(Model model, @PathVariable("WHid") Integer WHid) {
		Warehousing warehousing = this.warehousingService.getWarehousing(WHid);
		model.addAttribute("warehousing", warehousing);
		return "Mm/warehousing_detail";
	}
	
	@GetMapping("/modify/{WHid}")
	public String warehousingModify(WarehousingUpdateForm wf, @PathVariable("WHid") Integer WHid) {
		Warehousing warehousing = this.warehousingService.getWarehousing(WHid);
		
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
	
	@PostMapping("/modify/{WHid}")
    public String warehousingModify(@Valid WarehousingUpdateForm wf, BindingResult br, @PathVariable("WHid") Integer WHid) {
        if (br.hasErrors()) {
            return "Mm/warehousing_update";
        }
        Warehousing warehousing = this.warehousingService.getWarehousing(WHid); {
       
        this.warehousingService.modify(warehousing, wf.getWHAName(),wf.getWHACode(),wf.getWHIName(),wf.getWHICode(),wf.getWHPName(),wf.getWHPNum(),wf.getWHDT(),wf.getWHCAmount(),wf.getWHLocation(),wf.getWHState());
        return String.format("redirect:/warehousing/list", WHid);
    
        }
	}
	
	@GetMapping("/delete/{WHid}")
    public String inventoryDelete(@PathVariable("WHid") Integer WHid) {
		Warehousing warehousing = this.warehousingService.getWarehousing(WHid);
      
        this.warehousingService.delete(warehousing);
        return "redirect:/warehousing/list";
    }
}
