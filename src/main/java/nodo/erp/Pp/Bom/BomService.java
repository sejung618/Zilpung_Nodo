package nodo.erp.Pp.Bom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nodo.erp.Pp.Item.ItemService;

@RequiredArgsConstructor
@Service
public class BomService {
	
	private final ItemService itemService;
	private final BomRepository bomRepository;
	
	public List<Bom> getList() {
		return this.bomRepository.findAll();
	}
	
	public Page<Bom> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.bomRepository.findAll(pageable);
	}
	
	public void create(String NewBomCode, Integer bpItemId, Integer bpCount, Integer bpHour) {
		Bom bom = new Bom();
		bom.setBomCode(NewBomCode);
		bom.setBpItem(itemService.getItem(bpItemId));
		bom.setBpCount(bpCount);
//		bom.setBcList(bcIdList);
		bom.setBpHour(bpHour);
		bom.setCreateDate(LocalDateTime.now());
		this.bomRepository.save(bom);
	}
	
}
