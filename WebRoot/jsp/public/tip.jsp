<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<div class="public-black"></div>
<div class="public-tip-box">
	<div class="public-tip-title">信息提示</div>
	<div class="public-tip-content outer">
		<div class="middle">
			<div class="inner tip-info">您的密码已经修改成功！</div>
		</div>
	</div>
	<div class="public-tip-footer">
		<a href="javascript:closeAlert();" class="green-button">确定</a>
	</div>
</div>
<script type="text/javascript">
function show_alert(opacity, tipword){
    var back = document.createElement("div");
    back.id = "black_background";
    back.className = "public-black";
    var styleStr = "opacity:" + opacity / 100;
    document.body.appendChild(back);
    back.style.cssText = styleStr;
    
    var content = document.createElement("div");
    content.id = "black_content";
    content.className = "public-tip-box";
    content.innerHTML = "<div class=\"public-tip-title\">信息提示</div><div class=\"public-tip-content outer\"><div class=\"middle\"><div class=\"inner\">" + tipword + "</div></div></div><div class=\"public-tip-footer\"><a href=\"javascript:close_alert();\" class=\"green-button\">纭畾</a></div>";
    document.body.appendChild(content);
}

function close_alert(){
    $("#black_background").remove();
    $("#black_content").remove();
}

</script>