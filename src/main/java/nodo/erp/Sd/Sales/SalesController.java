package nodo.erp.Sd.Sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;
import nodo.erp.Sd.Reservation.ReserService;
import nodo.erp.Sd.Reservation.Reservation;

@RequestMapping("/Sales")
@RequiredArgsConstructor
@Controller
public class SalesController {

	@Autowired
	private final SalesService SS;
	private final ItemService itemService;
	private final ReserService reserService;
	
	
	
	/*
	@GetMapping("/list")
	public String list(Model model) {
		List<Sales> SalesList = this.SS.getList();
		model.addAttribute("SalesList", SalesList);
		return "Sd/Sales_List";
	}
	*/
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Sales> SalesList = this.SS.getList();
		model.addAttribute("SalesList", SalesList);
		return "Sd/Sales_List";
	}
	
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Sales sales = this.SS.getSales(id);
		model.addAttribute(sales);
		return "Sd/Sal_detail";
	}
	
	@GetMapping("/create")
	public String SLCreate(Model model, SalesCreateForm salesCreateForm) {
		List<Item> ItemList = this.itemService.getList();
		List<Reservation> ReserList = this.reserService.getList(); 
		model.addAttribute("ItemList", ItemList);
		model.addAttribute("ReserList", ReserList);
		return "Sd/sal_create";
	}
	
	@PostMapping("/create")
	public String SalesCreate(@Valid SalesCreateForm salesCreateForm, BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			return "Sd/sal_create";
		}
		this.SS.create(salesCreateForm.getSanum(), salesCreateForm.getSamehod(), salesCreateForm.getSadate(), salesCreateForm.getSaitem(), salesCreateForm.getSaicode(), Integer.parseInt(salesCreateForm.getSacount()), Integer.parseInt(salesCreateForm.getSaprice()), Integer.parseInt(salesCreateForm.getSacp()), Integer.parseInt(salesCreateForm.getSavat()), Integer.parseInt(salesCreateForm.getSasum()));
		return "redirect:/sales/list";
	}
	
	@GetMapping("/delete/{id}")
	public String SalesDelete(@PathVariable("id") Integer id) {
		this.SS.delete(id);
		return "redirect:/sales/list";
	}
	
}
