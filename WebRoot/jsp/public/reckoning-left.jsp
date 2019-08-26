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
		<!--<li>
			<a onclick="" mark="user" name="user" href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning.jsp">结款汇总</a>
		</li>
		-->
		<li>
			<a onclick="" mark="shop" name="shop"  id="reckoning_2" href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp?sort=2">快店</a>
		</li>
		<li>
			<a onclick="" mark="shop" name="shop"  id="reckoning_2" href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop-new.jsp?sort=2">快店（待做）</a>
		</li>
		<!--<li>
			<a onclick="" mark="take_out"  name="take_out" id="reckoning_1"  href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp?sort=1">外卖</a>
		</li>
		<li>
			<a onclick="" mark="take_out"  name="take_out" id="reckoning_3"  href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp?sort=3">水果</a>
		</li>
	--></ul>
</div>