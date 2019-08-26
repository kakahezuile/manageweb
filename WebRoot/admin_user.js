$(document).ready(function(){
	head_select();
});
function head_select(){
	$(".admin_nav").find("ul li a").removeClass("selected");
	$(".admin_nav").find("ul li a[mark=user]").addClass("selected");
}
