package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import project.service.TodayAirportInfoService;

@Controller
public class AirportAPIController {
	
	@Value("${airport.api.service.key}")
	String airport_ServiceKey;
	
	@Value("${travel.warning.api.service.key}")
	String travel_warning_ServiceKey;
	
	@Autowired
	TodayAirportInfoService service;
	
	@GetMapping("/worldTime")
	public String worldTime() {
		return "/airportInfoList/worldTime";
	}
	
	@GetMapping("/todayAirportInfoList")
	public String todayAirportInfoList() {
		return "/airportInfoList/todayAirportList";
	}
	
	@ResponseBody
	@PostMapping("/todayAirport")
	public ModelAndView test(@RequestParam("url") String url, @RequestParam("option") String option, ModelAndView modelAndView) {
		
		String finalAPIURL = url +airport_ServiceKey+ option;
		
		service.apiService(finalAPIURL,modelAndView);
		
		return modelAndView;
	}
	
	@GetMapping("/travelWarning")
	public String travelWarning() {
		return "/airportInfoList/travelWarning";
	}
	
	@ResponseBody
	@PostMapping("/mkTravelWarningURL")
	public String testing(@RequestParam("url") String url,@RequestParam("option") String option) {
		String furl=url+travel_warning_ServiceKey+option;
		return furl;
	}
	
}
