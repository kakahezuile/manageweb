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
<title>通知详情_小间物业管理系统</title>
<%


String bulletinId=request.getParameter("bulletinId");

 %>
 
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>
<script type="text/javascript">

	var communityId = window.parent.document
			.getElementById("community_id_index").value;
function getPublishDetail(bulletinId){
	$("#gonggao_referesh").html("");
	var path = "<%= path%>/api/v1/communities/"+communityId+"/bulletin/detail?bulletinId="+bulletinId;
	$.post(path,function(data){
	data=data.info;
	var createTime=data.createTime;
	var myCreateDate = new Date(createTime*1000);
	var month=myCreateDate.getMonth()+1;
		//	console.info(myCreateDate.Format("yyyy-MM-dd "));
	var sysDate = myCreateDate.getFullYear() + "-" +month + "-" + myCreateDate.getDate();
	document.getElementById("notice_datail_theme").innerHTML=data.theme;
	document.getElementById("notice_datail_user_name").innerHTML=data.userName;
	document.getElementById("notice_datail_sysDate").innerHTML=sysDate;
	var repair_overman = $("#notice_datail_object");
	repair_overman.empty();
		repair_overman.append("<b>通知对象</b>：");
	if(data.senderObject=="portion"){
	   var listUser=data.listUser;
	   for ( var i = 0; i <listUser.length; i++) {
		     var listUser2="";
		     var listUser2="	<span>"+listUser[i].username+"  "+listUser[i].phone+"  "+listUser[i].userFloor+listUser[i].room+"</span>";
		     repair_overman.append(listUser2);
	   }
	}else{
	 repair_overman.append("<span> 小区全部用户</span>");
	}
	document.getElementById("notice_datail_text").innerHTML=data.bulletinText;
	
	},"json");
}

</script>
</head>
<body>

	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../common/notice-left.jsp"></jsp:include>
			<div class="notice-detail-content">
				<div class="notice-detail-head">
					<div class="notice-detail-title" id="notice_datail_theme">停水通知</div>
					<div class="notice-detail-sender">
						<b>发送人</b>：<span id="notice_datail_user_name">王班长</span>
					</div>
					<div class="notice-detail-time">
						<b>发送时间</b>：<span id="notice_datail_sysDate">2015-03-11  17:25</span>
					</div>
					
					<div class="notice-detail-recivers" id="notice_datail_object">
					</div>
				</div>
				<div class="notice-detail-info" id="notice_datail_text">
				</div>
			</div>
			
		</div>
	</section>
</body>

<script type="text/javascript">

getPublishDetail("<%=bulletinId%>");
notice_left("notice_notice_history");
</script>
</html>