<%@ page language="java" import="java.util.*, com.xj.utils.PropertyTool" pageEncoding="utf-8"%>

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
	<title>周边商铺_小间物业管理系统</title>
	<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
	<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/jquery-1.11.1.min.js?version=<%=versionNum%>"></script>
	<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
	<script src="${pageContext.request.contextPath }/js/shop/nearby.js?version=<%=versionNum %>"></script>
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
				<div class="shop-dosage">
					<ul>
						<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/nearby.jsp" class="select">抢购活动</a></li>
						<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/nearby-cover-scope.jsp">商家活动发布范围</a></li>
						<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/nearby-pay.jsp">店家充值</a></li>
					</ul>
				</div>
				<div class="main clearfix">
					<div class="nearby-total clearfix">
						
					</div>
					<div class="nearby-search">
						<input type="text" placeholder="抢购活动名称 / 店家名称  " id="facilitiesName_nearby" />
						<a class="green-button" href="javascript:;" onclick="selectNearbyCrazySalesShop(1,10);">搜索</a>
					</div>
					<div class="nearby-shop-list">
						<table id="facilitiesName_nearby_table">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td class="operation">
									<a href="javascript:;" class="green-button">编辑</a>
									<a href="javascript:;" class="red-button">删除</a>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td>
									<a href="javascript:;" class="green-button">编辑</a>
									<a href="javascript:;" class="red-button">删除</a>
								</td>
							</tr>
						</table>
					</div>
					<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">
						<input type="hidden" id="repairRecordPageNum_get" />
						<input type="hidden" id="repairRecordPageSize_get" />
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-3);">首页</a>
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-1);">上一页</a>
						当前第
						<font id="repairRecordPageNum"></font> 页 共
						<font id="repairRecordPageSize"></font> 页 共
						<font id="repairRecordSum"></font> 条
	
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-2);">下一页</a>
						<a href="javascript:void(0);" onclick="getRepairRecordList2(-4);">尾页</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
selectNearbyCrazySalesShop(1,10) ;
</script>
</html>