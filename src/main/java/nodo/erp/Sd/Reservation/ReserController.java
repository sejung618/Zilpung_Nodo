package nodo.erp.Sd.Reservation;

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
import nodo.erp.Mm.Inventory.Inventory;
import nodo.erp.Mm.Inventory.InventoryService;

@RequestMapping("/reservation")
@RequiredArgsConstructor
@Controller
public class ReserController {

	@Autowired
	private final ReserService rs;
	private final InventoryService invenService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Reservation> ResList = this.rs.getList();
		model.addAttribute("ResList", ResList);
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
	public String ResCreate(ResCreateForm resCreateForm) {
		List<Inventory> InvenList = this.invenService.getList();
		return "Sd/res_create";
	}
	
	@PostMapping("/create")
	public String ReservationCreate(@Valid ResCreateForm resCreateForm, BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			return "Sd/res_create";
		}
		
		this.rs.create(resCreateForm.getRV_Num(),
                resCreateForm.getRV_Date(),
                resCreateForm.getRV_Item(),
                resCreateForm.getRV_Icode(),
                Integer.valueOf(resCreateForm.getRV_Count()),
                Integer.valueOf(resCreateForm.getRV_Price()),
                Integer.valueOf(resCreateForm.getRV_CP()),
                Integer.valueOf(resCreateForm.getRV_VAT()),
                Integer.valueOf(resCreateForm.getRV_Sum()),
                resCreateForm.getRV_Pick(),
                null);
		return "redirect:/reservation/list";
		}
	
	//getmapping update
	@GetMapping("/update/{id}")
	public String ReserUpdate(ResUpdateForm resUpdateForm, @PathVariable("id") Integer id) {
		Reservation reser = this.rs.getReservation(id);
		
		resUpdateForm.setRV_Pick(reser.getRV_Pick());
		resUpdateForm.setRV_PTime(reser.getRV_PTime());
		
		return "Sd/res_update";
	}
	
	@PostMapping("/update/{id}")
	public  String ReservationUpdate(@PathVariable("id") Integer id, @Valid ResUpdateForm resUpdateForm, BindingResult bindResult) {
		Reservation reser = this.rs.getReservation(id);
		if(bindResult.hasErrors()) {
			return "Sd/res_update";
		}
		this.rs.update(reser, resUpdateForm.getRV_Pick(), resUpdateForm.getRV_PTime());
		return "redirect:/reservation/list";
	}
	
	@GetMapping("/delete/{id}")
	public String ReserDelete(@PathVariable("id") Integer id) {
		this.rs.delete(id);
		return "redirect:/reservation/list";
	}
	
	//search 기능구현 예정
	
}
