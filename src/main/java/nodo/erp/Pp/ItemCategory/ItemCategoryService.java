package nodo.erp.Pp.ItemCategory;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nodo.erp.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemCategoryService {
	
	private final ItemCategoryRepository itemCategoryRepository;
	
	public List<ItemCategory> getList() {
		return this.itemCategoryRepository.findAll();
	}
	
	public ItemCategory getItemCategory(Integer IcId) {
		Optional<ItemCategory> itemCategory = this.itemCategoryRepository.findById(IcId);
		if (itemCategory.isPresent()) {
            return itemCategory.get();
        } else {
            throw new DataNotFoundException("itemCategory not found");
        }
	}
	
	public String getIcCodeIdByName(String IcName) {
		String IcCode = "";
		List<ItemCategory> itemCategoryList = this.itemCategoryRepository.findAll();
		for(ItemCategory ic : itemCategoryList) {
			if((ic.getIcName()).equals(IcName)) {
				IcCode = ic.getIcCode();
				break;
			}
		}
		return IcCode;
	}
	
	public void create(String IcCode, String IcName) {
        ItemCategory ic = new ItemCategory();
        ic.setIcCode(IcCode);
        ic.setIcName(IcName);
        this.itemCategoryRepository.save(ic);
    }
	
	public void modify(ItemCategory ic, String IcName) {
		ic.setIcName(IcName);
		this.itemCategoryRepository.save(ic);
	}
	
	public void delete(ItemCategory ic) {
        this.itemCategoryRepository.delete(ic);
    }
}
