$(function() {
			
			defaultTime();
			function defaultTime(){ //날짜 기본값 오늘로 설정하는 함수
				today = new Date().toISOString().slice(0, 10);
				document.querySelector("#dateStart").value = today;
				document.querySelector("#dateEnd").value = today;
				
				//console.log(today);
			}
		});