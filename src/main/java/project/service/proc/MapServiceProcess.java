package project.service.proc;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import project.domain.DTO.MapMarkerDTO;
import project.domain.DTO.MapMarkerListDTO;
import project.domain.repository.MapEntityRepository;
import project.service.MapService;

@Service
public class MapServiceProcess implements MapService{

	@Autowired
	private MapEntityRepository mapEntityRepository;
	
	@Override
	public void save(MapMarkerDTO dto) {
		
		mapEntityRepository.save(dto.toMapEntity());
		
	}

	@Override
	public void map(Model model) {
		model.addAttribute("list", mapEntityRepository.findAll()
				.stream()
				.map(MapMarkerListDTO::new)
				.collect(Collectors.toList())
				);
	}

}
