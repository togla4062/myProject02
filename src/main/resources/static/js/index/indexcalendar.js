$(function() {
	calendar();
});

function calendar() {
	// calendar element 취득
	var calendarEl = $('#calendar')[0];
	// full-calendar 생성하기
	var calendar = new FullCalendar.Calendar(calendarEl, {
		height: '320px', // calendar 높이 설정
		expandRows: true, // 화면에 맞게 높이 재설정
		slotMinTime: '06:00', // Day 캘린더에서 시작 시간
		slotMaxTime: '23:00', // Day 캘린더에서 종료 시간
		// 해더에 표시할 툴바
		headerToolbar: {
			left: 'prev',//'prev, next',
			center: 'title',
			right: 'next'
		},
		initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
		//initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
		navLinks: false, // 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크
		editable: false, // 이벤트 위치 변경 가능 기능
		selectable: true, // 달력 일자에 내용 추가 가능 기능
		nowIndicator: true, // 현재 시간 마크
		dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
		locale: 'ko', // 한국어 설정

		/*eventAdd : function(obj) { // 이벤트가 추가되면 발생하는 이벤트
							console.log(obj);
						},
						eventChange : function(obj) { // 이벤트가 수정되면 발생하는 이벤트
							console.log(obj);
						},
						eventRemove : function(obj) { // 이벤트가 삭제되면 발생하는 이벤트
							console.log(obj);
						},*/
		select: function(arg) { // 캘린더에서 드래그로 이벤트를 생성할 수 있다.
		location.href="/scheduleList?clickedDate=" + arg.startStr;
		/*	var title = prompt('금일 일정을 넣어주세요:');
			if (title) {
				calendar.addEvent({
					title: title,
					start: arg.start,
					end: arg.end,
					allDay: arg.allDay
				})
			}*/
			calendar.unselect()
		},
		// 이벤트 

		//events: data

	});
	// 캘린더 랜더링
	calendar.render();
}