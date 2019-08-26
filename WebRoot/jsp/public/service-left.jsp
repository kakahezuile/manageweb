<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

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

<script type="text/javascript">
	function leftClikCss(id) {

		var left_clean_fei = document.getElementsByName("left_clean_fei");
		var left_repair_fei = document.getElementsByName("left_repair_fei");
		var left_express_fei = document.getElementsByName("left_express_fei");

		if (id == "left_clean_fei") {
			for ( var i = 0; i < left_clean_fei.length; i++) {
				left_clean_fei[i].className = "select";
			}
			
		shops_phone_clean_id_fei();
		} else {
			for ( var i = 0; i < left_clean_fei.length; i++) {
				left_clean_fei[i].className = "";
			}
		}
		if (id == "left_repair_fei") {
			for ( var i = 0; i < left_repair_fei.length; i++) {
				left_repair_fei[i].className = "select";
			}
		} else {
			for ( var i = 0; i < left_repair_fei.length; i++) {
				left_repair_fei[i].className = "";
			}
		}
		if (id == "left_express_fei") {
			for ( var i = 0; i < left_express_fei.length; i++) {
				left_express_fei[i].className = "select";
			}
		} else {
			for ( var i = 0; i < left_express_fei.length; i++) {
				left_express_fei[i].className = "";
			}
		}
	}

	function left_repair_fei() {
		leftClikCss("left_repair_fei");
		setNone();
		$("#community_service_div_id").attr("style", "display:block");

		overman();
		maintainData();
	}
</script>
<div class="left-body">
	<ul>
		<li>
			<a class="select" href="javascript:void(0);" name="left_repair_fei"
				onclick="left_repair_fei();">维修</a>
		</li>

		<li>
			<a href="javascript:void(0);" name="left_clean_fei" onclick="experss_id_fei(); leftClikCss('left_clean_fei');">保洁</a>
		</li>
		<li>
			<a href="javascript:void(0);"
				onclick="experss_id_fei(); leftClikCss('left_express_fei');"
				name="left_express_fei">快递</a>
		</li><!--
		<li>
			<a href="javascript:void(0);" name="left_carriage_fei">送水</a>
		</li>
	--></ul>
</div>