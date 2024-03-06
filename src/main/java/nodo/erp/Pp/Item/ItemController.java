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
	public String list(Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "st", defaultValue = "") String st,
			@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Item> paging = this.itemService.getList(page, st, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("st", st);
		model.addAttribute("kw", kw);
		return "Pp/item/item_list";
	}
	
	@GetMapping("/create")
	public String ItemCreate(ItemForm itemForm, Model model) {
		List<ItemGroup> itemGroupList = this.itemService.getIgList();
		List<ItemCategory> itemCategoryList = this.itemService.getIcList();
		model.addAttribute("itemGroupList", itemGroupList);
		model.addAttribute("itemCategoryList", itemCategoryList);
		return "Pp/item/item_form";
	}
	
	@PostMapping("/create")
	public String ItemCreate(@Valid ItemForm itemForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<ItemGroup> itemGroupList = this.itemService.getIgList();
			List<ItemCategory> itemCategoryList = this.itemService.getIcList();
			model.addAttribute("itemGroupList", itemGroupList);
			model.addAttribute("itemCategoryList", itemCategoryList);
            return "Pp/item/item_form";
        }
		this.itemService.create(itemForm.getItmName(), itemForm.getItmGroup(), itemForm.getItmCategory(), itemForm.getItmStandard(), itemForm.getItmSprice(), itemForm.getItmRprice());
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
		itemForm.setItmGroup(item.getItmGroup().getIgId());
		itemForm.setItmCategory(item.getItmCategory().getIcId());
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