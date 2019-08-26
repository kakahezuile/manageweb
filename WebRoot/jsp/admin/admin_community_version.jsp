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
<title>添加物业_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/appUpdate.js"></script>
</head>
<body>
	<jsp:include flush="true" page="../public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix" style="background:#fff;">
			<jsp:include flush="true" page="../public/admin_community_menu.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix" style="position:relative;">
					<div id="admin_community_title" class="community-titile">小区App版本设置</div>
					<div>
						<a class="update-all" herf="javascript:void(0);" onclick="window.appUpdate.updateAll()">点击升级</a>
					</div>
					<div class="admin_community_update">全部小区升级到&nbsp;&nbsp;<select id="update-all"></select></div>
				</div>
				<div class="community-box">
					<table class="express-list-table" id="maintenanceMain">
						<tr>
							<th>小区名称</th>
							<th>当前版本</th>
							<th>最新版本</th>
							<th style="text-align:right;">
								<input class="search-input" type="text" placeholder="输入小区名称" id="search-input">
								<a class="search-btn" href="javascript:void(0);" onclick="window.appUpdate.queryCommunitiesByName();">&nbsp;&nbsp;</a>
							</th>
						</tr>
						<tr class="odd">
							<td><a class="community" href="${pageContext.request.contextPath }/jsp/admin/admin_personal_version.jsp">狮子城</a></td>
							<td><span>1.1</span></td>
							<td>
								<select>
									<option>1.0</option>
									<option>1.1</option>
									<option>1.2</option>
									<option>1.3</option>
								</select>
							</td>
							<td style="text-align:right;">
								<a href="javascript:showEdit();">升级版本</a>
							</td>
						</tr>
						<tr class="even">
							<td><a class="community" href="${pageContext.request.contextPath }/jsp/admin/admin_personal_version.jsp">天华小区</a></td>
							<td><span>1.2</span></td>
							<td>
								<select>
									<option>1.0</option>
									<option>1.1</option>
									<option>1.2</option>
									<option>1.3</option>
								</select>
							</td>
							<td style="text-align:right;">
								<a href="javascript:showEdit();">升级版本</a>
							</td>
						</tr>
						<tr class="odd">
							<td><a class="community" href="${pageContext.request.contextPath }/jsp/admin/admin_personal_version.jsp">首邑溪谷</a></td>
							<td><span>1.2</span></td>
							<td>
								<select>
									<option>1.0</option>
									<option>1.1</option>
									<option>1.2</option>
									<option>1.3</option>
								</select>
							</td>
							<td style="text-align:right;">
								<a href="javascript:showEdit();">升级版本</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;" id="page">
					<input type="hidden" id="repairRecordPageNum_get" />
					<input type="hidden" id="repairRecordPageSize_get" />
			
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunities('first');">首页</a>
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunities('pre');">上一页</a>
					当前第
					<font id="repairRecordPageNum"></font> 页 共
					<font id="repairRecordPageSize"></font> 页 共
					<font id="repairRecordSum"></font> 条
			
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunities('next');">下一页</a>
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunities('last');">尾页</a>
				</div>
			</div>
		</div>
	</section>
	<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
	</div>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	window.basePath = '<%=basePath%>';
	window.appUpdate.getCommunities('first');
});

</script>
</html>