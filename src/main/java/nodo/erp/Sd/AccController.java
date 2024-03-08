package nodo.erp.Sd;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping("/account")
@RequiredArgsConstructor
@Controller
public class AccController {
	
	@Autowired
	private final AccService accService;
	
	
	/*
	@GetMapping("/list")
	public String list(Model model) {
		List<Account> AccList = this.accService.getList();
		model.addAttribute("AccList", AccList);
		return "Sd/Acc_List";
	}
	*/
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "category", defaultValue = "") String category) {
		Page<Account> paging = this.accService.getList(page, kw);
		//Page<Account> paging = this.accService.searchAll(page, kw);
		
		
		if(category == null && category.isEmpty()) {
			paging = this.accService.searchAll(page, kw);
		}
		if ("acaddress".equals(category)) {
			paging = this.accService.findByAcaddress(page, kw);
		}
		if ("acitem".equals(category)) {
			paging = this.accService.findByAcitem(page, kw);
		}
		if ("acdate".equals(category)) {
			paging = this.accService.findByAcdate(page, kw);
		}
		if ("accompany".equals(category)) {
			paging = this.accService.findByAccompany(page, kw);
		}
		
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		
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
		this.accService.create(accCreateForm.getAccode(), accCreateForm.getAccompany(), accCreateForm.getAcaddress(), accCreateForm.getAcname(), accCreateForm.getAcphone(), accCreateForm.getAcitem(), accCreateForm.getAcicode(), accCreateForm.getVat(), accCreateForm.getAcdate(), Integer.parseInt(accCreateForm.getAcprice()), accCreateForm.getAcnum());
		return "redirect:/account/list";
	}
	
	@GetMapping("/update/{id}")
	public String AccUpdate(AccUpdateForm accUpdateForm, @PathVariable("id") Integer id) {
		Account account = this.accService.getAccount(id);
		
		accUpdateForm.setAccompany(account.getAccompany());
		accUpdateForm.setAcaddress(account.getAcaddress());
		accUpdateForm.setAcname(account.getAcname());
		accUpdateForm.setAcphone(account.getAcphone());
		accUpdateForm.setAcprice(account.getAcprice());
		
		return "Sd/acc_update";
	}
	
	@PostMapping("/update/{id}")
	public String AccountUpdate(@PathVariable("id") Integer id, @Valid AccUpdateForm accUpdateForm, BindingResult bindResult) {
		Account account = this.accService.getAccount(id);
		if(bindResult.hasErrors()) {
			return "Sd/acc_update";
		}
		this.accService.update(account, accUpdateForm.getAccompany(), accUpdateForm.getAcaddress(), accUpdateForm.getAcname(), accUpdateForm.getAcphone(), accUpdateForm.getAcprice());
		return "redirect:/account/list";
	}
	
	@GetMapping("/delete/{id}")
	public String AccDelete(@PathVariable("id") Integer id) {
		this.accService.delete(id);
		return "redirect:/account/list";
	}
}
