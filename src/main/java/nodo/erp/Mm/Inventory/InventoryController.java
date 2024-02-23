package nodo.erp.Mm.Inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/inventory")
@RequiredArgsConstructor
@Controller
public class InventoryController {
//	private final InventoryRepository inventoryRepositiry;
//	
//	@GetMapping("/inventory/list")
//	public String list(Model model) {
//		List<Inventory> inventoryList = this.inventoryRepositiry.findAll();
//		model.addAttribute("inventoryList", inventoryList);
//		return "inventory_list";
	
	
	private final InventoryService inventoryService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Inventory> paging = this.inventoryService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "Mm/inventory_list";
		
	}
	
//	@GetMapping("/inventory/create")
//	public String inventoryCreate(){
//		return "inventory_form";
//	}
//	
//	@PostMapping("/inventory/create")
//	public String inventoryCreate(@RequestParam(value="INDate") String INDate,@RequestParam(value="INPName") String INPName,@RequestParam(value="INPNum") String INPNum,@RequestParam(value="ININame") String ININame,@RequestParam(value="INICode") String INICode,@RequestParam(value="INBQ") Integer INBQ,@RequestParam(value="INAQ") Integer INAQ,@RequestParam(value="INStandard") String INStandard) {
//		this.inventoryService.create(INDate, ININame, INPName, INAQ, INBQ, INPNum, INICode, INStandard);
//		return "redirect:/inventory/list";
//	}
	
	
	@GetMapping("/create")
	public String inventoryCreate(InventoryForm inventoryForm){
		return "Mm/inventory_form";
	}
	
	
	@PostMapping("/create")
	public String inventoryCreate(@Valid InventoryForm inf, BindingResult br) {
		if (br.hasErrors()) {
			return "Mm/inventory_form"; 
		}
		this.inventoryService.create(inf.getINDate(),inf.getININame(),inf.getINPName(),inf.getINQuantity(),inf.getINPNum(),inf.getINICode(),inf.getINStandard());
		return "redirect:/inventory/list";
	}
	
	@GetMapping(value = "/detail/{INid}")
	public String detail(Model model, @PathVariable("INid") Integer INid) {
		Inventory inventory = this.inventoryService.getInventory(INid);
		model.addAttribute("inventory", inventory);
		return "Mm/inventory_detail";
	}
    
	@GetMapping("/modify/{INid}")
	public String inventoryModify(InventoryUpdateForm inf, @PathVariable("INid") Integer INid) {
		Inventory inventory = this.inventoryService.getInventory(INid);
	
		inf.setINICode(inventory.getINICode());
		inf.setININame(inventory.getININame());
		inf.setINPName(inventory.getINPName());
		inf.setINPNum(inventory.getINPNum());
		inf.setINQuantity(inventory.getINQuantity());
		inf.setINStandard(inventory.getINStandard());
		return "Mm/inventory_update";
	}
	
	@PostMapping("/modify/{INid}")
    public String inventoryModify(@Valid InventoryUpdateForm inf, BindingResult br, @PathVariable("INid") Integer INid) {
        if (br.hasErrors()) {
            return "Mm/inventory_update";
        }
        Inventory inventory = this.inventoryService.getInventory(INid); {
       
        this.inventoryService.modify(inventory, inf.getININame(),inf.getINPName(),inf.getINQuantity(),inf.getINPNum(),inf.getINICode(),inf.getINStandard());
        return String.format("redirect:/inventory/list", INid);
    
        }
	}
	
	@GetMapping("/delete/{INid}")
    public String inventoryDelete(@PathVariable("INid") Integer INid) {
		Inventory inventory = this.inventoryService.getInventory(INid);
      
        this.inventoryService.delete(inventory);
        return "redirect:/inventory/list";
    }
}
    