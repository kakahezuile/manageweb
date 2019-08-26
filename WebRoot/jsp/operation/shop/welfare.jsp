<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = com.xj.utils.PropertyTool.getPropertites("/configure.properties");
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
<title>新增福利_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<![endif]-->
<link href="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/css/bootstrap.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/css/bootstrap-datetimepicker.min.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/jquery-1.11.1.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/bootstrap.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/bootstrap-datetimepicker.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/bootstrap-datetimepicker.zh-CN.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/shop/welfare.js"></script>
</head>

<body>
<section>
	<div class="content clearfix center-personal-info-content">
		<jsp:include flush="true" page="./shop-manage-left.jsp">
			<jsp:param name="module" value="welfare"/>
		</jsp:include>
		<div class="nearby_right-body">
			<div class="shop-dosage">
				<ul>
					<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/welfare.jsp" class="select">福利</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/add-welfare.jsp">添加福利</a></li>
				</ul>
			</div>
			<div class="main">
				<div class="welfare-box">
					<table>
						<thead>
							<tr>
								<th>产品图片</th>
								<th>产品名称</th>
								<th>产品状态</th>
								<th>订单详情</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody id="welfare-list">
							<tr>
								<td colspan="5" style="height:100px;text-align:center;color:red;">福利正在加载中...</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>

<div id="welfare-notify-bg" class="public-black" style="display:none;">&nbsp;</div>
<div id="welfare-notify" class="welfare-note-box" style="display:none;height:500px;">
	<p class="welfare-user-img"><img src="http://7d9lcl.com2.z0.glb.qiniucdn.com/common_pic_welfare_avatar.png"></p>
	<p id="notify-title"></p>
	<p class="welfare-notice-content"><textarea id="notify-content" placeholder="请输入要通知的内容"></textarea><input id="notify-welfareId" type="hidden"></p>
	<p>
		<input type="checkbox" id="welfare-status-paid" checked="checked">支付成功用户
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="checkbox" id="welfare-status-refunded">退款用户
	</p>
	<p class="welfare-operation">
		<a href="javascript:welfares.notify(601);">发送成功消息</a>
		<a href="javascript:welfares.notify(602);">发送失败消息</a>
	</p>
	<p class="welfare-operation">
		<a href="javascript:welfares.notify(603);">发送普通消息</a>
		<a href="javascript:welfares.hideNotify();">取消</a>
	</p>
</div>
<script type="text/javascript">
var communityId = window.parent.document.getElementById("community_id_index").value;
$(function() {
	welfares.list();
});
</script>
</body>
</html>