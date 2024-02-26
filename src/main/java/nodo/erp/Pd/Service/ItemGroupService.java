package nodo.erp.Pd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nodo.erp.DataNotFoundException;
import nodo.erp.Pd.Entity.ItemGroup;
import nodo.erp.Pd.Repository.ItemGroupRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemGroupService {
	
	private final ItemGroupRepository itemGroupRepository;
	
	public List<ItemGroup> getList() {
		return this.itemGroupRepository.findAll();
	}
	
	public ItemGroup getItemGroup(Integer id) {
		Optional<ItemGroup> itemGroup = this.itemGroupRepository.findById(id);
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
