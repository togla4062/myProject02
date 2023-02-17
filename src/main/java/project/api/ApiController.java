package project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import project.api.airline.AirlineService;

@RestController //@Response가 기본적으로 적용되어있는 @Controller
public class ApiController {
	
	@Autowired
	private AirlineService alService;
	
	//@ResponseBody 적용중....
	//페이지 이동
	@GetMapping("/api/airline")
	public ModelAndView airline() {
		return new ModelAndView("openapi/airline");
	}
	
	@GetMapping("/api/airline/ajax")
	public ModelAndView findAirPortBus(ModelAndView mv, String schDate, long pageNo, String schDeptCityCode, String schArrvCityCode) {
		alService.getAirlineInfo(mv, schDate, pageNo, schDeptCityCode, schArrvCityCode);
		mv.setViewName("openapi/airlinelist");
		return mv;
	}
	
	
}
