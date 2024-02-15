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
	
	@GetMapping("/SD/Account/List")
	public String list(Model model) {
		List<Account> AccList = this.accountService.getList();
		model.addAttribute("AccList", AccList);
		return "Account_List";
	}
}
