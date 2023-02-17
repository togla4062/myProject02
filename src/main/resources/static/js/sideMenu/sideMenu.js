$(function() {
	display();
	$(".menuList dt").click(menuClicked);
});
function menuClicked() {

	if ($(this).parent().children("dd").hasClass("target")) {
		$(this).parent().children("dd").removeClass("target");
	} else {
		$(this).parent().children("dd").addClass("target");
	}
	display();
	$(".target").show();
}
function display() {
	$(".menuList dd").hide();
}
