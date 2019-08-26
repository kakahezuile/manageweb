<%@ page language="java" import="java.util.*,com.xj.utils.PropertyTool,com.xj.bean.ShopType" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	
	List<ShopType> listShopType = (List<ShopType>)request.getAttribute("listShopType");
	session.setAttribute("listShopType",listShopType);
%>

<html class="">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>登录_小间物业管理系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/easymob-webim1.0/css/webim.css?version=<%=versionNum%>" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/easymob-webim1.0/css/bootstrap.css?version=<%=versionNum%>" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/calendar.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/jquery-1.11.1.js?version=<%=versionNum%>"></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/easymob-webim1.0/strophe-custom-2.0.1.js?version=<%=versionNum%>'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/easymob-webim1.0/json2.js?version=<%=versionNum%>'></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/easemob.im-1.0.7.js?version=<%=versionNum%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/bootstrap.js?version=<%=versionNum%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/localData.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar.js"></script>
<script type="text/javascript">
var isClick=0;
function userMessageList(){//列表 用户
	var path = "<%= basePath%>api/v1/communities/${communityId}/messageList/${adminId}";
	$.ajax({
		url: path,
		type: "GET",
		dataType:"json",
		success:function(data){
			if(data.status == "yes"){
				data = data.info;
				for(var i = 0 ; i < data.length ; i++){
					homeUsersList = homeUsersList || [];
					
					homeUsersList.push({
						"from":data[i].emobIdFrom ,
						"ext":{
							"nickname":data[i].nickname,
							"avatar":data[i].avatar
						}
					});
				}
				addHomeUserList();
			}
		}
	});
}
function getShopList(){
	var path = "<%= path%>/api/v1/communities/${communityId}/users/getShopUser";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			for(var i = 0 ; i < data.length ; i++){
				myHomeMap[data[i].emobId] = "1";
			}
		}
	});
}
function getMessageListFromUser(from, to) {
	schedule();
	$.ajax({
		url: "<%= basePath%>api/v1/usersMessages?messageFrom=" + from + "&messageTo=" + to,
		type: "GET",
		dataType:"json",
		success:function(data){
			if(data.status == "yes"){
				data = data.info;
				var len = data.length;
				var messageArray = [];
				for(var i = 0 ; i < len; i++){
					messageArray.push({
						"from":data[i].messageFrom,
						"to":data[i].messageTo,
						"msg":data[i].msg,
						"timestamp":data[i].timestamp,
						"data":data[i].msg,
						"url":data[i].url,
						"type":data[i].msgType,
						"ext":{
							"nickname":data[i].nickname,
							"avatar":data[i].avatar,
							"content":data[i].content
						}
					});
				}
				historyKeFuMap[from] = messageArray;
				homeUsersMessageMap={};
				onSchedule();
				getHomeMessageList(from);
			}
		},
		error:function(er){
			alert("网络连接错误...");
			onSchedule();
		}
	});
}

var myUserName = "<%=request.getAttribute("username") %>";
var myPassWord = "<%=request.getAttribute("password") %>";
function isLogin(){
	//easemobwebim-sdk注册回调函数列表
	$(document).ready(function() {
		conn = new Easemob.im.Connection();
		//初始化连接
		conn.init({
			https : false,
			//当连接成功时的回调方法
			onOpened : function() {
				handleOpen(conn);
			},
			//当连接关闭时的回调方法
			onClosed : function() {
				handleClosed();
			},
			//收到文本消息时的回调方法
			onTextMessage : function(message) {
				handleTextMessage(message);
			},
			//收到表情消息时的回调方法
			onEmotionMessage : function(message) {
				handleEmotion(message);
			},
			//收到图片消息时的回调方法
			onPictureMessage : function(message) {
				handlePictureMessage(message);
			},
			//收到音频消息的回调方法
			onAudioMessage : function(message) {
				handleAudioMessage(message);
			},
			onLocationMessage : function(message) {
				handleLocationMessage(message);
			},
			//异常时的回调方法
			onError : function(message) {
				handleError(message);
			}
		});
		<%
			if(request.getAttribute("username") != null){
				request.setAttribute("username",null);
		%>
			myLogin();
		<%}else{%>
			showWaitLoginedUI();
		<% }%>

		//在 密码输入框时的回车登录
		$('#password').keypress(function(e) {
			var key = e.which;
			if (key == 13) {
				login();
			}
		});

		$(function() {
			$(window).bind('beforeunload', function() {
				if (conn) {
					conn.close();
				}
			});
		});
	});
}

//登录系统时的操作方法
function login() {
	var user = $("#username").val();
	var pass = $("#password").val();
	if (!user || !pass) {
		user = "<%= request.getAttribute("username") %>";
		pass = "<%= request.getAttribute("password") %>";
		if (!user || !pass) {
			alert("请输入用户名和密码");
			return;
		}
	}
	user = hex_md5(user);
	pass = hex_md5(pass);
	showWaitLoginedUI();
	//根据用户名密码登录系统
	conn.open({
		apiUrl : apiURL,
		user : user,
		pwd : pass,
		appKey : 'xiaojiantech#bangbang'
	});
	return false;
}
</script>
</head>
<body>
<input id="tousu_index" type="hidden" value="" />
<input id="cur_user_id_index" type="hidden" value="" />
<input id="myUserName_index" type="hidden" value="" />
<input type="hidden" value="${communityId}" id="community_id_index" />
<input type="hidden" value="${adminId}" id="admin_id_index" />
<input type="hidden" value="<%=request.getAttribute("username") %>" id="username_id_index" />
<input type="hidden" value="${password}" id="password_id_index" />
<input type="hidden" value="${emobId}" id="emobId_id_index" />
<input type="hidden" value="${listShopType}" id="listShopType_id_index" />
<div id="waitLoginmodal" class="modal hide fade" data-backdrop="static">
	<img src="${pageContext.request.contextPath}/easymob-webim1.0/img/waitting.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在努力加载中...</img>
</div>
<jsp:include flush="true" page="public/operation-head.jsp" >
	<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
	<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
	<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
	<jsp:param name="username" value="{parameterValue | ${username}}" />
	<jsp:param name="password" value="{parameterValue | ${password}}" />
	<jsp:param name="newUserName" value="{parameterValue | ${newUserName}}" />
</jsp:include>
<div id="fuwu_yinchang_id">
	<section>
		<div class="content-normal clearfix">
			<c:if test="${!empty list }">
				<c:forEach items="${list }" varStatus="status" var="myList">
					<c:if test="${status.index == 0}">
						<c:if test="${myList.navId=='ke_fu'}">
							<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
							<script type="text/javascript">isLogin();</script>
							<div class="main clearfix" id="mychat_div_id">
								<jsp:include flush="true" page="../jsp/mychat.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
						</c:if>
						<c:if test="${myList.navId=='zhou_bian'}">
							<script type="text/javascript" src="${pageContext.request.contextPath }/js/index.js"></script>
							<script type="text/javascript">isLogin();</script>
							<div class="main clearfix" id="tousu_div_id">
								<jsp:include flush="true" page="../jsp/tousu.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
							<div class="main clearfix" id="mychat_div_id" style="display: none;">
								<jsp:include flush="true" page="../jsp/mychat.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
						</c:if>
						<c:if test="${myList.navId=='userFeedBack'}">
							<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
							<script type="text/javascript">isLogin();</script>
							<div class="main clearfix" id="userFeedBack_div">
								<jsp:include flush="true" page="../jsp/userFeedBack.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
							<div class="main clearfix" id="userFeedBackAll_div" style="display:none;">
								<jsp:include flush="true" page="../jsp/userFeedBackAll.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
						</c:if>
					</c:if>
					<c:if test="${status.index != 0}">
						<c:if test="${myList.navId=='ke_fu'}">
							<div class="main clearfix" id="mychat_div_id" style="display: none;">
								<jsp:include flush="true" page="../jsp/mychat.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
						</c:if>
						<c:if test="${myList.navId=='zhou_bian'}">
							<div class="main clearfix" id="tousu_div_id" style="display: none;">
								<jsp:include flush="true" page="../jsp/tousu.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
							<div class="main clearfix" id="tousu_lie_div_id" style="display: none;">
								<jsp:include flush="true" page="../jsp/tousu_lie.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
						</c:if>
						<c:if test="${myList.navId=='userFeedBack'}">
							<div class="main clearfix" id="userFeedBack_div" style="display: none;">
								<jsp:include flush="true" page="../jsp/userFeedBack.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
							<div class="main clearfix" id="userFeedBackAll_div" style="display: none;">
								<jsp:include flush="true" page="../jsp/userFeedBackAll.jsp">
									<jsp:param name="communityId" value="{parameterValue | ${communityId}}" />
									<jsp:param name="listShopType" value="{parameterValue | ${listShopType}}" />
									<jsp:param name="adminId" value="{parameterValue | ${adminId}}" />
									<jsp:param name="username" value="{parameterValue | ${username}}" />
									<jsp:param name="password" value="{parameterValue | ${password}}" />
								</jsp:include>
							</div>
						</c:if>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	</section>
</div>
<iframe frameborder="0" name="iframeName" id="iframeName" style="display: none;" height="1000px" scrolling="no" width="100%" src="">
<script type="text/javascript">
parent.document.all("iframeName").style.height=document.body.scrollHeight; 
</script>
</iframe>
<c:if test="${!empty list }">
	<c:forEach items="${list }" varStatus="status" var="myList">
		<c:if test="${status.index == 0}">
			<c:choose>
				<c:when test="${myList.navId=='ke_fu' || myList.navId=='zhou_bian' || myList.navId=='userFeedBack'}"></c:when>
				<c:otherwise>
					<script type="text/javascript">
						index("<%=path %>/jsp/operation/${myList.ruleName}");
					</script>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:forEach>
</c:if>
</body>
<script type="text/javascript">
function reinitIframe(){
	var iframe = document.getElementById("iframeName");
	try {
		iframe.height = Math.max(iframe.contentWindow.document.body.scrollHeight, window.screen.height);
	} catch (ex) {
	}
}

window.setInterval("reinitIframe()", 200);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/click_num2.js?version=<%=versionNum%>"></script>
</html>