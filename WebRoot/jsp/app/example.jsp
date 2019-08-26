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
<title>抢购活动示例</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/app.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/complain.js"></script>
</head>
<body>
	<section>
		<div class="example-body">
			<div class="example-title">研磨时光金枪鱼三明治+咖啡三选一，原价62元现15元</div>
			<div class="example-content">研磨时光金枪鱼三明治+咖啡三选一，原价62元现15元
【8月13日—23日8:30-18:00到店凭二维码消费】一份金枪鱼三明治，加一杯香醇四溢的研磨时光经典咖啡，早餐午餐下午茶皆宜！咖啡口味三选一：研磨、拿铁、摩卡。快节奏的SOHO间隙，静静的坐在店里享受这片刻的宁静吧~【温馨提示】下单成功的小伙伴记得一定要在【8月13日—23日8:30-18:00】到店里消费哦！逾期就无法享受特价了。</div>
			<div class="lost-time">限时抢购剩余时间：<span>12天</span></div>
			<div class="example-img">
				<img src="${pageContext.request.contextPath }/images/app/example.jpg">
				<span class="looknumber">被浏览次数：120</span>
				<div class="residue">
					<span class="shopping-num">每人限抢<b>2</b>份</span>
					<span class="surplus-num">当前剩余<b>18</b>份</span>
				</div>
			</div>
			<div class="example-detail">
				<div class="purchase-box">
					<div class="purchase">
						<span class="sub">&nbsp;</span>
						<span class="purchase-number">1</span>
						<span class=add>&nbsp;</span>
					</div>
					<span class="purchase-btn">抢购</span>
				</div>
				<div class="example-open-time">
					<span>研磨时光咖啡营业时间：8:00-21:00</span>
				</div>
				<div class="example-phone">18515356585<b>&nbsp;</b></div>
				<div class="example-place">望京SOHO T2底商2120<b>&nbsp;</b></div>
			</div>
		</div>
	</section>
</body>
</html>