function EditModeclicked() {
	var no = $("#empNo").val();

	if (confirm("수정 페이지로 이동 하시겠습니까?") == true) {    //확인
		location.href = "/ozc/groupDetail/" + no
	} else {   //취소
		return false;
	}
}
	
