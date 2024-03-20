package nodo.erp.Sd.Orders;

import java.util.List;
import java.util.stream.Collectors;

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
import nodo.erp.Sd.Purchase.PurService;
import nodo.erp.Sd.Purchase.Purchase;

@RequestMapping("/orders")
@RequiredArgsConstructor
@Controller
public class OrController {
	
	private final OrService orService;
	private final PurService purService;
	/*
	@GetMapping("/list")
	public String list(Model model) {
		List<Orders> OrList = this.orService.getList();
		model.addAttribute("OrList", OrList);
		return "Sd/Or_List";
	}
	*/
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "kw", defaultValue = "") String kw,
	        @RequestParam(value = "category", defaultValue = "") String category) {
		Page<Orders> paging = this.orService.searchAll(page, kw);
		
		if(category == null && category.isEmpty()) {
			paging = this.orService.searchAll(page, kw);
		} else if ("ordate".equals(category)) {
			paging = this.orService.findByOrdate(page, kw);
		} else if ("oritem".equals(category)) {
			paging = this.orService.findByOritem(page, kw);
		} else if ("orcompany".equals(category)) {
			paging = this.orService.findByOrcompany(page, kw);
		} else if ("ornum".equals(category)) {
			paging = this.orService.findByOrnum(page, kw);
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		
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
		this.orService.create(orCreateForm.getOrnum(), orCreateForm.getOrdate(),
				orCreateForm.getOritem(), orCreateForm.getOricode(),
				orCreateForm.getOrcompany(), Integer.parseInt(orCreateForm.getOrcount()),
				Integer.parseInt(orCreateForm.getOrprice()), Integer.parseInt(orCreateForm.getOrcp()),
				orCreateForm.getVat(), Integer.parseInt(orCreateForm.getOrvat()),
				Integer.parseInt(orCreateForm.getOrvatsum()), orCreateForm.getOrcode());
		return "redirect:/orders/list";
	}
	
	
	
}
