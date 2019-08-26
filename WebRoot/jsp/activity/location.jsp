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
<title>活动群组敏感词_小间物业管理系统</title>
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
<script src="${pageContext.request.contextPath }/js/nearby.js"></script>
</head>
<body>
	<jsp:include flush="true" page="../public/head.jsp"/>
	<div style="display:none;">
		<jsp:include flush="true" page="../public/nav.jsp">
				<jsp:param name="list" value="{parameterValue | ${list }}" />
		</jsp:include>
	</div>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/activity_left.jsp"></jsp:include>
			<div class="right-body" style="border:none;">
				<div class="location-head">
					<table>
						<tr class="location-head-name">
							<td>法轮功</td>
							<td>狼人</td>
							<td>2015-04-09 10:18:24</td>
							<td class="close-group" rowspan="2"><a href="javascript:;">关闭群</a></td>
						</tr>
						<tr class="location-head-content">
							<td>敏感词</td>
							<td>群组名称</td>
							<td>出现时间</td>
						</tr>
					</table>
				</div>
				<div id="sensitiveCenter" class="sensitiveCenter">
		   			<ul>
		   				<li class="text-right">
		   					<img src="${pageContext.request.contextPath }/images/chat/serviceHeader.png"/>
		   					<div class="right-speak-frame">
		   						<div class="right-speaker-position">3号楼2单元603</div>
			   					<div class="right-speak-box">
									<div class="right-speak">
										法轮功这帮人真是没事干，天天就是发这发那的，没事在家呆着多好。
									</div>
								</div>
							</div>
							<span>&nbsp;</span>
		   				</li>
		   				<li class="text-time">
		   					<div class="time"> <span class="timeBg left"></span> 9:47 <span class="timeBg right"></span> </div>
		   				</li>
		   				<li class="text-left">
		   					<img src="${pageContext.request.contextPath }/images/repair/b2.jpg"/>
		   					<div class="left-speak-frame">
		   						<div class="left-speaker-position">狮子城-李阿姨</div>
			   					<div class="left-speak-box">
									<div class="left-speak">
										就是的，我们小区也有发传单的，估计快要被抓了。
									</div>
								</div>
							</div>
							<span>&nbsp;</span>
		   				</li>
		   				<li class="text-right">
		   					<img src="${pageContext.request.contextPath }/images/chat/serviceHeader.png"/>
		   					<div class="right-speak-frame">
			   					<div class="right-speaker-position">3号楼2单元603</div>
			   					<div class="right-speak-box">
									<div class="right-speak">
										就是的，我们小区也有发传单的，估计快要被抓了。就是的，我们小区也有发传单的，估计快要被抓了。就是的，我们小区也有发传单的，估计快要被抓了。就是的，我们小区也有发传单的，估计快要被抓了。
									</div>
								</div>
							</div>
							<span>&nbsp;</span>
		   				</li>
					</ul>
					
		   		</div>
				
				
				
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>