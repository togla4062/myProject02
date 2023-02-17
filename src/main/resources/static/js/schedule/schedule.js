$(function() {
	$(".edit-mode").hide();
});
function chageEditMode(el) {
	$(el).parents('tr').children('.edit-mode').show();
	$(el).parents('tr').children('.show-mode').hide();
}
function chageShowMode(el) {
	$(el).parents('tr').children('.show-mode').show();
	$(el).parents('tr').children('.edit-mode').hide();
}
function changeDate() {
	var date = $("#changeDate").val() + "";
	window.location.href = "/scheduleList?clickedDate=" + date;
}
function scheduleDelCheck(el) {

	if (confirm("해당 일정을 정말로 삭제하시겠습니까?") == true) {    //확인
		$(el).parents('div').children("#scheduleDel").submit();
	} else {   //취소
		return false;
	}
}