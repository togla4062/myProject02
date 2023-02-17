package project.domain.DTO.fes;

import lombok.Data;

@Data
public class Header{
    public int totalPage;
    public int pageNo;
    public String resultCode;
    public int totalCount;
    public int numOfRows;
    public String resultMsg;
}
