<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.xj.utils.PropertyTool"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String welfareId = request.getParameter("welfareId");
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
<title>购买详情_小间运营系统</title>
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
<script src="${pageContext.request.contextPath }/js/statistics-welfare.js?version=<%=versionNum %>"></script>
</head>
<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<ul>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/welfare/statistics-usage.jsp" class="select">使用量</a></li>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/welfare/statistics-welfare.jsp">福利统计</a></li>
						</ul>
					</div>
					<div class="welfare-date" ><span id="welfare-info">稻香村大米10kg，限量抢福利9.9(成功)</span></div>
					<div class="statistics-public clearfix">
						<table id="summary">
							<tr>
								<th>购买用户</th>
								<th>手机号</th>
								<th>实际付款</th>
								<th>使用帮帮币</th>
								<th>付款时间</th>
								<th>支付方式</th>
								<th>交易号</th>
								<th>订单号</th>
								<th>福利码</th>
							</tr>
							<tbody id="statistics-summary">
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
							</tbody> 
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
$(function() {
	
	var welfareId = <%=welfareId%>;
	window.welfare.communityId = window.parent.document.getElementById("community_id_index").value;
	window.welfare.getWelfareOrders(welfareId);//已购买的订单信息
	window.welfare.getWelfareInfo(welfareId); //获取订单消息
	selectMenu("welfare");
});
</script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>