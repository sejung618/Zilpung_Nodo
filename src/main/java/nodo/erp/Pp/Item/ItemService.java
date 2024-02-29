package nodo.erp.Pp.Item;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Pp.ItemGroup.ItemGroup;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public List<Item> getList() {
		return this.itemRepository.findAll();
	}
	
	public Item getItem(Integer ItmId) {
		Optional<Item> item= this.itemRepository.findById(ItmId);
		if (item.isPresent()) {
	        return item.get();
	    } else {
	        throw new DataNotFoundException("item not found");
	    }
	}
	
	public void create(String ItmCode, String ItmName, ItemGroup ItmGroup, String ItmCategory, String ItmStandard, Integer ItmSprice, Integer ItmRprice) {
	    Item itm = new Item();
	    itm.setItmCode(ItmCode);
	    itm.setItmName(ItmName);
	    itm.setItmGroup(ItmGroup);
	    itm.setItmCategory(ItmCategory);
	    itm.setItmStandard(ItmStandard);
	    itm.setItmSprice(ItmSprice);
	    itm.setItmRprice(ItmRprice);
	    this.itemRepository.save(itm);
	}
	
	public void modify(Item itm, String ItmName, String ItmGroup, String ItmCategory, String ItmStandard, Integer ItmSprice, Integer ItmRprice) {
		itm.setItmName(ItmName);
	    itm.setItmCategory(ItmCategory);
	    itm.setItmStandard(ItmStandard);
	    itm.setItmSprice(ItmSprice);
	    itm.setItmRprice(ItmRprice);
	    this.itemRepository.save(itm);
	}
	
	public void delete(Item itm) {
	    this.itemRepository.delete(itm);
	}
}