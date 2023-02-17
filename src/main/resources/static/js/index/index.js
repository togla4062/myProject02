/**
 * 
 */
// <!-- 230111 출퇴근시간 스크립트 시작 안나 -->
$(function() {

	/* 시, 분 얻어오기 */
	var now = new Date();
	var hours = now.getHours(); // 시 얻어오기
	var minutes = now.getMinutes(); // 분 얻어오기

	/* 시, 분 한 자리수면 앞에 0 붙여주기 */
	hours = resetPlusZero(hours);
	minutes = resetPlusZero(minutes);
	function resetPlusZero(i) {
		if (i < 10) {
			i = "0" + i
		}
		;
		return i;
	}

	/* status값 얻어오기 */
	var status = $('#status').val();
	console.log("status: " + status);

	/* 근무중일 때만 나오는 버튼들 */
	$("#start-time-today").hide(); //출근시간
	$("#end-time-today").hide(); //퇴근시간
	if (status == '근무중') {
		$("#start-time-today").show();
	}
	if (status == '근무종료') {
		$("#start-time-today").show();
		$("#end-time-today").show();
	}

	/* 출근 버튼 클릭 시 시작*/
	$("#start-time")
		.click(
			function() {
				if (status == '근무중' || status == '근무종료') { //미출근시에만 출근가능하도록
					alert("이미 출근하였습니다.");
				} else { //출근기록이 없으면 */

					// 출근 버튼 누르면 나오는 팝업창
					var startResult = confirm("현재 시각은 " + hours
						+ "시 " + minutes + "분입니다. \n출근하시겠습니까?");

					//출근 data 통신
					if (startResult) {
						$
							.ajax({
								async: false,
								type: "post",
								url: "/attendance/in",
								contentType: "application/x-www-form-urlencoded; charset=utf-8",
								dataType: "text",
								success: function(data) {
									console.log("성공 data:"
										+ data);
									if (data == "attinSuccess") { //데이터 리턴이 잘 됐으면
										alert("출근완료");
										location.reload(); //출근 완료 시 새로고침하여 출근기록 보이기
									} else {
										alert("이상발생");
									}
								},
								error: function() {
									console.log("실패");
								}
							})
					} else {
						alert("취소하였습니다."); // confirm창 취소 시 알림
					}
				}//출근기록 if else문 끝 */
			});
	/* 출근 버튼 클릭 시 끝*/

	/* 퇴근 버튼 클릭 시 시작*/
	$("#end-time")
		.click(
			function() {
				if (status == null || status == '미출근') { //퇴근 조건
					alert("출근 후 퇴근 가능합니다.");
				} else {
					// 퇴근 버튼 누르면 나오는 팝업창
					var endResult = confirm("현재 시각은 " + hours
						+ "시 " + minutes + "분입니다. \n퇴근하시겠습니까?");

					//퇴근 data 통신
					if (endResult) {
						$
							.ajax({
								async: false,
								type: "patch",
								url: "/attendance/out",
								contentType: "application/json-patch+json; charset=utf-8",
								dataType: "text",
								success: function(data) {
									console.log("성공 data:"
										+ data);
									if (data == "attoutSuccess") { //데이터 리턴이 잘 됐으면
										alert("퇴근완료");
										location.reload(); //새로고침
									} else {
										alert("이상발생");
									}
								},
								error: function() {
									console.log("실패");
								}
							});
					} else {
						alert("취소하였습니다."); // confirm창 취소 시 알림
					} // endResult if문 끝
				}//퇴근조건 if문 끝		
			}); //퇴근버튼 클릭 끝
	selectNotice();

	//<!-- 230111 출퇴근시간 스크립트 끝 안나 -->
	var weatherIcon = {
		'01': 'fas fa-sun',
		'02': 'fas fa-cloud-sun',
		'03': 'fas fa-cloud',
		'04': 'fas fa-cloud-meatball',
		'09': 'fas fa-cloud-sun-rain',
		'10': 'fas fa-cloud-showers-heavy',
		'11': 'fas fa-poo-storm',
		'13': 'far fa-snowflake',
		'50': 'fas fa-smog'
	};

	// 날씨 api - 서울
	var apiURI = "http://api.openweathermap.org/data/2.5/weather?q="
		+ 'Seoul' + "&lang=" + "kr" + "&appid=";

	//URL 생성
	$.ajax({
		url: "/mkWeatherURL",
		type: 'POST',
		data: {
			url: apiURI
		},
		success: function(result) {
			//생성된 URL로 데이터 요청 및 처리
			$.ajax({
				url: result,
				dataType: "json",
				type: "GET",
				async: "false",
				success: function(resp) {

					var $Icon = (resp.weather[0].icon)
						.substr(0, 2);
					var $weather_description = resp.weather[0].main;
					var $Temp = Math
						.floor(resp.main.temp - 273.15)
						+ 'º';
					var $humidity = '습도&nbsp;&nbsp;&nbsp;&nbsp;'
						+ resp.main.humidity + ' %';
					var $wind = '바람&nbsp;&nbsp;&nbsp;&nbsp;'
						+ resp.wind.speed + ' m/s';
					var $city = '서울';
					var $cloud = '구름&nbsp;&nbsp;&nbsp;&nbsp;'
						+ resp.clouds.all + "%";
					var $temp_min = '최저 온도&nbsp;&nbsp;&nbsp;&nbsp;'
						+ Math
							.floor(resp.main.temp_min - 273.15)
						+ 'º';
					var $temp_max = '최고 온도&nbsp;&nbsp;&nbsp;&nbsp;'
						+ Math
							.floor(resp.main.temp_max - 273.15)
						+ 'º';

					$('.weather_icon')
						.append(
							'<i class="' + weatherIcon[$Icon] + ' fa-5x" style="height : 60px; width : 60px;"></i>');
					$('.weather_description').prepend(
						$weather_description);
					$('.current_temp').prepend($Temp);
					$('.humidity').prepend($humidity);
					$('.wind').prepend($wind);
					$('.city').append($city);
					$('.cloud').append($cloud);
					$('.temp_min').append($temp_min);
					$('.temp_max').append($temp_max);
				}
			})
		}

	})
	var cityName = "New York";
	var cityKorName = "뉴욕";
	mkWeatherURL(cityName, cityKorName);

});//function 끝

// 도시변경시 날씨 변경되게 하는 함수
function changeCityWeather(el) {
	var cityName = $(el).val();
	var cityKorName = $("#cityName option:selected").text();
	mkWeatherURL(cityName, cityKorName);
}

//날씨 URL 정보 생성하는 함수
function mkWeatherURL(cityName, cityKorName) {

	var apiURI = "http://api.openweathermap.org/data/2.5/weather?q="
		+ cityName + "&lang=" + "kr" + "&appid=";

	$.ajax({
		url: "/mkWeatherURL",
		type: 'POST',
		data: {
			url: apiURI
		},
		success: function(result) {
			//생성된 URL로 데이터 요청
			cityIndexWeather(cityKorName, result);
		}

	})

}

// 날씨 api 
function cityIndexWeather(cityKorName, apiURI) {

	var weatherIcon = {
		'01': 'fas fa-sun',
		'02': 'fas fa-cloud-sun',
		'03': 'fas fa-cloud',
		'04': 'fas fa-cloud-meatball',
		'09': 'fas fa-cloud-sun-rain',
		'10': 'fas fa-cloud-showers-heavy',
		'11': 'fas fa-poo-storm',
		'13': 'far fa-snowflake',
		'50': 'fas fa-smog'
	};

	$.ajax({
		url: apiURI,
		dataType: "json",
		type: "GET",
		async: "false",
		success: function(resp) {

			var $Icon = (resp.weather[0].icon).substr(0, 2);
			var $weather_description = resp.weather[0].main;
			var $Temp = Math.floor(resp.main.temp - 273.15) + 'º';
			var $humidity = '습도&nbsp;&nbsp;&nbsp;&nbsp;'
				+ resp.main.humidity + ' %';
			var $wind = '바람&nbsp;&nbsp;&nbsp;&nbsp;'
				+ resp.wind.speed + ' m/s';
			var $city = cityKorName;
			var $cloud = '구름&nbsp;&nbsp;&nbsp;&nbsp;'
				+ resp.clouds.all + "%";
			var $temp_min = '최저 온도&nbsp;&nbsp;&nbsp;&nbsp;'
				+ Math.floor(resp.main.temp_min - 273.15) + 'º';
			var $temp_max = '최고 온도&nbsp;&nbsp;&nbsp;&nbsp;'
				+ Math.floor(resp.main.temp_max - 273.15) + 'º';

			emptyIndexWeather();

			$('.new-weather_icon')
				.append(
					'<i class="' + weatherIcon[$Icon] + ' fa-5x" style="height : 60px; width : 60px;"></i>');
			$('.new-weather_description').prepend(
				$weather_description);
			$('.new-current_temp').prepend($Temp);
			$('.new-humidity').prepend($humidity);
			$('.new-wind').prepend($wind);
			$('.new-city').append($city);
			$('.new-cloud').append($cloud);
			$('.new-temp_min').append($temp_min);
			$('.new-temp_max').append($temp_max);
		}
	})
}
//기존의 뿌려준 날씨 데이터 삭제 기능
function emptyIndexWeather() {
	$('.new-weather_icon').empty();
	$('.new-weather_description').empty();
	$('.new-current_temp').empty();
	$('.new-humidity').empty();
	$('.new-wind').empty();
	$('.new-city').empty();
	$('.new-cloud').empty();
	$('.new-temp_min').empty();
	$('.new-temp_max').empty();
}
// 익일 스케줄 페이지로 이동
function gotoTomorrowSchedule() {
	var date = $("#tomorrowDate").val() + "";
	window.location.href = "/scheduleList?clickedDate="
		+ date;
};
// 금일 스케줄 페이지로 이동
function gotoTodaySchedule() {
	var date = $("#today").val() + "";
	window.location.href = "/scheduleList?clickedDate="
		+ date;
};
// 인덱스 게시판 - 공지사항 선택
function selectNotice() {
	var select = "공지사항";
	removeclassToBTN();
	$("#notice-board").addClass("board-target");
	indexBoard(select);
}
// 인덱스 게시판 - 경조사 선택
function selectCNC() {
	var select = "경조사";
	removeclassToBTN();
	$("#cnc-board").addClass("board-target");
	indexBoard(select);
}
// 인덱스 게시판 - 건의사항 선택
function selectsuggest() {
	var select = "건의사항";
	removeclassToBTN();
	$("#suggest-board").addClass("board-target");
	indexBoard(select);
}
// 인덱스 게시판 선택된 탭의 내용을 처리하는 함수
function indexBoard(selectBoard) {
	$.ajax({
		url: "/indexboard?select=" + selectBoard,
		success: function(result) {
			$("#indexBoardShow").html(result);
		}
	})
}
// 게시판 선택시 기존에 선택되었던 버튼의 CSS를 제거하는 함수
function removeclassToBTN() {
	$("#notice-board").removeClass("board-target");
	$("#cnc-board").removeClass("board-target");
	$("#suggest-board").removeClass("board-target");
}