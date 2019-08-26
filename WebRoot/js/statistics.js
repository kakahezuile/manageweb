$(document).ready(function(){
	head_select();
});
function head_select(){
	$(".operation-nav").find("ul li a").removeClass("select");
	$(".operation-nav").find("ul li a[mark=statistics]").addClass("select");
	$(".left-body").find("ul li a").removeClass("select");
	$(".left-body").find("ul li a[mark=user]").addClass("select");
}
/*
// 日历控件
$(function() {
	$("#txtBeginDate").calendar({
		controlId : "divDate", // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
		speed : 200, // 三种预定速度之一的字符串("slow", "normal", or
		// "fast")或表示动画时长的毫秒数值(如：1000),默认：200
		complement : true, // 是否显示日期或年空白处的前后月的补充,默认：true
		readonly : true, // 目标对象是否设为只读，默认：true
		upperLimit : new Date(), // 日期上限，默认：NaN(不限制)
		lowerLimit : new Date("2011/01/01"), // 日期下限，默认：NaN(不限制)
		callback : function() { // 点击选择日期后的回调函数
			// alert("您选择的日期是：" + $("#txtBeginDate").val());
		}
	});
	$("#txtEndDate").calendar();
});
function stringToTime(string) {
	var f = string.split(' ', 2);
	var d = (f[0] ? f[0] : '').split('-', 3);
	var t = (f[1] ? f[1] : '').split(':', 3);
	return (new Date(parseInt(d[0], 10) || null, (parseInt(d[1], 10) || 1) - 1,
			parseInt(d[2], 10) || null, parseInt(t[0], 10) || null, parseInt(
					t[1], 10)
					|| null, parseInt(t[2], 10) || null)).getTime();

}

function alterMonth(type, startTime) {

	var myDate = new Date(startTime);
	var startTime = "";
	var endTime = "";
	if (num == 1) {
		var month = myDate.getMonth() + 1;
		startTime = myDate.getFullYear() + "-" + myDate.getMonth() + "-" + 1
				+ " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	} else if (num == 2) {
		var month = myDate.getMonth() + 2;
		var month2 = month + 1;
		startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month2 + "-" + 1 + " 00:00:00";

	} else {
		var month = myDate.getMonth() + 1;
		startTime = myDate.getFullYear() + "-" + myDate.getMonth() + "-" + 1
				+ " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";

	}
	return starTime + endTime;

}*/
