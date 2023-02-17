package project.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BusController {
	
	@Autowired
	private busService service;
	
	@GetMapping("/bus/busInfo")
	public ModelAndView bus() {
		return new ModelAndView ("bus/busInfo");
	}
	
	@GetMapping("/bus/search")
	public ModelAndView busSearch(String areaSrch, ModelAndView mv) {
		mv.setViewName("bus/airportlist");
		service.getBus(areaSrch, mv);
		return mv;
	}
	
	
	
	
	
}
