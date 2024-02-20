package nodo.erp.Pd;

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
        return "Pd/itemgroup_list";
    }
	
	@GetMapping(value = "/detail/{IgCode}")
    public String detail(Model model, @PathVariable("IgCode") String IgCode) {
        ItemGroup itemGroup = this.itemGroupService.getItemGroup(IgCode);
        model.addAttribute("itemGroup", itemGroup);
		return "Pd/itemgroup_detail";
    }
	
	@GetMapping("/create")
	public String ItemGroupCreate(ItemGroupForm itemGroupForm) {
		return "Pd/itemgroup_form";
	}
	
	@PostMapping("/create")
    public String ItemGroupCreate(@Valid ItemGroupForm itemGroupForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "Pd/itemgroup_form";
        }
		
		List<ItemGroup> itemGroupList = this.itemGroupService.getList();
		if (itemGroupList.size() == 0) {
			this.itemGroupService.create("IG-001", itemGroupForm.getIgName());
		} else {
			String FinalIgCode = itemGroupList.get(itemGroupList.size()-1).getIgCode();
			int NewIgCodeNum = Integer.parseInt(FinalIgCode.substring(3)) + 1;
			String NewIgCode = "IG-" + String.format("%03d", NewIgCodeNum); 
			this.itemGroupService.create(NewIgCode, itemGroupForm.getIgName());
		}
        return "redirect:/basic/itemgroup/list";
    }
}
