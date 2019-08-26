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
	function shopsPhoneLeft(id) {

		var shops_phone_1_id = document.getElementsByName("shops_phone_1_id");
		var shops_phone_12_id = document.getElementsByName("shops_phone_12_id");
		var shops_phone_11_id = document.getElementsByName("shops_phone_11_id");

		if (id == "shops_phone_1_id") {
			for ( var i = 0; i < shops_phone_1_id.length; i++) {
				document.getElementById("shops_phone_left_id_stor").value = "4";
				shops_phone_1_id[i].className = "select";
			}
			document.getElementById("business_start_time_business_end_time").style.display = "";

		} else {
			for ( var i = 0; i < shops_phone_1_id.length; i++) {
				shops_phone_1_id[i].className = "";
			}
		}
		if (id == "shops_phone_12_id") {
			for ( var i = 0; i < shops_phone_12_id.length; i++) {
				document.getElementById("shops_phone_left_id_stor").value = "12";
				shops_phone_12_id[i].className = "select";
			}
			kuaiDi();
			return;
		} else {
			for ( var i = 0; i < shops_phone_12_id.length; i++) {
				shops_phone_12_id[i].className = "";
			}

		}
		if (id == "shops_phone_11_id") {
			for ( var i = 0; i < shops_phone_11_id.length; i++) {
				document.getElementById("shops_phone_left_id_stor").value = "11";
				shops_phone_11_id[i].className = "select";
			}
			//document.getElementById("business_start_time_business_end_time").style.display="none";
		} else {
			for ( var i = 0; i < shops_phone_11_id.length; i++) {
				shops_phone_11_id[i].className = "";
			}
		}
		putong();
		selectShopsPhone('', 1);

	}

	function putong() {
		setNone();
		$("#tousu_lie_div_id").attr("style", "display:block");
	}
	function kuaiDi() {

		setNone();
		$("#shops_phone_express_id_fei").attr("style", "display:block");
		selectShopsPhoneKuai('', 1);

	}
	function nearby() {

		setNone();
		$("#nearby_home_fei").attr("style", "display:block");
		countFacilitiesTop();

	}
	function setNone() {
		$("#shops_phone_express_id_fei").attr("style", "display:none");
		$("#nearby_home_fei").attr("style", "display:none");
		$("#tousu_lie_div_id").attr("style", "display:none");
		$("#shops_edit_id_fei").attr("style", "display:none");
		$("#express_edit_id_fei").attr("style", "display:none");

	}
</script>


<div class="left-body">

	<ul>
		<li>
			<a href="javascript:void(0);" name="shops_phone_1_id"
				onclick="shopsPhoneLeft('shops_phone_1_id'); " class="select">送水</a>
		</li>
		<li>
			<a href="javascript:void(0);" name="shops_phone_12_id"
				onclick="shopsPhoneLeft('shops_phone_12_id');">快递</a>
		</li>
		<li>
			<a href="javascript:void(0);" name="shops_phone_11_id"
				onclick="shopsPhoneLeft('shops_phone_11_id')">便捷号码</a>
		</li>
		<!--<li><div style="height:2px; background:#7dd0f8;"></div></li>
		<li>
			<a href="javascript:void(0);" onclick="nearby();">周边商铺</a>
		</li>
	-->
	</ul>
</div>