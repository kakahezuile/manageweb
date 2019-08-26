<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Properties properties = PropertyTool
			.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<%
String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
%>
<%

Integer lastQuitTime = null;
if (session.isNew()){
	
	Cookie cookies[]=request.getCookies();
	for(int i=0;i<cookies.length;i++) {
		if("quitTime".equals(cookies[i].getName())) {
			lastQuitTime = Integer.parseInt(cookies[i].getValue());
			break;
		}
	}
	if(lastQuitTime==null){
		lastQuitTime = (int)(System.currentTimeMillis()/1000l);
	}
	//登录时记录访问时间
	Integer newTime = (int) (System.currentTimeMillis()/1000l);
	Cookie cookie = new Cookie("quitTime",newTime+""); 
  	cookie.setMaxAge(60*60*24*30); 
  	response.addCookie(cookie);
}else{
	Cookie cookies[]=request.getCookies();
	for(int i=0;i<cookies.length;i++) {
		if("quitTime".equals(cookies[i].getName())) {
			lastQuitTime = Integer.parseInt(cookies[i].getValue());
			break;
		}
	}
	if(lastQuitTime==null){
		lastQuitTime = (int)(System.currentTimeMillis()/1000l);
	}
}
%>
<script type="text/javascript">
	function logout() {
		var b = document.getElementById("logout").style.display;
		document.getElementById("logout").style.display = "";

	}
	function logoutOpen() {
		var open = document.getElementById("no_open").value;
		if (open == "no") {
			return;
		}
		document.getElementById("logout").style.display = "none";
	}

	function noOpen() {
		document.getElementById("no_open").value = "no";
	}

	function yesOpen() {
		document.getElementById("no_open").value = "yes";
		var oInput = document.getElementById("male");
		oInput.focus();
	}

	function upPasswd() {
		document.getElementById("logout").style.display = "none";
		fuwuNone();
	}
	
</script>
<header class="header">
	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${sessionScope.community_id}">
	<div class="header-box clearfix">
		<div class="logo">
			<a class="logo" href="javascript:void(0);" onclick="myHeadGoHome();"><img
				src="${pageContext.request.contextPath }/images/public/logo-head.png"
				alt="LOGO" class="logo"> </a>
		</div>
		<div class="tenement-name" id="community_id_name">水军后台</div>
		<div class="operation-nav-box">
			<nav class="operation-nav">
				<ul id="right_ul" class="clearfix">
					<li><a href="${pageContext.request.contextPath}/jsp/navy/navy-alllife.jsp?community_id=<%=community_id %>&communityName=<%=str %>" class="select">生活圈</a>
					
					<em  id="newMessageWarning">
					</em>
					</li>
					
					<li><a href="${pageContext.request.contextPath}/jsp/navy/navys.jsp?community_id=<%=community_id %>&communityName=<%=str %>" class="select">水军管理</a> </li>
					<li><a href="${pageContext.request.contextPath}/jsp/activity/activities_all.jsp?community_id=<%=community_id %>&communityName=<%=str %>" class="select">活动话题</a> </li>	
				</ul>
			</nav>
		</div>
		<div class="operation-nav-user-info" id="logon" onblue="logoutOpen();">
			<input type="hidden" value="yes" id="no_open"> 
				<a class="operation-nav-user-box" href="javascript:;"> 
					<label for="male"></label>

					<div class="operation-nav-user-name" onclick="logout();">
						<p>
							<span class="operation-nav-user-real-name">${newUserName}（登录）</span>
						</p>
					</div>
					</a> <input type="button" id="male" onblur="logoutOpen();" style="width:0;height:0;opacity:0;position:absolute;"/> 
					
			<div style="display: none;" class="operation-nav-user-item"
				id="logout">
				<p>
					<a
						href="<%=path %>/jsp/operation/password.jsp?userName=${newUserName}"
						onMouseOver="noOpen();" onMouseOut="yesOpen();"
						target="iframeName">修改密码</a>
				</p>
				<p>
					<a href="/" onMouseOver="noOpen();" onMouseOut="yesOpen();">退出登录</a>
				</p>
			</div>


		</div>

	</div>

	</div>
</header>
