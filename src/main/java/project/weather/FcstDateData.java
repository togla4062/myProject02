package project.weather;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcstDateData {
	String FcstTime;
	@Builder.Default
	List<FcstTimeData> timeData=new ArrayList<>();
}
