package project.bus.map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import project.bus.map.dto.ArriveRequestDTO;
import project.bus.map.dto.BusArriveItem;

@RestController
public class mapController {
	
	@Autowired
	private BusMapService service;
	
	//맵 팝업창
	@RequestMapping(value = "/bus/map.html")
	public ModelAndView Map(String stationSrch) {
		return new ModelAndView ("/bus/map");
	}
	
	@GetMapping("/bus/arriveInfo")
	public List<BusArriveItem> arriveInfo(ArriveRequestDTO dto) {
		return service.arriveInfo(dto);
	}
	
	
	//ajax 요청
	
	@GetMapping("/map/search")
	public ModelAndView busSearch(String strSrch, ModelAndView mv) {
		mv.setViewName("bus/list");
		service.getBusPath(strSrch, mv);
		return mv;
	}
	
	//@ajax 요청방식
	//리스트에 버스번호 클릭시 처리되는기능
	@GetMapping("/bus/{busRouteId}")
	public ModelAndView getStaionsByRouteList(@PathVariable String busRouteId, ModelAndView mv) {
		mv.setViewName("bus/station-list");
		service.getStaionsByRouteList(busRouteId, mv);
		return mv;
	}
	
	@GetMapping("/placesSearch")
	public ModelAndView placesSearch(String search, ModelAndView mv) {
		mv.setViewName("bus/list");
		service.getPlacesSearch(search, mv);
		return mv;
	}
	
}
