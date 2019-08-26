<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.xj.utils.PropertyTool"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
%>
<%
String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
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
	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${sessionScope.community_id}">
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
					<div class="shop-dosage">
						<ul>
							<li><a href="${pageContext.request.contextPath}/jsp/navy/navy_user_regist.jsp?community_id=<%=community_id%>&communityName=<%=str%>">+添加水军用户</a></li>
							<li><a href="${pageContext.request.contextPath}/jsp/navy/navy_shop_regist.jsp?community_id=<%=community_id%>&communityName=<%=str%>" >+添加水军店铺</a></li>
						</ul>
						<input type="text" id="querynavy" placeholder="用户名/昵称" style="height: 30px">
						<button  id="queryButton" onclick="window.waterAdd.searchNavy()">点击查询</button>
					</div>
					
					<div class="statistics-public clearfix">
						<table id="summary">
							<tr>
								<th>头像</th>
								<th>昵称</th>
								<th>账号</th>
								<th>
									<select name="communityId" id="communities" style="height: 30px" onchange="window.waterAdd.getNavys('first',this.value)">
										<option value="1" selected="selected">天华小区</option>
										<option value="2">首邑溪谷</option>
									</select> 
								</th>
								<th>楼号</th>
								<th>性别</th>
								<th>人品值</th>
								<th>生活圈条数</th>
								<th>个性签名</th>
								<th>备注</th>
								<th>编辑</th>
							</tr>
							 <tbody id="statistics-summary">
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="even"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
								<tr class="odd"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
							</tbody> 
						</table>
					</div>
						
					<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;" id="page">
						<input type="hidden" id="repairRecordPageNum_get" />
						<input type="hidden" id="repairRecordPageSize_get" />
	
						<a href="javascript:void(0);" onclick="window.waterAdd.getNavys('first',0);">首页</a>
						<a href="javascript:void(0);" onclick="window.waterAdd.getNavys('pre',0);">上一页</a>
						当前第
						<font id="repairRecordPageNum"></font> 页 共
						<font id="repairRecordPageSize"></font> 页 共
						<font id="repairRecordSum"></font> 条
	
						<a href="javascript:void(0);" onclick="window.waterAdd.getNavys('next',0);">下一页</a>
						<a href="javascript:void(0);" onclick="window.waterAdd.getNavys('last',0);">尾页</a>
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
	window.waterAdd.getNavys('first',0); 
	window.waterAdd.getCommunities();
});
</script>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>