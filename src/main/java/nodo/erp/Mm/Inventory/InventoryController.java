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
import nodo.erp.Hr.Dto.Emp_Form;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;

@RequestMapping("/inventory")
@RequiredArgsConstructor
@Controller
public class InventoryController {

	private final InventoryService inventoryService;
	private final ItemService itemService;
	private final Emp_Service emp_Service;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "category", defaultValue = "") String category) {

		Page<Inventory> paging = this.inventoryService.getList(page, kw);

//		Page<Inventory> paging = this.inventoryService.searchAllCategories(page, kw);

		if (category == null && category.isEmpty()) {
			paging = this.inventoryService.searchAllCategories(page, kw);
		}
		if ("indate".equals(category)) {
			paging = this.inventoryService.findByindate(page, kw);
		}
		if ("itmname".equals(category)) {
			paging = this.inventoryService.findByItmName(page, kw);
		}
		if ("itmcode".equals(category)) {
			paging = this.inventoryService.findByItmCode(page, kw);
		}

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);

		return "Mm/inventory_list";

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String inventoryCreate(Model model, InventoryForm inventoryForm) {
		List<Employee> empList = this.emp_Service.getList();
		List<Item> itemList = this.itemService.getList();
		model.addAttribute("empList", empList);
		model.addAttribute("itemList", itemList);
		return "Mm/inventory_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String inventoryCreate(Model model, @Valid InventoryForm inf, BindingResult br) {
		Employee employee = this.emp_Service.getEmpDetail(inf.getEmpnum());
		Item item = this.itemService.getItem(inf.getItmcode());

		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			List<Item> itemList = this.itemService.getList();

			model.addAttribute("empList", empList);
			model.addAttribute("itemList", itemList);
			return "Mm/inventory_form";
		}
		this.inventoryService.create(inf.getIndate(), item, inf.getInquantity(), employee);
		return "redirect:/inventory/list";
	}

	@GetMapping(value = "/detail/{inid}")
	public String detail(Model model, @PathVariable("inid") Integer inid) {
		Inventory inventory = this.inventoryService.getInventory(inid);
		model.addAttribute("inventory", inventory);
		return "Mm/inventory_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{inid}")
	public String inventoryModify(Model model, InventoryForm inf, @PathVariable("inid") Integer inid,
			Principal principal) {
		Inventory inventory = this.inventoryService.getInventory(inid);
		List<Employee> empList = this.emp_Service.getList();
		List<Item> itemList = this.itemService.getList();
		model.addAttribute("empList", empList);
		model.addAttribute("itemList", itemList);

		inf.setIndate(inventory.getIndate());
		inf.setItmcode(inventory.getItem().getItmId());
		inf.setEmpnum(inventory.getEmployee().getId());
		inf.setInquantity(inventory.getInquantity());
		return "Mm/inventory_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{inid}")
	public String inventoryModify(Model model, @Valid InventoryForm inf, BindingResult br,

			@PathVariable("inid") Integer INid, Principal principal) {
		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			List<Item> itemList = this.itemService.getList();
			model.addAttribute("empList", empList);
			model.addAttribute("itemList", itemList);
			return "Mm/inventory_form";
		}
		Inventory inventory = this.inventoryService.getInventory(INid);
		Employee employee = this.emp_Service.getEmpDetail(inf.getEmpnum());
		Item item = this.itemService.getItem(inf.getItmcode());

		{

			this.inventoryService.modify(inventory, inf.getIndate(), item, inf.getInquantity(), employee);
			return String.format("redirect:/inventory/list", INid);

		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{inid}")
	public String inventoryDelete(@PathVariable("inid") Integer INid, Principal principal) {
		Inventory inventory = this.inventoryService.getInventory(INid);


		this.inventoryService.delete(inventory);
		return "redirect:/inventory/list";
	}
}