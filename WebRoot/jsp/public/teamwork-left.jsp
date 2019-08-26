<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>

<div class="left-body">
<input type="hidden" id="com_id" value="">
<input type="hidden" id="community_id" value="${sessionScope.community_id}">
<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<ul id="com_ul">
		<li>
			<a onclick="" mark="user" name="user" href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-survey.jsp">安装注册量</a>
		</li>
	</ul>
</div>
<script type="text/javascript">

function communityId() {
    var community_id=document.getElementById("community_id").value;
    var emobId=document.getElementById("emobId").value;
   
	var path = "/api/v1/communities/summary/getListCommunityEmobId/"+emobId;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data=data.info;
			
			var repair_overman = $("#com_ul");
			repair_overman.empty();
			repair_overman.append("<li>"+
			"<a onclick=\"\" id=\"community_id_\" mark=\"user\" name=\"user\" href=\"${pageContext.request.contextPath }/jsp/teamwork/teamwork-survey.jsp\">安装注册量</a>"+
		"</li>");
		var liList = "";
			for ( var i = 0; i < data.length; i++) {
				liList+="<li>";
				liList+="<a onclick=\"\" id=\"community_id_"+data[i].communityId+"\" mark=\"shop\" name=\"shop\" href=\"${pageContext.request.contextPath }/stat/teamwork/stat.do?modules=3&shopType=2&communityId="+data[i].communityId+"\">"+data[i].communityName+"</a>";
				liList+="</li>";

			}
			
			repair_overman.append(liList);
			leftSelect();
		},
		error : function(er) {
		}
	});

}

communityId();

function  leftSelect() {

	var community_id=document.getElementById("community_id").value;
	document.getElementById("community_id_"+community_id).className="select";
	
}


</script>