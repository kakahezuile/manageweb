<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String communityId = request.getParameter("communityId");
	String appVersion = request.getParameter("appVersion");
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
					<div id="admin_community_title" class="community-titile"><span>狮子城</span>用户版本配置</div>
				</div>
				<div class="community-box">
					<table class="express-list-table" id="maintenanceMain">
						<tr>
							<th>用户昵称</th>
							<th>手机号</th>
							<th>App版本</th>
							<th style="text-align:right;">
								<input class="search-input" type="text" placeholder="输入用户昵称/手机号" id="search-input" >
								<a class="search-btn" href="javascript:void(0);" onclick="window.appUpdate.getCommunityUsers('first');">&nbsp;&nbsp;</a>
							</th>
						</tr>
						<tr class="odd">
							<td>乐呵呵</td>
							<td>13812363525</td>
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
							<td>不知所措</td>
							<td>13595854585</td>
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
							<td>真的是我</td>
							<td>18542525245</td>
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
			
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunityUsers('first');">首页</a>
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunityUsers('pre');">上一页</a>
					当前第
					<font id="repairRecordPageNum"></font> 页 共
					<font id="repairRecordPageSize"></font> 页 共
					<font id="repairRecordSum"></font> 条
			
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunityUsers('next');">下一页</a>
					<a href="javascript:void(0);" onclick="window.appUpdate.getCommunityUsers('last');">尾页</a>
				</div>
			</div>
		</div>
	</section>
	<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	window.basePath = '<%=basePath%>';
	window.appUpdate.communityId='<%=communityId%>' ;
	window.appUpdate.appVersion='<%=appVersion%>' ;
	window.appUpdate.getCommunityUsers('first');
});

</script>
</html>