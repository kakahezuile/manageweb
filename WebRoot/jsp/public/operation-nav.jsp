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
<nav class="operation-nav">
	<ul id="right_ul" class="clearfix">
		<li>
			<a mark="yezhuzixun" class="" id="yezhuzixun" href="javascript:void(0);">店家管理</a>
		</li>
		<li>
			<a mark="dianjiazixun" id="dianjiazixun" href="javascript:void(0);">店家客服</a>
		</li>
		<li>
			<a mark="gonggao" id="gonggao" href="javascript:void(0);">周边店家</a>
		</li>
		<li>
			<a mark="tousu" id="tousu" href="javascript:void(0);">生活号码</a>
		</li>
		<li>
			<a mark="jiaofei" id="jiaofei" href="javascript:void(0);">结款</a>
		</li>
		<li>
			<a mark="qianyue" id="qianyue" href="javascript:void(0);">帮帮券</a>
		</li>
		<li>
			<a mark="zhoubian" id="zhoubian" href="javascript:void(0);">监控</a>
		</li>
		<li>
			<a mark="huodong" id="huodong" href="javascript:void(0);">数据统计</a>
		</li>
	</ul>
</nav>