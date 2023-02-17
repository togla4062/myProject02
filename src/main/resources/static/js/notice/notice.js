$(function() {
	$(".board-edit-mode").hide();
	$(".reply-edit-mode").hide();
});
function boardEditModeOn() {
	$(".board-veiw-mode").hide();
	$(".board-edit-mode").show();
}
function boardEditModeOff() {
	$(".board-edit-mode").hide();
	$(".board-veiw-mode").show();
}
function BoardRemoveCheck() {

 if (confirm("게시글을 정말 삭제하시겠습니까?") == true){    //확인
     document.removefrm.submit();
 }else{   //취소
     return false;
 }
}
function BoardEditCheck() {

 if (confirm("게시글을 수정하시겠습니까?") == true){    //확인
     document.boardEdit.submit();
 }else{   //취소
     return false;
 }
}