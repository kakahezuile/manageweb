$(document).ready(function(){
	head_select();
	left_select();
});
function head_select(){
	$(".admin_nav").find("ul li a").removeClass("selected");
	$(".admin_nav").find("ul li a[mark=sort]").addClass("selected");
}
function left_select(){
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=repair]").addClass("select");
}

