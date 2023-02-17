package project.api.airline;

import org.springframework.web.servlet.ModelAndView;

public interface AirlineService {

	void getAirlineInfo(ModelAndView mv, String schDate, long pageNo, String schDeptCityCode, String schArrvCityCode);

}
