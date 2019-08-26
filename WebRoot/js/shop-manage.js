$(document).ready(function(){
	head_select();
});
function head_select(){
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=shopManage]").addClass("select");
}
