$(document).ready(function() {
	var communityId = document.getElementById("communityId").value;
	var type_id = document.getElementById("type_id").value;
	var path_t = document.getElementById("path_t").value;
	
	var u = navigator.userAgent,
		isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1, //android终端或者uc浏览器
		isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		isWeChat = /MicroMessenger/i.test(u),//微信内打开
		btn = $("#btn-download");
	
	if (isAndroid) {
		if (isWeChat) {
			btn.on("click", function() {
				$("#blank_bg").show();
			});
		} else {
			btn.attr("href", path_t + "/api/v1/apps/android/version/addAppDownload/android/" + communityId + "/" + type_id);
		}
	} else {
		btn.on("click", function() {
			$.ajax({
				url: path_t + "/api/v1/apps/ios/version/addAppDownload/ios/" + communityId + "/" + type_id,
				type : "GET"
			});
			
			location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=xj.property&g_f=991653";
		});
	}
});