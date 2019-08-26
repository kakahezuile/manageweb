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
<title>帮帮会员卡优惠说明</title>
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
		<div class="privilege">
			<div class="s-title">使用范围</div>
			<div class="b-content">发卡商家店内的所有消费</div>
			<div class="s-title">使用期限</div>
			<div class="b-content">长期有效</div>
			<div class="s-title">使用规则</div>
			<div class="b-content">折扣卡不可与店内的其它优惠活动叠加使用，并且只能用于线上支付。</div>
			<div class="s-title">操作说明</div>
			<div class="b-content">当您进入商家进行消费时，可直接点击此商家的折扣卡进行在线支付。您需要手动输入您的总消费金额，系统会根据卡的折扣自动计算出折扣后的金额请您支付。</div>

		<!-- 
			<div class="b-sub-title">特权</div>
			<div class="b-content">1、给活动/话题和生活圈加精华</div>
			<div class="b-content">2、置顶活动/话题和生活圈（置顶时长为6小时）</div>
			<div class="b-content">3、一天内可对同一人最多点赞5次</div>
			
			<div class="b-sub-title">奖励</div>
			<div class="b-content">每月3000帮帮币，每月1号中午12点核实帮主身份，核实后次日发放上个自然月的奖励（节假日顺延至下一个工作日）</div>
			
			<div class="b-title">副帮主特权</div>
			
			<div class="b-sub-title">荣誉</div>
			<div class="b-content">获得副帮主特殊身份头像标志</div>
			
			<div class="b-sub-title">特权</div>
			<div class="b-content">1、给活动/话题和生活圈加精华</div>
			<div class="b-content">2、一天内可对同一人最多点赞3次</div>
			
			<div class="b-sub-title">奖励</div>
			<div class="b-content">每月1000帮帮币。每月1号中午12点核实副帮主身份，核实后次日发放上个自然月的奖励（节假日顺延至下一个工作日）</div>
			
			<div class="b-title">长老特权</div>
			
			<div class="b-sub-title">荣誉</div>
			<div class="b-content">获得长老特殊身份头像标志</div>
			
			<div class="b-sub-title">特权</div>
			<div class="b-content">一天内可对同一人最多点赞2次</div>
			
			<div class="b-sub-title">奖励</div>
			<div class="b-content">每月100帮帮币，每月1号中午12点核实长老身份，核实后次日发放上个自然月的奖励（节假日顺延至下一个工作日）</div>
			
			<div class="b-title">帮主／副帮主／长老 的判定：</div>
			
			<div class="b-sub-title">首届</div>
			<div class="b-content">满足基本条件时，人品值高的成为首届帮主；</div>
			<div class="b-content">在满足基本条件的人中，人品值最高的成为首届帮主，第二名和第三名成为副帮主，第四至第九成为长老。</div>
			<div class="b-sub-title">首届后</div>
			<div class="b-content">每个月2号进行一次帮主、副帮主、长老的重新排名，必须满足以下基本条件</div>
			<div class="b-content">1、每月以人品值重新排列先后顺序，新晋升用户必须满足相对级别的基本要求，进入相应级别。</div>
			<div class="b-content">2、新晋升用户将重新排名原有用户的级别，未达到本级别排名的用户将被降级。</div>
			<div class="b-content">3、降级是根据用户的排名顺序自动降级</div>
		 -->
		</div>
	</section>
</body>
</html>