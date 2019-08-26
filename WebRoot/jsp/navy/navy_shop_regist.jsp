<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<%
String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
 %>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="" lang="en">
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
<script src="${pageContext.request.contextPath }/js/ajaxfileupload2.js"></script>
<script src="${pageContext.request.contextPath}/js/waterCarriage/water-add.js"></script>
<script src="${pageContext.request.contextPath}/js/fileUpload.js"></script>

<style type="text/css">
.div1{
	margin-left: 30%;
	margin-top: 10%;
}

</style>
</head>
<body>
	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${sessionScope.community_id}">
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%=basePath%>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display:none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/navy-head.jsp" />
	
<section>
	<div class="content clearfix">
		<div class="nearby_right-body">
			<div class="main">
				<form id="waterForm" method="post" enctype="multipart/form-data">
					<input name="welfareId" type="hidden" disabled="disabled">
					
					<div class="clearfix">
						<div class="welfare-tag"><span>用户名:</span></div>
						<div class="welfare-content"><input name="username" type="text" placeholder="用户名" required="required"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>密码:</span></div>
						<div class="welfare-content"><input name="password" type="password" placeholder="密码" required="required"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>确认密码</span></div>
						<div class="welfare-content"><input name="repassword" type="password" placeholder="密码" required="required"></div>
					</div>
						<div class="clearfix">
						<div class="welfare-tag"><span>店铺名:</span></div>
						<div class="welfare-content"><input name="shopName" type="text" placeholder="店铺名" required="required"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>店铺Logo</span></div>
						<div class="welfare-content" id="destination">
							<span class="welfare-img-box">
								<img src="${pageContext.request.contextPath}/images/upload-default.jpg" id="img-poster" alt="请选择用户头像" style="max-width:200px;">
								<input id="imgUpload" type="file"  name="poster" />
							</span>
						</div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>所在小区</span></div>
						<div class="welfare-content">
							<select name="communityId" id="communities" >
								<option value="1">天华小区</option>
								<option value="2">首邑溪谷</option>
							</select>
						</div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>店铺类型</span></div>
						<div class="welfare-content">
							<select name="sort" id="shopTypes">
								<option value="2">帮帮快店</option>
								<option value="13">帮帮周边</option>
							</select>
						</div>
					</div>
					
					<div class="clearfix">
						<div class="welfare-tag"><span>备注</span></div>
						<div class="welfare-content"><textarea name="remarks" placeholder="输入备注" class="welfare-rule"></textarea></div>
					</div>
					<div class="welfare-submit">
						<a href="javascript:waterAdd.submit('shop');">提交</a>
					</div>
				</form>
			</div>
			</div>
		</div>
<section>
</body>
<script type="text/javascript">
$(function() {
	var basePath =  '<%=basePath%>';
	window.basePath = basePath;
	window.waterAdd.getCommunities(); 
});

</script>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>