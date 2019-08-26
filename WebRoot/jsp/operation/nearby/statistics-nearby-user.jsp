<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
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
<title>周边使用量_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/calendar.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/nearby/statistics-nearby-user.js?version=<%=versionNum %>223"></script>

<style type="text/css">
.upload-master-face-bg{background:url("../../../images/public/bg-blank-60.png") repeat;position:fixed;top:0;width:100%;height:100%;z-index:21;left:0;}
.loadingbox{height: 32px;width:32px;left: 50%;margin-left: -16px;margin-top: -16px;position: absolute;top: 50%;z-index: 22;}
</style>
</head>

<body>
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<ul>
							<li><a href="#" class="select">使用量</a></li>
						</ul>
					</div>
					<div class="statistics-public clearfix">
						<table>
							<thead><tr><th>时间</th><th>点击量</th><th>点击人数</th><th>购买量</th><th>购买人数</th><th>验码量</th></tr></thead>
							<tbody id="statistics-summary">
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td></tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="shop-dosage-detail-box">
					<div class="shop-dosage-detail">
						<div class="time-chose">
							<input readonly="readonly" id="txtBeginDate" value="请选择开始日期"/>
							<input readonly="readonly" id="txtEndDate" value="请选择结束日期"/>
							<a href="javascript:void(0);" onclick="nearby.statSelect();">查询</a>
						</div>
						<div class="repair-title">
							<a href="javascript:void(0);" onclick="nearby.swithTab(this, 'day');" class="select">日</a>
							<a href="javascript:void(0);" onclick="nearby.swithTab(this, 'week');">周</a>
							<a href="javascript:void(0);" onclick="nearby.swithTab(this, 'month');">月</a>
						</div>
					</div>
					<div class="shop-dosage-total" id="tab-day">
						<ul>
							<li><a href="javascript:nearby.stat('day', -1);">上一日</a></li>
							<li><span><b>统计时间</b></span><span id="dayBegin"></span><span>到</span><span id="dayEnd"></span></li>
							<li><a href="javascript:nearby.stat('day', 1);">下一日</a></li>
						</ul>
					</div>
					<div class="shop-dosage-total" id="tab-week" style="display:none">
						<ul>
							<li><a href="javascript:nearby.stat('week', -1);">上一周</a></li>
							<li><span><b>统计时间</b></span><span id="weekBegin"></span><span>到</span><span id="weekEnd"></span></li>
							<li><a href="javascript:nearby.stat('week', 1);">下一周</a></li>
						</ul>
					</div>
					<div class="shop-dosage-total" id="tab-month" style="display:none">
						<ul>
							<li><a href="javascript:nearby.stat('month', -1);">上一月</a></li>
							<li><span><b>统计时间</b></span><span id="monthBegin"></span><span>到</span><span id="monthEnd"></span></li>
							<li><a href="javascript:nearby.stat('month', 1);">下一月</a></li>
						</ul>
					</div>
					
					<div class="shop-dosage-list" id="list-day">
						<table>
							<thead><tr><th>时间</th><th>点击量</th><th>点击人数</th><th>购买量</th><th>购买人数</th><th>验码量</th></tr></thead>
							<tbody id="statistics-day"><tr class="even"><td colspan="6" style="text-align:center;color:red;">请稍等，正在统计中...</td></tr></tbody>
						</table>
					</div>
					<div class="shop-dosage-list" id="list-week" style="display:none">
						<table>
							<thead><tr><th>时间</th><th>点击量</th><th>点击人数</th><th>购买量</th><th>购买人数</th><th>验码量</th></tr></thead>
							<tbody id="statistics-week"><tr class="even"><td colspan="6" style="text-align:center;color:red;">请稍等，正在统计中...</td></tr></tbody>
						</table>
					</div>
					<div class="shop-dosage-list" id="list-month" style="display:none">
						<table>
							<thead><tr><th>时间</th><th>点击量</th><th>点击人数</th><th>购买量</th><th>购买人数</th><th>验码量</th></tr></thead>
							<tbody id="statistics-month"><tr class="even"><td colspan="6" style="text-align:center;color:red;">请稍等，正在统计中...</td></tr></tbody>
						</table>
					</div>
					<div class="shop-dosage-list" id="list-select" style="display:none">
						<table>
							<thead><tr><th>时间</th><th>点击量</th><th>点击人数</th><th>购买量</th><th>购买人数</th><th>验码量</th></tr></thead>
							<tbody id="statistics-select"><tr class="even"><td colspan="6" style="text-align:center;color:red;">请稍等，正在统计中...</td></tr></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	window.nearby.communityId = window.parent.document.getElementById("community_id_index").value;
	window.nearby.summary();
	selectMenu("nearby-user");
});
</script>
</html>