package project.domain.DTO;

import lombok.Data;
import project.domain.entity.MapEntity;

@Data
public class MapMarkerDTO {
	
	private long no;
	private String name;
	private String latitude;
	private String longitude;
	
	public MapEntity toMapEntity() {
		return MapEntity.builder()
				.no(no)
				.name(name)
				.latitude(latitude)
				.longitude(longitude)
				.build();
	}
}
