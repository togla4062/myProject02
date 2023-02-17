$(function() {
	$.ajax({
		url: "/calendar/data",
		type: 'POST',
		success: function(data) {
			var datalist = JSON.parse(data);
			calendar(datalist);
		}
	});
});

function calendar(data) {
	// calendar element 취득
	var calendarEl = $('#calendar')[0];
	// full-calendar 생성하기
	var calendar = new FullCalendar.Calendar(calendarEl, {
		height: '700px', // calendar 높이 설정
		expandRows: true, // 화면에 맞게 높이 재설정
		slotMinTime: '06:00', // Day 캘린더에서 시작 시간
		slotMaxTime: '23:00', // Day 캘린더에서 종료 시간
		// 해더에 표시할 툴바
		headerToolbar: {
			left: 'prev,next today addEventButton',
			center: 'title',
			right: 'dayGridMonth,timeGridWeek,timeGridDay'
		},
		initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
		//initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
		navLinks: false, // 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크
		editable: false, // 이벤트 위치 변경 가능 기능
		selectable: false, // 달력 일자에 내용 추가 가능 기능
		nowIndicator: true, // 현재 시간 마크
		dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
		locale: 'ko', // 한국어 설정
		customButtons: {
			addEventButton: { // 추가한 버튼 설정
				text: "일정 추가",  // 버튼 내용
				click: function() { // 버튼 클릭 시 이벤트 추가
					$("#calendarModal").modal("show"); // modal 나타내기

					$("#addCalendar").on("click", function() {  // modal의 추가 버튼 클릭 시
						var select = $("#calendar_select").val()
						var content = $("#calendar_content").val();
						var start_date = $("#calendar_start_date").val();
						var end_date = $("#calendar_end_date").val();

						//내용 입력 여부 확인
						if (content == null || content == "") {
							alert("내용을 입력하세요.");
						} else if (select == null || select == "") {
							alert("일정 종류를 선택하세요.");
						} else if (start_date == "" || end_date == "") {
							alert("날짜를 입력하세요.");
						} else if (new Date(end_date) - new Date(start_date) < 0) { // date 타입으로 변경 후 확인
							alert("종료일이 시작일보다 먼저입니다.");
						} else { // 정상적인 입력 시
							var obj = {
								"select" : select,
								"title": content,
								"start": start_date,
								"end": end_date
							}//전송할 객체 생성

							console.log(obj); //서버로 해당 객체를 전달해서 DB 연동 가능

							$.ajax({
								type: "post",
								url: "/scheduleAdd",
								dataType: "json",
								cache: false,
								async: false,
								data: obj,
								success: function() {
								},
								error: function() {
									window.location.href = "/calendar";
								}
							}); 
						}
					});
				}
			}
		},
		// 이벤트 

		events: data

	});
	// 캘린더 랜더링
	calendar.render();
}