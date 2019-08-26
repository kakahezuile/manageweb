<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'iframeInner.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">

		/**
* 从 file 域获取 本地图片 url
*/
function getFileUrl(sourceId) {
var url;
if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
url = document.getElementById(sourceId).value;
} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
}
return url;
}

/**
* 将本地图片 显示到浏览器上
*/
function preImg(sourceId, targetId) {

var url = getFileUrl(sourceId);
var imgPre = window.parent.document.getElementById(targetId);
imgPre.src = url;
} 



</script>


  </head>
  
  <body>
    <form id="community_service_form"  enctype="multipart/form-data" method="post">
	    <input type="text" value="" name="community_service_main_status" id="community_service_main_status" />
	    <input type="text" value="" name="user_up_master_id" id="user_up_master_id" />
	    <input type="text" value="" name="community_service_main_phone" id="community_service_main_phone" />
	    <input type="text" value="" name="community_service_password" id="community_service_password" />
	    <input type="text" value="" name="community_service_main_username" id="community_service_main_username" />
	    <input type="text" value="" name="community_service_main_nickname" id="community_service_main_nickname" />
	    <input type="text" value="" name="community_service_main_shopPhone" id="community_service_main_shopPhone" />
	    <input type="text" value="" name="community_service_main_adress" id="community_service_main_adress" />
	    <input type="text" value="" name="community_service_main_idcard" id="community_service_main_idcard" />
	    <input type="text" value="" name="community_service_main_shopId" id="community_service_main_shopId" />
	    <input type="text" value="" name="community_service_main_service_array" id="community_service_main_service_array" />
	    <input type="file" name="addMasterUploadFile" id="addMasterUploadFile" accept="image/*" style="display: block;"  onchange="preImg('addMasterUploadFile','add_master_img_id');" />
	    <input type="text" value="" name="community_service_role" id="community_service_role" />
	    <input type="submit" value="aaaa"/>
    </form>
  </body>
</html>
