<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.xj.utils.PropertyTool"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String emobId = request.getParameter("emobId");
	String nickname = new String(request.getParameter("nickname").getBytes("iso8859-1"),"utf-8");
	String communityName = new String(request.getParameter("communityName").getBytes("iso8859-1"),"utf-8");
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
<title>福利统计_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/calendar.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/navy.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath}/js/respond.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/calendar.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/waterCarriage/water-add.js"></script>
</head>
<body>
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%=basePath%>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display:none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/navy-head.jsp" />
	
	<section>
		<div class="content clearfix">
		<jsp:include flush="true" page="../public/navy-left.jsp" >
				<jsp:param name="select" value="{parameterValue | 3}" />
				<jsp:param name="communityId" value="{parameterValue | 5}" />
		</jsp:include>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div style="height: 70px;"><font color="green" size="11"><%=communityName %>小区的<%=nickname %>发布的生活圈</font></div>
					
					<div class="statistics-public clearfix">
						<table id="summary">
							<tr>
								<th>话题内容</th>
								<th>获赞人品</th>
								<th>获评论量</th>
								<th>发布时间</th>
								<th>操作</th>
							</tr>
							 <tbody id="statistics-summary">
								<tr class="even"><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td></tr>
							</tbody> 
						</table>
					</div>
					
					<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">
						<input type="hidden" id="repairRecordPageNum_get" />
						<input type="hidden" id="repairRecordPageSize_get" />
	
						<a href="javascript:void(0);" onclick="window.waterAdd.getlifeCircles('<%=communityName %>','<%=emobId%>','first');">首页</a>
						<a href="javascript:void(0);" onclick="window.waterAdd.getlifeCircles('<%=communityName %>','<%=emobId%>','pre');">上一页</a>
						当前第
						<font id="repairRecordPageNum"></font> 页 共
						<font id="repairRecordPageSize"></font> 页 共
						<font id="repairRecordSum"></font> 条
	
						<a href="javascript:void(0);" onclick="window.waterAdd.getlifeCircles('<%=communityName %>','<%=emobId%>','next');">下一页</a>
						<a href="javascript:void(0);" onclick="window.waterAdd.getlifeCircles('<%=communityName %>','<%=emobId%>','last');">尾页</a>
					</div>
				</div>
				
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
$(function() {
	var basePath =  '<%=basePath%>';
	window.basePath = basePath;
	window.waterAdd.getlifeCircles('<%=communityName %>','<%=emobId%>','first'); 
});
</script>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>