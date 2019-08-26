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
<title>周边商铺_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/admin_shop_sort.js"></script>

<script type="text/javascript">

var communityId = window.parent.document.getElementById("community_id_index").value;
function qiShong(){

	var path = "/api/v1/communities/"
			+ communityId
			+ "/shops/shopLimit/2";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			   data = data.info;
			   document.getElementById("shop_id_id").value=data.shopId;
				document.getElementById("qi_id").innerHTML="当前起送费："+data.deliverLimit;
		},
		error : function(er) {
		}
	});

}

function upShong(){
  var shop_id_id=document.getElementById("shop_id_id").value;
  var deliverLimit=document.getElementById("deliverLimit").value;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/shops/upShop/"+shop_id_id+"?deliverLimit="+deliverLimit;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
		alert("成功");
			  qiShong();
			  
		},
		error : function(er) {
		}
	});

}

</script>
	</head>
	<body>
		<input id="shop_id_id" type="hidden" value="">
		<input id="facilities_class" type="hidden" value="">
		<input id="facilities_type" type="hidden" value="">
		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="./shop-manage-left.jsp">
					<jsp:param name="module" value="quick_shop"/>
				</jsp:include>
				<div class="nearby_right-body">
					<div class="shop-dosage">
						<ul>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/quick-shop.jsp" class="select">分类配置</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/quick-shop.jsp">起送费配置</a></li>
						</ul>
					</div>
					<div class="main clearfix">
						<div class="main clearfix">
							<div class="adduser-main">
								<div class="adduse-item">
									<p id="qi_id">当前起送费：20</p>
								</div>
								<div class="adduse-item">
									<span>快店起送费</span>
									<input type="text" name="fastShopDescription"
										id="deliverLimit" placeholder="如：v1 v2" />
								</div>
							</div>
							<div class="adduse-add-btn">
								<a class="admin-green-button" href="javascript:void(0);"
									onclick="upShong();">修改</a>
							</div>
						</div>
						

					</div>
				</div>
			</div>
		</section>
		<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
	
	</body>
	<div id="bg_upnearby" class="activity-blank-bg" style="display: none;">
		&nbsp;
	</div>
	

<script type="text/javascript">
	qiShong();
</script>
</html>