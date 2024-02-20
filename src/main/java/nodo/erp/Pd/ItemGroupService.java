package nodo.erp.Pd;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nodo.erp.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemGroupService {
	
	private final ItemGroupRepository itemGroupRepository;
	
	public List<ItemGroup> getList() {
		return this.itemGroupRepository.findAll();
	}
	
	public ItemGroup getItemGroup(String IgCode) {
		Optional<ItemGroup> itemGroup = this.itemGroupRepository.findById(IgCode);
		if (itemGroup.isPresent()) {
            return itemGroup.get();
        } else {
            throw new DataNotFoundException("itemGroup not found");
        }
	}
	
	public void create(String IgCode, String IgName) {
        ItemGroup ig = new ItemGroup();
        ig.setIgCode(IgCode);
        ig.setIgName(IgName);
        this.itemGroupRepository.save(ig);
    }
}
