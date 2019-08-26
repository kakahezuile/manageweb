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
<title>缴费_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

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
	<div>
		<jsp:include flush="true" page="../public/nav.jsp">
				<jsp:param name="list" value="{parameterValue | ${list }}" />
		</jsp:include>
	</div>
	<section>
		<div class="content clearfix" style="border:none;background:none;">
			<jsp:include flush="true" page="../public/payment_left.jsp"></jsp:include>
			<div class="activity-right-body">
				<div class="payment-head">
					<div class="payment-tab">
						<a class="select" href="javascript:void(0);">电费</a>
						<a href="javascript:void(0);">水费</a>
						<a href="javascript:void(0);">燃气费</a>
						<a href="javascript:void(0);">宽带费</a>
					</div>
					<div class="payment-summary clearfix">
						<p>当前缴费<span>4</span>笔</p>
						<p>缴费金额<span>1200</span></p>
					</div>
				</div>
				<div class="payment-list">
					<div class="payment-filter">
						<a class="select" href="javascript:;">默认分类</a>
						<a href="javascript:;">按金额分类</a>
					</div>
					<div class="payment-operate">
						<span class="checkbox-style">
							<input type="checkbox" value="1" id="checkboxInput" name="" />
		  					<label for="checkboxInput"></label>
		  				</span>
						<label>全选</label>
						<a class="payment-notice" href="javascript:;">通知</a>
						<a class="payment-print" href="javascript:;">打印</a>
					</div>
					<div class="payment-item-all">
						<ul>
							<li>
								<div>
									<span class="checkbox-style">
										<input type="checkbox" value="1" id="checkboxInput1" name="" />
					  					<label for="checkboxInput1"></label>
					  				</span>
					  				<span class="payment-order">订单号：12345876904</span>
					  				<span class="payment-time">下班时间:2015-2-13 12：30</span>
								</div>
								<div class="payment-item-detail">
									<table>
										<tr>
											<td><img src="/images/repair/b1.jpg"/></td>
											<td>王朝</td>
											<td>18513595854</td>
											<td>
												<p>2号楼4单元201</p>
												<p>房主-<span>王小明</span></p>
											</td>
											<td><span>200</span>元</td>
											<td>等待时长</td>
											<td><a href="javascript:;">缴费通知</a></td>
										</tr>
									</table>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
	</section>
</body>


<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>