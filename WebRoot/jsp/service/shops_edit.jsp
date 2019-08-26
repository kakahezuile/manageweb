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
		<title>编辑商家_小间物业管理系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<!--


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
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/shops_phone_left.jsp"></jsp:include>
			<div class="express-right-body">
				<div class="repair-title-line">
					<p class="shops-title">编辑商家信息</p>
				</div>
				<div class="shop-edit">
					<div class="shop-edit-box">
						<span>商家名称</span><input type="text"/>
					</div>
					<div class="shop-edit-box">
						<span>商家电话</span><input type="text"/>

		-->

<script type="text/javascript">

function statUpShposEdit(shopId,shopName,phone){
document.getElementById("shops_name_up_id").value=shopName;
document.getElementById("shops_phone_up_id").value=phone;
document.getElementById("shops_shopId_up_id").value=shopId;


}
function upShopsEdit(){
	var shop_name_fei = document.getElementById("shops_name_up_id").value;
	var shop_phone_fei = document.getElementById("shops_phone_up_id").value;
	var shopId=document.getElementById("shops_shopId_up_id").value;
	var sort = document.getElementById("shops_phone_left_id_stor").value;
  			var water_supply_data = {
  				"shopName" : shop_name_fei ,
  				
  				"phone" : shop_phone_fei ,
  				"sort" : sort
  			};
     var path = "<%= path%>/api/v1/communities/${communityId}/shops/"
				+ shopId + "/";

		$.ajax({
			url : path,

			type : "PUT",

			data : JSON.stringify(water_supply_data),

			dataType : "json",

			success : function(data) {
				fanHui();
			},
			error : function(er) {
				alert(er);
			}
		});

	}
	function fanHui() {
		setNone();
		$("#shops_phone_id_fei").attr("style", "display:block");
		var sort = document.getElementById("shops_phone_left_id_stor").value;
		selectShopsPhone("", 1);
	}
</script>

	</head>
	<body>
		<input type="hidden" id="shops_shopId_up_id" value="" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/shops_phone_left.jsp"></jsp:include>
				<div class="express-right-body">
					<div class="repair-title-line">
						<p class="clean-rule-title">
							编辑商家信息
						</p>
					</div>
					<div class="shop-edit">
						<div class="shop-edit-box">
							<span>商家名称</span>
							<input type="text" id="shops_name_up_id" />
						</div>
						<div class="shop-edit-box">
							<span>商家电话</span>
							<input type="text" id="shops_phone_up_id" />
						</div>
						<div class="shop-edit-time">
							<span>营业时间</span>
							<input type="text" id="shops_phone_up_id" /><label>到</label><input type="text" id="shops_phone_up_id" />
						</div>
						<div class="shop-edit-change">
							<a href="javascript:;" onclick="upShopsEdit();">修改</a>
						</div>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>