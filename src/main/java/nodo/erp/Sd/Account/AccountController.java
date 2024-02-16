package nodo.erp.Sd.Account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AccountController {

	@Autowired
	private final AccountService accountService;
	
	@GetMapping("/zilpung/SD/account")
	public String list(Model model) {
		List<Account> AccList = this.accountService.getList();
		model.addAttribute("AccList", AccList);
		return "Sd/Account_List";
	}
	
	@GetMapping(value = "/zilpung/SD/account/detail{AC_Code}")
	public String detail(Model model, @PathVariable("AC_Code") String AC_Code) {
		Account account = this.accountService.getAccount(AC_Code);
		model.addAttribute("account", account);
		return "Sd/Account_Detail";
	}
	
	
	
	@GetMapping("/zilpung/SD/account/create")
	public String AccCreate(AccountCreateForm accCreateForm) {
		return "Sd/Account_Create";
	}
	
	@PostMapping("/zilpung/SD/account/create")
	public String AccountCreate(AccountCreateForm accCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "Sd/Account_Create";
		}
		this.accountService.create(accCreateForm.getAC_Code(), accCreateForm.getAC_Company(), accCreateForm.getAC_Address(), accCreateForm.getAC_Name(),
				accCreateForm.getAC_Phone(), accCreateForm.getAC_Item(), accCreateForm.getAC_Icode(), 10.0f, accCreateForm.getAC_Date(), Integer.parseInt(accCreateForm.getAC_Price()), accCreateForm.getAC_Num());
		return "redirect:/zilpung/SD/account";
	}
	
	
}
