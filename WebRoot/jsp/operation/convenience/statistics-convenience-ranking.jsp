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

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>快店_小间运营系统</title>
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
<script src="${pageContext.request.contextPath }/js/convenience/statistics-convenience-ranking.js?version=<%=versionNum %>"></script>

</head>
<body>
		<input type="hidden" id="time_day" value="0"/>
		<input type="hidden" id="master_repir_sort_fei" value="0"/>
		<input type="hidden" id="master_repir_startTime" />
		<input type="hidden" id="master_repir_endTime" />
		<input type="hidden" id="is_list_detail" />
		<input type="hidden" id="shop_id_detail" />
		<input type="hidden" id="date_type_get" />
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<ul>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/convenience/statistics-convenience-dosage.jsp" >使用量</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/convenience/statistics-convenience-turnover.jsp" >营业额</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/convenience/statistics-convenience-comment.jsp">投诉</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/convenience/statistics-convenience-time.jsp">服务时间段</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/convenience/statistics-convenience-ranking.jsp" class="select">购买排行</a></li>
						</ul>
					</div>
					<div class="shop-dosage-summary clearfix">
					</div>
				</div>
				<div class="shop-dosage-detail-box">
					<div class="shop-dosage-list">
						<table id="statistics_list_id">
						</table>
					</div>
					
				</div>
			</div>
		</div>
	</section>
		<script type="text/javascript">
		
         getQuickShopList("trade_num");

//	quickShopData();
	
	</script>
</body>
<script
		src="${pageContext.request.contextPath }/js/convenience/statistics-convenience.js"></script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>



















