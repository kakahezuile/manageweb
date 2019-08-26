<%@page import="com.xj.utils.PropertyTool"%>
<%@page import="com.xj.utils.MD5Util"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Properties properties = PropertyTool.getPropertites("/configure.properties");
String versionNum = properties.getProperty("version");
%>
<%
String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
String emobGroupId = request.getParameter("emobGroupId");
String topic = request.getParameter("topic");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <title>群聊</title>
    
    <link href="${pageContext.request.contextPath}/easymob-webim1.0/css/webim.css?version=1.6" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/operation.css?version=1.6" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/navy.css?version=1.6" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easymob-webim1.0/jquery-1.11.1.js?version=<%=versionNum%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/strophe-custom-2.0.1.js?version=<%=versionNum%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/json2.js?version=<%=versionNum%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/easemob.im-1.0.7.js?version=<%=versionNum%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easymob-webim1.0/bootstrap.js?version=<%=versionNum%>"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	//发送图片
// 	function upImg(el){
// 		$('#message').val('');//清除输入框中文本信息
		
// 		var me = $(el),
// 			li = me.closest("li");
		
// 		li.before("<li><p><img></p><p><a href='javascript:void(0)' onclick='delImg(this)'>删除</a></p></li>");
// 		li.hide();
// 		preview(el, li.prev().find("img"));
// 	}
	
	
	//获取用户列表
	function getListTryOutUser(){
		
		var url = "<%=path %>/api/v1/communities/summary/getListTryOutUser?communityId=<%=community_id %>";
		$.ajax({
				url: url,
				type: "GET",
				dataType: "json",
				success: function(data) {
					data=data.info;
					var liList="<option  selected='selected' disabled='disabled' >请选择用户</option>";
					
					for ( var i = 0; i < data.length; i++) {
						liList+="<option value='"+data[i].emobId+"'>"+data[i].nickname+"</option> ";
					}
					$("#tryOutUser").html(liList);
						
					},
				error: function(e) {
					alert(e);
				}
			});
	}
	
	//选择水军时登录
	function login(sel){
		conn.close();//先断开现有连接
		$("#chatroom").empty();//清空现有聊天记录
		var username = $(sel).val();
		var password = prompt("请输入密码","");
		if (!password) {
			alert("密码不能为空!");
			return;
		}
		var url = "<%=path %>/api/v1/communities/1/users/password?password="+password;
		$.ajax({
			url: url,
			type: "GET",
			dataType: "json",
			success: function(data) {
				password = data.info;
				conn.open({
					user: username,
					pwd: password,
					appKey: "xiaojiantech#bangbang"
				});
			},
			error: function(e) {
				alert(e);
			}
		});
		
		var json = {"emobUserId":username,"groupStatus":"block"};
		$.ajax({
			url: "<%=path %>/api/v1/communities/<%=community_id %>/groups/<%=emobGroupId %>/members/",
			type: "POST",
			data:  JSON.stringify(json),
			dataType: "json",
			success: function(data) {
				data = data.info;
				alert("用户登录成功，加入群聊");
			},
			error: function(e) {
				alert(e);
			}
		});
	}
	//退出登录
	function logout() {
		alert("断开连接，用户退出");
		conn.close();
	}
	//获取时间
	function getTime(unixtime){
		var date = null;
		if(unixtime!=null){
			date = new Date(unixtime);
		}else{
			date = new Date();
		}
		return date.getFullYear()+"年"+addZero(date.getMonth()+1)+"月"+addZero(date.getDate())+"日  "+addZero(date.getHours())+":"+addZero(date.getMinutes())+":"+addZero(date.getSeconds());
	}
	//时间添0
	function addZero(num){
		return num<10?"0"+num:num;
	}
	$(function() {
		getListTryOutUser();//获取水军
		window.conn = new Easemob.im.Connection();
		conn.init({
			onOpened: function() {//登录成功后会调用该方法
				conn.setPresence();//登录之后需要设置在线状态，才能收到消息，这个方法用于上线
			},
			onPresence: function(message) {
				
			},
			onTextMessage: function(message) {//收到文字消息处理动作
				var nickname = message.ext.nickname;//消息发送者的昵称
				var emobId = message.from;//消息发送者的emobId
				var avatar= message.ext.avatar;//头像
				if(avatar=="" || avatar==null){
					avatar="<%=path %>/images/head_photo.png";
				}
				var time = getTime(message.ext.time);
				var content = message.data;//文本消息体
				var optionList = $("#tryOutUser")[0];
				//其他人发的消息
				var chatList="<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-left\"><img src=\""+avatar+"\"><div class=\"me-name-left\">"+nickname+"</div><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"+content+"</div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
				//过滤水军发的消息
				for(var i =1;i<optionList.length;i++){
					if(emobId==optionList[i].value){
						var chatList="<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-right\"><img src=\""+avatar+"\"><div class=\"me-name-right\">"+nickname+"</div><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">"+content+"</div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
						break;
					}
				}
				$(chatList).appendTo("#chatroom");
					
			},
			onEmotionMessage: function(message) {//收到表情消息处理动作
				
			},
			onPictureMessage: function(message) {//收到图片消息处理动作
				var nickname = message.ext.nickname;//昵称
				var emobId = message.from;//emobId
				var avatar = message.ext.avatar;//头像
				if(avatar=="" || avatar==null){
					avatar="<%=path %>/images/head_photo.png";
				}
				var time = getTime(message.ext.time);
				var url = message.url;
				var chatList="<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-left\"><img src=\""+avatar+"\"><div class=\"me-name-left\">"+nickname+"</div><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"><img src=\""+url+"\"></div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
				var optionList = $("#tryOutUser")[0];
				//过滤水军发的消息
				for(var i =1;i<optionList.length;i++){
					if(emobId==optionList[i].value){
						var chatList="<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-right\"><img src=\""+avatar+"\"><div class=\"me-name-right\">"+nickname+"</div><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\"><img src=\""+url+"\"></div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
						break;
					}
				}
				$(chatList).appendTo("#chatroom");
				
			},
			onAudioMessage: function(message) {//收到语音消息处理动作
				var nickname = message.ext.nickname;//昵称
				var emobId = message.from;//emobId
				var avatar = message.ext.avatar;//头像
				if(avatar=="" || avatar==null){
					avatar="<%=path %>/images/head_photo.png";
				}
				var time = getTime(message.ext.time);
				var options = message;
				options.mimeType = "audio";
				options.onFileDownloadComplete = function(response, xhr) {
					var objectURL = window.URL.createObjectURL(response);
					var chatList="<div class=\"chat-time\"><span>"+time+"</span></div>"+
								 "<div class=\"text-left\"><img src=\""+avatar+"\">"+
								 	"<div class=\"me-name-left\">"+nickname+"</div>"+
								 	"<div class=\"left-speak-frame\">"+
								 		"<div class=\"left-speak-box\">"+
								 			"<div class=\"left-speak\">"+
								 				"<div class=\"radio-style\">"+
								 					"<span class=\"radio-icon\"></span>"+
									 				"<audio controls='controls'>"+
									 					"<source  src=\""+objectURL+"\" >"+
									 				"</audio>"+
									 			"</div>"+
								 			"</div>"+
								 		"</div>"+
								 	"</div>"+
								 	"<span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span>"+
								 "</div>";
					var optionList = $("#tryOutUser")[0];
					//过滤水军发的消息
					for(var i =1;i<optionList.length;i++){
						if(emobId==optionList[i].value){
							var chatList="<div class=\"chat-time\"><span>"+time+"</span></div>";
								"<div class=\"text-right\"><img src=\""+avatar+"\">"+
						 	"<div class=\"me-name-right\">"+nickname+"</div>"+
						 	"<div class=\"right-speak-frame\">"+
						 		"<div class=\"right-speak-box\">"+
						 			"<div class=\"right-speak\">"+
						 				"<div class=\"radio-style\">"+
						 					"<span class=\"radio-icon\"></span>"+
							 				"<audio controls='controls'>"+
							 					"<source  src=\""+objectURL+"\" >"+
							 				"</audio>"+
							 			"</div>"+
						 			"</div>"+
						 		"</div>"+
						 	"</div>"+
						 	"<span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span>"+
						 "</div>";
						 break;
						}
					}
					$(chatList).appendTo("#chatroom");

				};
				options.headers = {
						"Accept" : "audio/mp3"
					};
				Easemob.im.Helper.download(options);
			},
			onClosed: function() {
				
			},
			onError: function(e) {
				alert(e.msg);
			}
		});
		
		//发送图片
		function sendPicture(user) {
			var fileInputId = "picture",
				fileObj = Easemob.im.Helper.getFileUrl(fileInputId),
				filetype = fileObj.filetype;
			if (filetype in {"jpg": true, "gif": true, "png": true, "bmp": true}) {
				conn.sendPicture({
					type: "groupchat",
					to: "<%=emobGroupId %>",
					fileInputId: fileInputId,
					ext: {
						"nickname":user.nickname,
						"avatar":user.avatar
						},
					onFileUploadError: function(error) {
						console.log(error);
						alert("图片上传失败!");
					},
					onFileUploadComplete: function(data) {
						console.log("图片上传成功!");
					}
				});
				return;
			}
			alert("不支持此图片类型" + filetype);
		}
		
		$("#send").on("click", function() {
			
			var messageEl = $("#message"),
				pictureEl = $("#picture"),
				message = messageEl.val(),
				picture = pictureEl.val();
			if (!message && !picture) {
				alert("请输入内容!");
				messageEl.focus();
				return;
			}
			
			var emobId = $("#tryOutUser").val();
			var url = "<%=path %>/api/v1/communities/1/users/"+emobId;
			$.ajax({
				url: url,
				type: "GET",
				dataType: "json",
				success: function(data) {
					var user = data.info;
					var time = getTime(null);
					if (message) {
						conn.sendTextMessage({
							type: "groupchat",
							to: "<%=emobGroupId %>",
							msg: message,
							ext: {
								"nickname":user.nickname,
								"avatar":user.avatar
							}
						});
						var chatList="<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-right\"><img src=\""+user.avatar+"\"><div class=\"me-name-right\">"+user.nickname+"</div><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" >"+message+"</div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";	
						$(chatList).appendTo("#chatroom");
						
					} else if (picture) {//发送图片信息
						sendPicture(user);
						var f=document.getElementById("picture").files[0];
						var src = window.URL.createObjectURL(f);
						var chatList="<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-right\"><img src=\""+user.avatar+"\"><div class=\"me-name-right\">"+user.nickname+"</div><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" ><img src=\""+src+"\"></div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";	
						$(chatList).appendTo("#chatroom");
					}
				},
				error: function(e) {
					alert(e); 
				}
			});
		});
		
		$("#checkHistory").on("click", function() {
			var url = "<%=path %>/api/v1/communities/1/users/0/activities/history?messageTo=<%=emobGroupId %>&chatType=groupchat";
			$.ajax({
				url: url,
				type: "GET",
				dataType: "json",
				success: function(data) {
					var list = data.info;
					var chatList = "";
					var optionList = $("#tryOutUser")[0];
					for(var i = list.length-1; i>=0;i--){
						var obj = list[i];
						var time = getTime(obj.created);
						var emobId = obj.messageFrom;
						var nickname = obj.nickname;
						var avatar = obj.avatar;
						if(avatar=="" || avatar==null){
							avatar="<%=path %>/images/head_photo.png";
						}
						if(obj.msgType=="txt"){//处理文本消息
							var content = obj.msg;
							chatList = "<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-left\"><img src=\""+avatar+"\"><div class=\"me-name-left\">"+nickname+"</div><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"+content+"</div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
							//过滤水军发的消息
							for(var j =1;j<optionList.length;j++){
								if(emobId==optionList[j].value){
									chatList = "<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-right\"><img src=\""+avatar+"\"><div class=\"me-name-right\">"+nickname+"</div><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">"+content+"</div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
									break;
								}
							}
						}else if(obj.msgType=="img"){//处理图片消息
							var url = obj.url;
							chatList = "<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-left\"><img src=\""+avatar+"\"><div class=\"me-name-left\">"+nickname+"</div><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"><img src=\""+url+"\"></div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
							//过滤水军发的消息
							for(var j =1;j<optionList.length;j++){
								if(emobId==optionList[j].value){
									chatList = "<div class=\"chat-time\"><span>"+time+"</span></div><div class=\"text-right\"><img src=\""+avatar+"\"><div class=\"me-name-right\">"+nickname+"</div><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\"><img src=\""+url+"\"></div></div></div><span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span></div>";
									break;
								}
							}
						}else if(obj.msgType=="audio"){//处理音频消息
							var url = obj.url;
							chatList ="<div class=\"chat-time\"><span>"+time+"</span></div>"+ 
									  "<div class=\"text-left\"><img src=\""+avatar+"\">"+
						 				 "<div class=\"me-name-left\">"+nickname+"</div>"+
						 				 "<div class=\"left-speak-frame\">"+
						 				 	"<div class=\"left-speak-box\">"+
						 						"<div class=\"left-speak\">"+
						 							"<div class=\"radio-style\">"+
						 								"<span class=\"radio-icon\"></span>"+
							 							"<audio controls='controls' src=\""+url+"\" >"+
							 							"</audio>"+
							 						"</div>"+
											 	"</div>"+
											 "</div>"+
										 "</div>"+
										 "<span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span>"+
									 "</div>";
							//过滤水军发的消息
							for(var j =1;j<optionList.length;j++){
								if(emobId==optionList[j].value){
									chatList ="<div class=\"chat-time\"><span>"+time+"</span></div>"+ 
											  "<div class=\"text-right\"><img src=\""+avatar+"\">"+
												 	"<div class=\"me-name-right\">"+nickname+"</div>"+
												 	"<div class=\"right-speak-frame\">"+
												 		"<div class=\"right-speak-box\">"+
												 			"<div class=\"right-speak\">"+
												 				"<div class=\"radio-style\">"+
												 					"<span class=\"radio-icon\"></span>"+
													 				"<audio controls='controls' src=\""+url+"\" >"+
													 				"</audio>"+
													 			"</div>"+
												 			"</div>"+
												 		"</div>"+
												 	"</div>"+
												 	"<span class=\"chat-me\" style=\"top:30px;\">&nbsp;</span>"+
												 "</div>";
								 break;
								}
							}
						}
						$("#chatroom").prepend(chatList);
					}
				},
				error: function(e) {
					alert(e); 
				}
			});
		});
	});
	</script>
  </head>
  <body>
    <div>
    	<div class="navy-activity-head">
    		<label><%=topic %></label><a href="<%=path %>/jsp/activity/activities_all.jsp?community_id=<%=community_id %>&communityName=<%=str %>" onclick="logout()">返回活动话题列表</a>
    	</div>
	    <div id="chatroom" class="navy-chatroom"></div>
	    <div class="navy-chat-panel">
	    	<a href="javascript:;" class="navy-message-send"  id="checkHistory">聊天记录</a>
	    	<label>请选择水军号</label>
	    	<select id="tryOutUser" onchange="login(this)">
				<option  selected="selected" >请选择用户</option>
			</select>
			<input class="chat-pic" type="file" id="picture" onchange="$('#message').val('');">
			<a class="chat-images-send" href="javascript:;">&nbsp;</a>
			<input type="password" style="display:none;position:absolute;"/>
	    	<input class="navy-message"  id="message" onchange="$('#picture').val('');">
			<a href="javascript:;" class="navy-message-send"  id="send">发送</a>
	    </div>
    </div>
  </body>
</html>
