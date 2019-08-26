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
			<a class="select" onclick="bangbang_create_bangbang_sub();" href="javascript:void(0);">创建帮帮券</a>
		</li>
		<li>
			<a class="select" onclick="bangbang_send_bangbang_sub();" href="javascript:void(0);">发放帮帮券</a>
		</li>
		<li>
			<a href="javascript:void(0);" onclick="bangbang_record_bangbang_sub();">发放记录</a>
		</li>
	</ul>
</div>