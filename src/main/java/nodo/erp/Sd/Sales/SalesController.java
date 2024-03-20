package nodo.erp.Sd.Sales;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;
import nodo.erp.Sd.Reservation.ReserService;
import nodo.erp.Sd.Reservation.Reservation;

@RequestMapping("/Sales")
@RequiredArgsConstructor
@Controller
public class SalesController {

	@Autowired
	private final SalesService ss;
	private final ItemService itemService;
	private final ReserService reserService;
	
	
	
	/*
	@GetMapping("/list")
	public String list(Model model) {
		List<Sales> SalesList = this.ss.getList();
		model.addAttribute("SalesList", SalesList);
		return "Sd/Sales_List";
	}
	*/
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "kw", defaultValue = "") String kw,
	        @RequestParam(value = "category", defaultValue = "") String category) {
		Page<Sales> paging = this.ss.searchAll(page, kw);
		
		int totalPrice = 0;
		for(Sales sales : paging) {
			totalPrice += sales.getSasum();
		}
		
		int totalQuantity = 0;
		for(Sales sales : paging) {
			totalQuantity += sales.getSacount();
		}
		
		if ("sanum".equals(category)) {
			paging = this.ss.findBySanum(page, kw);
		} else if ("saitem".equals(category)) {
			paging = this.ss.findBySaitem(page, kw);
		} else if ("samethod".equals(category)) {
			paging = this.ss.findBySamethod(page, kw);
		} else {
			paging = this.ss.searchAll(page, kw);
		}
		
		
		
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalQuantity", totalQuantity);
		
		return "Sd/Sales_List";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Sales sales = this.ss.getSales(id);
		model.addAttribute("sales", sales);
		return "Sd/Sal_detail";
	}
	
	@GetMapping("/create")
	public String SLCreate(Model model, SalesCreateForm salesCreateForm) {
		List<Item> ItemList = this.itemService.getList();
		model.addAttribute("ItemList", ItemList);
		return "Sd/sal_create";
	}
	
	@PostMapping("/create")
	public String SalesCreate(@Valid SalesCreateForm salesCreateForm, BindingResult bindResult) {
	    if (bindResult.hasErrors()) {
	        return "Sd/sal_create";
	    }

	    // 필드 값 가져오기
	    String sanum = salesCreateForm.getSanum();
	    String samethod = salesCreateForm.getSamethod();
	    LocalDate sadate = LocalDate.now(); //salesCreateForm.getSadate();
	    String saitem = salesCreateForm.getSaitem();
	    String saicode = salesCreateForm.getSaicode();
	    Integer sacount = Integer.parseInt(salesCreateForm.getSacount());
	    Integer saprice = Integer.parseInt(salesCreateForm.getSaprice());
	    Integer sacp = Integer.parseInt(salesCreateForm.getSacp());
	    Integer savat = Integer.parseInt(salesCreateForm.getSavat());
	    Integer sasum = Integer.parseInt(salesCreateForm.getSasum());

	    // 필요한 로직 수행

	    // 예시로 로직 수행 대신에 서비스 클래스의 create 메서드 호출
	    this.ss.create(sanum, samethod, sadate, saitem, saicode, sacount, saprice, sacp, savat, sasum);

	    return "redirect:/Sales/list";
	}
	
	@GetMapping("/delete/{id}")
	public String SalesDelete(@PathVariable("id") Integer id) {
		this.ss.delete(id);
		return "redirect:/Sales/list";
	}
	
}
