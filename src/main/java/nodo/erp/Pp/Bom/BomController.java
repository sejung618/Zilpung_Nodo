package nodo.erp.Pp.Bom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemService;

@RequestMapping("/BOM")
@RequiredArgsConstructor
@Controller
public class BomController {
	
	private final ItemService itemService;
	private final BomService bomService;
	
	@GetMapping("/list")
	public String list(Model model,
			@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Bom> paging = this.bomService.getList(page);
		model.addAttribute("paging", paging);
		return "Pp/bom/bom_list";
	}
	
	@GetMapping("/create")
	public String BomCreate(BomForm bomForm, Model model) {
		List<Item> itemList = this.itemService.getList();
		List<Bom> bomList = this.bomService.getList();
		model.addAttribute("itemList", itemList);
		model.addAttribute("bomList", bomList);
		return "Pp/bom/bom_form";
	}
//	
//	@PostMapping("/create")
//	public String BomCreate(@Valid BomForm bomForm, BindingResult bindingResult, Model model) {
//		if (bindingResult.hasErrors()) {
//			List<Item> itemList = this.itemService.getList();
//			model.addAttribute("itemList", itemList);
//            return "Pp/bom/bom_form";
//        }
//		this.bomService.create(bomForm.getBpItemId(), bomForm.getBpCount(), bomForm.getBpHour());
//        return "redirect:/BOM/list";
//	}
//	
}
