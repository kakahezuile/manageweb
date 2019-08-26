<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
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
<title>商铺编辑_小间物业管理系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/shop/nearby-edit.js?version=<%=versionNum%>"></script>
</head>
<body>
	<input id="crazy_sales_id" type="hidden" value="${param.CrazySalesId}" >
	<input id="facilities_class" type="hidden" value="" >
	<input id="facilities_type" type="hidden" value="" >
	
	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="./shop-manage-left.jsp"></jsp:include>
			<div class="nearby_right-body">
				<div class="shop-dosage">
					<ul>
						<li><a class="select" href="./shop-manage-left.jsp">抢购活动</a></li>
						<li><a href="/jsp/operation/user/statistics-user-active.jsp">抢购活动出价设置</a></li>
					</ul>
				</div>
				<div class="main clearfix">
					<input id="nearby_edit_img_input" type="hidden"/>
					<div class="nearby-edit-img">
						<span class="nearby-img-box">
							<img id="nearby_edit_img">
						</span>
						<span class="nearby-edit-change">
							<a href="javascript:void(0);">修改图片</a>
							<input type="file"  id="crazySales_file_edit" onchange="preview(this,'#nearby_edit_img');" name="crazySales_file_edit"/>
						</span>
					</div>
					<div class="nearby-edit-item">
						<span>抢购标题</span>
						<input id="nearby_edit_title" class="title" type="text" placeholder="抢购活动标题">
					</div>
					<div class="nearby-edit-item">
						<span>抢购数量</span>
						<input id="nearby_edit_total"  type="text" placeholder="抢购活动数量">
					</div>
					<div class="nearby-edit-item">
						<span>每人限购</span>
						<input type="text" id="nearby_edit_per_limit" placeholder="每人限购数量">
					</div>
					<div class="nearby-edit-item">
						<span>抢购详情</span>
						<textarea class="edit-content" id="nearby_edit_descr"  placeholder="输入通知的内容"></textarea>
					</div>
					<div class="nearby-edit-save">
						<a href="javascript:;" onclick="upCrazySales();">保存</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>