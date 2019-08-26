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
			<a class="select"  href="javascript:void(0);" id="jiaofei_left_click_1" onclick="jiaofei_left_click(0);jiaofei_left_clile('1');">新的缴费通知</a>
		</li>
		<li>
			<a href="javascript:void(0);" id="jiaofei_left_click_2" onclick="jiaofei_left_click(1);jiaofei_left_clile('2');">处理中的缴费</a>
		</li>
		<li>
			<a href="javascript:void(0);" id="jiaofei_left_click_3" onclick="jiaofei_left_click(2);jiaofei_left_clile('3');">完成的缴费</a>
		</li>
		<li>
			<a href="javascript:void(0);" id="jiaofei_left_click_4" onclick="jiaofei_tongzhi_click();jiaofei_left_clile('4');">通知类编辑</a>
		</li>
		<li>
			<a href="javascript:void(0);" id="jiaofei_left_click_5" onclick="paymentEditClick();jiaofei_left_clile('5');">缴费信息</a>
		</li>
	</ul>
</div>
