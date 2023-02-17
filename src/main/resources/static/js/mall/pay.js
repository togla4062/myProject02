function btnPgClicked(){
	var title = $(".title").val();
	var payPrice = $(".payPrice").val();
	var merchant_uid = "ORD"+new Date().getTime();
	var data = {
			merchant_uid: merchant_uid, //주문번호
			name: title,
			amount: payPrice, // 숫자 타입
			buyer_email: $("#user-email").val(),
			buyer_name: $("#user-name").val(),
			buyer_tel: "",
			buyer_addr: "",
			buyer_postcode: ""
	 	};
	requestPay(data);
}


//iamport 결제창 띄우기
function requestPay(data) {
  // IMP.request_pay(param, callback) 결제창 호출
  IMP.request_pay({ // param
      pg: "html5_inicis",
      pay_method: "card",
      merchant_uid: data.merchant_uid, //주문번호
      name: data.name,
      amount: data.amount, //숫자타입
      buyer_email: data.buyer_email,
      buyer_name: data.buyer_name,
      buyer_tel: data.buyer_tel,
      buyer_addr: data.buyer_addr,
      buyer_postcode: data.buyer_postcode
  }, function (rsp) { // callback
      if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
        // jQuery로 HTTP 요청
        jQuery.ajax({
            url: "/mall/list", // 예: https://www.myservice.com/payments/complete
            method: "POST",
            headers: { "Content-Type": "application/json" },
            data: {
                imp_uid: rsp.imp_uid,
                merchant_uid: rsp.merchant_uid
            }
        }).done(function (data) {
          // 가맹점 서버 결제 API 성공시 로직
          alert("결제 완료. 떠나요!");
        })
      } else {
        alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
      }
    });
}
