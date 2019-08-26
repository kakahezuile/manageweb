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
<title>帮帮</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/app.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
</head>
<body>
	<section>
		<div class="content">
			<div class="service-notice_title"><b>&nbsp;</b>收费标准</div>
			<div class="service-notice-item">
				<p>
					<span><b>服务费用：</b></span>
					<span class="service-notice-price">25元/小时 （2小时起）</span>
					<span>超时部分，小于10分钟不收费；10－30分钟，按半小时收费，超过半小时按1小时收费。</span>
				</p>
			</div>
			<div class="service-notice_title"><b>&nbsp;</b>服务范围</div>
			<div class="service-notice-item">
				<p>
					<span><b>卧室：</b></span>
					<span>床铺整理、物品整理、家具表面清洁、内窗玻璃清洁、门及门框清洁、地面清洁、垃圾清理。</span>
				</p>
				<p>
					<span><b>客厅&餐厅：</b></span>
					<span>物品整理、家具及家电表面清洁、内窗玻璃清洁、门及门框清洁、地面清洁、 垃圾清理。</span>
				</p>
				<p>
					<span><b>厨房：</b></span>
					<span>油烟机&灶具表面清洁、橱柜表面清洁 、洗菜池与台面清洁、物品整理、墙面 清洁门及门框清洁、地面清洁、垃圾清理。</span>
				</p>
				<p>
					<span><b>卫生间：</b></span>
					<span>洗漱台&洗脸盆清洁、沐浴房清洁、马桶刷洗、墙面清洁、门及门框清洁、地 面清洁、垃圾清理。</span>
				</p>
				<p>
					<span style="color:#ff6600">温馨提示：外窗玻璃、天花板及附属物(吊顶、灯具)、柜体内部、宗教陈设、 易损易碎品、古董字画及价值在人民币5000元以上的物品,不在日常保洁的服务范围内。</span>
				</p>
			</div>


			<div class="service-notice_title"><b>&nbsp;</b>服务标准</div>
			<div class="service-notice-item">
				<p>积极热情，微笑服务。</p>
				<p>守时守信，不磨洋工。</p>
				<p>专业高效，自备工具。</p>
			</div>
			<div class="service-notice_title"><b>&nbsp;</b>免责声明</div>
			<div class="service-notice_declare">
				<p>①业主应了解并知悉，帮帮保洁是北京小间科技发展有限公司（以下简称“小间科技”）为保洁员及业主提供保洁服务信息发布及访寻的居间服务平台。业主与保洁员通过帮帮保洁的居间服务，在双方之间达成独立的保洁服务协议，并承担相应的权利和义务。如因保洁员原因导致业主遭受相关财产及人身损害的，业主应直接向保洁员追偿，小间科技不承担因保洁员与业主之间保洁服务协议履行所致任何纠纷的赔偿责任。</p>
				<p>②小间科技将竭诚按业主的要求提供专业的服务，但不能百分百保证其提供的服务一定能满足业主的要求和期望。</p>
				<p>③小间科技提供业主与保洁员之间在平台上交往交流的管理等服务，小间科技不承揽业主家庭的具体工作，对保洁员及业主双方过错不负有任何责任。</p>
				<p>④对于保洁员的资料、证件等，小间科技将采用相关措施进行审核，但不能完全保证其内容的正确性、合法性或可靠性，相关责任由提供上述内容的保洁员负责。</p>
				<p>⑤对于业主的投诉，小间科技将认真、严格地核实，但不能保证最终的投诉结果能满足业主的所有心理预期，对于投诉内容侵犯他人隐私权、名誉权等合法权利的，所有法律责任由投诉者承担，与小间科技无关。</p>
			</div>
		</div>
	</section>
</body>
</html>