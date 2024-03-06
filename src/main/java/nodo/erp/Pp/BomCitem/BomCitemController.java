//package nodo.erp.Pp.BomCitem;
//
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import lombok.RequiredArgsConstructor;
//import nodo.erp.Pp.Bom.Bom;
//
//@RequestMapping("/BOM")
//@RequiredArgsConstructor
//@Controller
//public class BomCitemController {
//	
//	@GetMapping("/list")
//	public String list(Model model,
//			@RequestParam(value = "page", defaultValue = "0") int page) {
//		Page<Bom> paging = this.bomService.getList(page);
//		model.addAttribute("paging", paging);
//		return "Pp/bom/bom_list";
//	}
//	
//}
