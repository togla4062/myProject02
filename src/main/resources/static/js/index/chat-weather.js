/**
 * 
 */

/*$(function(){
	$("#question").keyup(questionKeyuped);
});*/
function openChat(){
	setConnectStated(true);//챗창보이게처리
	connect();
	//mqConnect();
}

function showMessage(message) {
    $("#chat-content").append(message);
	//대화창 스크롤을 항상 최하위에 배치   
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}

function setConnectStated(isTrue){
	if(isTrue){//true
		$("#btn-chat-open").hide();
		$("#chat-disp").show();
	}else{
		$("#btn-chat-open").show();
		$("#chat-disp").hide();
	}
	//챗봇창 화면 클리어
	$("#chat-content").html("");
}

function disconnect() {
    setConnectStated(false);
    console.log("Disconnected");
}
//버튼클릭시 접속
function connect() {
	var formData={message:"안녕", order:0};
	sendMessage(formData);
}

function sendMessage(formData){
	
	//주소에 대한 좌표확인
	
	if(formData.order==1){
		console.log("입력된 주소의 좌표값확인");
		getPositionOfAddress(formData);
		return;
	}
	
	exec(formData);
	
}
function exec(formData){
	$.ajax({
		url:`/weather-bot/${formData.order}`,
		type:"post",
		data:formData,
		success:function(responsedHtml){
			showMessage(responsedHtml);
			if(formData.order==3){
				$("form").each(function(){
					this.reset();
				});
				connect();
			}
		}
	});
}

function getPositionOfAddress(formData){
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	var rs={};
	geocoder.addressSearch(formData.message, function(result, status) {
			
		if (status === kakao.maps.services.Status.OK) {
			rs=dfs_xy_conv("toXY", result[0].y, result[0].x);
			
		}else{
			rs['x']=0;
			rs['y']=0;
		}
		console.log("x:"+rs.x);
		console.log("y:"+rs.y);
		formData.nx=rs.x;
		formData.ny=rs.y;
		if(rs.x==0){
			showMessage(inputTagWrontText("잘못입력된 장소입니다.</br>다시입력해주세요"));
			return;}
		exec(formData);
	});
	
}


function inputTagWrontText(text){
	var now=new Date();
	var ampm=(now.getHours()>11)?"오후":"오전";
	var time= ampm + now.getHours()%12+":"+now.getMinutes();
	var message=`
		
		<div class="msg bot flex">
			<div  class="icon">
				<img src="/image/icon/robot.png">
			</div>
			<div class="message">
				<div class="part">
					<p>${text}</p>
				</div>
				<div class="time">${time}</div>
			</div>
		</div>
	`;
	return message;
}


function inputTagString(text){
	var now=new Date();
	var ampm=(now.getHours()>11)?"오후":"오전";
	var time= ampm + now.getHours()%12+":"+now.getMinutes();
	var message=`
		<div class="msg user flex end">
			<div class="message">
				<div class="part">
					<p>${text}</p>
				</div>
				<div class="time">${time}</div>
			</div>
		</div>
	`;
	return message;
}
//메뉴클릭시 메뉴 텍스트 화면에 표현 
function menuclicked(el){
	var text=$(el).text().trim();
	var fToken=$(el).siblings(".f-token").val();
	console.log("-----> fToken:"+fToken+"----");
	var message=inputTagString(text);
	showMessage(message);
}

//엔터가 입력이되면 질문을 텍스트 화면에 표현 
function questionKeyuped(event){
	if(event.keyCode!=13)return;
	btnMsgSendClicked()
}

//전송버튼 클릭이되면 질문을 텍스트 화면에 표현
function btnMsgSendClicked(){
	var question=$("#question").val().trim();//질문에대한답
	var forms=$(".form");
	var form=$(forms[forms.length-1]);
	var formData={
		message:question,
		order:form.find(".order").val(),
		areaInfo:form.find(".areaInfo").val(),
		nx:form.find(".nx").val(),
		ny:form.find(".ny").val(),
		fcstDate:form.find(".fcstDate").val(),
		fcstTime:form.find(".fcstTime").val(),
	};
	
	
	if(question=="" || question.length<2){
		showMessage(inputTagWrontText("두 글자 이상 입력해주세요"));
		return;
	}
	
	//검색 시간은 현재보다 최신으로 해야함
	//20230215 01:00시의 경우 20230214 23:00으로 조회된다
	//근데 시간 설정하기 귀찮으니까 작은 날짜 + 작은 시간은 검색 불가능하도록 하자
	var tempToday = new Date();
	var tempYear = tempToday.getFullYear();
	var tempMonth = ('0' + (tempToday.getMonth() + 1)).slice(-2);
	var tempDay = ('0' + tempToday.getDate()).slice(-2);
	var tempBaseDate = tempYear + tempMonth + tempDay;
	
	if(formData.order=="2" && formData.message < tempBaseDate){
		showMessage(inputTagWrontText("과거 기상정보는 조회 불가능합니다."));
		return;
	}
	//미래 검색할때
	var tomorrow= new Date(tempToday.setDate(tempToday.getDate() + 1));
	tempYear = tomorrow.getFullYear();
	tempMonth = ('0' + (tomorrow.getMonth() + 1)).slice(-2);
	tempDay = ('0' + tomorrow.getDate()).slice(-2);
	var empBaseDate2 = tempYear + tempMonth + tempDay;
	
	if(formData.order=="2" && formData.message > empBaseDate2){
		showMessage(inputTagWrontText("금일~익일 기상정보만 조회 가능합니다."));
		return;
	}
	
	// 0000은 건너뛴다 당일 25일 0300시에 25일 0000시 검색한다면? -> 검색결과 없음
	// 애매한것이 있음 현재시각 0000~0159 비교하면 23:00데이터 가져올수 있으니까 과거 기상정보 조회가능
	var tempH = ('0' + tempToday.getHours()).slice(-2);
	var tempM = ('0' + tempToday.getMinutes()).slice(-2);
	var tempHHMM = tempH+tempM
	
	/*//0000이면 0001로 바꿔서 비교한다.
	if(formData.order=="3" && formData.message == "0000") {
		formData.message="0001"
	}*/
	if(formData.order=="3" 
	&& formData.message < tempHHMM){
		showMessage(inputTagWrontText("과거 기상정보는 검색불가능합니다."));
		return;
	}
	//메세지 서버로 전달
	console.log(formData);
	//var order=$(forms[forms.length-1]).find(".order");
	sendMessage(formData);
	 
	var message=inputTagString(question);
	showMessage(message);//사용자가 입력한 메세지 채팅창에 출력
	$("#question").val("");//질문 input 리셋
}