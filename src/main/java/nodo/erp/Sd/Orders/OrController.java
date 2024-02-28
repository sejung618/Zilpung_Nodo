package nodo.erp.Sd.Orders;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Sd.Purchase.PurService;
import nodo.erp.Sd.Purchase.Purchase;

@RequestMapping("/orders")
@RequiredArgsConstructor
@Controller
public class OrController {
	
	private final OrService orService;
	private final PurService purService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Orders> OrList = this.orService.getList();
		model.addAttribute("OrList", OrList);
		return "Sd/Or_List";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Orders or = this.orService.getOrders(id);
		model.addAttribute("or", or);
		return "Sd/Or_detail";
	}
	
	@GetMapping("/create")
	public String OrCreate(Model model, OrCreateForm orCreateForm) {
		List<Purchase> PurList = this.purService.getList();
		model.addAttribute("PurList", PurList);
		
		return "Sd/or_create";
	}
	
	@PostMapping("/create")
	public String OrdersCreate(@Valid OrCreateForm orCreateForm, BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			return "Sd/or_create";
		}
		this.orService.create(orCreateForm.getOR_Num(), orCreateForm.getOR_Date(),
				orCreateForm.getOR_Item(), orCreateForm.getOR_Icode(),
				orCreateForm.getOR_Company(), Integer.parseInt(orCreateForm.getOR_Count()),
				Integer.parseInt(orCreateForm.getOR_Price()), Integer.parseInt(orCreateForm.getOR_CP()),
				orCreateForm.getVAT(), Integer.parseInt(orCreateForm.getOR_VAT()),
				Integer.parseInt(orCreateForm.getOR_VATSUM()), orCreateForm.getOR_Pay());
		return "redirect:/orders/list";
	}
	
}
