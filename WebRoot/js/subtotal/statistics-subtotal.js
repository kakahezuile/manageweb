$(document).ready(function() {
	head_select();
});
/*
 * function head_select(){ $(".operation-nav").find("ul li
 * a").removeClass("select"); $(".operation-nav").find("ul li
 * a[mark=statistics]").addClass("select"); $(".left-body").find("ul li
 * a").removeClass("select"); $(".left-body").find("ul li
 * a[mark=shop]").addClass("select"); }
 */
function head_select() {
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=subtotal]").addClass("select");
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=subtotal]").addClass("select");

}
