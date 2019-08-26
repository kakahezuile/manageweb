<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<script type="text/javascript">
function bangbang_send_bangbang_sub() {
		setNone();
		$("#bangbang_juan_div_id").attr("style", "display:block");
		$("#send_bangbang_td").attr("style", "display:block");
		open("bangbang_send_bangbang_sub");
		getBonusList();
	}

	function bangbang_create_bangbang_sub() {
		setNone();
		$("#bangbang_juan_div_id").attr("style", "display:block");
		$("#create_bangbang_td").attr("style", "display:block");
	    open("bangbang_create_bangbang_sub");
	}
	function bangbang_record_bangbang_sub() {
		setNone();
		$("#bangbang_juan_div_id").attr("style", "display:block");
		$("#record_bangbang_td").attr("style", "display:block");
		open("bangbang_record_bangbang_sub");
		record_bonus(1,"全部","","","");
	}
	function setNone(){
		$("#bangbang_juan_div_id").attr("style", "display:none");
		$("#record_bangbang_td").attr("style", "display:none");
		$("#create_bangbang_td").attr("style", "display:none");
		$("#send_bangbang_td").attr("style", "display:none");
	}
	
	function open(name){
		var bangbang_create_bangbang_sub=document.getElementsByName("bangbang_create_bangbang_sub");
		var bangbang_send_bangbang_sub=document.getElementsByName("bangbang_send_bangbang_sub");
		var bangbang_record_bangbang_sub=document.getElementsByName("bangbang_record_bangbang_sub");
	
	   	if (name == "bangbang_create_bangbang_sub") {
			for ( var i = 0; i < bangbang_create_bangbang_sub.length; i++) {
				bangbang_create_bangbang_sub[i].className = "select";
			}
			
		} else {
			for ( var i = 0; i < bangbang_create_bangbang_sub.length; i++) {
				bangbang_create_bangbang_sub[i].className = "";
			}
		}
		if (name == "bangbang_send_bangbang_sub") {
			for ( var i = 0; i < bangbang_send_bangbang_sub.length; i++) {
				bangbang_send_bangbang_sub[i].className = "select";
			}
		} else {
			for ( var i = 0; i < bangbang_send_bangbang_sub.length; i++) {
				bangbang_send_bangbang_sub[i].className = "";
			}
		}
		if (name == "bangbang_record_bangbang_sub") {
			for ( var i = 0; i < bangbang_record_bangbang_sub.length; i++) {
				bangbang_record_bangbang_sub[i].className = "select";
			}
		} else {
			for ( var i = 0; i < bangbang_record_bangbang_sub.length; i++) {
				bangbang_record_bangbang_sub[i].className = "";
			}
		}
	}
</script>
<div class="left-body">
	<ul>
		<li>
			<a class="select" name="bangbang_create_bangbang_sub" onclick="bangbang_create_bangbang_sub();" href="javascript:void(0);">创建帮帮券</a>
		</li>
		<li>
			<a  onclick="bangbang_send_bangbang_sub();" name="bangbang_send_bangbang_sub" href="javascript:void(0);">发放帮帮券</a>
		</li>
		<li>
			<a href="javascript:void(0);" name="bangbang_record_bangbang_sub" onclick="bangbang_record_bangbang_sub();">发放记录</a>
		</li>
	</ul>
</div>