package nodo.erp.Pp.Item;

import java.util.List;

import org.springframework.data.domain.Page;
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
import nodo.erp.Pp.ItemCategory.ItemCategory;
import nodo.erp.Pp.ItemCategory.ItemCategoryService;
import nodo.erp.Pp.ItemGroup.ItemGroup;
import nodo.erp.Pp.ItemGroup.ItemGroupService;


@RequestMapping("/basic/item")
@RequiredArgsConstructor
@Controller
public class ItemController {
	
	private final ItemCategoryService itemCategoryService;
	private final ItemGroupService itemGroupService;
	private final ItemService itemService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Item> paging = this.itemService.getList(page);
		model.addAttribute("paging", paging);
		return "Pp/item/item_list";
	}
	
	@GetMapping("/create")
	public String ItemCreate(ItemForm itemForm, Model model) {
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		List<ItemCategory> itemCategoryList = this.itemCategoryService.getList();
		model.addAttribute("itemGroupList", itemGroupList);
		model.addAttribute("itemCategoryList", itemCategoryList);
		return "Pp/item/item_form";
	}
	
	@PostMapping("/create")
	public String ItemCreate(@Valid ItemForm itemForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<ItemGroup> itemGroupList = this.itemGroupService.getList();
			List<ItemCategory> itemCategoryList = this.itemCategoryService.getList();
			model.addAttribute("itemGroupList", itemGroupList);
			model.addAttribute("itemCategoryList", itemCategoryList);
            return "Pp/item/item_form";
        }
		List<Item> itemList = this.itemService.getList();
		if (itemList.size() == 0) {
			int IgCodeNum = Integer.parseInt( (itemGroupService.getIgCodeIdByName(itemForm.getItmGroup())).substring(3));
			int IcCodeNum = Integer.parseInt( (itemCategoryService.getIcCodeIdByName(itemForm.getItmCategory())).substring(3));
			String NewItmCode = "ITM-" + String.format("%03d", IgCodeNum) + "-" + String.format("%03d", IcCodeNum) + "-001";
			this.itemService.create(NewItmCode, itemForm.getItmName(), itemForm.getItmGroup(), itemForm.getItmCategory(), itemForm.getItmStandard(), itemForm.getItmSprice(), itemForm.getItmRprice());
		} else {
			String FinalItmCode = itemList.get(itemList.size()-1).getItmCode();
			int IgCodeNum = Integer.parseInt( (itemGroupService.getIgCodeIdByName(itemForm.getItmGroup())).substring(3));
			int IcCodeNum = Integer.parseInt( (itemCategoryService.getIcCodeIdByName(itemForm.getItmCategory())).substring(3));
			int NewItmCodeNum = Integer.parseInt(FinalItmCode.substring(12)) + 1;
			String NewItmCode = "ITM-" + String.format("%03d", IgCodeNum) + "-" + String.format("%03d", IcCodeNum) + "-" + String.format("%03d", NewItmCodeNum);
			this.itemService.create(NewItmCode, itemForm.getItmName(), itemForm.getItmGroup(), itemForm.getItmCategory(), itemForm.getItmStandard(), itemForm.getItmSprice(), itemForm.getItmRprice());
		}
        return "redirect:/basic/item/list";
	}
	
	@GetMapping("/detail/{ItmId}")
	public String ItemDetail(@PathVariable("ItmId") Integer ItmId, Model model) {
		Item item = this.itemService.getItem(ItmId);
		model.addAttribute("item", item);
		return "Pp/item/item_detail";
	}	
	
	@GetMapping("/modify/{ItmId}")
	public String ItemModify(@PathVariable("ItmId") Integer ItmId, ItemForm itemForm, Model model) {
		Item item = this.itemService.getItem(ItmId);
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		List<ItemCategory> itemCategoryList = this.itemCategoryService.getList();
		model.addAttribute("itemGroupList", itemGroupList);
		model.addAttribute("itemCategoryList", itemCategoryList);
		itemForm.setItmCode(item.getItmCode());
		itemForm.setItmName(item.getItmName());
		itemForm.setItmGroup(item.getItmGroup());
		itemForm.setItmCategory(item.getItmCategory());
		itemForm.setItmStandard(item.getItmStandard());
		itemForm.setItmSprice(item.getItmSprice());
		itemForm.setItmRprice(item.getItmRprice());
		return "Pp/item/item_modify";
	}
	
	@PostMapping("/modify/{ItmId}")
	public String ItemModify(@Valid ItemForm itemForm, BindingResult bindingResult, @PathVariable("ItmId") Integer ItmId) {
		if (bindingResult.hasErrors()) {
            return "Pp/item/item_modify";
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