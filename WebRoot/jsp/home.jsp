<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
	<!--<![endif]-->

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
		<meta name="keywords" content="小间物业,小间物业管理系统" />
		<meta name="Description" content="小间物业,小间物业管理系统" />
		<title>登录_小间物业管理系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js?version=<%=versionNum %>"></script>
		<script type='text/javascript'
			src='${pageContext.request.contextPath }/easymob-webim1.0/strophe-custom-1.0.0.js?version=<%=versionNum %>'></script>
		<script type='text/javascript'
			src='${pageContext.request.contextPath }/easymob-webim1.0/json2.js?version=<%=versionNum %>'></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/easemob.im-1.0.3.js?version=<%=versionNum %>"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/bootstrap.js?version=<%=versionNum %>"></script>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath }/easymob-webim1.0/css/webim.css?version=<%=versionNum %>" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath }/easymob-webim1.0/css/bootstrap.css?version=<%=versionNum %>" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/md5.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/base64.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>

		<script type="text/javascript">

	var homeShopsMessageMap = {};
	var homeUsersMessageMap = {};
	var homeUsersList;
	var homeShopsList;
	var homeUserThisObj;
    var homeUserobjSpan=0;
    var homeUserTousuSpan=0;
	var usersOrShops = 0; // 0 - users 1 - shops
	
	function getMessageListFromUser(from,to){
	
		var path = "<%= basePath%>api/v1/usersMessages?messageFrom="+from+"&messageTo="+to;
			$.ajax({
				url: path,
				type: "GET",
				dataType:"json",
				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;		
						var len = data.length;
						messageArray = new Array();
						for(var i = 0 ; i < len ; i++){
							var message = {
								"from":data[i].messageFrom ,
								"to":data[i].messageTo,
								"msg":data[i].msg,
								"ext":{
									"nickname":data[i].nickname,
									"avatar":data[i].avatar,
									"content":data[i].content
								}
							};
							messageArray.push(message);
						}
						homeUsersMessageMap[from] = messageArray;
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function userMessageList(){
		
		var path = "<%= basePath%>api/v1/communities/${communityId}/messageList/${adminId}";
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;		
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							
							if(i == 0){
							//	getMessageListFromUser(data[i].emobIdFrom,data[i].emobIdTo);
							}
						
							var message = {
								"from":data[i].emobIdFrom ,
								
								"ext":{
									"nickname":data[i].nickname,
									"avatar":data[i].avatar
									
								}
							};
							if(homeUsersList == null || homeUsersList == undefined){
								homeUsersList = new Array();
							}
							homeUsersList.push(message);
						}
						addHomeUserList();
								
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function addHomeUserList(){
		if(usersOrShops == 0){ // 加载用户列表
			if(homeUsersList != null && homeUsersList != undefined){
				var len = homeUsersList.length;
				for(var i = 0 ; i < len ; i++){
					message = homeUsersList[i];
					from = message.from;
					homeExt = message.ext;
					if(i == 0){
						$("#mychat_user_title").html("");
						$("#mychat_user_title").html(homeExt.nickname);
						getHomeMessageList(from);
					}
					$("#contactlistUL").append("<li id=\""+from+"\" onclick=\"getHomeMessageList('"+from+"');\"><img src=\""+homeExt.avatar+"\"></img><a style=\"display:none;\"  href=\"#/"+homeExt.nickname+"/\">"+homeExt.nickname+"</a>"+homeExt.nickname+"</li>");	
				}
			}
		}else{ // 加载店家列表
			if(homeShopsList != null && homeShopsList != undefined){
				var len = homeShopsList.length;
				for(var i = 0 ; i < len ; i++){
					message = homeShopsList[i];
					from = message.from;
					homeExt = message.ext;
					if(i == 0){
						$("#mychat_user_title").html("");
						$("#mychat_user_title").html(homeExt.nickname);
						getHomeMessageList(from);
					}
					$("#contactlistUL").append("<li id=\""+from+"\" onclick=\"getHomeMessageList('"+from+"');\"><img src=\""+homeExt.avatar+"\"></img>"+homeExt.nickname+"</li>");	
				}
			}
		}
	}
	
	function getHomeMessageList(users){
		var objSpan = document.getElementById("span_"+users);
		if (objSpan != null) {
			objSpan.parentNode.removeChild(objSpan);
			homeUserobjSpan--;
		}
	    if(homeUserobjSpan==0||homeUserobjSpan<0){
	       var objSpan2 = document.getElementById("span_yezhuzixun");
		if (objSpan2 != null) {
			objSpan2.parentNode.removeChild(objSpan2);
			homeUserobjSpan=0;
		}
	    }
	    
	    
	    
	    
	
	  $("#mychat_user_title_emob_id").val(users);
	  var seLi= document.getElementById(users);
	  if(seLi != null){
	 	 	$(seLi).siblings("li").attr("class","");
	    	seLi.className="select";
			homeUserThisObj = users; 
	  }
	 
		var myThis = $("#null-nouser");
		myThis.html("");
		if(usersOrShops == 0){
			myMessageList = homeUsersMessageMap[users];
			if(myMessageList == null || myMessageList == undefined){
				getMessageListFromUser(users,curUserId);
				myMessageList = homeUsersMessageMap[users];
			}
			if(myMessageList != null && myMessageList != undefined){
				var len = myMessageList.length;
				for(var i = 0 ; i < len ; i ++){
				var message = myMessageList[i];
			
			
				var from = message.from;//消息的发送者
				var mestype = message.type;//消息发送的类型是群组消息还是个人消息
				var messageContent = message.data;//文本消息体
				var homeExt = message.ext;
				if(i == 0){
					$("#mychat_user_title").html(homeExt.nickname);
				}
				if(from == curUserId){
					myThis.append("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">"+message.msg+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+messageContent+" : 我 </font><br/>");
				}else{
					myThis.append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"+homeExt.content+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					//myThis.append("<font>"+homeUsersMessageMap[from][0].ext.nickname+" ："+message.ext.content+"</font><br/>");
				}
			}
			}
			
		}else{
			myMessageList = homeShopsMessageMap[users];
			if(myMessageList != null && myMessageList != undefined){
				var len = myMessageList.length;
			for(var i = 0 ; i < len ; i ++){
				var message = myMessageList[i];
			
			
				var from = message.from;//消息的发送者
				var mestype = message.type;//消息发送的类型是群组消息还是个人消息
				var messageContent = message.data;//文本消息体
				var homeExt = message.ext;
				if(i == 0){
					$("#mychat_user_title").html(homeExt.nickname);
				}
				if(from == curUserId){
					myThis.append("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+messageContent+" : 我 </font><br/>");
				}else{
					myThis.append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"+message.ext.content+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					//myThis.append("<font>"+homeShopsMessageMap[from][0].ext.nickname+" ："+message.ext.content+"</font><br/>");
				}
			}
			}
			
		}
	}
	
	function homeSendText(){
		var myThis = $("#null-nouser");
		var sendVal = $("#talkInputId").val();
			var ext={
		avatar:"<%= path%>/images/chat/serviceHeader.png",
		CMD_CODE:401,
		nickname:"物业客服"
		};
		var options = {
			to : homeUserThisObj,
			msg : sendVal,
			type : "chat",
			ext: ext
		};
		
		myThis.append("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">"+sendVal+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
		//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+sendVal+" : 我</font><br/>");
		conn.sendTextMessage(options);
	
		var myText = {
			from: curUserId ,
			to: homeUserThisObj ,
			type: "chat" , 
			data: sendVal,
			ext: ext
		};
		
		
		
		if(usersOrShops == 0){
			if(homeUsersMessageMap[homeUserThisObj] == undefined){
				var myMessageArray = new Array();
				myMessageArray.push(myText);
				homeUsersMessageMap[homeUserThisObj] = myMessageArray;
			}else{
				homeUsersMessageMap[homeUserThisObj].push(myText);
			}
		}else{
			if(homeShopsMessageMap[homeUserThisObj] == undefined){
				var myMessageArray = new Array();
				myMessageArray.push(myText);
				homeShopsMessageMap[homeUserThisObj] = myMessageArray;
			}else{
				homeShopsMessageMap[homeUserThisObj].push(myText);
			}
		}
		$("#talkInputId").val("");
		
	}

	function myFormatSeconds(value) {

    	var theTime = parseInt(value);// 秒

    	var theTime1 = 0;// 分

    	var theTime2 = 0;// 小时

   	 	if(theTime > 60) {

        	theTime1 = parseInt(theTime/60);

        	theTime = parseInt(theTime%60);

            	if(theTime1 > 60) {

            		theTime2 = parseInt(theTime1/60);

            		theTime1 = parseInt(theTime1%60);

            }

    	}

        var result = ""+parseInt(theTime)+"秒";

        if(theTime1 > 0) {

        	result = ""+parseInt(theTime1)+"分"+result;

        }

        if(theTime2 > 0) {

        	result = ""+parseInt(theTime2)+"小时"+result;

        }
	
    	return result;

	}
	
	function myHeadGoHome(){
		document.getElementById("head_username").value = myUserName;
		document.getElementById("head_form").submit();
	}

	var myHomeMap = {};
	var myUserMap = {};
	var myMessageMap = {};
	getShopList();
	function getShopList(){
		var path = "<%= path%>/api/v1/communities/${communityId}/users/getShopUser";
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				data = data.info;
				for(var i = 0 ; i < data.length ; i++){
					var emobId = data[i].emobId;
					myHomeMap[emobId] = "1";
				}
				console.info(myHomeMap);
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	
	function homeMarks(users){
		var objSpan = document.getElementById("tousu_span_"+users);
		if (objSpan != null) {
			objSpan.parentNode.removeChild(objSpan);
			homeUserTousuSpan--;
		}
		if(homeUserobjSpan==0||homeUserobjSpan<0){
			var objSpan2 = document.getElementById("span_tousu");
			if (objSpan2 != null) {
				objSpan2.parentNode.removeChild(objSpan2);
				homeUserTousuSpan=0;
			}
		}
	}
	
	var myUserName = "<%=request.getAttribute("username") %>";
	var myPassWord = "<%=request.getAttribute("password") %>";
	var apiURL = null;
	var curUserId = null;
	var curChatUserId = null;
	var conn = null;
	var msgCardDivId = "chat01";
	var talkToDivId = "talkTo";
	var talkInputId = "talkInputId";
	var fileInputId = "fileInput";
	var maxWidth = 200;
	var textSending = false;
	var time = 0;
	window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
	var showWaitLoginedUI = function() {
		$('#waitLoginmodal').modal('show');
	};
	var hiddenWaitLoginedUI = function() {
		$('#waitLoginmodal').modal('hide');
	};
	
	//easemobwebim-sdk注册回调函数列表
	$(document).ready(function() {
		showWaitLoginedUI();
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

	var logout = function() {
		conn.close();
	};
	
	var showChatUI = function() {
		$('#content').css({
			"display" : "block"
		});
		var login_userEle = document.getElementById("login_user").children[0];
		login_userEle.innerHTML = curUserId;
		login_userEle.setAttribute("title", curUserId);
	};
	//登录之前不显示web对话框
	var hiddenChatUI = function() {
		$('#content').css({
			"display" : "none"
		});
		document.getElementById(talkInputId).value = "";
	};
	//定义消息编辑文本域的快捷键，enter和ctrl+enter为发送，alt+enter为换行
	//控制提交频率
	$(function() {
		$("textarea").keydown(function(event) {
			if (event.altKey && event.keyCode == 13) {
				e = $(this).val();
				$(this).val(e + '\n');
			} else if (event.ctrlKey && event.keyCode == 13) {
				//e = $(this).val();
				//$(this).val(e + '<br>');
				event.returnValue = false;
				sendText(); 
				return false;
			} else if (event.keyCode == 13) {
				event.returnValue = false;
				sendText();
				return false;
			}

		});
	});
	

	//处理连接时函数,主要是登录成功后对页面元素做处理
	var handleOpen = function(conn) {
		//从连接中获取到当前的登录人注册帐号名
		curUserId = conn.context.userId;
		userMessageList();
		
		hiddenWaitLoginedUI();
		showChatUI();
		conn.setPresence();//设置用户上线状态，必须调用
	};

	//连接中断时的处理，主要是对页面进行处理
	var handleClosed = function() {
		alert("连接中断了，请确认您的账号是否在别处登录!");
		top.location='/';
	};
	//异常情况下的处理方法
	var handleError = function(e) {
		if (curUserId) {
			var msg = e.msg;
			if (e.type == EASEMOB_IM_CONNCTION_SERVER_CLOSE_ERROR) {
				if (msg == "") {
					alert("服务器器断开连接,可能是因为在别处登录");
						top.location='/'; 
				} else {
					alert("服务器器断开连接");
						top.location='/'; 
				}
			} else {
				alert(msg);
			}
		} else {
			hiddenWaitLoginedUI();
			alert(e.msg + ",请重新登录");
			top.location='/';
		}
	};
	//判断要操作的联系人和当前联系人列表的关系
	var contains = function(roster, contact) {
		var i = roster.length;
		while (i--) {
			if (roster[i].name === contact.name) {
				return true;
			}
		}
		return false;
	};

	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i].name == val.name)
				return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};

	//登录系统时的操作方法
	var login = function() {
		var user = $("#username").val();
		var pass = $("#password").val();
		if (user == '' || pass == '') {
			user = "<%= request.getAttribute("username") %>";
			pass = "<%= request.getAttribute("password") %>";
			if (user == '' || pass == '') {
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
			//连接时提供appkey
			appKey : 'xiaojiantech#bangbang'
		});
		return false;
	};

	var myLogin = function() {
		var user = myUserName;
		var pass = myPassWord;
		if (user == '' || pass == '') {
			alert("请输入用户名和密码");
			return;
		}

		pass = base64decode(pass);

		showWaitLoginedUI();
		//根据用户名密码登录系统
		conn.open({
			apiUrl : apiURL,
			user : user,
			pwd : pass,
			//连接时提供appkey
			appKey : 'xiaojiantech#bangbang'
		});

		return false;
	};

	//设置当前显示的聊天窗口div，如果有联系人则默认选中联系人中的第一个联系人，如没有联系人则当前div为null-nouser
	var setCurrentContact = function(defaultUserId) {
		showContactChatDiv(defaultUserId);
		if (curChatUserId != null) {
			hiddenContactChatDiv(curChatUserId);
		} else {
			$('#null-nouser').css({
				"display" : "none"
			});
		}
		curChatUserId = defaultUserId;
	};

	//构造联系人列表
	var buildContactDiv = function(contactlistDivId, roster, isUser) {

		var uielem = document.getElementById("contactlistUL");
		var cache = {};
		for (i = 0; i < roster.length; i++) {
			if (!(roster[i].subscription == 'both' || roster[i].subscription == 'from')) {
				continue;
			}
			var jid = roster[i].jid;
			var userName = jid.substring(jid.indexOf("_") + 1).split("@")[0];
			if (userName in cache) {
				continue;
			}
			console.info(userName);
			if (isUser == 1) {
				if (myHomeMap[userName] == "1") {
					continue;
				}
			} else {
				if (myHomeMap[userName] != "1") {
					continue;
				}
			}
			cache[userName] = true;
			var lielem = $('<li>').attr({
				'id' : userName,
				'class' : 'offline',
				'className' : 'offline',
				'type' : 'chat',
				'displayName' : userName
			}).click(function() {
				chooseContactDivClick(this);
			});
			$('<img>')
					.attr(
							"src",
							"${pageContext.request.contextPath }/easymob-webim1.0/img/head/contact_normal.png")
					.appendTo(lielem);
			$('<span>').html(userName).appendTo(lielem);
			$('#contactlistUL').append(lielem);
		}
		var contactlist = document.getElementById(contactlistDivId);
		var children = contactlist.children;
		if (children.length > 0) {
			contactlist.removeChild(children[0]);
		}
		contactlist.appendChild(uielem);
		console.info(new Date().getTime());
	};

	//选择联系人的处理
	var getContactLi = function(chatUserId) {
		return document.getElementById(chatUserId);
	};

	//构造当前聊天记录的窗口div
	var getContactChatDiv = function(chatUserId) {
		return document.getElementById(curUserId + "-" + chatUserId);
	};

	//如果当前没有某一个联系人的聊天窗口div就新建一个
	var createContactChatDiv = function(chatUserId) {
		var msgContentDivId = curUserId + "-" + chatUserId;
		var newContent = document.createElement("div");
		$(newContent).attr({
			"id" : msgContentDivId,
			"class" : "chat01_content",
			"className" : "chat01_content",
			"style" : "display:none"
		});
		return newContent;
	};

	//显示当前选中联系人的聊天窗口div，并将该联系人在联系人列表中背景色置为蓝色
	var showContactChatDiv = function(chatUserId) {
		var contentDiv = getContactChatDiv(chatUserId);
		if (contentDiv == null) {
			contentDiv = createContactChatDiv(chatUserId);
			document.getElementById(msgCardDivId).appendChild(contentDiv);
		}
		contentDiv.style.display = "block";
		var contactLi = document.getElementById(chatUserId);
		if (contactLi == null) {
			return;
		}
		contactLi.style.backgroundColor = "#fff";
		var dispalyTitle = chatUserId;//聊天窗口显示当前对话人名称
		document.getElementById(talkToDivId).children[0].innerHTML = dispalyTitle;
	};
	//对上一个联系人的聊天窗口div做隐藏处理，并将联系人列表中选择的联系人背景色置空
	var hiddenContactChatDiv = function(chatUserId) {
		var contactLi = document.getElementById(chatUserId);
		if (contactLi) {
			contactLi.style.backgroundColor = "";
		}
		var contentDiv = getContactChatDiv(chatUserId);
		if (contentDiv) {
			contentDiv.style.display = "none";

		}

	};
	//切换联系人聊天窗口div
	var chooseContactDivClick = function(li) {
		var chatUserId = li.id;
		if ($(li).attr("type") == 'groupchat'
				&& ('true' != $(li).attr("joined"))) {
			conn.join({
				roomId : $(li).attr("roomId")
			});
			$(li).attr("joined", "true");
		}
		if (chatUserId != curChatUserId) {
			if (curChatUserId == null) {
				showContactChatDiv(chatUserId);
			} else {
				showContactChatDiv(chatUserId);
				hiddenContactChatDiv(curChatUserId);
			}
			curChatUserId = chatUserId;
		}
		//对默认的null-nouser div进行处理,走的这里说明联系人列表肯定不为空所以对默认的聊天div进行处理
		$('#null-nouser').css({
			"display" : "none"
		});
		var badgespan = $(li).children(".badge");
		if (badgespan && badgespan.length > 0) {
			li.removeChild(li.children[2]);
		}

		//点击有未读消息对象时对未读消息提醒的处理
		var badgespanGroup = $(li).parent().parent().parent().find(".badge");
		if (badgespanGroup && badgespanGroup.length == 0) {
			$(li).parent().parent().parent().prev().children().children()
					.remove();
		}
	};

	var clearContactUI = function(contactlistUL, contactChatDiv) {
		//清除左侧联系人内容
		$('#contactlistUL').empty();

		//清除右侧对话框内容
		document.getElementById(talkToDivId).children[0].innerHTML = "";
		var chatRootDiv = document.getElementById(contactChatDiv);
		var children = chatRootDiv.children;
		for ( var i = children.length - 1; i > 1; i--) {
			chatRootDiv.removeChild(children[i]);
		}
		$('#null-nouser').css({
			"display" : "block"
		});
	};

	var emotionFlag = false;
	var showEmotionDialog = function() {
		if (emotionFlag) {
			$('#wl_faces_box').css({
				"display" : "block"
			});
			return;
		}
		emotionFlag = true;
		// Easemob.im.Helper.EmotionPicData设置表情的json数组
		var sjson = Easemob.im.Helper.EmotionPicData;
		for ( var key in sjson) {
			var emotions = $('<img>').attr({
				"id" : key,
				"src" : sjson[key],
				"style" : "cursor:pointer;"
			}).click(function() {
				selectEmotionImg(this);
			});
			$('<li>').append(emotions).appendTo($('#emotionUL'));
		}
		$('#wl_faces_box').css({
			"display" : "block"
		});
	};
	//表情选择div的关闭方法
	var turnoffFaces_box = function() {
		$("#wl_faces_box").fadeOut("slow");
	};
	var selectEmotionImg = function(selImg) {
		var txt = document.getElementById(talkInputId);
		txt.value = txt.value + selImg.id;
		txt.focus();
	};

	var sendText = function() {
		if (textSending) {
			return;
		}
		textSending = true;

		var msgInput = document.getElementById(talkInputId);
		var msg = msgInput.value;

		if (msg == null || msg.length == 0) {
			return;
		}
		var to = curChatUserId;
		if (to == null) {
			return;
		}
		var options = {
			to : to,
			msg : msg,
			type : "chat"
		};
		//easemobwebim-sdk发送文本消息的方法 to为发送给谁，meg为文本消息对象
		conn.sendTextMessage(options);

		//当前登录人发送的信息在聊天窗口中原样显示
		var msgtext = msg.replace(/\n/g, '<br>');

		appendMsg(curUserId, to, msgtext);
		turnoffFaces_box();
		msgInput.value = "";
		msgInput.focus();

		setTimeout(function() {
			textSending = false;
		}, 1000);
	};
	//easemobwebim-sdk收到文本消息的回调方法的实现
	var handleTextMessage = function(message) {
		var from = message.from;//消息的发送者

		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var messageContent = message.data;//文本消息体
		//   根据消息体的to值去定位那个群组的聊天记录
		var room = message.to;

		var homeExt = message.ext;
		console.info(homeExt);
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "403") { // 投诉
             var tousu_emob_id_from = document
					.getElementById("tousu_emob_id_from").value;
			var objSpan403 = document.getElementById("span_tousu");
			if (objSpan403 == null) {
				var tousuClassName = $("#tousu").attr("class");
				if (tousuClassName != "select") {
					$("#tousu")
							.append(
									"<span id=\"span_tousu\" class=\"pop-tip\">&nbsp;</span>");
				}
			}

			$("#tousu_contactlistUL").find("li").css("background", "#FFFFFF");
			var objSpan4032 = document.getElementById("tousu_span_" + from);
			if (objSpan4032 == null) {

				var tousuClassName2 = $("#tousu").attr("class");
				if (tousuClassName2 != "select" || tousu_emob_id_from != from) {
					$("#" + from)
							.prepend(
									"<span id=\"tousu_span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					homeUserTousuSpan++;
				}

			}
			//$("#" + from ).css("background", "#6A7593");

			if (myMessageMap[from] == undefined) {
				var myMessageArray = new Array();
				myMessageArray.push(message);
				myMessageMap[from] = myMessageArray;
			} else {
				myMessageMap[from].push(message);
			}
			
			if (tousu_emob_id_from == from) {
				var myThis = $("#tousu_null-nouser");
				myThis.html("");
				if (myMessageMap[from] != undefined) {
					var myTouSuList = myMessageMap[from];
					for ( var i = 0; i < myTouSuList.length; i++) {
						var message = myTouSuList[i];
						var from = message.from;//消息的发送者
						var mestype = message.type;//消息发送的类型是群组消息还是个人消息
						var messageContent = message.data;//文本消息体
						//   根据消息体的to值去定位那个群组的聊天记录
						var room = message.to;
						if (from == curUserId) {
							myThis
									.append("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">"
											+ messageContent
											+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
							//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + "： 我</font><br/>");
						} else {
							myThis
									.append("<div class=\"text-left\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
											+ myUserMap[from]
											+ " ："
											+ messageContent
											+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
							//myThis.append("<font>" + myUserMap[from] + " ："+ messageContent + "</font><br/>");
						}

					}
				}
			}
			return;
		} else if (homeExtCMD_CODE == "401") { // 业主咨询
			var emob_id = $("#mychat_user_title_emob_id").val();
			var objSpan3 = document.getElementById("span_yezhuzixun");
			if (objSpan3 == null) {

				var headClassName = $("#yezhuzixun").attr("class");
				if (headClassName != "select") {
					$("#yezhuzixun")
							.append(
									"<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");

				}
				if (emob_id != from & emob_id != "") {

					$("#yezhuzixun")
							.append(
									"<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");

				}

			}

			if (emob_id == from || emob_id == "") {
				$("#mychat_user_title").html("");
				$("#mychat_user_title_emob_id").val(from);
				$("#mychat_user_title").html(homeExt.nickname);
				$("#accordion1").html("所有业主");

				usersOrShops = 0;
				//	document.getElementById("yezhuzixun").click();
				if (homeUsersMessageMap[from] == undefined) {
					var myMessageArray = new Array();
					myMessageArray.push(message);
					homeUsersMessageMap[from] = myMessageArray;
				} else {
					homeUsersMessageMap[from].push(message);
				}
				if (homeUsersList == null) {
					homeUsersList = new Array();

				}
				var len = homeUsersList.length;
				var isNull = 0; // 1 - 有  0 - 没有
				for ( var i = 0; i < len; i++) {
					if (homeUsersList[i].from == from) {
						isNull = 1;
						break;
					}
				}
				if (isNull == 0) {
					homeUsersList.push(message);

					$("#contactlistUL")
							.prepend("<li id=\""
											+ from
											+ "\" onclick=\"getHomeMessageList('"
											+ from
											+ "');\"><img src=\""+homeExt.avatar+"\"></img><a style=\"display:none;\"  href=\"#/"+homeExt.nickname+"/\">"+homeExt.nickname+"</a>"+homeExt.nickname+"</li>");
				}
				var headClassName = $("#yezhuzixun").attr("class");
				if (headClassName != "select") {

					var objSpan5 = document.getElementById("span_" + from);
					if (objSpan5 == null) {
						$("#" + from)
								.prepend(
										"<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
						homeUserobjSpan++;
					}
				}
				if (mestype == 'groupchat') {
					//appendMsg(message.from, message.to, messageContent, mestype);
				} else {
					//	appendMsg(from, from, messageContent);
				}

				if (from == homeUserThisObj || homeUserThisObj == null) {
					$("#null-nouser")
							.append(
									"<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
											+ homeExt.content
											+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					//$("#null-nouser").append("<font>" + homeExt.nickname + ": " + homeExt.content + "</font></br>");
				} else { // 获取from的内容

				}

				homeUserThisObj = from;

			} else {

				$("#accordion1").html("所有业主");

				usersOrShops = 0;
				//	document.getElementById("yezhuzixun").click();
				if (homeUsersMessageMap[from] == undefined) {
					var myMessageArray = new Array();
					myMessageArray.push(message);
					homeUsersMessageMap[from] = myMessageArray;
				} else {
					homeUsersMessageMap[from].push(message);
				}
				if (homeUsersList == null) {
					homeUsersList = new Array();

				}
				var len = homeUsersList.length;
				var isNull = 0; // 1 - 有  0 - 没有
				for ( var i = 0; i < len; i++) {
					if (homeUsersList[i].from == from) {
						isNull = 1;
						break;
					}
				}
				if (isNull == 0) {
					homeUsersList.push(message);

					$("#contactlistUL")
							.prepend(
									"<li id=\""
											+ from
											+ "\" onclick=\"getHomeMessageList('"
											+ from
											+ "');\"><span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span><img src=\""+homeExt.avatar+"\"></img>"
											+ homeExt.nickname + "</li>");
					homeUserobjSpan++;
				} else {
					var objSpan = document.getElementById("span_" + from);
					if (objSpan == null) {
						$("#" + from)
								.prepend(
										"<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
						homeUserobjSpan++;
					}
				}

				if (mestype == 'groupchat') {
					//appendMsg(message.from, message.to, messageContent, mestype);
				} else {
					//	appendMsg(from, from, messageContent);
				}

				//homeUserThisObj = from;

			}
		} else if (homeExtCMD_CODE == "402") {// 店家咨询
			//document.getElementById("dianjiazixun").click();
			$("#mychat_user_title").html("");
			$("#mychat_user_title").html(homeExt.nickname);
			$("#accordion1").html("所有店家");
			$("#dianjiazixun").addClass("select").parent().siblings("li").find(
					"a").removeClass("select");
			setNone();
			$("#yezhu_div_id").attr("style", "display:block");

			usersOrShops = 1;
			if (homeShopsMessageMap[from] == undefined) {
				var myMessageArray = new Array();
				myMessageArray.push(message);
				homeShopsMessageMap[from] = myMessageArray;
			} else {
				homeShopsMessageMap[from].push(message);
			}
			if (homeShopsList == null) {
				homeShopsList = new Array();

			}
			var len = homeShopsList.length;
			var isNull = 0; // 1 - 有  0 - 没有
			for ( var i = 0; i < len; i++) {
				if (homeShopsList[i].from == from) {
					isNull = 1;
					break;
				}
			}
			if (isNull == 0) {
				homeShopsList.push(message);
				$("#contactlistUL")
						.append(
								"<li id=\""
										+ from
										+ "\" onclick=\"getHomeMessageList('"
										+ from
										+ "');\"><img src=\""+homeExt.avatar+"\"></img>"
										+ homeExt.nickname + "</li>");
			}

			if (from == homeUserThisObj || homeUserThisObj == null) {
				$("#null-nouser")
						.append(
								"<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
										+ homeExt.content
										+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				//$("#null-nouser").append("<font>" + homeExt.nickname + ": " + homeExt.content+ "</font></br>");
			} else { // 获取from的内容

			}

			homeUserThisObj = from;
		} else if (homeExtCMD_CODE == "404") {// 缴费通知
			document.getElementById("jiaofei").click();
		}

	};
	//easemobwebim-sdk收到表情消息的回调方法的实现，message为表情符号和文本的消息对象，文本和表情符号sdk中做了
	//统一的处理，不需要用户自己区别字符是文本还是表情符号。
	var handleEmotion = function(message) {
		var from = message.from;
		var room = message.to;
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "401") { // 业主咨询表情处理
			document.getElementById("yezhuzixun").click();
			if (homeUsersMessageMap[from] == undefined) {
				var tempArray = new Array();
				tempArray.push(message);
				homeUsersMessageMap[from] = tempArray;
			} else {
				homeUsersMessageMap[from].push(message);
			}
			if (mestype == 'groupchat') {
				appendMsg(message.from, message.to, message, mestype);
			} else {
				appendMsg(from, from, message);
			}
		} else if (homeExtCMD_CODE == "402") { // 店家咨询表情处理
			document.getElementById("dianjiazixun").click();
			if (homeShopsMessageMap[from] == undefined) {
				var tempArray = new Array();
				tempArray.push(message);
				homeShopsMessageMap[from] = tempArray;
			} else {
				homeShopsMessageMap[from].push(message);
			}
			if (mestype == 'groupchat') {
				appendMsg(message.from, message.to, message, mestype);
			} else {
				appendMsg(from, from, message);
			}
		} else if (homeExtCMD_CODE == "403") { // 投诉表情处理

		} else if (homeExtCMD_CODE == "404") { // 缴费

		}

	};
	//easemobwebim-sdk收到图片消息的回调方法的实现
	var handlePictureMessage = function(message) {
		var filename = message.filename;//文件名称，带文件扩展名
		var from = message.from;//文件的发送者
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var contactDivId = from;
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "401") { // 业主咨询表情处理
			document.getElementById("yezhuzixun").click();
			if (homeUsersMessageMap[from] == undefined) {
				var tempArray = new Array();
				tempArray.push(message);
				homeUsersMessageMap[from] = tempArray;
			} else {
				homeUsersMessageMap[from].push(message);
			}

		} else if (homeExtCMD_CODE == "402") { // 店家咨询表情处理
			document.getElementById("dianjiazixun").click();
			if (homeShopsMessageMap[from] == undefined) {
				var tempArray = new Array();
				tempArray.push(message);
				homeShopsMessageMap[from] = tempArray;
			} else {
				homeShopsMessageMap[from].push(message);
			}

		} else if (homeExtCMD_CODE == "403") { // 投诉表情处理

		} else if (homeExtCMD_CODE == "404") { // 缴费

		}

		var options = message;
		// 图片消息下载成功后的处理逻辑
		options.onFileDownloadComplete = function(response, xhr) {
			var objectURL = window.URL.createObjectURL(response);
			img = document.createElement("img");
			img.onload = function(e) {
				img.onload = null;
				window.URL.revokeObjectURL(img.src);
			};
			img.onerror = function() {
				img.onerror = null;
				if (typeof FileReader == 'undefined') {
					img.alter = "当前浏览器不支持blob方式";
					return;
				}
				img.onerror = function() {
					img.alter = "当前浏览器不支持blob方式";
				};
				var reader = new FileReader();
				reader.onload = function(event) {
					img.src = this.result;
				};
				reader.readAsDataURL(response);
			};
			img.src = objectURL;
			var pic_real_width = options.width;

			if (pic_real_width == 0) {
				$("<img/>").attr("src", objectURL).load(function() {
					pic_real_width = this.width;
					if (pic_real_width > maxWidth) {
						img.width = maxWidth;
					} else {
						img.width = pic_real_width;
					}
					appendMsg(from, contactDivId, {
						data : [ {
							type : 'pic',
							filename : filename,
							data : img
						} ]
					});

				});
			} else {
				if (pic_real_width > maxWidth) {
					img.width = maxWidth;
				} else {
					img.width = pic_real_width;
				}
				appendMsg(from, contactDivId, {
					data : [ {
						type : 'pic',
						filename : filename,
						data : img
					} ]
				});
			}
		};
		options.onFileDownloadError = function(e) {
			appendMsg(from, contactDivId, e.msg + ",下载图片" + filename + "失败");
		};
		//easemobwebim-sdk包装的下载文件对象的统一处理方法。
		Easemob.im.Helper.download(options);
	};

	//easemobwebim-sdk收到音频消息回调方法的实现
	var handleAudioMessage = function(message) {
		var filename = message.filename;
		var filetype = message.filetype;
		var from = message.from;

		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var contactDivId = from;
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "401") { // 业主咨询表情处理
			document.getElementById("yezhuzixun").click();
			if (homeUsersMessageMap[from] == undefined) {
				var tempArray = new Array();
				tempArray.push(message);
				homeUsersMessageMap[from] = tempArray;
			} else {
				homeUsersMessageMap[from].push(message);
			}

		} else if (homeExtCMD_CODE == "402") { // 店家咨询表情处理
			document.getElementById("dianjiazixun").click();
			if (homeShopsMessageMap[from] == undefined) {
				var tempArray = new Array();
				tempArray.push(message);
				homeShopsMessageMap[from] = tempArray;
			} else {
				homeShopsMessageMap[from].push(message);
			}

		} else if (homeExtCMD_CODE == "403") { // 投诉表情处理

		} else if (homeExtCMD_CODE == "404") { // 缴费

		}

		var options = message;
		options.onFileDownloadComplete = function(response, xhr) {
			var objectURL = window.URL.createObjectURL(response);
			var audio = document.createElement("audio");
			if (("src" in audio) && ("controls" in audio)) {
				audio.onload = function() {
					audio.onload = null;
					window.URL.revokeObjectURL(audio.src);
				};
				audio.onerror = function() {
					audio.onerror = null;
					appendMsg(from, contactDivId, "当前浏览器不支持播放此音频:" + filename);
				};
				audio.controls = "controls";
				audio.src = objectURL;
				appendMsg(from, contactDivId, {
					data : [ {
						type : 'audio',
						filename : filename,
						data : audio
					} ]
				});
				//audio.play();
				return;
			}
		};
		options.onFileDownloadError = function(e) {
			appendMsg(from, contactDivId, e.msg + ",下载音频" + filename + "失败");
		};
		options.headers = {
			"Accept" : "audio/mp3"
		};
		Easemob.im.Helper.download(options);
	};

	var handleLocationMessage = function(message) {
		var from = message.from;
		var addr = message.addr;
		var ele = appendMsg(from, from, addr);
		return ele;
	};

	//显示聊天记录的统一处理方法
	var appendMsg = function(who, contact, message, chattype) {
		var contactUL = document.getElementById("contactlistUL");
		var contactDivId = contact;
		var contactLi = getContactLi(contactDivId);
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		var localMsg = null;
		if (typeof message == 'string') {
			localMsg = Easemob.im.Helper.parseTextMessage(message);
			localMsg = localMsg.body;
		} else {
			localMsg = message.data;
		}
		var headstr = [ "<p1>" + who + "   <span></span>" + "   </p1>",
				"<p2>" + getLoacalTimeString() + "<b></b><br/></p2>" ];
		var header = $(headstr.join(''));

		var lineDiv = document.createElement("div");
		for ( var i = 0; i < header.length; i++) {
			var ele = header[i];
			lineDiv.appendChild(ele);
		}
		var messageContent = localMsg;
		for ( var i = 0; i < messageContent.length; i++) {
			var msg = messageContent[i];
			var type = msg.type;
			var data = msg.data;
			if (type == "emotion") {
				var eletext = "<p3><img src='" + data + "'/></p3>";
				var ele = $(eletext);
				for ( var j = 0; j < ele.length; j++) {
					lineDiv.appendChild(ele[j]);
				}
			} else if (type == "pic") {
				var filename = msg.filename;
				var fileele = $("<p3>" + filename + "</p3><br>");
				for ( var j = 0; j < fileele.length; j++) {
					lineDiv.appendChild(fileele[j]);
				}
				lineDiv.appendChild(data);
			} else if (type == 'audio') {
				var filename = msg.filename;
				var fileele = $("<p3>" + filename + "</p3><br>");
				for ( var j = 0; j < fileele.length; j++) {
					lineDiv.appendChild(fileele[j]);
				}
				lineDiv.appendChild(data);
			} else {
				var eletext = "<p3>" + data + "</p3>";
				var ele = $(eletext);
				ele[0].setAttribute("class", "chat-content-p3");
				ele[0].setAttribute("className", "chat-content-p3");
				if (curUserId == who) {
					ele[0].style.backgroundColor = "#EBEBEB";
				}
				for ( var j = 0; j < ele.length; j++) {
					lineDiv.appendChild(ele[j]);
				}
			}
		}
		if (curChatUserId == null && chattype == null) {
			setCurrentContact(contact);
			if (time < 1) {
				$('#accordion3').click();
				time++;
			}
		}
		if (curChatUserId && curChatUserId.indexOf(contact) < 0) {
			var contactLi = getContactLi(contactDivId);
			if (contactLi == null) {
				return;
			}
			contactLi.style.backgroundColor = "green";
			var badgespan = $(contactLi).children(".badge");
			if (badgespan && badgespan.length > 0) {
				var count = badgespan.text();
				var myNum = new Number(count);
				myNum++;
				badgespan.text(myNum);

			} else {
				$(contactLi).append('<span class="badge">1</span>');
			}
			//联系人不同分组的未读消息提醒
			var badgespanGroup = $(contactLi).parent().parent().parent().prev()
					.children().children(".badgegroup");
			if (badgespanGroup && badgespanGroup.length == 0) {
				$(contactLi).parent().parent().parent().prev().children()
						.append('<span class="badgegroup">New</span>');
			}

		}
		var msgContentDiv = getContactChatDiv(contactDivId);
		if (curUserId == who) {
			lineDiv.style.textAlign = "right";
		} else {
			lineDiv.style.textAlign = "left";
		}
		var create = false;
		if (msgContentDiv == null) {
			msgContentDiv = createContactChatDiv(contactDivId);
			create = true;
		}
		msgContentDiv.appendChild(lineDiv);
		if (create) {
			document.getElementById(msgCardDivId).appendChild(msgContentDiv);
		}
		msgContentDiv.scrollTop = msgContentDiv.scrollHeight;
		return lineDiv;
	};

	//添加输入框鼠标焦点进入时清空输入框中的内容
	var clearInputValue = function(inputId) {
		$('#' + inputId).val('');
	};

	//清除聊天记录
	var clearCurrentChat = function clearCurrentChat() {
		var currentDiv = getContactChatDiv(curChatUserId) || createContactChatDiv(curChatUserId);
		currentDiv.innerHTML = "";
	};

	var getObjectURL = function getObjectURL(file) {
		var url = null;
		if (window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	};
	var getLoacalTimeString = function getLoacalTimeString() {
		var date = new Date();
		return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	};
</script>
	</head>
	<body>

		<jsp:include flush="true" page="public/head.jsp">
			<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
			<jsp:param name="username" value="{parameterValue | ${username }}" />
			<jsp:param name="password" value="{parameterValue | ${password }}" />
			<jsp:param name="communityId"
				value="{parameterValue | ${communityId }}" />
		</jsp:include>
		<!--<div>
			<jsp:include flush="true" page="public/nav.jsp">
				<jsp:param name="list" value="{parameterValue | ${list }}" />
			</jsp:include>
		</div>
		
		-->
		<section>
			<div class="content-normal clearfix">


				<c:if test="${!empty list }">

					<c:forEach items="${list }" varStatus="status" var="myList">

						<c:if test="${status.index == 0}">
							<div class="main clearfix" id="${myList.divId }"
								style="display: block;">
								<jsp:include flush="true" page="../jsp/${myList.ruleName }">
									<jsp:param name="communityId"
										value="{parameterValue | ${communityId }}" />
									<jsp:param name="listShopType"
										value="{parameterValue | ${listShopType }}" />
									<jsp:param name="adminId"
										value="{parameterValue | ${adminId }}" />
									<jsp:param name="username"
										value="{parameterValue | ${username }}" />
									<jsp:param name="password"
										value="{parameterValue | ${password }}" />
								</jsp:include>
							</div>
						</c:if>

						<c:if test="${status.index != 0}">
							<div class="main clearfix" id="${myList.divId }"
								style="display: none;">
								<jsp:include flush="true" page="../jsp/${myList.ruleName }">
									<jsp:param name="communityId"
										value="{parameterValue | ${communityId }}" />
									<jsp:param name="listShopType"
										value="{parameterValue | ${listShopType }}" />
									<jsp:param name="adminId"
										value="{parameterValue | ${adminId }}" />
									<jsp:param name="username"
										value="{parameterValue | ${username }}" />
									<jsp:param name="password"
										value="{parameterValue | ${password }}" />
								</jsp:include>
							</div>
						</c:if>

					</c:forEach>
				</c:if>

				<div class="main clearfix" id="tousu_lie_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/tousu_lie.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="listShopType"
							value="{parameterValue | ${listShopType }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="community_cleaning_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/communityCleaning.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="listShopType"
							value="{parameterValue | ${listShopType }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="express_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/express.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />

					</jsp:include>
				</div>

				<div class="main clearfix" id="bangbang_juan_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/bangbang.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="repair_price_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/repair_price.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="add_master_div_id"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/add_master.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="repair_price_div_id_fei"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/repair_record.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="master_repair_datail_id_fei"
					style="display: none;">
					<jsp:include flush="true"
						page="../jsp/service/master_repair_detail.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="experss_id_fei"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/express.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

				<div class="main clearfix" id="experss_place_id_fei"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/express_place.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="shops_phone_id_fei"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/shops_phone.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="shops_edit_id_fei"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/shops_edit.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="shops_phone_express_id_fei"
					style="display: none;">
					<jsp:include flush="true"
						page="../jsp/service/shops_phone_express.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>



				<div class="main clearfix" id="shops_phone_clean_id_fei"
					style="display: none;">
					<jsp:include flush="true" page="../jsp/service/clean.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="add_clean_fei" style="display: none;">
					<jsp:include flush="true" page="../jsp/service/add_clean.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>




				<div class="main clearfix" id="nearby_home_fei"
					style="display: none;">
					<jsp:include flush="true" page="./nearby.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="password_home_id"
					style="display: none;">
					<jsp:include flush="true" page="./password.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>




				<div class="main clearfix" id="notice_notice_all_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./notice/notice_all.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>






				<div class="main clearfix" id="notice_notice_history_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./notice/notice_history.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>



				<div class="main clearfix" id="notice_notice_special_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./notice/notice_special.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>



				<div class="main clearfix" id="notice_notice_detail_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./notice/notice_detail.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>



				<div class="main clearfix" id="activity_activities_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./activity/activities.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>



				<div class="main clearfix" id="activity_detail_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./activity/activity-detail.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>


				<div class="main clearfix" id="activity_sensitive_words_jsp"
					style="display: none;">
					<jsp:include flush="true" page="./activity/SensitiveWords.jsp">

						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
						<jsp:param name="newUserName"
							value="{parameterValue | ${newUserName }}" />
					</jsp:include>
				</div>





			</div>

		</section>
	</body>
</html>