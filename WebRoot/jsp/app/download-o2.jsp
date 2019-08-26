<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version") + "123456";
	String communityId = (String)request.getParameter("communityId");
	String type = (String)request.getParameter("type");
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
<meta name="keywords" content="邻居,邻居帮帮,小区,交友,社区,生活,社交,活动,兴趣爱好,拼车,邻里,生活圈,话题,牛人,物业,业主,周边,求助" />
<meta name="Description" content="邻居帮帮是一款以邻里社交为切入点的手机APP，可为你提供更加可信、真实的社交环境，在这里你可以找到有共同兴趣的邻居一起健身、打牌、旅游。和邻居二手置换、拼车，找邻居寄养宠物。和邻居中的医生、教师交朋友。和邻居探讨宽带哪家快、医院哪家好。团结邻居有组织的解决物业、供暖等小区问题。" />
<title>邻居帮帮客户端下载</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/download-new.css?version=<%=versionNum %>3" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
<script>
    (function (doc, win) {
      var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
          var clientWidth = docEl.clientWidth;
          if (!clientWidth) return;
          docEl.style.fontSize = 20 * (clientWidth / 320) + 'px';
        };

      if (!doc.addEventListener) return;
      win.addEventListener(resizeEvt, recalc, false);
      doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
</script>
</head>

<body>
	<input type="hidden" id="path_t" value="<%= path%>"/>
	<input type="hidden" id="communityId" value="${requestScope.communityId}"/>
	<input type="hidden" id="type_id" value="${requestScope.type}"/>
	<section>
		<div class="download-content">
			<div class="download-img"><img class="bg" src="${pageContext.request.contextPath}/images/app/download/download-bg.jpg"></div>
			<p class="logo-box"><img src="${pageContext.request.contextPath}/images/app/download/download-logo.png"></p>
			<p class="btn-box"><a href="javascript:;" id="btn-download"><img src="${pageContext.request.contextPath}/images/app/download/download-btn.png"></a></p>
			<!-- <div class="download">
				<a href="javascript:;" id="btn-download">立刻下载</a>
			</div> -->
		</div>
	</section>
	<div class="blank-bg" id="blank_bg" style="display:none;">
		<div id="android-word" class="tip-word">
			<p class="tip-img"><img src="${pageContext.request.contextPath}/images/app/line-tip.png"/></p>
			<p class="tip-content">微信内无法下载，请点击右上角&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/app/more.png">&nbsp;&nbsp;选择&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/app/browser.png">&nbsp;&nbsp;下载安装。</p>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/jsp/app/download-o2.js?version=<%=versionNum %>1"></script>
</html>