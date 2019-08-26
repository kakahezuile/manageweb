<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = com.xj.utils.PropertyTool.getPropertites("/configure.properties");
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
<title>新增福利_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<![endif]-->
<link href="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/css/bootstrap.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/css/bootstrap-datetimepicker.min.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/jquery-1.11.1.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/bootstrap.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/bootstrap-datetimepicker.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/bootstrap-datetimepicker.zh-CN.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/shop/welfare-add.js"></script>
</head>

<body>
<section>
	<div class="content clearfix center-personal-info-content">
		<jsp:include flush="true" page="./shop-manage-left.jsp">
			<jsp:param name="module" value="welfare"/>
		</jsp:include>
		<div class="nearby_right-body">
			<div class="shop-dosage">
				<ul>
					<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/welfare.jsp">福利</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/operation/shop/add-welfare.jsp" class="select">添加福利</a></li>
				</ul>
			</div>
			<div class="main">
				<form id="welfareForm" method="post" enctype="multipart/form-data">
					<input name="welfareId" type="hidden" disabled="disabled">
					<div class="clearfix">
						<div class="welfare-tag"><span>福利海报</span></div>
						<div class="welfare-content">
							<span class="welfare-img-box">
								<img src="${pageContext.request.contextPath}/images/upload-default.jpg" id="img-poster" alt="请选择福利海报图片" style="max-width:400px;">
								<input type="file" onchange="preview(this,'#img-poster');preview(this,'#preview-poster');$('name=posterValue').val('').attr('disabled', 'disabled');" name="poster" />
								<input type="hidden" name="posterValue" disabled="disabled" />
							</span>
						</div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>福利标题</span></div>
						<div class="welfare-content"><input name="title" type="text" placeholder="福利标题"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>福利价格</span></div>
						<div class="welfare-content"><input name="price" type="text" placeholder="如：9.9"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>详情图片</span></div>
						<div class="welfare-content">
							<div class="welfare-upload-img">
								<ul class="clearfix" id="content-list">
									<li class="welfare-upload">
										<a><input name="content" type="file" onchange="welfareAdd.addContent(this)"></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>要求人品值</span></div>
						<div class="welfare-content"><input name="charactervalues" type="text" placeholder="要求人品值"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>福利总数</span></div>
						<div class="welfare-content"><input name="total" type="text" placeholder="福利总数"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>活动截止日期</span></div>
						<div class="welfare-content"><input type="text" name="endTime" placeholder="活动截止日期"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>活动规则</span></div>
						<div class="welfare-content"><textarea name="rule" placeholder="输入活动规则" class="welfare-rule"></textarea></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>联系电话</span></div>
						<div class="welfare-content"><input name="phone" type="text" placeholder="联系电话"></div>
					</div>
					<div class="clearfix">
						<div class="welfare-tag"><span>发放说明</span></div>
						<div class="welfare-content"><input name="provideInstruction" type="text" placeholder="限40字（如10月31日本活动由狮子城物业统一发放）"></div>
					</div>
					<div class="welfare-submit">
						<a href="javascript:welfareAdd.showPreview();;">预览</a>
						<a href="javascript:welfareAdd.submit();">提交</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<div id="preview-shade" class="public-black" style="display:none;">&nbsp;</div>
<div id="preview-container" class="welfare-preview-box" style="display:none;">
	<div class="welfare-iphone-box">
		<p class="welfare-poster">
			<img id="preview-poster">
		</p>
		<div class="welfare-info">
			<p><b id="preview-title"></b></p>
			<p><span class="title">福利价格：</span><b id="preview-price"></b></p>
			<p><span class="title">福利人品值要求：</span><b id="preview-charactervalues"></b></p>
			<p><span class="title">福利总数：</span><b id="preview-total"></b></p>
			<p><span class="title">活动截止日期：</span><b id="preview-endTime"></b></p>
			<p><span class="title">活动规则：</span><b id="preview-rule"></b></p>
			<p><span class="title">联系电话：</span><b id="preview-phone"></b></p>
			<p><span class="title">发放说明：</span><b id="preview-provideInstruction"></b></p>
		</div>
		<div id="preview-content"></div>
	</div>
	<div class="welfare-preview-opt">
		<a href="javascript:welfareAdd.hidePreview();">返回编辑</a>
		<a href="javascript:welfareAdd.submit();">确认提交</a>
	</div>
</div>
<script type="text/javascript">
var communityId = window.parent.document.getElementById("community_id_index").value;
var welfareId = "${param.welfareId}" || "${welfareId}" || "0";
$(function() {
	welfareAdd.init("${message}");
});
</script>
</body>
</html>