package project.domain.DTO.fes;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Source {
	@JsonProperty("FestivalBaseInfo") 
    public ArrayList<FestivalBaseInfo> festivalBaseInfo;
    public Header header;
}
