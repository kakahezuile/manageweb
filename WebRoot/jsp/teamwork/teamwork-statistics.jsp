<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
	
		String community_id=request.getParameter("community_id");
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

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
		<meta name="keywords" content="小间物业,小间物业管理系统" />
		<meta name="Description" content="小间物业,小间物业管理系统" />
		<title>注册用户数_小间运营系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-statistics.js?version=<%=versionNum %>"></script>
		<script type="text/javascript">
	
</script>
	</head>
	<body>
<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="<%=community_id %>">
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/teamwork-head.jsp" />
	
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/teamwork-left.jsp" >
				
				<jsp:param name="select"
												value="{select | <%=community_id %>}" />
												
												</jsp:include>
				<div class="right-body" style="border:none;">
					<div class="static-type">
						<ul>
							<li><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-statistics.jsp" class="select" >快店</a></li>
							<li class="static-more"><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-more.jsp?community_id=<%=community_id %>" >查看更多...</a></li>
						</ul>
					</div>
					<div class="static-abstract">
						<table>
							<tr>
								<td><span>本月点击量：</span><span id="this_month_click">1024</span></td>
								<td><span>成单量：</span><span id="this_month_end">102</span></td>
								<td><span>交易额：</span><span id="this_month_par">306.20</span></td>
								<td><span>分成比例：</span><span >2%</span></td>
								<td><span>收益：</span><span id="this_month_earnings">62.40</span></td>
								<td><span style="color: red;">已结：</span><span></span></td>
								<td><span style="color: red;">未结：</span><span></span></td>
							</tr>
						</table>
					</div>
					<div class="static-detail">
						<table>
							<tr>
								<th>交易时间</th>
								<th>点击量</th>
								<th>成单量</th>
								<th>交易金额</th>
								<th>收益</th>
								<th>详情</th>
							</tr>
							<tr class="odd">
								<td >昨日</td>
								<td id="to_day_click">0</td>
								<td id="to_day_end">0</td>
								<td id="to_day_par"> 0</td>
								<td id="to_day_earnings">0</td>
								<td><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-detail.jsp?timeType=to_day&community_id=<%=community_id %>">详情</a></td>
							</tr>
							<tr class="enen">
								<td >本周</td>
								<td id="this_week_click" >0</td>
								<td id="this_week_end" >0</td>
								<td id="this_week_par" >0</td>
								<td id="this_week_earnings">0</td>
								<td><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-detail.jsp?timeType=this_week&community_id=<%=community_id %>">详情</a></td>
							</tr>
							<tr class="odd">
								<td >上周</td>
								<td id="last_week_click">0</td>
								<td id="last_week_end">0</td>
								<td id="last_week_par">0</td>
								<td id="last_week_earnings">0</td>
								<td><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-detail.jsp?timeType=last_week&community_id=<%=community_id %>">详情</a></td>
							</tr>
						</table>
					</div>
				</div>	
			</div>
		</section>
	</body>
	
	<script type="text/javascript">
	getTryOut();
	</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
