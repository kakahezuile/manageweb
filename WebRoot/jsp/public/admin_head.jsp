<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle");
%>
<script type="text/javascript">
function logout(){
	var b=document.getElementById("logout").style.display;
	   document.getElementById("logout").style.display="";
	
	}
	function logoutOpen(){
	var open= document.getElementById("no_open").value;
	if(open=="no"){
	return ;
	}
	    document.getElementById("logout").style.display = "none";
	}
	
	function noOpen(){
	  document.getElementById("no_open").value="no";
	}
	
	function yesOpen(){
	  document.getElementById("no_open").value="yes";
	  var oInput = document.getElementById("male");
	oInput.focus();
	}
	
	
	function upPasswd(){
	  document.getElementById("logout").style.display = "none";
	  fuwuNone();
	}
</script>

	
<header class="header">
	<div class="header-box clearfix">
		<div class="logo"><a class="logo" href="/"><img src="${pageContext.request.contextPath }/images/public/logo-head.png" alt="LOGO" class="logo"></a></div>
		<div class="admin_nav">
			<ul>
				<li><a href="${pageContext.request.contextPath }/jsp/admin_community.jsp" id="nav_community" class="selected" mark="community">物业管理</a></li>
				<li><a href="${pageContext.request.contextPath }/jsp/admin_user.jsp" id="nav_user" mark="user">用户管理</a></li>
				<li><a href="${pageContext.request.contextPath }/jsp/admin/admin_repair_sort.jsp" id="nav_sort" mark="sort">分类配置</a></li>
				<li><a href="${pageContext.request.contextPath }/jsp/admin/admin_allot.jsp" id="nav_allot" mark="allot">合作分成</a></li>
			</ul>
		</div>
	<div class="operation-nav-user-info" onblue="logoutOpen();">
		<a class="operation-nav-user-box" href="javascript:;">	
			<label for="male"  >
				<div class="operation-nav-user-name" onclick="logout();">
					<p>
						<span class="operation-nav-user-real-name" >${newUserName}（登录）</span>
					</p>
				</div> 
				<input type="button" id="male"  onblur="logoutOpen();" style="height:0;width:0;overflow:hidden;opacity:0;position:absolute;">
			</label>
		</a>
		<div style="display: none;" class="operation-nav-user-item" id="logout">
			<p>
				<a href="<%=path %>/jsp/operation/password.jsp?userName=admin1" onMouseOver="noOpen();" onMouseOut="yesOpen();" onclick="upPasswd();" target="iframeName">修改密码</a>
			</p>
			<p>
				<a href="/" onMouseOver="noOpen();" onMouseOut="yesOpen();" >退出登录</a>
			</p>
		</div>
	</div>
	</div>
</header>