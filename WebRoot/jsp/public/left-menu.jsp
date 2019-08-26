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
	<ul>
		
		<li>
			<a class="select" href="javascript:void(0);">清水湾</a>
		</li>
		<li>
			<a href="javascript:void(0);">海棠湾</a>
		</li>
		<li>
			<a href="javascript:void(0);">狮子城</a>
		</li>
		<li style="margin-top:50px;"></li>
		<li>
			<a class="left-menu-add" href="javascript:void(0);">创建新的项目</a>
		</li>
		<li>
			<a class="left-menu-add" href="javascript:void(0);">添加管理员</a>
		</li>
		<li>
			<a class="left-menu-add" href="javascript:void(0);" onclick="addRule();">权限分配</a>
		</li>
	</ul>
</div>