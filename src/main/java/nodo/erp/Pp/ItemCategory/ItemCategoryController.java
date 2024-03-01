package nodo.erp.Pp.ItemCategory;

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

@RequestMapping("/basic/itemcategory")
@RequiredArgsConstructor
@Controller
public class ItemCategoryController {
	
	private final ItemCategoryService itemCategoryService;
	
	@GetMapping("/list")
    public String list(Model model) {
		List<ItemCategory> itemCategoryList = this.itemCategoryService.getList();
		model.addAttribute("itemCategoryList", itemCategoryList);
        return "Pp/itemcategory/itemcategory_list";
    }
	
	@GetMapping(value = "/detail/{IcId}")
    public String detail(Model model, @PathVariable("IcId") Integer IcId) {
        ItemCategory itemCategory = this.itemCategoryService.getItemCategory(IcId);
        model.addAttribute("itemCategory", itemCategory);
		return "Pp/itemcategory/itemcategory_detail";
    }
	
	@GetMapping("/create")
	public String ItemCategoryCreate(ItemCategoryForm itemCategoryForm) {
		List<ItemCategory> itemCategoryList = this.itemCategoryService.getList();
		if (itemCategoryList.size() == 0) {
			String NewIcCode = "IC-001";
			itemCategoryForm.setIcCode(NewIcCode);
		} else {
			String FinalIcCode = itemCategoryList.get(itemCategoryList.size()-1).getIcCode();
			int NewIcCodeNum = Integer.parseInt(FinalIcCode.substring(3)) + 1;
			String NewIcCode = "IC-" + String.format("%03d", NewIcCodeNum);
			itemCategoryForm.setIcCode(NewIcCode);
		}
		return "Pp/itemcategory/itemcategory_form";
	}
	
	@PostMapping("/create")
    public String ItemCategoryCreate(@Valid ItemCategoryForm itemCategoryForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "Pp/itemcategory/itemcategory_form";
        }
		this.itemCategoryService.create(itemCategoryForm.getIcCode(), itemCategoryForm.getIcName());
		
        return "redirect:/basic/itemcategory/list";
    }
	
	@GetMapping("/modify/{IcId}")
	public String ItemGroupModify(@PathVariable("IcId") Integer IcId, ItemCategoryForm itemCategoryForm, Model model) {
		ItemCategory itemCategory = this.itemCategoryService.getItemCategory(IcId);
		itemCategoryForm.setIcCode(itemCategory.getIcCode());
		itemCategoryForm.setIcName(itemCategory.getIcName());
		model.addAttribute("itemCategoryForm", itemCategory);
		return "Pp/itemcategory/itemcategory_modify";
	}
	
	@PostMapping("/modify/{IcId}")
	public String ItemCategoryModify(@Valid ItemCategoryForm itemCategoryForm, BindingResult bindingResult, @PathVariable("IcId") Integer IcId) {
		if (bindingResult.hasErrors()) {
            return "Pp/itemcategory/itemcategory_modify";
        }
		ItemCategory itemCategory = this.itemCategoryService.getItemCategory(IcId);
		this.itemCategoryService.modify(itemCategory, itemCategoryForm.getIcName());
		return "redirect:/basic/itemcategory/list";
	}
	
    @GetMapping("/delete/{IcId}")
    public String itemCategoryDelete(@PathVariable("IcId") Integer IcId) {
        ItemCategory itemCategory = this.itemCategoryService.getItemCategory(IcId);
        this.itemCategoryService.delete(itemCategory);
        return "redirect:/basic/itemcategory/list";
    }
	
}
