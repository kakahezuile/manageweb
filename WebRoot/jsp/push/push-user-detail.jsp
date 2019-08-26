<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

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
		<title>推送统计_小间运营系统</title>
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
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-survey.js?version=<%=versionNum %>"></script>
		<script type="text/javascript">
	
</script>
	</head>
	<body>
	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="flag" value="${flag}">
	<input type="hidden" id="community_id" value="${communityId}">
			<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
		<header class="header">
			<div class="header-box clearfix">
				<div class="logo">
					<a class="logo" href="javascript:void(0);" onclick="myHeadGoHome();">
						<img src="${pageContext.request.contextPath }/images/public/logo-head.png" alt="LOGO" class="logo">
					</a>
				</div>
				<div class="tenement-name" id="community_id_name">推送统计</div>
				<div class="operation-nav-box">
					<nav class="operation-nav">
						<ul id="right_ul" class="clearfix">
							<li><a href="${pageContext.request.contextPath }/stat/PushMessage/communities/${communityId}/100/statNotificationPushMessage.do?page=1&pageSize=15" class="select1" id="push">通知推送统计</a></li>
							<li><a href="${pageContext.request.contextPath }/stat/PushMessage/communities/${communityId}/100/getPushMessagesUsers.do?page=1&pageSize=15" class="select2" id="user">用户接收统计</a></li>
						</ul>
					</nav>
				</div>
			</div>
		</header>
		<section>
			<div class="content clearfix">
				<div class="left-body">
					<input type="hidden" value="" id="com_id">
					<input type="hidden" value="" id="community_id">
					<input type="hidden" value="" id="emobId">
					<input type="hidden" value="" id="select">
					<ul id="com_ul">
						<li>
							<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/1/100/statNotificationPushMessage.do?page=1&pageSize=15" mark="" id="community_1" class="select1" >天华小区</a>
							<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/3/100/statNotificationPushMessage.do?page=1&pageSize=15" mark="" id="community_3" class="select3">狮子城小区</a>
							<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/5/100/statNotificationPushMessage.do?page=1&pageSize=15" mark="" id="community_5" class="select5">汇邦幸福城小区</a>
							<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/6/100/statNotificationPushMessage.do?page=1&pageSize=15" mark="" id="community_6" class="select6">天鹅湖小区</a>
						</li>
					</ul>
				</div>
				<div class="right-body" style="border:none;">
					<div class="push-table">
						<table id="communities_table">
							<tr>
								<th>消息创建时间</th>
								<th>消息内容</th>
								<th>推送次数</th>
								<th>当前状态</th>
								<th>推送成功的状态</th>
								<th>截止时间</th>
							</tr>
							
							<c:forEach items="${pageBean.pageData }" var="msg" varStatus="">
								<tr class="odd">
								<td>${msg.nickname }</td>
								<td><div class="push-title" title="${msg.content }">${msg.content }</div></td>
								<td>${msg.number }</td>
								<td><c:if test="${msg.type=='read' }">成功</c:if> <c:if test="${msg.type=='unread' }"><font color="red">失败</font></c:if></td>
								<td>暂时未知</td>
								<td>暂时未知</td>
								</tr>
							</c:forEach>
							
							
							
							<!--<tr class="odd">
								<td>6-29 18:36:54</td>
								<td><div class="push-title" title="报告××帮主，贵府电量已不足30度，恐耽误日常起居，还请您使用缴费功能，我们将快马加鞭为您续上电量。">报告××帮主，贵府电量已不足30度，恐耽误日常起居，还请您使用缴费功能，我们将快马加鞭为您续上电量。</div></td>
								<td>100</td>
								<td>成功</td>
								<td>循环推送</td>
								<td>6-29 18:36:54</td>
							</tr>
							<tr class="even">
								<td>6-28 10:16:15</td>
								<td><div class="push-title" title="报告××帮主，×月×日00：00为贵府成功续电××度，现电量为××，请帮主放心使用">报告××帮主，×月×日00：00为贵府成功续电××度，现电量为××，请帮主放心使用</div></td>
								<td>100</td>
								<td>成功</td>
								<td>主动唤醒</td>
								<td>6-29 18:36:54</td>
							</tr>
							<tr class="odd">
								<td>6-27 13:26:32</td>
								<td><div class="push-title" title="××帮主，承蒙您的厚爱，在此追加赠送贵府××度电，小小惊喜，不成敬意！3、电量低于30度">××帮主，承蒙您的厚爱，在此追加赠送贵府××度电，小小惊喜，不成敬意！3、电量低于30度</div></td>
								<td>100</td>
								<td><b class="failure">失败</b></td>
								<td><b class="failure">失败</b></td>
								<td>6-29 18:36:54</td>
							</tr>
						--></table>
					</div>

				</div>	
				
			<div   class="divide-page" style="float: left; width:  100% ; height: 50px; clear: both; text-align: center;">
			<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/${communityId}/${emobId }/100/getPushUserDetail.do?page=1&pageSize=15">首页</a>
			<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/${communityId}/${emobId }/100/getPushUserDetail.do?page=${pageBean.prev }&pageSize=15">上一页</a>
			当前第 <font id="activity_Page_Num" color="green">${pageBean.num}</font> 页
			共 <font id="activity_Page_Size">${pageBean.pageCount }</font> 页
			共 <font id="activity_Sum">${pageBean.rowCount }</font> 条
			
			<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/${communityId}/${emobId }/100/getPushUserDetail.do?page=${pageBean.next }&pageSize=15">下一页</a>
			<a href="${pageContext.request.contextPath }/stat/PushMessage/communities/${communityId}/${emobId }/100/getPushUserDetail.do?page=${pageBean.last }&pageSize=15">尾页</a>
			</div>
				
				
			</div>
		</section>
	</body>
	<script type="text/javascript">
	   //installsNum();
	   var flag = $("#flag").val();
	   var communityId = $("#community_id").val();

	   if(flag=="push"){
	   $("#push").attr("class","select");
	   }else if(flag=="user"){
	   $("#user").attr("class","select");
	   }

		document.getElementById("community_"+communityId).setAttribute("class", "select");
	</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
	
</html>
