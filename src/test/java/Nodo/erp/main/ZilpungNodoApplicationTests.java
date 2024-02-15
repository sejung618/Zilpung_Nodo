package Nodo.erp.main;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nodo.erp.Mm.Inventory;
import nodo.erp.Mm.InventoryRepository;


@SpringBootTest
class ZilpungNodoApplicationTests {
	
	//자재테스트
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Test
	void testJpa() {
		Inventory inv1 = new Inventory();
		inv1.setCreateDate(LocalDateTime.now());
		inv1.setINQuantity(99);
		inv1.setINDate("240206-011");
		inv1.setINICode("IG-A01");
		inv1.setININame("다이소");
		inv1.setINPName("김가을");
		inv1.setINPNum("2401011111234");
		inv1.setINStandard("규격");
		this.inventoryRepository.save(inv1);
		
		Inventory inv2 = new Inventory();
		inv2.setCreateDate(LocalDateTime.now());
		inv2.setINQuantity(999);
		inv2.setINDate("240912-066");
		inv2.setINICode("IG-A99");
		inv2.setININame("이마트");
		inv2.setINPName("김하늘");
		inv2.setINPNum("2405054561238");
		inv2.setINStandard("규격2");
		this.inventoryRepository.save(inv2);
	}
}
