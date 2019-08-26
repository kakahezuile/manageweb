<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
	function activity_left_List(name) {
		var activity_left_sensitive = document
				.getElementsByName("activity_left_sensitive");
		var activity_left_ongoing = document
				.getElementsByName("activity_left_ongoing");
		var activity_left_rejected = document
				.getElementsByName("activity_left_rejected");
		if (name == "activity_left_sensitive") {
			setNone();
			$("#activity_sensitive_words_jsp").attr("style", "display:block");
			for ( var i = 0; i < activity_left_sensitive.length; i++) {
				activity_left_sensitive[i].className = "select";
			}

		} else {
			for ( var i = 0; i < activity_left_sensitive.length; i++) {
				activity_left_sensitive[i].className = "";
			}

		}
		if (name == "activity_left_ongoing") {
			document.getElementById("activity_status_ongoing_rejected").value = "ongoing";
			setNone();
			$("#activity_activities_jsp").attr("style", "display:block");

			for ( var i = 0; i < activity_left_ongoing.length; i++) {
				activity_left_ongoing[i].className = "select";
			}

		} else {
			for ( var i = 0; i < activity_left_ongoing.length; i++) {
				activity_left_ongoing[i].className = "";
			}

		}
		if (name == "activity_left_rejected") {
			document.getElementById("activity_status_ongoing_rejected").value = "rejected";
			setNone();
			$("#activity_activities_jsp").attr("style", "display:block");

			for ( var i = 0; i < activity_left_rejected.length; i++) {
				activity_left_rejected[i].className = "select";
			}

		} else {
			for ( var i = 0; i < activity_left_rejected.length; i++) {
				activity_left_rejected[i].className = "";
			}

		}

	}
</script>
<div class="left-body">
	<ul>
		<li>
			<a onclick="activity_left_List('activity_left_sensitive');getSensitiveWords();" name="activity_left_sensitive"
				href="javascript:void(0);">活动群组敏感词</a>
		</li>
		<li>
			<a class="select" name="activity_left_ongoing"
				onclick="getActivitiesList(1,'','ongoing');activity_left_List('activity_left_ongoing');"
				href="javascript:void(0);">已发起的活动</a>
		</li>
		<li>
			<a
				onclick="getActivitiesList(1,'','rejected');activity_left_List('activity_left_rejected');"
				name="activity_left_rejected" href="javascript:void(0);">已屏蔽的活动</a>
		</li>
		<li>
			<a class="select" name="activity_left_ongoing"
				onclick="getActivitiesList(1,'','ongoing');activity_left_List('activity_left_ongoing');"
				href="javascript:void(0);">生活圈敏感词</a>
		</li>
	</ul>
</div>