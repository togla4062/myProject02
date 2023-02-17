package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.api.mall.MallService;
import project.api.mall.MallServiceProcess;

@Controller
public class MallController {

	//MallService mallService = new MallServiceProcess();
	@Autowired
	MallService mall;
	
	@GetMapping("/mall/list")
	public String list(Model model) {
		mall.getjson(model);
		return "mall/list";
	}
	

}
