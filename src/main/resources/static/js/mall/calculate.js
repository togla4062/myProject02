/**
 * 
 */
$(function(){
})
function myr() {
	var myr2 = document.getElementById('myr').value;
	calculateM(myr2);
}	
function calculateM(myr) {
	var rate = $('#rate').text();
    var result = eval('myr * rate'); // 식을 계산하고 result 변수에 저장합니다.
    var round = Math.round(result*100)/100;
    round = round.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    document.getElementById('krw').value = round;
}


function krw() {
	var krw2 = document.getElementById('krw').value;
	calculateK(krw2);
}
function calculateK(krw) {
	var rate = $('#rate').text();
    var result = eval('krw / rate'); // 식을 계산하고 result 변수에 저장합니다.
    var round = Math.round(result*100)/100;
    round = round.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    document.getElementById('myr').value = round;
}
