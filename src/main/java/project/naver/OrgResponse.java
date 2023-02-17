package project.naver;

import java.util.List;

import lombok.Data;

@Data
public class OrgResponse {
	private List<OrgUnit> orgUnits;
	private ResponseMetaData responseMetaData;

}