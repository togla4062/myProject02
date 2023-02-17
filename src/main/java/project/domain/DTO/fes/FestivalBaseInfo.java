package project.domain.DTO.fes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FestivalBaseInfo{
    public String id;
    public String festivalNm;
    public String festivalVenue;
    public String festivalBeginDate;
    public String festivalEndDate;
    public String festivalInfo;
    public String manageAgcNm;
    public String auspiceAgcNm;
    public String sponsorAgcNm;
    public String tel;
    public String homePage;
    public String relatedInfo;
    public String addrRoad;
    public String addrJibun;
    public String lat;
    public String lng;
    public String syncTime;
}
