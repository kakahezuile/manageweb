<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>

<script type="text/javascript">
function setNone() {
	$("#fuwu_yinchang_id").hide();
	$("#iframeName").hide();
	$("#tousu_div_id").hide();
	$("#tousu_lie_div_id").hide();
	$("#mychat_div_id").hide();
	$("#userFeedBack_div").hide();
	$("#userFeedBackAll_div").hide();
}
function fuwuNone() {
	setNone();
	document.getElementById("iframeName").style.display = "";
}
function fuwuKai() {
	setNone();
	window.parent.document.getElementById("fuwu_yinchang_id").style.display = "";
	window.parent.document.getElementById("mychat_div_id").style.display = "";
}
function index(url){
	setNone();
	document.getElementById("iframeName").style.display = "";
	document.getElementById("iframeName").src = url;
}
function tousu_div_id() {
	setNone();
	document.getElementById("fuwu_yinchang_id").style.display = "";
	document.getElementById("tousu_div_id").style.display = "";
}
function showUserFeedBack() {
	setNone();
	$("#userFeedBack_div").show();
	$("#userFeedBackAll_div").hide();
	$("#fuwu_yinchang_id").show();
}
$(function() {
	$("#right_ul > li a").click(function() {
		$(this).addClass("select").parent().siblings("li").find("a").removeClass("select");
		if (this.id == "shopManage") {//店家管理
			fuwuNone();
		} else if (this.id == "ke_fu") {//店家客服
			fuwuKai();
		} else if (this.id == "zhou_bian") {//投诉评价
			tousu_div_id();
		} else if (this.id == "sheng_huo") {//生活号码
			fuwuNone();
		} else if (this.id == "jie_kuan") {//结款
			fuwuNone();
		} else if (this.id == "bangbang") {//帮帮券
			fuwuNone();
		} else if (this.id == "jiankong") {//监控
			$("#span_jiangkong").removeClass("pop-tip");
			fuwuNone();
		} else if (this.id == "statistics") {//数据统计
			fuwuNone();
		} else if (this.id == "userFeedBack") {//数据统计
			showUserFeedBack();
		}
	});
	
	$(document).on("click", function() {
		if (_openLogout) {
			_openLogout = false;
		} else {
			document.getElementById("logout").style.display = "none";
		}
	});
});
var _openLogout = false;
function logoutOpen(){
	document.getElementById("logout").style.display = "block";
	_openLogout = true;
}
function doLogout() {
	if (logout && $.isFunction(logout)) {
		logout();
	}
	location.href = "/";
}
function community(){
	var path = "<%=path%>/api/v1/communities/${communityId}/services/getCommunity";
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data=data.info;
			document.getElementById("community_id_name").innerHTML=data.communityName+"运营";
		}
	});
}
community();
</script>

<header class="header">
<div class="header-box clearfix">
	<div class="logo">
		<a class="logo" href="javascript:void(0);" onclick="myHeadGoHome();">
			<img src="${pageContext.request.contextPath}/images/public/logo-head.png" alt="LOGO" class="logo">
		</a>
	</div>
	<div class="tenement-name" id="community_id_name">运营管理系统</div>
	<div class="operation-nav-box">
		<nav class="operation-nav">
		<ul id="right_ul" class="clearfix">
			<c:if test="${!empty list }">
				<c:forEach items="${list}" varStatus="status" var="item">
					<c:if test="${status.index == 0 }">
						<c:choose>
							<c:when test="${item.navId=='ke_fu'}">
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" class="select" href="javascript:void(0);">${item.modelName}</a>
								</li>
							</c:when>
							<c:when test="${item.navId=='zhou_bian'}">
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" class="select" href="javascript:void(0);">${item.modelName}</a>
								</li>
							</c:when>
							<c:when test="${item.navId=='userFeedBack'}">
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" class="select" href="javascript:void(0);">${item.modelName}</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" class="select" href="<%=path %>/jsp/operation/${item.ruleName}">${item.modelName}</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${status.index != 0 }">
						<c:choose>
							<c:when test="${item.navId=='ke_fu'}">
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" href="javascript:void(0);">${item.modelName}</a>
								</li>
							</c:when>
							<c:when test="${item.navId=='zhou_bian'}">
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" href="javascript:void(0);">${item.modelName}</a>
								</li>
							</c:when>
							<c:when test="${item.navId=='userFeedBack'}">
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" href="javascript:void(0);">${item.modelName}</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a target="iframeName" mark="${item.navId}" name="${item.navId}" id="${item.navId}" href="<%=path %>/jsp/operation/${item.ruleName}">${item.modelName}</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</c:if>
		</ul>
		</nav>
	</div>
	<div class="operation-nav-user-info">
		<a class="operation-nav-user-box" onclick="logoutOpen();">
			<div class="operation-nav-user-name">
				<p>
					<span class="operation-nav-user-real-name" >${newUserName}（登录）</span>
				</p>
			</div>
		</a>
		<div style="display:none;" class="operation-nav-user-item" id="logout">
			<p>
				<a href="<%=path %>/jsp/operation/password.jsp?userName=${newUserName}" target="iframeName">修改密码</a>
			</p>
			<p>
				<a href="javascript:doLogout()">退出登录</a>
			</p>
		</div>
	</div>
</div>
</header>