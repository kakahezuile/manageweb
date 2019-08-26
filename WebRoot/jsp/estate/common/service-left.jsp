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

		var left_clean_fei = document.getElementById("left_clean_fei");
		var left_repair_fei = document.getElementById("left_repair_fei");
		var left_express_fei = document.getElementById("left_express_fei");

		if (id == "left_clean_fei") {
		   left_clean_fei.className="select";
		} else {
		   left_clean_fei.className="";
		}
		if (id == "left_repair_fei") {
			left_repair_fei.className="select";
		} else {
			left_repair_fei.className="";
		}
		if (id == "left_express_fei") {
		    left_express_fei.className="select";
		} else {
			left_express_fei.className="";
		}
	}

</script>
<div class="left-body">
	<ul>

		<li>
			<a target="iframeName" class="select" href="<%=path %>/jsp/estate/property/repair.jsp" name="left_repair_fei"
				id="left_repair_fei">维修</a>
		</li>
		<li style="display: none;">
			<a href="<%=path %>/jsp/estate/property/clean.jsp" name="left_clean_fei" id="left_clean_fei">保洁</a>
		</li>
		<li>
			<a href="<%=path %>/jsp/estate/express/express.jsp"
			id="left_express_fei"
				name="left_express_fei">快递</a>
		</li><!--
		<li>
			<a href="javascript:void(0);" name="left_carriage_fei">送水</a>
		</li>
	--></ul>
</div>