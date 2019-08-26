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
<title>添加保洁类型_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath}/js/fileUpload.js"></script>
</head>
<body>
	<jsp:include flush="true" page="../public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix center-personal-info-content">
			<jsp:include flush="true" page="../public/admin_sort_left.jsp"></jsp:include>
			<div class="main">
				<form  method="post" enctype="multipart/form-data" action="<%=basePath%>api/v1/communities/summary/homepic">
					<div class="clearfix">
						<div class="welfare-tag"><span>首页背景图</span></div>
						<div class="welfare-content" id="destination">
							<span class="welfare-img-box">
								<img src="${pageContext.request.contextPath}/images/upload-default.jpg" id="img-poster" alt="请选择用户头像" style="max-width:200px;">
								<input id="imgUpload" type="file"  name="poster" />
							</span>
						<div >
							<button type="submit">上传</button>
						</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
		
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	

	/**
	 * 获取首页图片
	 */
	function getCommunities(){
		$.ajax({
			url: "<%=basePath%>api/v1/communities/summary/homepic" ,
			type: "GET",
			dataType: "json",
			success: function(data) {
				$("#img-poster").attr("src",data.info);
				},
			error: function(e) {
				alert("出错了");
			}
		});
	}
	
	getCommunities();
});

</script>
</html>