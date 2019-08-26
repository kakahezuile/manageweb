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

	
	function notice_left(id){
	
	    var notice_notice_all = document.getElementById("notice_notice_all");
		var notice_notice_special = document.getElementById("notice_notice_special");
		var notice_notice_history = document.getElementById("notice_notice_history"); 

		if (id == "notice_notice_all") {
			notice_notice_all.className="select";
		} else {
		   notice_notice_all.className="";
		}
		if (id == "notice_notice_special") {
		notice_notice_special.className="select";
		} else {
		notice_notice_special.className="";
		}
		if (id == "notice_notice_history") {
		notice_notice_history.className="select";
		} else {
		notice_notice_history.className="";
		}
	
	}
	
	
</script>
<div class="left-body">
	<ul>
		
		<li>
			<a class="select" id="notice_notice_all" href="<%=path %>/jsp/estate/notice/notice_all.jsp" name="notice_notice_all" >通知全部用户</a>
		</li>
		<li>
			<a id="notice_notice_special"  href="<%=path %>/jsp/estate/notice/notice_special.jsp" name="notice_notice_special"  onclick="notice_left('notice_notice_special');">通知指定用户</a>
		</li>
		<li>
			<a  id="notice_notice_history" href="<%=path %>/jsp/estate/notice/notice_history.jsp"  name="notice_notice_history" onclick="notice_left('notice_notice_history');">历史通知</a>
		</li>
	</ul>
</div>