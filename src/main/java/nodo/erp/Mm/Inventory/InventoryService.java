package nodo.erp.Mm.Inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nodo.erp.DataNotFoundException;
import nodo.erp.Hr.Entity.Employee;
import nodo.erp.Hr.Repository.Emp_Repository;
import nodo.erp.Pp.Item.Item;
import nodo.erp.Pp.Item.ItemRepository;

@RequiredArgsConstructor
@Service
public class InventoryService {

	private final InventoryRepository inventoryRepository;
	private final Emp_Repository empRepository;
	private final ItemRepository itemRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public List<Inventory> getList() {
		return this.inventoryRepository.findAll();

	}

	public Employee getEmpDetail(Integer id) {
		Optional<Employee> empDetail = this.empRepository.findById(id);
		if (empDetail.isPresent()) {
			return empDetail.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String INDate, Item item, Integer INQuantity, Employee empnum) {

		String yy = INDate.substring(2, 4);
		String mm = INDate.substring(5, 7);
		String dd = INDate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateInvNum(ymd));

		Inventory i = new Inventory();

		i.setINNum(ymd + "-" + Num);
		i.setINDate(INDate);
		i.setItem(item);
		i.setINQuantity(INQuantity);
		i.setEmployee(empnum);
		i.setCreateDate(LocalDateTime.now());
		this.inventoryRepository.save(i);
	}

	private Integer generateInvNum(String ymd) {
		jakarta.persistence.Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(i.INNum,-3) AS int)) "
				+ "FROM Inventory i WHERE SUBSTRING(i.INNum, 1, 6) = :ymd");
		query.setParameter("ymd", ymd);
		Integer maxNum = (Integer) query.getSingleResult();

		return (maxNum == null) ? 1 : maxNum + 1;
	}

	// 디테일
	public Inventory getInventory(Integer INid) {
		Optional<Inventory> inventory = this.inventoryRepository.findById(INid);
		if (inventory.isPresent()) {
			return inventory.get();
		} else {
			throw new DataNotFoundException("inventory not found");
		}
	}

	public void modify(Inventory inventory, String INDate, Item Item, Integer INQuantity, Employee empnum) {

		String yy = INDate.substring(2, 4);
		String mm = INDate.substring(5, 7);
		String dd = INDate.substring(8, 10);
		String ymd = yy + mm + dd;
		String Num = String.format("%03d", generateInvNum(ymd));

		inventory.setINNum(ymd + "-" + Num);
		inventory.setINDate(INDate);
		inventory.setItem(Item);
		inventory.setEmployee(empnum);
		inventory.setINQuantity(INQuantity);
		inventory.setModifyDate(LocalDateTime.now());
		this.inventoryRepository.save(inventory);
	}

	public void delete(Inventory inventory) {
		this.inventoryRepository.delete(inventory);
	}

	public Page<Inventory> findByINDate(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findByINDateContaining(pageable, kw);
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

	public Page<Inventory> searchAllCategories(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return inventoryRepository.findByINDateContainingOrItem_ItmNameContainingOrItem_ItmCodeContaining(pageable, kw, kw, kw);
	}

	public Page<Inventory> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.inventoryRepository.findAll(pageable);
	}

}