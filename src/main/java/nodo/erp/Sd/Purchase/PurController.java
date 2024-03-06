package nodo.erp.Sd.Purchase;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nodo.erp.Sd.AccService;
import nodo.erp.Sd.Account;

@RequestMapping("/purchase")

@RequiredArgsConstructor
@Controller
public class PurController {
	
	private final PurService purService;
	private final AccService accService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Purchase> PurList = this.purService.getList();
		model.addAttribute("PurList", PurList);
		return "Sd/Pur_List";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Purchase pur = this.purService.getPurchase(id);
		model.addAttribute("pur", pur);
		return "Sd/Pur_detail";
	}
	
	@GetMapping("/create")
	public String PurCreate(Model model, PurCreateForm purCreateForm) {
		List<Account> AccList = this.accService.getList();
		model.addAttribute("AccList", AccList);
		
		return "Sd/pur_create";
	}
		
	@PostMapping("/create")
	public String PurchaseCreate(@Valid PurCreateForm purCreateForm, BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			return "Sd/pur_create";
		}
		this.purService.create(purCreateForm.getPcnum(), purCreateForm.getPcdate(), purCreateForm.getPccode(), purCreateForm.getPccompany(), purCreateForm.getPcitem(), purCreateForm.getPcicode(), Integer.parseInt(purCreateForm.getPccount()), Integer.parseInt(purCreateForm.getPcprice()), Integer.parseInt(purCreateForm.getPccp()) , Integer.parseInt(purCreateForm.getPcvat()), Integer.parseInt(purCreateForm.getPcvatsum()));
		return "redirect:/purchase/list";
	}
	
	@GetMapping("/delete/{id}")
	public String PurDelete(@PathVariable("id") Integer id) {
		this.purService.delete(id);
		return "redirect:/purchase/list";
	}

}
