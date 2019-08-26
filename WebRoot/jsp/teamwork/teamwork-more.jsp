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
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-more.js?version=<%=versionNum %>"></script>
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
					<div class="static-detail" id="static_month">
						
						<!--<table>
							<tr>
								<td colspan="9" class="static-more-title"><div class="static-collect">6月交易汇总</div></td>
							</tr>
							<tr>
								<th>交易类型</th>
								<th>点击量</th>
								<th>交易单量</th>
								<th>交易金额</th>
								<th>分成比例</th>
								<th>收益</th>
								<th>已结款</th>
								<th>未结款</th>
								<th>详情</th>
							</tr>
							<tr class="odd">
								<td>快店</td>
								<td>101</td>
								<td>16</td>
								<td>3411.5</td>
								<td>2%</td>
								<td>68.23</td>
								<td>48.23</td>
								<td>20</td>
								<td><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-detail.jsp">详情</a></td>
							</tr>
							<tr class="enen">
								<td>送水</td>
								<td>98</td>
								<td>6</td>
								<td>2040</td>
								<td>1%</td>
								<td>20.40</td>
								<td>18.40</td>
								<td>2.00</td>
								<td><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-detail.jsp">详情</a></td>
							</tr>
							<tr class="odd">
								<td>保洁</td>
								<td>87</td>
								<td>8</td>
								<td>1106.33</td>
								<td>3%</td>
								<td>33.19</td>
								<td>33.19</td>
								<td>0.00</td>
								<td><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-detail.jsp">详情</a></td>
							</tr>
						</table>
					--></div>
				</div>	
			</div>
		</section>
	</body>
	<script type="text/javascript">
	getTop();
	
	</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
