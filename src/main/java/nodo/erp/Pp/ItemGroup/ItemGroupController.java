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
	
	@GetMapping(value = "/detail/{IgId}")
    public String detail(Model model, @PathVariable("IgId") Integer IgId) {
        ItemGroup itemGroup = this.itemGroupService.getItemGroup(IgId);
        model.addAttribute("itemGroup", itemGroup);
		return "Pp/itemgroup/itemgroup_detail";
    }
	
	@GetMapping("/create")
	public String ItemGroupCreate(ItemGroupForm itemGroupForm) {
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		if (itemGroupList.size() == 0) {
			String NewIgCode = "IG-001";
			itemGroupForm.setIgCode(NewIgCode);
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
	
	@GetMapping("/modify/{IgId}")
	public String ItemGroupModify(@PathVariable("IgId") Integer IgId, ItemGroupForm itemGroupForm, Model model) {
		ItemGroup itemGroup = this.itemGroupService.getItemGroup(IgId);
		itemGroupForm.setIgCode(itemGroup.getIgCode());
		itemGroupForm.setIgName(itemGroup.getIgName());
		model.addAttribute("itemGroupForm", itemGroup);
		return "Pp/itemgroup/itemgroup_modify";
	}
	
	@PostMapping("/modify/{IgId}")
	public String ItemGroupModify(@Valid ItemGroupForm itemGroupForm, BindingResult bindingResult, @PathVariable("IgId") Integer IgId) {
		if (bindingResult.hasErrors()) {
            return "Pp/itemgroup/itemgroup_modify";
        }
		ItemGroup itemGroup = this.itemGroupService.getItemGroup(IgId);
		this.itemGroupService.modify(itemGroup, itemGroupForm.getIgName());
		return "redirect:/basic/itemgroup/list";
	}
	
    @GetMapping("/delete/{IgId}")
    public String itemGroupDelete(@PathVariable("IgId") Integer IgId) {
        ItemGroup itemGroup = this.itemGroupService.getItemGroup(IgId);
        this.itemGroupService.delete(itemGroup);
        return "redirect:/basic/itemgroup/list";
    }
	
}
