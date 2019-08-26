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
			<a class="select" href="javascript:void(0);">李静</a>
		</li>
		<li>
			<a href="javascript:void(0);">张三</a>
		</li>
		<li>
			<a href="javascript:void(0);">王小五</a>
		</li>
		<li>
			<a class="left-menu-add" href="javascript:void(0);">添加管理员</a>
		</li>
		</li>
	</ul>
</div>