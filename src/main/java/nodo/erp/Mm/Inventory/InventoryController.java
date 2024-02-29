package nodo.erp.Mm.Inventory;

import java.security.Principal;
import java.util.List;

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
	private final Emp_Service emp_Service;

	@GetMapping("/list")
	public String list(Model model,
	                   @RequestParam(value = "page", defaultValue = "0") int page,
	                   @RequestParam(value = "kw", defaultValue = "") String kw,
	                   @RequestParam(value = "category", defaultValue = "") String category) {

	    Page<Inventory> paging = this.inventoryService.getList(page, kw);

	    if (category == null && category.isEmpty()) {
	    	paging = this.inventoryService.getList(page, kw);
	    } if ("INDate".equals(category)) {
	    	paging = this.inventoryService.findByINDate(page, kw);
	    } else if ("ININame".equals(category)) {
	    	paging = this.inventoryService.findByININame(page, kw);
	    } else if ("INICode".equals(category)) {
	    	paging = this.inventoryService.findByINICode(page, kw);
	    }
	    
	    model.addAttribute("paging", paging);
	    model.addAttribute("kw", kw);
	    

	    return "Mm/inventory_list";

	}

//	@GetMapping("/list")
//	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
//		Page<Inventory> paging = this.inventoryService.getList(page, kw);
//		model.addAttribute("paging", paging);
//		model.addAttribute("kw", kw);
//		return "Mm/inventory_list";
//		
//	}

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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String inventoryCreate(Model model, InventoryForm inventoryForm) {
		List<Employee> empList = this.emp_Service.getList();
		model.addAttribute("empList", empList);
		return "Mm/inventory_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String inventoryCreate(Model model, @Valid InventoryForm inf, BindingResult br,
			Authentication authentication) {
		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			model.addAttribute("empList", empList);
			return "Mm/inventory_form";
		}
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		Employee employee = this.emp_Service.getEmpDetail(customUserDetails.getEmpid());
		this.inventoryService.create(inf.getINDate(), inf.getININame(), inf.getINPName(), inf.getINQuantity(),
				inf.getINPNum(), inf.getINICode(), inf.getINStandard(), employee);
		return "redirect:/inventory/list";
	}

	@GetMapping(value = "/detail/{INid}")
	public String detail(Model model, @PathVariable("INid") Integer INid) {
		Inventory inventory = this.inventoryService.getInventory(INid);
		model.addAttribute("inventory", inventory);
		return "Mm/inventory_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{INid}")
	public String inventoryModify(Model model, InventoryUpdateForm inf, @PathVariable("INid") Integer INid,
			Principal principal) {
		Inventory inventory = this.inventoryService.getInventory(INid);
		List<Employee> empList = this.emp_Service.getList();
		model.addAttribute("empList", empList);
		if (!inventory.getEmployee().getEmpnum().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		inf.setINICode(inventory.getINICode());
		inf.setININame(inventory.getININame());
		inf.setINPName(inventory.getINPName());
		inf.setINPNum(inventory.getINPNum());
		inf.setINQuantity(inventory.getINQuantity());
		inf.setINStandard(inventory.getINStandard());
		return "Mm/inventory_update";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{INid}")
	public String inventoryModify(Model model, @Valid InventoryUpdateForm inf, BindingResult br,
			@PathVariable("INid") Integer INid, Principal principal) {
		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			model.addAttribute("empList", empList);
			return "Mm/inventory_update";
		}
		Inventory inventory = this.inventoryService.getInventory(INid);
		{
			if (!inventory.getEmployee().getEmpnum().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
			}
			this.inventoryService.modify(inventory, inf.getININame(), inf.getINPName(), inf.getINQuantity(),
					inf.getINPNum(), inf.getINICode(), inf.getINStandard());
			return String.format("redirect:/inventory/list", INid);

		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{INid}")
	public String inventoryDelete(@PathVariable("INid") Integer INid, Principal principal) {
		Inventory inventory = this.inventoryService.getInventory(INid);

		if (!inventory.getEmployee().getEmpnum().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		this.inventoryService.delete(inventory);
		return "redirect:/inventory/list";
	}
}
