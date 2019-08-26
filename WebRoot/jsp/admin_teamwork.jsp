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
<title>添加物业_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/admin_community.js"></script>
<script type="text/javascript">



function getCommunity() {
	var path = "/api/v1/communities/summary/getListCommunityQ";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			 var repair_overman = $("#admin_community_stop");
			repair_overman.empty();
			var liList="";
			for(var i = 0; i < data.length; i++){
			   var com=document.getElementById("community_id_"+data[i].communityId);
			   if(com==null){
			      liList+="<li><a href=\"javascript:;\" id=\"community_id_"+data[i].communityId+"\" title=\"关闭服务\">"+data[i].communityName+"</a></li>";
			   }
			   
			}
			repair_overman.append(liList);
		},
		error : function(er) {
		}
	});
}
function getCommunityQ() {
	var path = "/api/v1/communities/summary/getListCommunityEmobId/e";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			 var repair_overman = $("#admin_community_start");
			repair_overman.empty();
			var liList="";
			for(var i = 0; i < data.length; i++){
			    liList+="<li><a href=\"javascript:;\"  id=\"community_id_"+data[i].communityId+"\" title=\"关闭服务\">"+data[i].communityName+"</a></li>";
			}
			repair_overman.append(liList);
			getCommunity();
			
		},
		error : function(er) {
		}
	});
}
function addObject(community_id) {

}

function delObject(community_id){

}




</script>
</head>
<body>
	<jsp:include flush="true" page="public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix">
			<jsp:include flush="true" page="public/admin_community_menu.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix" style="position:relative;">
					<div id="admin_community_title" class="user-titile">运营合作伙伴</div>
				</div>
				
				<div class="community-box">
					<div class="community-item-title">运营合作小区</div>
					<div class="community-item-open clearfix">
						<ul id="admin_community_start">
							<li><a href="javascript:;" title="关闭服务">业主活动</a></li>
							<li><a href="javascript:;" title="关闭服务">缴费</a></li>
							<li><a href="javascript:;" title="关闭服务">快店</a></li>
							<li><a href="javascript:;" title="关闭服务">外卖</a></li>
							<li><a href="javascript:;" title="关闭服务">紧急号码</a></li>
							<li><a href="javascript:;" title="关闭服务">送水</a></li>
							<li><a href="javascript:;" title="关闭服务">维修</a></li>
						</ul>
					</div>
					<div class="community-item-title">未分配合作伙伴小区</div>
					<div class="community-item-close clearfix">
						<ul id="admin_community_stop">
							<li><a href="javascript:;" title="开启服务">业主活动</a></li>
							<li><a href="javascript:;" title="开启服务">缴费</a></li>
							<li><a href="javascript:;" title="开启服务">快店</a></li>
							<li><a href="javascript:;" title="开启服务">外卖</a></li>
							<li><a href="javascript:;" title="开启服务">紧急号码</a></li>
							<li><a href="javascript:;" title="开启服务">送水</a></li>
							<li><a href="javascript:;" title="开启服务">维修</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>