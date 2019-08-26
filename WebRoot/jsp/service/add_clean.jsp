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
<title>添加保洁人员_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">


</head>
<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
			<div class="add-master-body">
				<div class="repair-title-line">
					<p class="add-master-title">添加保洁人员</p>
				</div>
				<div class="add-master-info">
					<ul>
						<li>
							<span>真识姓名</span><input type="text" placeholder="如：李晓雪"/>
							<span>昵称</span><input type="text" placeholder="如：李阿姨"/>
						</li>
						<li>
							<span>地址</span><input type="text" placeholder="如：法华南里38号"/>
							<span>电话</span><input type="text" placeholder="如：13845411245"/>
						</li>
						<li>
							<span>身份证号</span><input type="text" placeholder="如：102114198012140532"/>
						</li>
					</ul>
				</div>
				
				<div class="add-master-opt clearfix">
					<div class="add-master-type">服务类型</div>
					<ul>
						<li><a class="select" href="javascript:;">通用</a></li>
					</ul>
				</div>
				<div class="add-master-upload-pic">
					<span>上传照片</span>
					<div class="add-master-pic-box clearfix">
						<img src="${pageContext.request.contextPath }/images/repair/add-master-pic.jpg"/>
						<p class="add-master-message">请上传保洁阿姨的清楚脸部正面照,以便用户更方便快速的找到自己满意的保洁阿姨。</p>
						<p class="add-master-button"><a href="javascript:;">上传照片</a></p>
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
	<div class="upload-master-face-bg"  style="display:none;">&nbsp;</div>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>