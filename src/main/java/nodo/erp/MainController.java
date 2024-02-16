package nodo.erp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/zilpung")
    public String index() {
        return "main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/zilpung";
    }
    
    @GetMapping("/zilpung/SD")
    public String SD() {
    	return "SD";
    }
}
