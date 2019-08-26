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
function notice_notice_history_jsp(){

setNone();
	$("#notice_notice_history_jsp").attr("style", "display:block");
}
function notice_notice_all_jsp(){
	setNone();
	$("#notice_notice_all_jsp").attr("style", "display:block");
	}
	
	function notice_notice_special_jsp(){
	setNone();
	$("#notice_notice_special_jsp").attr("style", "display:block");
	
	}
	
	function notice_left(id){
	
	var notice_notice_all = document.getElementsByName("notice_notice_all");
		var notice_notice_special = document.getElementsByName("notice_notice_special");
		var notice_notice_history = document.getElementsByName("notice_notice_history");

		if (id == "notice_notice_all") {
			for ( var i = 0; i < notice_notice_all.length; i++) {
				notice_notice_all[i].className = "select";
			}
			
		} else {
			for ( var i = 0; i < notice_notice_all.length; i++) {
				notice_notice_all[i].className = "";
			}
		}
		if (id == "notice_notice_special") {
			for ( var i = 0; i < notice_notice_special.length; i++) {
				notice_notice_special[i].className = "select";
			}
		} else {
			for ( var i = 0; i < notice_notice_special.length; i++) {
				notice_notice_special[i].className = "";
			}
		}
		if (id == "notice_notice_history") {
			for ( var i = 0; i < notice_notice_history.length; i++) {
				notice_notice_history[i].className = "select";
			}
		} else {
			for ( var i = 0; i < notice_notice_history.length; i++) {
				notice_notice_history[i].className = "";
			}
		}
	
	}
	
	
</script>
<div class="left-body">
	<ul>
		
		<li>
			<a class="select" href="javascript:void(0);" name="notice_notice_all" onclick="notice_notice_all_jsp();notice_left('notice_notice_all');">通知全部用户</a>
		</li>
		<li>
			<a  href="javascript:void(0);" name="notice_notice_special"  onclick="notice_notice_special_jsp();notice_left('notice_notice_special');">通知指定用户</a>
		</li>
		<li>
			<a href="javascript:void(0);"  name="notice_notice_history" onclick="notice_notice_history_jsp();notice_left('notice_notice_history');">历史通知</a>
		</li>
	</ul>
</div>