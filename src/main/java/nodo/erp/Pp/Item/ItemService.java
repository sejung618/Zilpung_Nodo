package nodo.erp.Pp.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Pp.ItemCategory.ItemCategory;
import nodo.erp.Pp.ItemCategory.ItemCategoryRepository;
import nodo.erp.Pp.ItemCategory.ItemCategoryService;
import nodo.erp.Pp.ItemGroup.ItemGroup;
import nodo.erp.Pp.ItemGroup.ItemGroupRepository;
import nodo.erp.Pp.ItemGroup.ItemGroupService;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemGroupService itemGroupService;
	private final ItemCategoryService itemCategoryService;
	
	private final ItemRepository itemRepository;
	private final ItemGroupRepository itemGroupRepository;
	private final ItemCategoryRepository itemCategoryRepository;
	
	public List<Item> getList() {
		return this.itemRepository.findAll();
	}
	
	public List<ItemGroup> getIgList() {
		return this.itemGroupRepository.findAll();
	}
	
	public List<ItemCategory> getIcList() {
		return this.itemCategoryRepository.findAll();
	}
	
	public Page<Item> getList(int page, String st, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Item> sItem = search(st, kw);
		return this.itemRepository.findAll(sItem, pageable);
	}
	
	public static Specification<Item> search(String search_terms, String search_kw) {
		return (Root<Item> item, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			Join<Item, ItemGroup> IG = item.join("ItmGroup", JoinType.LEFT);
			Join<Item, ItemCategory> IC = item.join("ItmCategory", JoinType.LEFT);
			if (search_terms.equals("ItmGroup")) {
				return cb.like(IG.get("IgName"), "%" + search_kw + "%");
			} else if (search_terms.equals("ItmCategory")) {
				return cb.like(IC.get("IcName"), "%" + search_kw + "%");
			} else if (search_terms.equals("ItmName")) {
				return cb.like(item.get("ItmName"), "%" + search_kw + "%");
			} else {
				return cb.or(cb.like(IG.get("IgName"), "%" + search_kw + "%"),
						cb.like(IC.get("IcName"), "%" + search_kw + "%"),
						cb.like(item.get("ItmName"), "%" + search_kw + "%") );
			}
		};
	}
	
	public Item getItem(Integer ItmId) {
		Optional<Item> item= this.itemRepository.findById(ItmId);
		if (item.isPresent()) {
	        return item.get();
	    } else {
	        throw new DataNotFoundException("item not found");
	    }
	}
	
	public void create(String ItmName, Integer ItmGroup, Integer ItmCategory, String ItmStandard, Integer ItmSprice, Integer ItmRprice) {
		Item itm = new Item();
		String NewItmCode = "";
		List<Item> itemList = this.itemRepository.findAll();
		if (itemList.size() == 0) {
			int IgCodeNum = Integer.parseInt( itemGroupService.getItemGroup(ItmGroup).getIgCode().substring(3) );
			int IcCodeNum = Integer.parseInt( itemCategoryService.getItemCategory(ItmCategory).getIcCode().substring(3) );
			NewItmCode = "ITM-" + String.format("%03d", IgCodeNum) + "-" + String.format("%03d", IcCodeNum) + "-001";
		} else {
			String FinalItmCode = itemList.get(itemList.size()-1).getItmCode();
			int IgCodeNum = Integer.parseInt( itemGroupService.getItemGroup(ItmGroup).getIgCode().substring(3) );
			int IcCodeNum = Integer.parseInt( itemCategoryService.getItemCategory(ItmCategory).getIcCode().substring(3) );
			int NewItmCodeNum = Integer.parseInt(FinalItmCode.substring(12)) + 1;
			NewItmCode = "ITM-" + String.format("%03d", IgCodeNum) + "-" + String.format("%03d", IcCodeNum) + "-" + String.format("%03d", NewItmCodeNum);
		}
	    itm.setItmCode(NewItmCode);
	    itm.setItmName(ItmName);
	    itm.setItmGroup(itemGroupService.getItemGroup(ItmGroup));
	    itm.setItmCategory(itemCategoryService.getItemCategory(ItmCategory));
	    itm.setItmStandard(ItmStandard);
	    itm.setItmSprice(ItmSprice);
	    itm.setItmRprice(ItmRprice);
	    itm.setCreateDate(LocalDateTime.now());
	    this.itemRepository.save(itm);
	}
	
	public void modify(Item itm, String ItmName, Integer ItmGroup, Integer ItmCategory, String ItmStandard, Integer ItmSprice, Integer ItmRprice) {
		itm.setItmName(ItmName);
		itm.setItmGroup(itemGroupService.getItemGroup(ItmGroup));
	    itm.setItmCategory(itemCategoryService.getItemCategory(ItmCategory));
	    itm.setItmStandard(ItmStandard);
	    itm.setItmSprice(ItmSprice);
	    itm.setItmRprice(ItmRprice);
	    this.itemRepository.save(itm);
	}
	
	public void delete(Item itm) {
	    this.itemRepository.delete(itm);
	}
}