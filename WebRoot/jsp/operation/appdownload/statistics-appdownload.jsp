<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.xj.utils.PropertyTool"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>App扫码下载统计_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/calendar.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath}/js/respond.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/calendar.js?version=<%=versionNum%>"></script>
</head>

<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<div class="time-chose" style="float:right;position:static;padding:6px 20px;">
							<input id="communityName" placeholder="小区名称"/>
							<input id="txtBeginDate" placeholder="请选择开始日期" readonly="readonly"/>
							<input id="txtEndDate" placeholder="请选择结束日期" readonly="readonly"/>
							<a href="javascript:void(0);" onclick="statAppDownload();">查询</a>
						</div>
					</div>
					<div class="statistics-public clearfix">
						<table>
							<thead><tr><th>时间</th><th>设备</th><th>小区</th><th>类型</th><th>扫码下载次数</th></tr></thead>
							<tbody id="appdownloadContainer"><tr><td colspan="5" style="text-align:center;color:red;">请填写要查询的小区</td></tr></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
function statAppDownload() {
	var communityName = $("#communityName"),
		txtBeginDate = $("#txtBeginDate"),
		txtEndDate = $("#txtEndDate");
	
	if (!communityName.val()) {
		alert("请输入小区名称!");
		communityName.focus();
		return;
	}
	
	schedule();
	$.ajax({
		url: "/api/v1/appDownloadStatistics/day?communityName=" + communityName.val() + "&startDay=" + txtBeginDate.val() + "&endDay=" + txtEndDate.val(),
		type: "GET",
		dataType: "json",
		success : function(data) {
			if (!data || data.length == 0) {
				$("#appdownloadContainer").html("<tr><td colspan='5' style='text-align:center;color:red;'>没有查询到数据!</td></tr>");
				return;
			}
			
			var html = "",
				ios = 0,
				android = 0;
			for (var i = 0; i < data.length; i++) {
				var d = data[i],
					terrace = d.terrace,
					result = d.result;
				if (terrace == "ios") {
					ios += result;
				} else if (terrace == "android") {
					android += result;
				}
				
				html += "<tr><td>" + d.time + "</td><td>" + terrace + "</td><td>" + d.communityName + "</td><td>" + d.type + "</td><td>" + result + "</td></tr>";
			}
			
			html = "<tr><td colspan='5'><strong>总计：</strong>&nbsp;&nbsp;&nbsp;&nbsp;iOS -> " + ios + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Andoird -> " + android + "</td></tr>" + html;
			
			$("#appdownloadContainer").html(html);
			
			onSchedule();
		},
		error: function() {
			onSchedule();
		}
	});
}
$(function() {
	selectMenu("appdownload");
});
</script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>