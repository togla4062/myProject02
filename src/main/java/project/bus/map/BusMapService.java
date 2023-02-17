package project.bus.map;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import project.bus.map.dto.ArriveRequestDTO;
import project.bus.map.dto.BusArriveItem;

public interface BusMapService {

	List<BusArriveItem> arriveInfo(ArriveRequestDTO dto);

	void getBusPath(String strSrch, ModelAndView mv);

	void getStaionsByRouteList(String busRouteId, ModelAndView mv);

	void getPlacesSearch(String search, ModelAndView mv);

}
