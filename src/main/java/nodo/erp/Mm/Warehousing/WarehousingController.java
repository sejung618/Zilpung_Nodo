package nodo.erp.Mm.Warehousing;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Hr.CustomUserDetails;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;
import nodo.erp.Sd.AccService;
import nodo.erp.Sd.Account;


@RequestMapping("/warehousing")
@RequiredArgsConstructor
@Controller
public class WarehousingController {
	
	private final WarehousingService warehousingService;
	private final Emp_Service emp_Service;
	private final AccService accService;
	private final ItemService itemService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Warehousing> paging = this.warehousingService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "Mm/warehousing_list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String warehousingCreate(Model model, WarehousingForm warehousingForm){
		List<Employee> empList = this.emp_Service.getList();
		List<Account> accList = this.accService.getList();
		List<Item> itemList = this.itemService.getList();

		model.addAttribute("empList", empList);
		model.addAttribute("accList", accList);
		model.addAttribute("itemList", itemList);
		return "Mm/warehousing_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String warehousingCreate(Model model, @Valid WarehousingForm warehousingForm, BindingResult br) {
		Employee employee = this.emp_Service.getfindById(warehousingForm.getEmpnum());
		Item item = this.itemService.getItem(warehousingForm.getItmcode());
		Account acc = this.accService.getAccount(warehousingForm.getAccode());
		if (br.hasErrors()) {
			List<Employee> empList = this.emp_Service.getList();
			List<Account> accList = this.accService.getList();
			List<Item> itemList = this.itemService.getList();

			model.addAttribute("empList", empList);
			model.addAttribute("accList", accList);
			model.addAttribute("itemList", itemList);
			return "Mm/warehousing_form"; 
		}
		
		this.warehousingService.create(warehousingForm.getWhdate(), warehousingForm.getWhdt(), warehousingForm.getWhcamount(), 
				warehousingForm.getWhlocation(), warehousingForm.getWhstate(), employee, acc, item);
		return "redirect:/warehousing/list";
	}
	
	@GetMapping(value = "/detail/{whid}")
	public String detail(Model model, @PathVariable("whid") Integer whid) {
		Warehousing warehousing = this.warehousingService.getWarehousing(whid);
		model.addAttribute("warehousing", warehousing);
		return "Mm/warehousing_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{whid}")
	public String warehousingModify(Model model, WarehousingForm wf, @PathVariable("whid") Integer whid, Principal principal) {
		Warehousing warehousing = this.warehousingService.getWarehousing(whid);
		List<Employee> empList = this.emp_Service.getList();
		List<Account> accList = this.accService.getList();
		List<Item> itemList = this.itemService.getList();

		model.addAttribute("empList", empList);
		model.addAttribute("accList", accList);
		model.addAttribute("itemList", itemList);
		
		wf.setWhdate(warehousing.getWhdate());
		wf.setWhdt(warehousing.getWhdt());
		wf.setWhcamount(warehousing.getWhcamount());
		wf.setWhlocation(warehousing.getWhlocation());
		wf.setWhstate(warehousing.getWhstate());
		wf.setItmcode(warehousing.getItem().getItmId());
		wf.setEmpnum(warehousing.getEmployee().getId());
		wf.setAccode(warehousing.getAccount().getId());
		return "Mm/warehousing_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{whid}")
    public String warehousingModify(Model model, @Valid WarehousingForm wf, BindingResult br, @PathVariable("whid") Integer whid, Principal principal) {
        if (br.hasErrors()) {
        	List<Employee> empList = this.emp_Service.getList();
    		List<Account> accList = this.accService.getList();
    		List<Item> itemList = this.itemService.getList();

    		model.addAttribute("empList", empList);
    		model.addAttribute("accList", accList);
    		model.addAttribute("itemList", itemList);
            return "Mm/warehousing_form";
        }
        Warehousing warehousing = this.warehousingService.getWarehousing(whid);
        Employee employee = this.emp_Service.getfindById(wf.getEmpnum());
		Item item = this.itemService.getItem(wf.getItmcode());
		Account account = this.accService.getAccount(wf.getAccode());{
    	
        this.warehousingService.modify(warehousing, wf.getWhdate(), wf.getWhdt(),wf.getWhcamount(),wf.getWhlocation(),wf.getWhstate(), employee, account, item);
        return String.format("redirect:/warehousing/list", whid);
    
        }
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{whid}")
    public String warehousingDelete(@PathVariable("whid") Integer whid, 
            Principal principal) {
		Warehousing warehousing = this.warehousingService.getWarehousing(whid);
		
        this.warehousingService.delete(warehousing);
        return "redirect:/warehousing/list";
    }
}