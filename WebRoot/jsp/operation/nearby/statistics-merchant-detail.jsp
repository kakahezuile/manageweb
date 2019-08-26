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
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>统计概况_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath}/js/respond.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js?version=<%=versionNum%>"></script>
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
							<li><a href="${pageContext.request.contextPath}/jsp/operation/nearby/statistics-nearby-survey.jsp">统计概况</a></li>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/nearby/statistics-nearby-merchant.jsp" class="select" >商家统计</a></li>
							<li><a href="${pageContext.request.contextPath}/jsp/operation/nearby/statistics-nearby-community.jsp" >小区统计</a></li>
						</ul>
					</div>
					<div class="statistics-public clearfix">
						<table id="shopDetails">
							<tr>
								<th>抢购活动名称</th>
								<th>发布小区</th>
								<th>出价金额</th>
								<th>限购量</th>
								<th>抢购量</th>
								<th>验码量</th>
							</tr>
							<tr class="even">
								<td colspan="6" style="text-align:center;color:red;">请稍等，正在查询中...</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
nearby.shopDetails("${param.shopEmobId}");
</script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>