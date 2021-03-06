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
<title>统计概况_小间运营系统</title>
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
<script src="${pageContext.request.contextPath}/js/nearby/statistics-nearby-dosage.js?version=<%=versionNum%>"></script>
</head>

<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<ul>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/nearby/statistics-nearby-survey.jsp" class="select">统计概况</a></li>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/nearby/statistics-nearby-merchant.jsp">商家统计</a></li>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/nearby/statistics-nearby-community.jsp">小区统计</a></li>
						</ul>
					</div>
					<div class="statistics-public clearfix">
						<table id="summary">
							<tr>
								<th>日期</th>
								<th>下载量</th>
								<th>注册量</th>
								<th>发布活动量</th>
								<th>出价总额</th>
							</tr>
							<tr class="even"><td></td><td></td><td></td><td></td><td></td></tr>
							<tr class="odd"><td></td><td></td><td></td><td></td><td></td></tr>
							<tr class="even"><td></td><td></td><td></td><td></td><td></td></tr>
							<tr class="odd"><td></td><td></td><td></td><td></td><td></td></tr>
							<tr class="even"><td></td><td></td><td></td><td></td><td></td></tr>
							<tr class="odd"><td></td><td></td><td></td><td></td><td></td></tr>
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
							<a href="javascript:void(0);" onclick="nearby.swithTab(this, 'day');" class="select">今日</a>
							<a href="javascript:void(0);" onclick="nearby.swithTab(this, 'week');">上周</a>
							<a href="javascript:void(0);" onclick="nearby.swithTab(this, 'month');">上月</a>
						</div>
					</div>
					<div class="shop-dosage-total" id="tab-day">
						<ul>
							<li><a href="javascript:nearby.prevDay();">上一日</a></li>
							<li><span><b>统计时间</b></span><span id="dayBegin"></span><span>到</span><span id="dayEnd"></span></li>
							<li><a href="javascript:nearby.nextDay();">下一日</a></li>
						</ul>
					</div>
					<div class="shop-dosage-list" id="list-day">
						<table>
							<tr>
								<th>日期</th>
								<th>下载量</th>
								<th>注册量</th>
								<th>发布活动量</th>
								<th>出价总额</th>
							</tr>
							<tr class="even">
								<td colspan="5" style="text-align:center;color:red;">请稍等，正在统计中...</td>
							</tr>
						</table>
					</div>
					<div class="shop-dosage-total" id="tab-week" style="display:none">
						<ul>
							<li><a href="javascript:nearby.prevWeek();">上一周</a></li>
							<li><span><b>统计时间</b></span><span id="weekBegin"></span><span>到</span><span id="weekEnd"></span></li>
							<li><a href="javascript:nearby.nextWeek();">下一周</a></li>
						</ul>
					</div>
					<div class="shop-dosage-list" id="list-week" style="display:none">
						<table>
							<tr>
								<th>日期</th>
								<th>下载量</th>
								<th>注册量</th>
								<th>发布活动量</th>
								<th>出价总额</th>
							</tr>
							<tr class="even">
								<td colspan="5" style="text-align:center;color:red;">请稍等，正在统计中...</td>
							</tr>
						</table>
					</div>
					<div class="shop-dosage-total" id="tab-month" style="display:none">
						<ul>
							<li><a href="javascript:nearby.prevMonth();">上一月</a></li>
							<li><span><b>统计时间</b></span><span id="monthBegin"></span><span>到</span><span id="monthEnd"></span></li>
							<li><a href="javascript:nearby.nextMonth();">下一月</a></li>
						</ul>
					</div>
					<div class="shop-dosage-list" id="list-month" style="display:none">
						<table>
							<tr>
								<th>日期</th>
								<th>下载量</th>
								<th>注册量</th>
								<th>发布活动量</th>
								<th>出价总额</th>
							</tr>
							<tr class="even">
								<td colspan="5" style="text-align:center;color:red;">请稍等，正在统计中...</td>
							</tr>
						</table>
					</div>
					<div class="shop-dosage-list" id="list-select" style="display:none">
						<table>
							<tr>
								<th>日期</th>
								<th>下载量</th>
								<th>注册量</th>
								<th>发布活动量</th>
								<th>出价总额</th>
							</tr>
							<tr class="even">
								<td colspan="5" style="text-align:center;color:red;">请稍等，正在统计中...</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
$(function() {
	nearby.summary();
	nearby.statDay();
	
	selectMenu("nearby-shop");
});
</script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>