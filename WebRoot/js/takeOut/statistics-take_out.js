$(document).ready(function() {
	head_select();
});
function head_select() {
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=statistics]").addClass("select");

	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=take_out]").addClass("select");

}
