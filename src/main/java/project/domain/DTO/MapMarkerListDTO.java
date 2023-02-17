package project.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.domain.entity.MapEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MapMarkerListDTO {
	
	private long no;
	private String name;
	private String latitude;
	private String longitude;
	
	public MapMarkerListDTO(MapEntity e) {
		this.no=e.getNo();
		this.name=e.getName();
		this.latitude=e.getLatitude();
		this.longitude=e.getLongitude();
	}
}
