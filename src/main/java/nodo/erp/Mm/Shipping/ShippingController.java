package nodo.erp.Mm.Shipping;

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
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;
import nodo.erp.Sd.AccService;
import nodo.erp.Sd.Account;

@RequestMapping("/shipping")
@RequiredArgsConstructor
@Controller
public class ShippingController {

	private final ShippingService shippingService;
	private final Emp_Service emp_Service;
	private final AccService accService;
	private final ItemService itemService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "state", defaultValue = "") Shipping state) {

		Page<Shipping> paging = this.shippingService.getList(page, kw);

		if ("all".equals(state)) {
			paging = this.shippingService.State(page, state);
		}

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("state", state);
		
		return "Mm/shipping_list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String shippingCreate(Model model, ShippingForm shippingForm) {
		List<Employee> empList = this.emp_Service.getList();
		List<Account> accList = this.accService.getList();
		List<Item> itemList = this.itemService.getList();

		model.addAttribute("empList", empList);
		model.addAttribute("accList", accList);
		model.addAttribute("itemList", itemList);
		return "Mm/shipping_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String shippingCreate(Model model, @Valid ShippingForm shippingForm, BindingResult br) {
		Employee employee = this.emp_Service.getfindById(shippingForm.getEmpnum());
		Item item = this.itemService.getItem(shippingForm.getItmcode());
		Account acc = this.accService.getAccount(shippingForm.getAccode());

		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			List<Account> accList = this.accService.getList();
			List<Item> itemList = this.itemService.getList();

			model.addAttribute("empList", empList);
			model.addAttribute("accList", accList);
			model.addAttribute("itemList", itemList);
			return "Mm/shipping_form";
		}

		this.shippingService.create(shippingForm.getSpdate(), shippingForm.getSpdt(), shippingForm.getSpcamount(),
				shippingForm.getSplocation(), shippingForm.getSpstate(), employee, acc, item);
		return "redirect:/shipping/list";
	}

	@GetMapping(value = "/detail/{spid}")
	public String detail(Model model, @PathVariable("spid") Integer spid) {
		Shipping shipping = this.shippingService.getShipping(spid);
		model.addAttribute("shipping", shipping);
		return "Mm/shipping_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{spid}")
	public String shippingModify(Model model, ShippingForm sf, @PathVariable("spid") Integer spid,
			Principal principal) {
		Shipping shipping = this.shippingService.getShipping(spid);
		List<Employee> empList = this.emp_Service.getList();
		List<Account> accList = this.accService.getList();
		List<Item> itemList = this.itemService.getList();

		model.addAttribute("empList", empList);
		model.addAttribute("accList", accList);
		model.addAttribute("itemList", itemList);

		sf.setSpdate(shipping.getSpdate());
		sf.setSpdt(shipping.getSpdt());
		sf.setSpcamount(shipping.getSpcamount());
		sf.setSplocation(shipping.getSplocation());
		sf.setSpstate(shipping.getSpstate());
		sf.setItmcode(shipping.getItem().getItmId());
		sf.setEmpnum(shipping.getEmployee().getId());
		sf.setAccode(shipping.getAccount().getId());

		return "Mm/shipping_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{spid}")
	public String shippingModify(Model model, @Valid ShippingForm sf, BindingResult br,
			@PathVariable("spid") Integer spid, Principal principal) {
		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			List<Account> accList = this.accService.getList();
			List<Item> itemList = this.itemService.getList();

			model.addAttribute("empList", empList);
			model.addAttribute("accList", accList);
			model.addAttribute("itemList", itemList);

			return "Mm/shipping_form";
		}
		Shipping shipping = this.shippingService.getShipping(spid);
		Employee employee = this.emp_Service.getfindById(sf.getEmpnum());
		Account account = this.accService.getAccount(sf.getAccode());
		Item item = this.itemService.getItem(sf.getItmcode());

		{

			this.shippingService.modify(shipping, sf.getSpdate(), sf.getSpdt(), sf.getSpcamount(), sf.getSplocation(),
					sf.getSpstate(), employee, account, item);
			return String.format("redirect:/shipping/list", spid);

		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{spid}")
	public String shippingDelete(@PathVariable("spid") Integer spid, Principal principal) {
		Shipping shipping = this.shippingService.getShipping(spid);

		this.shippingService.delete(shipping);
		return "redirect:/shipping/list";
	}

}