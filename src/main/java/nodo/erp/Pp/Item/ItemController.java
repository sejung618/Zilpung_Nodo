package nodo.erp.Pp.Item;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nodo.erp.Pp.ItemGroup.ItemGroup;
import nodo.erp.Pp.ItemGroup.ItemGroupForm;
import nodo.erp.Pp.ItemGroup.ItemGroupService;


@RequestMapping("/basic/item")
@RequiredArgsConstructor
@Controller
public class ItemController {
	
	private final ItemGroupService itemGroupService;
	private final ItemService itemService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Item> itemList = this.itemService.getList();
		model.addAttribute("itemList", itemList);
		return "Pp/item/item_list";
	}
	
	@GetMapping("/create")
	public String ItemCreate(ItemForm itemForm, Model model) {
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		model.addAttribute("itemGroupList", itemGroupList);
		return "Pp/item/item_form";
	}
	
	@PostMapping("/create")
	public String ItemCreate(@Valid ItemForm itemForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "Pp/item/item_form";
        }
		/*
		List<Item> itemList = this.itemService.getList();
		if (itemList.size() == 0) {
			this.itemService.create("ITM-001-001-001", itemForm.getItmName(), itemForm.getItmGroup(), itemForm.getItmCategory(), itemForm.getItmStandard(), itemForm.getItmSprice(), itemForm.getItmRprice());
		} else {
			String FinalItmCode = itemList.get(itemList.size()-1).getItmCode();
			int NewItmCodeNum = Integer.parseInt(FinalItmCode.substring(12)) + 1;
			String NewItmCode = "ITM-" + "001" + "001" + String.format("%03d", NewItmCodeNum);
			this.itemService.create(NewItmCode, itemForm.getItmName(), itemForm.getItmGroup(), itemForm.getItmCategory(), itemForm.getItmStandard(), itemForm.getItmSprice(), itemForm.getItmRprice());
		}		
		 */
        return "redirect:/basic/item/list";
	}
	
	@GetMapping("/modify/{ItmId}")
	public String ItemModify(@PathVariable("ItmId") Integer ItmId, ItemForm itemForm, Model model) {
		Item item = this.itemService.getItem(ItmId);
		itemForm.setItmCode(item.getItmCode());
		itemForm.setItmName(item.getItmName());
		itemForm.setItmGroup(item.getItmGroup());
		itemForm.setItmCategory(item.getItmCategory());
		itemForm.setItmStandard(item.getItmStandard());
		itemForm.setItmSprice(item.getItmSprice());
		itemForm.setItmRprice(item.getItmRprice());
		model.addAttribute("itemForm", item);
		return "Pp/item/item_modify";
	}
	
	@PostMapping("/modify/{ItmId}")
	public String ItemModify(@Valid ItemForm itemForm, BindingResult bindingResult, @PathVariable("ItmId") Integer ItmId) {
		if (bindingResult.hasErrors()) {
            return "item_form";
        }
		Item item = this.itemService.getItem(ItmId);
		this.itemService.modify(item, itemForm.getItmName(), itemForm.getItmGroup(), itemForm.getItmCategory(), itemForm.getItmStandard(), itemForm.getItmSprice(), itemForm.getItmRprice());
		return "redirect:/basic/item/list";
	}
	
    @GetMapping("/delete/{ItmId}")
    public String itemDelete(@PathVariable("ItmId") Integer ItmId) {
        Item item = this.itemService.getItem(ItmId);
        this.itemService.delete(item);
        return "redirect:/basic/item/list";
    }
	
}