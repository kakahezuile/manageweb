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
			<a onclick="" mark="user" name="user" href="${pageContext.request.contextPath }/jsp/operation/statistics-user.jsp">用户</a>
		</li>
		<li>
			<a onclick="" mark="shop" name="shop" href="${pageContext.request.contextPath }/jsp/operation/convenience/statistics-convenience-dosage.jsp">快店</a>
		</li>
		<li>
			<a onclick="" mark="take_out"  name="take_out"  href="${pageContext.request.contextPath }/jsp/operation/takeOut/statistics-take-dosage.jsp">外卖</a>
		</li>
		<li>
			<a onclick="" mark="pay" name="pay" href="${pageContext.request.contextPath }/jsp/operation/pay/statistics-pay-dosage.jsp"  >缴费</a>
		</li>
		<li>
			<a onclick=""  mark="maintain" name="maintain" href="${pageContext.request.contextPath }/jsp/operation/statistics-shop-dosage.jsp?pages=5">维修</a>
		</li>
		<li>
			<a onclick="" mark="clean"  name="clean"  href="${pageContext.request.contextPath }/jsp/operation/statistics-shop-dosage.jsp?pages=6">保洁</a>
		</li>
		<li>
			<a onclick="" name="" href="javascript:void(0);">客服</a>
		</li>
		<li>
			<a onclick="" name="" href="javascript:void(0);">活动</a>
		</li>
		<li>
			<a onclick="" name="" href="${pageContext.request.contextPath }/jsp/operation/waterCarriage/statistics-water-dosage.jsp?">送水</a>
		</li>
		<li>
			<a onclick="" name="" href="javascript:void(0);">快递</a>
		</li>
	</ul>
</div>