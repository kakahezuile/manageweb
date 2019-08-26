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
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

	</head>
	<body>
	<input id="facilities_class" type="hidden" value="" >
	<input id="facilities_type" type="hidden" value="" >
		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="./shop-manage-left.jsp">
					<jsp:param name="module" value="nearby"/>
				</jsp:include>
				<div class="nearby_right-body">
					<div class="main clearfix">
						<div class="nearby-total clearfix">
							
						</div>
						<div class="nearby-search">
							<input type="text" placeholder="抢购活动名称 / 店家名称  "
								id="facilitiesName_nearby" />
							<a class="green-button" href="javascript:;"
								onclick="selectFacilitiesName('1');">搜索</a>
						</div>
						<div class="nearby-shop-list">
							<table>
								<tr>
									<td>阿拉兰牛肉面套餐原价29，特价9.9</td>
									<td>老家肉饼</td>
									<td>2015年6月15日</td>
									<td class="operation">
										<a href="javascript:;" class="green-button">编辑</a>
										<a href="javascript:;" class="red-button">删除</a>
									</td>
								</tr>
								<tr>
									<td>果然爱菠萝沙拉原价25，现仅需要10元就可以获得！</td>
									<td>渝阿婆</td>
									<td>2015年6月15日</td>
									<td>
										<a href="javascript:;" class="green-button">编辑</a>
										<a href="javascript:;" class="red-button">删除</a>
									</td>
								</tr>
							</table>
							<!--<ul id="nearby_list">
								<li>
									<div class="shop-detail">
										<div class="shop-name">
											<span>阿拉兰牛肉面套餐原价29，特价9.9</span>
										</div>
									</div>
									<div class="shop-type">
										<span>老家肉饼</span>
									</div>
									<div class="shop-open-time">
										<span>2015年6月15日</span>
									</div>
									<div class="">
										<a href="javascript:;" class="green-button">编辑</a>
										<a href="javascript:;" class="red-button">删除</a>
									</div>
								</li>
								<li>
									<div class="shop-detail">
										<div class="shop-name">
											<span>果然爱菠萝沙拉原价25，现仅需要10元就可以获得！</span>
										</div>
									</div>
									<div class="shop-type">
										<span>渝阿婆</span>
									</div>
									<div class="shop-open-time">
										<span><span>2015年6月15日</span></span>
									</div>
									<div>
										<a href="javascript:;" class="green-button">编辑</a>
										<a href="javascript:;" class="red-button">删除</a>
									</div>
								</li>
							</ul>
						--></div>

						<div class="divide-page" 
							style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


							<input type="hidden" id="nearby_list_number_get" />
							<input type="hidden" id="nearby_list_sizepage_get" />

							<a href="javascript:void(0);" onclick="getNearbyList(-3);">首页</a>
							<a href="javascript:void(0);" onclick="getNearbyList(-1);">上一页</a>
							当前第
							<font id="nearby_list_number"></font> 页 共
							<font id="nearby_list_sizepage"></font> 页 共
							<font id="nearby_list_sun_id"></font> 条

							<a href="javascript:void(0);" onclick="getNearbyList(-2);">下一页</a>
							<a href="javascript:void(0);" onclick="getNearbyList(-4);">尾页</a>
						</div>
					</div>
				</div>
			</div>
		</section>
	</body>

	<script type="text/javascript">
		countFacilitiesTop();
	</script>
</html>