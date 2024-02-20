package nodo.erp.Sd;

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

@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
public class AccController {
	
	@Autowired
	private final AccService accService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Account> AccList = this.accService.getList();
		model.addAttribute("AccList", AccList);
		return "Sd/Acc_List";
	}
	
	@GetMapping(value = "/detail/{id}") // AC_Code >> id
	public String detail(Model model, @PathVariable("id") Integer id) {
		Account account = this.accService.getAccount(id);
		model.addAttribute("account", account);
		return "Sd/Acc_detail";
	}
	
	@GetMapping("/create")
	public String AccCreate(AccCreateForm accCreateForm) {
		return "Sd/acc_create";
	}
	
	@PostMapping("/create")
	public String AccountCreate(@Valid AccCreateForm accCreateForm, BindingResult bindResult) {
		if(bindResult.hasErrors()) {
			return "Sd/acc_create";
		}
		this.accService.create(accCreateForm.getAC_Code(), accCreateForm.getAC_Company(), accCreateForm.getAC_Address(), accCreateForm.getAC_Name(), accCreateForm.getAC_Phone(), accCreateForm.getAC_Item(), accCreateForm.getAC_Icode(), accCreateForm.getVAT(), accCreateForm.getAC_Date(), Integer.parseInt(accCreateForm.getAC_Price()), accCreateForm.getAC_Num());
		return "redirect:/account/list";
	}
	
	@GetMapping("/update/{id}")
	public String AccUpdate(AccUpdateForm accUpdateForm, @PathVariable("id") Integer id) {
		Account account = this.accService.getAccount(id);
		
		accUpdateForm.setAC_Company(account.getAC_Company());
		accUpdateForm.setAC_Address(account.getAC_Address());
		accUpdateForm.setAC_Name(account.getAC_Name());
		accUpdateForm.setAC_Phone(account.getAC_Phone());
		accUpdateForm.setAC_Price(account.getAC_Price());
		
		return "Sd/acc_update";
	}
	
	@PostMapping("/update/{id}")
	public String AccountUpdate(@PathVariable("id") Integer id, @Valid AccUpdateForm accUpdateForm, BindingResult bindResult) {
		Account account = this.accService.getAccount(id);
		if(bindResult.hasErrors()) {
			return "Sd/acc_update";
		}
		this.accService.update(account, accUpdateForm.getAC_Company(), accUpdateForm.getAC_Address(), accUpdateForm.getAC_Name(), accUpdateForm.getAC_Phone(), accUpdateForm.getAC_Price());
		return "redirect:/account/list";
	}
	
	@GetMapping("/delete/{id}")
	public String AccDelete(@PathVariable("id") Integer id) {
		this.accService.delete(id);
		return "redirect:/account/list";
	}
}
