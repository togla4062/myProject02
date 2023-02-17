package project.weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	String fcstDate;
	@Builder.Default
	List<FcstDateData> baseDate=new ArrayList<>();
	
	public ItemDTO add(FcstDateData data) {
		this.add(data);
		return this;
	}
}
