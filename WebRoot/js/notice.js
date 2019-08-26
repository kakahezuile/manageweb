$(document).ready(function(){
	head_select();
});
function head_select(){
	$(".navbar").find("ul li a").removeClass("select");
	$(".navbar").find("ul li a[mark=gonggatongzhi]").addClass("select");
}
