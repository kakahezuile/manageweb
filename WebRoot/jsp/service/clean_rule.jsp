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
<title>服务需知_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
</head>
<body>
	<jsp:include flush="true" page="../public/head.jsp"/>
	<div>
		<jsp:include flush="true" page="../public/nav.jsp">
				<jsp:param name="list" value="{parameterValue | ${list }}" />
		</jsp:include>
	</div>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
			<div class="notice-right-body">
				<div class="repair-title-line">
					<p class="clean-rule-title">修改服务需知</p>
				</div>
				<div class="clean-rule-scale">
					收费标准
				</div>
				<div class="clean-rule-scale-conten">
					<ul>
						<li class="clearfix">
							<div class="clean-rule-scale-box">
								<div>
									<span>费用</span>
									<input class="clean-rule-scale-number" type="text" placeholder="输入费用">
									<span>元/小时</span>
								</div>
								<div>
									<span>内容</span>
									<input class="clean-rule-scale-info" type="text" placeholder="日常保洁（玻璃擦洗、家具、地面清洁）">
								</div>
							</div>
							<div class="clean-rule-scale-opt"><a href="javascript:;">添加</a></div>
						</li>
						<li class="clearfix">
							<div class="clean-rule-scale-box">
								<div>
									<span>费用</span>
									<input class="clean-rule-scale-number" type="text" placeholder="输入费用">
									<span>元/小时</span>
								</div>
								<div>
									<span>内容</span>
									<input class="clean-rule-scale-info" type="text" placeholder="日常保洁（玻璃擦洗、家具、地面清洁）">
								</div>
							</div>
							<div class="clean-rule-scale-opt"><a class="del" href="javascript:;">删除</a></div>
						</li>
					</ul>
				</div>
				<div class="clean-rule-scale">
					服务标准
				</div>
				<div class="clean-rule-scale-conten">
					<ul>
						<li class="clearfix">
							<div class="clean-rule-scale-item">
								<input type="text" placeholder="输入费用"><a href="javascript:;">添加</a>
							</div>
						</li>
						<li class="clearfix">
							<div class="clean-rule-scale-item">
								<input type="text" placeholder="输入费用"><a class="del" href="javascript:;">删除</a>
							</div>
						</li>
					</ul>
				</div>
				<div class="clean-rule-scale">
					免责声明
				</div>
				<div>
					<textarea class="clean-rule-disclaimer" rows="" cols="">任何人因使用本网站而可能遭致的意外及其造成的损失（包括因下载本网站可能链接的第         三方网站内容而感染电脑病毒），我们对此概不负责，亦不承担任何法律责任</textarea>
				</div>
				<div class="notice-send">
					<a href="javascript:;">保存</a>
				</div>
				
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>