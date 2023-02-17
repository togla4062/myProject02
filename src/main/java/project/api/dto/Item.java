package project.api.dto;

import lombok.Data;

@Data
public class Item {//받아오는 값이 JSON형식이라 전부 문자열로 받아온다 **JSON형식은 텍스트 형식이라 가볍다는 장점이있다.
	
	String rnum;          //순번을 출력합니다.
	String rank;          //해당일자의 박스오피스 순위를 출력합니다.
	String rankInten;     //전일대비 순위의 증감분을 출력합니다.
	String rankOldAndNew; //랭킹에 신규진입여부를 출력합니다. “OLD” : 기존 , “NEW” : 신규
	String movieCd;       //영화의 대표코드를 출력합니다.
	String movieNm;       //영화명(국문)을 출력합니다.
	String openDt;        //영화의 개봉일을 출력합니다.
	String salesAmt;      //해당일의 매출액을 출력합니다.
	String salesShare;    //해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
	String salesInten;    //전일 대비 매출액 증감분을 출력합니다.
	String salesChange;   //전일 대비 매출액 증감 비율을 출력합니다.
	String salesAcc;      //누적매출액을 출력합니다.
	String audiCnt;       //해당일의 관객수를 출력합니다.
	String audiInten;     //전일 대비 관객수 증감분을 출력합니다.
	String audiChange;    //전일 대비 관객수 증감 비율을 출력합니다.
	String audiAcc;       //누적관객수를 출력합니다.
	String scrnCnt;       //해당일자에 상영한 스크린수를 출력합니다.
	String showCnt;       //해당일자에 상영된 횟수를 출력합니다.

}
