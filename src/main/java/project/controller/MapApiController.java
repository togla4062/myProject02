package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import project.domain.DTO.MapMarkerDTO;
import project.service.MapService;

@Controller
public class MapApiController {
	
	@Autowired
	private MapService service;
	
	@GetMapping("/map")
	public String map(Model model) {
		service.map(model);
		return "mapapi/mapapi";
	}
	
	@GetMapping("/map/marker")
	public String mapmarker() {
		return "mapapi/addmarker";
	}
	
	@PostMapping("/map/marker")
	public String markerSave(MapMarkerDTO dto) {
		
		service.save(dto);
		return "redirect:/map/marker";
	}
}
