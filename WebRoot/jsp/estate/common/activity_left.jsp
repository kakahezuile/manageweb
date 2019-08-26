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
	function activity_left_List(name) {
		var activity_left_sensitive = document
				.getElementById("activity_left_sensitive");
		var activity_left_ongoing = document
				.getElementById("activity_left_ongoing");
		var activity_left_rejected = document
				.getElementById("activity_left_rejected");
		var activity_left_rejected = document
				.getElementById("activity_left_rejected");
		var activity_left_lifecircle = document
				.getElementById("activity_left_lifecircle");
		var life_circle_sensitive = document
				.getElementById("life_circle_sensitive");
		if (name == "activity_left_sensitive") {
            activity_left_sensitive.className="select";
		} else {
			activity_left_sensitive.className="";

		}
		if (name == "activity_left_ongoing") {
               activity_left_ongoing.className="select";
		} else {
               activity_left_ongoing.className="";
		}
		if (name == "activity_left_rejected") {
               activity_left_rejected.className="select";
		} else {
              activity_left_rejected.className="";
		}
		if (name == "activity_left_lifecircle") {
               activity_left_lifecircle.className="select";
		} else {
              activity_left_lifecircle.className="";
		}
		if (name == "life_circle_sensitive") {
               life_circle_sensitive.className="select";
		} else {
              life_circle_sensitive.className="";
		}

	}
</script>
<div class="left-body">
	<ul>
		<li>
			<a  id="activity_left_sensitive" name="activity_left_sensitive"
				href="<%=path %>/jsp/estate/activity/SensitiveWords.jsp">活动群组敏感词</a>
		</li>
		<li>
			<a class="select" id="activity_left_ongoing" name="activity_left_ongoing"
				href="<%=path%>/jsp/estate/activity/activities.jsp?status=ongoing">已发起的活动</a>
		</li>
		<li>
			<a
				 id="activity_left_rejected"
				name="activity_left_rejected" href="<%=path %>/jsp/estate/activity/activities.jsp?status=rejected">已屏蔽的活动</a>
		</li>
		<li>
			<a class="" id="activity_left_lifecircle" name="activity_left_lifecircle"
				href="<%=path%>/jsp/estate/lifecircle/lifecircle.jsp?status=ongoing">生活圈</a>
		</li>
		<li>
			<a  id="life_circle_sensitive" name="life_circle_sensitive"
				href="<%=path %>/jsp/estate/lifecircle/LifecircleSensitiveWords.jsp">生活圈监控</a>
		</li>
	</ul>
</div>