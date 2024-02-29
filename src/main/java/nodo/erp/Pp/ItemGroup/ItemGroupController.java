package nodo.erp.Pp.ItemGroup;

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

@RequestMapping("/basic/itemgroup")
@RequiredArgsConstructor
@Controller
public class ItemGroupController {
	
	private final ItemGroupService itemGroupService;
	
	@GetMapping("/list")
    public String list(Model model) {
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		model.addAttribute("itemGroupList", itemGroupList);
        return "Pp/itemgroup/itemgroup_list";
    }
	
	@GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        ItemGroup itemGroup = this.itemGroupService.getItemGroup(id);
        model.addAttribute("itemGroup", itemGroup);
		return "Pp/itemgroup/itemgroup_detail";
    }
	
	@GetMapping("/create")
	public String ItemGroupCreate(ItemGroupForm itemGroupForm) {
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		if (itemGroupList.size() == 0) {
			this.itemGroupService.create("IG-001", itemGroupForm.getIgName());
		} else {
			String FinalIgCode = itemGroupList.get(itemGroupList.size()-1).getIgCode();
			int NewIgCodeNum = Integer.parseInt(FinalIgCode.substring(3)) + 1;
			String NewIgCode = "IG-" + String.format("%03d", NewIgCodeNum);
			itemGroupForm.setIgCode(NewIgCode);
		}
		return "Pp/itemgroup/itemgroup_form";
	}
	
	@PostMapping("/create")
    public String ItemGroupCreate(@Valid ItemGroupForm itemGroupForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "Pp/itemgroup/itemgroup_form";
        }
		this.itemGroupService.create(itemGroupForm.getIgCode(), itemGroupForm.getIgName());
		
        return "redirect:/basic/itemgroup/list";
    }
	
	@GetMapping("/modify/{id}")
	public String ItemGroupModify(@PathVariable("id") Integer id, ItemGroupForm itemGroupForm, Model model) {
		ItemGroup itemGroup = this.itemGroupService.getItemGroup(id);
		itemGroupForm.setIgCode(itemGroup.getIgCode());
		itemGroupForm.setIgName(itemGroup.getIgName());
		model.addAttribute("itemGroupForm", itemGroup);
		return "Pp/itemgroup/itemgroup_modify";
	}
	
	@PostMapping("/modify/{id}")
	public String ItemGroupModify(@Valid ItemGroupForm itemGroupForm, BindingResult bindingResult, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
            return "itemgroup_form";
        }
		ItemGroup itemGroup = this.itemGroupService.getItemGroup(id);
		this.itemGroupService.modify(itemGroup, itemGroupForm.getIgName());
		return "redirect:/basic/itemgroup/list";
	}
	
    @GetMapping("/delete/{id}")
    public String itemGroupDelete(@PathVariable("id") Integer id) {
        ItemGroup itemGroup = this.itemGroupService.getItemGroup(id);
        this.itemGroupService.delete(itemGroup);
        return "redirect:/basic/itemgroup/list";
    }
	
}
