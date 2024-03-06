package nodo.erp.Sd.Sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import nodo.erp.Mm.Inventory.InventoryService;

@RequestMapping("/sales")
@RequiredArgsConstructor
@Controller
public class SalesController {

	@Autowired
	private final SalesService SS;
	private final InventoryService invenService;
	
	//@GetMapping("/list")
	
}
