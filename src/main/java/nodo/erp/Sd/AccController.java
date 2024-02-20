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
	public String detail(Model model, @PathVariable("id") String id) {
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
	
	@GetMapping("/update/{AC_Code}")
	public String AccUpdate(Model model, AccUpdateForm accUpdateForm, @PathVariable("AC_Code") String AC_Code) {
		Account account = accService.getAccount(AC_Code);
		model.addAttribute("account", account);
		return "Sd/acc_update";
	}
	
	@PostMapping("/update/{AC_Code}")
	public String AccountUpdate(@PathVariable("AC_Code") String AC_Code, @Valid Account account) {
		this.accService.update(AC_Code, account.getAC_Company(), account.getAC_Address(), account.getAC_Name(), account.getAC_Phone(), account.getAC_Item(), account.getAC_Icode(), account.getVAT(), account.getAC_Date(), account.getAC_Price(), account.getAC_Num());
		return "redirect:/account/list";
	}
	
	@GetMapping("/delete/{AC_Code}")
	public String AccDelete(@PathVariable("AC_Code") String AC_Code) {
		this.accService.delete(AC_Code);
		return "redirect:/account/list";
	}
}
