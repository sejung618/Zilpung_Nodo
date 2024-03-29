package nodo.erp.Mm.Inventory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Pp.Item.Item;

@RequiredArgsConstructor
@Service
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public List<Inventory> getList() {
		return this.inventoryRepository.findAll();

	}

	public void create(String indate, Item itmcode, Integer inquantity, Employee empnum) {

		String yy = indate.substring(2, 4);
		String mm = indate.substring(5, 7);
		String dd = indate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateInvNum(ymd));

		Inventory i = new Inventory();

		i.setInnum(ymd + "-" + Num);
		i.setIndate(indate);
		i.setItem(itmcode);
		i.setInquantity(inquantity);
		i.setEmployee(empnum);
		i.setCreateDate(LocalDateTime.now());
		this.inventoryRepository.save(i);
	}

	private Integer generateInvNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(i.innum,-3) AS int)) "
				+ "FROM Inventory i WHERE SUBSTRING(i.innum, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}

	// 디테일
	public Inventory getInventory(Integer inid) {
		Optional<Inventory> inventory = this.inventoryRepository.findById(inid);
		if (inventory.isPresent()) {
			return inventory.get();
		} else {
			throw new DataNotFoundException("inventory not found");
		}
	}

	public void modify(Inventory inventory, String indate, Item itmcode, Integer inquantity, Employee empnum) {

		String yy = indate.substring(2, 4);
		String mm = indate.substring(5, 7);
		String dd = indate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateInvNum(ymd));

		inventory.setInnum(ymd + "-" + Num);
		inventory.setIndate(indate);
		inventory.setItem(itmcode);
		inventory.setEmployee(empnum);
		inventory.setInquantity(inquantity);
		inventory.setModifyDate(LocalDateTime.now());
		this.inventoryRepository.save(inventory);
	}

	public void delete(Inventory inventory) {
		this.inventoryRepository.delete(inventory);
	}

	public Page<Inventory> findByIndate(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findByIndateContaining(pageable, kw);
	}

	public Page<Inventory> findByItmName(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findByItem_ItmNameContaining(pageable, kw);
	}

	public Page<Inventory> findByItmCode(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findByItem_ItmCodeContaining(pageable, kw);
	}
	
	public Page<Inventory> findByEmpname(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findByEmployee_EmpnameContaining(pageable, kw);
	}
	
	public Page<Inventory> findByEmpnum(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findByEmployee_EmpnumContaining(pageable, kw);
	}

	public Page<Inventory> searchAllCategories(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return inventoryRepository.findByIndateContainingOrItem_ItmNameContainingOrItem_ItmCodeContainingOrEmployee_EmpnameContainingOrEmployee_EmpnumContaining(pageable, kw, kw, kw,kw, kw);
	}

	public Page<Inventory> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findAll(pageable);
	}
	
	public List<String[]> listInventory() {
        List<Inventory> list = inventoryRepository.findAll(); // SampleMapper를 InventoryMapper로 수정
        List<String[]> listStrings = new ArrayList<>();
        listStrings.add(new String[]{"일련번호", "일자", "담당사번", "담당자", "품목코드", "품목명", "규격", "수량"});
        for (Inventory in: list) {
            String[] rowData = new String[30];
            rowData[0] = in.getInnum();
            rowData[1] = in.getIndate();
            rowData[2] = in.getEmployee().getEmpnum();
            rowData[3] = in.getEmployee().getEmpname();
            rowData[4] = in.getItem().getItmCode();
            rowData[5] = in.getItem().getItmName();
            rowData[6] = in.getItem().getItmStandard();
            rowData[7] = in.getInquantity().toString();
            listStrings.add(rowData);
        }
        return listStrings;
    }
	
}