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
		window.parent.document.getElementById("fuwu_yinchang_id").style.display = "none";
		window.parent.document.getElementById("iframeName").style.display = "none";
		window.parent.document.getElementById("tousu_div_id").style.display = "none";
		window.parent.document.getElementById("tousu_lie_div_id").style.display = "none";
	}
	function fuwuNone() {
		setNone();
		window.parent.document.getElementById("iframeName").style.display = "";

	}
	function fuwuKai() {
		setNone();
		window.parent.document.getElementById("fuwu_yinchang_id").style.display = "";
		window.parent.document.getElementById("tousu_lie_div_id").style.display = "";

		topClass("ke_fu");
	}
	function topNone(type) {
		window.parent.document.getElementById("ke_fu").className = "";
		window.parent.document.getElementById("zhou_bian").className = "";
		document.getElementById("shopManage").className = "";
		document.getElementById("ke_fu").className = "";
		document.getElementById("zhou_bian").className = "";
		document.getElementById("sheng_huo").className = "";
		document.getElementById("jie_kuan").className = "";
		document.getElementById("bangbang").className = "";
		document.getElementById("jiankong").className = "";
		document.getElementById("statistics").className = "";
		var shopManage = document.getElementsByName("shopManage");
		var ke_fu = document.getElementsByName("ke_fu");
		var zhou_bian = document.getElementsByName("zhou_bian");
		var sheng_huo = document.getElementsByName("sheng_huo");
		var jie_kuan = document.getElementsByName("jie_kuan");
		var bangbang = document.getElementsByName("bangbang");
		var jiankong = document.getElementsByName("jiankong");
		var statistics = document.getElementsByName("statistics");

		for ( var i = 0; i < shopManage.length; i++) {
			shopManage[i].className = "";
		}

		for ( var i = 0; i < ke_fu.length; i++) {
			ke_fu[i].className = "";
		}

		for ( var i = 0; i < zhou_bian.length; i++) {
			zhou_bian[i].className = "";
		}

		for ( var i = 0; i < sheng_huo.length; i++) {
			sheng_huo[i].className = "";
		}

		for ( var i = 0; i < jie_kuan.length; i++) {
			jie_kuan[i].className = "";
		}

		for ( var i = 0; i < bangbang.length; i++) {
			bangbang[i].className = "";
		}

		for ( var i = 0; i < jiankong.length; i++) {
			jiankong[i].className = "";
		}

		for ( var i = 0; i < statistics.length; i++) {
			statistics[i].className = "";
		}

	}
	function topClass(type) {
		topNone();
		document.getElementById(type).className = "select";
		if (type == "ke_fu" || type == "zhou_bian") {
			window.parent.document.getElementById(type).className = "select";
		}
		var type = document.getElementsByName(type);
		for ( var i = 0; i < type.length; i++) {
			type[i].className = "select";
		}

	}

	function tousu_div_id() {
		setNone();
		window.parent.document.getElementById("fuwu_yinchang_id").style.display = "";
		window.parent.document.getElementById("tousu_div_id").style.display = "";
		topClass("zhou_bian");

	}
	
	function operationTop(){
	
	
	}
</script>
<header class="header">




<div class="header-box clearfix">
	<div class="logo">
		<a class="logo" href="javascript:void(0);" onclick="myHeadGoHome();"><img
				src="${pageContext.request.contextPath }/images/public/logo-head.png"
				alt="LOGO" class="logo"> </a>
	</div>
	<div class="operation-nav-box">
		<nav class="operation-nav">
		<ul id="right_ul" class="clearfix">
			<li>
				<a target="iframeName" mark="shopManage" onclick="fuwuNone();"
					name="shopManage" id="shopManage"
					href="${pageContext.request.contextPath }/jsp/operation/shop/shop-manage.jsp">店家管理</a>
			</li>
			<li>
				<a target="iframeName" mark="" onclick="fuwuKai();" name="ke_fu"
					id="ke_fu" href="javascript:void(0);" class="select">店家客服</a>
					<span class="pop-tip">&nbsp;</span>
			</li>
			<li>
				<a target="iframeName" mark="" name="zhou_bian" id="zhou_bian"
					onclick="tousu_div_id();" href="javascript:void(0);">投诉评价</a>
			</li>
			<li>
				<a target="iframeName" mark="" name="sheng_huo" id="sheng_huo"
					onclick="fuwuNone();"
					href="${pageContext.request.contextPath }/jsp/operation/life_page/life_page.jsp">生活号码</a>
			</li>
			<li>
				<a target="iframeName" mark="" name="jie_kuan" id="jie_kuan"
					onclick="fuwuNone();" href="javascript:void(0);">结款</a>
			</li>
			<li>
				<a target="iframeName" mark="" name="bangbang" id="bangbang"
					onclick="fuwuNone();"
					href="${pageContext.request.contextPath }/jsp/operation/bonus/operation-bonus.jsp">帮帮券</a>
			</li>
			<li>
				<a target="iframeName" mark="" name="jiankong" id="jiankong"
					onclick="fuwuNone();"
					href="${pageContext.request.contextPath }/jsp/operation/monitoring/SensitiveWords.jsp">监控</a>
			</li>
			<li>
				<a target="iframeName" mark="statistics" onclick="fuwuNone();"
					name="statistics" id="statistics"
					href="${pageContext.request.contextPath }/jsp/operation/statistics.jsp">数据统计</a>
			</li>
		</ul>

		</nav>
	</div>
	<div class="operation-nav-user-info">
		<a class="operation-nav-user-box" href="javascript:;">
			<div class="operation-nav-user-name">
				<p>
					<span class="operation-nav-user-real-name">徐艳荔（登录）</span>
				</p>
			</div> </a>
		<div style="display: none;" class="operation-nav-user-item">
			<p>
				<a href="javascript:;">个人资料</a>
			</p>
			<p>
				<a href="javascript:;">退出登录</a>
			</p>
		</div>


	</div>

</div>
</header>