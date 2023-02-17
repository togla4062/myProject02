package project.service;

import org.springframework.ui.Model;

import project.domain.DTO.MapMarkerDTO;

public interface MapService {

	void save(MapMarkerDTO dto);

	void map(Model model);

}
