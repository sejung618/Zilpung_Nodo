package nodo.erp.Sd.Reservation;

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
import nodo.erp.Mm.Inventory.Inventory;
import nodo.erp.Mm.Inventory.InventoryService;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;
import nodo.erp.Sd.Purchase.Purchase;

@RequestMapping("/reservation")
@RequiredArgsConstructor
@Controller
public class ReserController {

	@Autowired
	private final ReserService rs;
	private final ItemService itemService;
	private final InventoryService invenService;
	/*
	@GetMapping("/list")
	public String list(Model model) {
		List<Reservation> ResList = this.rs.getList();
		model.addAttribute("ResList", ResList);
		return "Sd/Res_List";
	}
	*/
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "kw", defaultValue = "") String kw,
	        @RequestParam(value = "category", defaultValue = "") String category) {
		Page<Reservation> paging = this.rs.searchAll(page, kw);
		
		int totalPrice = 0;
	    for(Reservation reservation : paging) {
	    	totalPrice += reservation.getRvsum();
	    }
	    
	    int totalQuantity = 0;
		for(Reservation reservation : paging) {
			totalQuantity += reservation.getRvcount();
		}
		
		if ("rvitem".equals(category)) {
			paging = this.rs.findByRvitem(page, kw);
		} else if ("rvnum".equals(category)) {
			paging = this.rs.findByRvnum(page, kw);
		} else {
			paging = this.rs.searchAll(page, kw);
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalQuantity", totalQuantity);
		
		return "Sd/Res_List";
	}
	
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Reservation reser = this.rs.getReservation(id);
		model.addAttribute("reser", reser);
		return "Sd/Res_detail";
	}
	
	// 예약 신청
	@GetMapping("/create")
	public String ResCreate(Model model, ResCreateForm resCreateForm) {
		List<Item> ItemList = this.itemService.getList();
		//List<Inventory> InvenList = this.invenService.getList();
		model.addAttribute("ItemList", ItemList);
		//model.addAttribute("InvenList", InvenList);
		return "Sd/res_create";
	}
	
	@PostMapping("/create")
	public String ReservationCreate(@Valid ResCreateForm resCreateForm, BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			return "Sd/res_create";
		}
		
		this.rs.create(resCreateForm.getRvnum(),
                resCreateForm.getRvdate(),
                resCreateForm.getRvitem(),
                resCreateForm.getRvicode(),
                Integer.parseInt(resCreateForm.getRvcount()),
                Integer.parseInt(resCreateForm.getRvprice()),
                Integer.parseInt(resCreateForm.getRvcp()),
                Integer.parseInt(resCreateForm.getRvvat()),
                Integer.parseInt(resCreateForm.getRvsum()),
                resCreateForm.getRvpick(),
                null);
		return "redirect:/reservation/list";
		}


	@GetMapping("/update/{id}")
	public String ReserUpdate(ResUpdateForm resUpdateForm, @PathVariable("id") Integer id) {
		Reservation reser = this.rs.getReservation(id);
		
		resUpdateForm.setRvpick(reser.getRvpick());
		resUpdateForm.setRvptime(reser.getRvptime());
		
		
		return "Sd/res_update";
	}
	
	@PostMapping("/update/{id}")
	public  String ReservationUpdate(@PathVariable("id") Integer id, @Valid ResUpdateForm resUpdateForm, BindingResult bindResult) {
		Reservation reser = this.rs.getReservation(id);
		if(bindResult.hasErrors()) {
			return "Sd/res_update";
		}
		this.rs.update(reser, resUpdateForm.getRvpick(), resUpdateForm.getRvptime());
		return "redirect:/reservation/list";
	}
	
	@GetMapping("/delete/{id}")
	public String ReserDelete(@PathVariable("id") Integer id) {
		this.rs.delete(id);
		return "redirect:/reservation/list";
	}
	
	//search 기능구현 예정
	
}
