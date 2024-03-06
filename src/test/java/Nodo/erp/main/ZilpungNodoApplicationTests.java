package Nodo.erp.main;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nodo.erp.Hr.Entity.Department;
import nodo.erp.Hr.Entity.Position;
import nodo.erp.Hr.Entity.Spot;
import nodo.erp.Hr.Service.Dep_Service;
import nodo.erp.Hr.Service.Emp_Service;
import nodo.erp.Hr.Service.Position_Service;
import nodo.erp.Hr.Service.Spot_Service;
import nodo.erp.Mm.Inventory.Inventory;
import nodo.erp.Mm.Inventory.InventoryRepository;


@SpringBootTest
class ZilpungNodoApplicationTests {

	@Autowired
    private Emp_Service empService;
	@Autowired
	private Dep_Service depService;
	@Autowired
	private Spot_Service spotService;
	@Autowired
	private Position_Service posiService;
	
	
	
	
	@Test
	void testJpa() {
//		for(int i=1 ;i<=30; i++) {
//			int a =(int)(Math.random()*9999);
//        	int b =(int)(Math.random()*9999);
//        	int c =(int)(Math.random()*9999);
//        	String stra = String.format("%04d", a);
//        	String strb = String.format("%04d", b);
//        	String strc = String.format("%04d", c);
//			depService.create(stra, stra);
//			spotService.create(strb, strb);
//			posiService.create(strc, strc);
//		}
        for (int i = 1; i <= 500; i++) {
        	int a =(int)(Math.random()*29) +1;
        	int b =(int)(Math.random()*29) +1;
        	int c =(int)(Math.random()*29) +1;
        	int d =(int)(Math.random()*9999);
        	int e =(int)(Math.random()*9999);
        	int f =(int)(Math.random()*9999);
        	int g=(int)(Math.random()*11)+1;
        	int h =(int)(Math.random()*29)+1;
        	int j =(int)(Math.random()*25);
            String empname = String.format("%04d", d);
            String empssn = String.format("123445-123%03d", d);
            String empadd = "경기도 서울시";
            String empphone = String.format("010-%04d-%04d", e,f);
            String empmail = "aaa@aa.aa";
            LocalDate date = LocalDate.of(2000+j, g, h);
            
            Spot spot = this.spotService.getFindById(a);
            Position posi = this.posiService.getFindById(b);
            Department depart = this.depService.getFindById(c);
            this.empService.create(empname, empssn, empadd, empphone, empmail, date, spot, posi, depart);
        }
    }
	
}
