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
<title>添加维修师傅_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

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
			<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
			<div class="add-master-body">
				<div class="repair-title-line">
					<p class="add-master-title">员工设置</p>
					<a class="del-master" href="javascript:;">删除员工</a>
				</div>
				<div class="add-master-info">
					<ul>
						<li>
							<span>手机号</span><input class="master-username" type="text" readonly="readonly" placeholder="13845411245"/>
							<span>真实姓名</span><input type="text" placeholder="如：李晓明"/>
						</li>
						<li>
							<span>昵称</span><input type="text" placeholder="如：李师傅"/>
							<span>电话</span><input type="text" placeholder="如：13845411245"/>
						</li>
						<li>
							<span>地址</span><input type="text" placeholder="如：法华南里38号"/>
							<span>身份证号</span><input type="text" placeholder="如：102114198012140532"/>
						</li>
					</ul>
				</div>
				
				<div class="add-master-opt clearfix">
					<div class="add-master-type">服务类型</div>
					<ul>
						<li><a class="select" href="javascript:;">弱电</a></li>
						<li><a class="select" href="javascript:;">强电</a></li>
						<li><a class="select" href="javascript:;">综合</a></li>
						<li><a href="javascript:;">上下水</a></li>
					</ul>
				</div>
				<div class="add-master-upload-pic">
					<span>上传照片</span>
					<div class="add-master-pic-box clearfix">
						<img src="${pageContext.request.contextPath }/images/repair/add-master-pic.jpg"/>
						<p class="add-master-message">请上传维修师傅的清楚脸部正面照,以便用户更方便快速的找到自己满意的维修师傅。</p>
						<p class="add-master-button"><a href="javascript:;">修改照片</a></p>
					</div>
				</div>
				<div class="add-master-save">
					<a href="javascript:;">保存</a>
				</div>
			</div>
		</div>
	</section>
	
	<!--   上传弹出窗     -->
	
	<div class="upload-master-face-box clearfix" style="display:none;">
		<div class="upload-master-face-title">上传头像<a class="upload-master-face-close" title="关闭" href="javascript:close_operate_div();">&nbsp;</a></div>
		<div class="upload-master-face-preview">
			<div class="upload-master-face-former">
				<img alt="师傅头像" id="target" src="${pageContext.request.contextPath }/images/upload-img.jpg" class="uploadImages" />
			</div>
			<div class="upload-master-face-finish">
				<div class="upload-master-face-finish-title">预览</div>
				<div class="upload-master-face-finish-img">
						<div id="preview_box"><img id="crop_preview" alt="" src="${pageContext.request.contextPath }/images/upload-img-preview.jpg" class="uploadImages" /></div>
				</div>
			</div>
		</div>
		<div class="upload-master-face-submit"><a class="green-button" href="javascript:;" id="save_img">保存</a><a class="green-button" href="javascript:close_operate_div();">取消</a></div>
	</div>
	<div class="upload-master-face-bg" style="display:none;">&nbsp;</div>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>