$(document).ready(function(){
	head_select();
});
function head_select(){
	$(".navbar").find("ul li a").removeClass("select");
	$(".navbar").find("ul li a[mark=qianyuedianjia]").addClass("select");
	
}
function creatShop(){
	$(".blank-background").show();
	$(".signed-creat-box").show();
}
function closeWindow(){
	$(".blank-background").hide();
	$(".signed-creat-box").hide();
}