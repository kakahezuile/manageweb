<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>

<div class="left-body">
	<ul id="admin_user_menu_ul">
		<li>
			<a class="select" href="${pageContext.request.contextPath }/jsp/admin/home_pic.jsp" mark="pic">首页图片配置</a>
		</li>
		<li>
			<a class="select" href="${pageContext.request.contextPath }/jsp/admin/admin_repair_sort.jsp" mark="repair">维修分类</a>
		</li>
		<li>
			<a class="select" href="${pageContext.request.contextPath }/jsp/admin/admin_clean_sort.jsp" mark="clean">保洁</a>
		</li>
		<li>
			<a class="select" href="${pageContext.request.contextPath }/jsp/admin/bonus_coin_conf.jsp" mark="clean">帮帮币</a>
		</li>
		<!--
		<li>
			<a href="${pageContext.request.contextPath }/jsp/admin/FastShopManagement.jsp" mark="shop"">快店分类</a>
		</li>
		-->
	</ul>
</div>