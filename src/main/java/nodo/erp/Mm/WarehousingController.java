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
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Warehousing warehousing = this.warehousingService.getWarehousing(id);
		model.addAttribute("warehousing", warehousing);
		return "Mm/warehousing_detail";
	}
	
	@GetMapping("/modify/{id}")
	public String warehousingModify(WarehousingForm warehousingForm, @PathVariable("id") Integer id) {
		Warehousing warehousing = this.warehousingService.getWarehousing(id);
		warehousingForm.setWHDate(warehousing.getWHDate());
		warehousingForm.setWHAName(warehousing.getWHAName());
		warehousingForm.setWHACode(warehousing.getWHACode());
		warehousingForm.setWHIName(warehousing.getWHIName());
		warehousingForm.setWHICode(warehousing.getWHICode());
		warehousingForm.setWHPName(warehousing.getWHPName());
		warehousingForm.setWHPNum(warehousing.getWHPNum());
		warehousingForm.setWHDT(warehousing.getWHDT());
		warehousingForm.setWHCAmount(warehousing.getWHCAmount());
		warehousingForm.setWHLocation(warehousing.getWHLocation());
		warehousingForm.setWHState(warehousing.getWHState());
		return "Mm/warehousing_form";
	}
	
	@PostMapping("/modify/{id}")
    public String warehousingModify(@Valid WarehousingForm warehousingForm, BindingResult br, @PathVariable("id") Integer id) {
        if (br.hasErrors()) {
            return "Mm/warehousing_form";
        }
        Warehousing warehousing = this.warehousingService.getWarehousing(id); {
       
        this.warehousingService.modify(warehousing, warehousingForm.getWHDate(),warehousingForm.getWHAName(),warehousingForm.getWHACode(),warehousingForm.getWHIName(),warehousingForm.getWHICode(),warehousingForm.getWHPName(),warehousingForm.getWHPNum(),warehousingForm.getWHDT(),warehousingForm.getWHCAmount(),warehousingForm.getWHLocation(),warehousingForm.getWHState());
        return String.format("redirect:/warehousing/list", id);
    
        }
	}
	
	@GetMapping("/delete/{id}")
    public String inventoryDelete(@PathVariable("id") Integer id) {
		Warehousing warehousing = this.warehousingService.getWarehousing(id);
      
        this.warehousingService.delete(warehousing);
        return "redirect:/warehousing/list";
    }
}
