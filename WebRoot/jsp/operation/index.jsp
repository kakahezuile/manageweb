<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.xj.utils.PropertyTool"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WebIM-DEMO</title>
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
			
			
			
			
<script type="text/javascript">
		
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
	var appkey = "easemob-demo#chatdemoui";
	var time = 0;

	window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
	var showWaitLoginedUI = function() {
		$('#waitLoginmodal').modal('show');
	};
	var hiddenWaitLoginedUI = function() {
		$('#waitLoginmodal').modal('hide');
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
			//收到位置消息的回调方法
			onLocationMessage : function(message) {
				handleLocationMessage(message);
			},
			//收到文件消息的回调方法
			onFileMessage : function(message) {
				handleFileMessage(message);
			},
			//收到视频消息的回调方法
			onVideoMessage : function(message) {
				handleVideoMessage(message);
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
					if (navigator.userAgent.indexOf("Firefox") > 0)
						return ' ';
					else
						return '';
				}
			});
		});
	});

	//处理连接时函数,主要是登录成功后对页面元素做处理
	var handleOpen = function(conn) {
		//从连接中获取到当前的登录人注册帐号名
		curUserId = conn.context.userId;
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
				if (msg == "" || msg == 'unknown' ) {
					alert("服务器器断开连接,可能是因为在别处登录");
				} else {
					alert("服务器器断开连接");
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
			alert("请输入用户名和密码");
			return;
		}
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

	var logout = function() {
		conn.close();
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
	var buildContactDiv = function(contactlistDivId, roster) {
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
			$('<img>').attr("src", "img/head/contact_normal.png").appendTo(
					lielem);
			$('<span>').html(userName).appendTo(lielem);
			$('#contactlistUL').append(lielem);
		}
		var contactlist = document.getElementById(contactlistDivId);
		var children = contactlist.children;
		if (children.length > 0) {
			contactlist.removeChild(children[0]);
		}
		contactlist.appendChild(uielem);
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
		contactLi.style.backgroundColor = "#33CCFF";
		var dispalyTitle = "与" + chatUserId + "聊天中";//聊天窗口显示当前对话人名称
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

		//处理联系人分组的未读消息处理
		var accordionChild = $('#accordionDiv').children();
		for (var i = 1; i <= accordionChild.length; i++) {
			var badgegroup = $('#accordion' + i).find(".badgegroup");
			if (badgegroup && badgegroup.length > 0) {
				$('#accordion' + i).children().remove();
			}
		}

		//清除右侧对话框内容
		document.getElementById(talkToDivId).children[0].innerHTML = "";
		var chatRootDiv = document.getElementById(contactChatDiv);
		var children = chatRootDiv.children;
		for (var i = children.length - 1; i > 1; i--) {
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
		//TODO  根据消息体的to值去定位那个群组的聊天记录
		var room = message.to;
		if (mestype == 'groupchat') {
			appendMsg(message.from, message.to, messageContent, mestype);
		} else {
			appendMsg(from, from, messageContent);
		}
	};
	//easemobwebim-sdk收到表情消息的回调方法的实现，message为表情符号和文本的消息对象，文本和表情符号sdk中做了
	//统一的处理，不需要用户自己区别字符是文本还是表情符号。
	var handleEmotion = function(message) {
		var from = message.from;
		var room = message.to;
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		if (mestype == 'groupchat') {
			appendMsg(message.from, message.to, message, mestype);
		} else {
			appendMsg(from, from, message);
		}

	};
	//easemobwebim-sdk收到图片消息的回调方法的实现
	var handlePictureMessage = function(message) {
		var filename = message.filename;//文件名称，带文件扩展名
		var from = message.from;//文件的发送者
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var contactDivId = from;
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
			}
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
        
        var redownLoadFileNum = 0;
		options.onFileDownloadError = function(e) {
            //下载失败时只重新下载一次
            if(redownLoadFileNum < 1){
               redownLoadFileNum++;
                options.accessToken = options_c;
               Easemob.im.Helper.download(options);
               
            }else{
              appendMsg(from, contactDivId, e.msg + ",下载图片" + filename + "失败");
              redownLoadFileNum = 0;
            }
           
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

	//处理收到文件消息
	var handleFileMessage = function(message) {
		var filename = message.filename;
		var filetype = message.filetype;
		var from = message.from;

		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var contactDivId = from;
		var options = message;
		options.onFileDownloadComplete = function(response, xhr) {
			var spans = "收到文件消息:" + filename + '   ';
			var content = spans + "【<a href='"
					+ window.URL.createObjectURL(response) + "' download='"
					+ filename + "'>另存为</a>】";
			appendMsg(from, contactDivId, content);
			return;
		};
		options.onFileDownloadError = function(e) {
			appendMsg(from, contactDivId, e.msg + ",下载文件" + filename + "失败");
		};
		Easemob.im.Helper.download(options);
	};

	//收到视频消息
	var handleVideoMessage = function(message) {

		var filename = message.filename;
		var filetype = message.filetype;
		var from = message.from;

		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var contactDivId = from;
		var options = message;
		options.onFileDownloadComplete = function(response, xhr) {
			//var spans = "收到视频消息:" + filename;
			//appendMsg(from, contactDivId, spans);
			var objectURL = window.URL.createObjectURL(response);
			var video = document.createElement("video");
			if (("src" in video) && ("controls" in video)) {
				video.onload = function() {
					video.onload = null;
					window.URL.revokeObjectURL(video.src);
				};
				video.onerror = function() {
					video.onerror = null;
					appendMsg(from, contactDivId, "当前浏览器不支持播放此音频:" + filename);
				};
				video.src = objectURL;
				video.controls = "controls";
				video.width = "320";
				video.height = "240";
				appendMsg(from, contactDivId, {
					data : [ {
						type : 'video',
						filename : filename,
						data : video
					} ]
				});
				//audio.play();
				return;
			}

		};
		options.onFileDownloadError = function(e) {
			appendMsg(from, contactDivId, e.msg + ",下载音频" + filename + "失败");
		};
		Easemob.im.Helper.download(options);
	};

	var handleLocationMessage = function(message) {
		var from = message.from;
		var to = message.to;
		var mestype = message.type;
		var content = message.addr;
		if (mestype == 'groupchat') {
			appendMsg(from, to, content, mestype);
		} else {
			appendMsg(from, from, content, mestype);
		}
	};
	//显示聊天记录的统一处理方法
	var appendMsg = function(who, contact, message, chattype) {
		var localMsg = null;
		if (typeof message == 'string') {
			localMsg = Easemob.im.Helper.parseTextMessage(message);
			localMsg = localMsg.body;
		} else {
			localMsg = message.data;
		}
		var headstr = [ "", "" ];
		var header = $(headstr.join(''))
		var lineDiv = document.createElement("div");
		for (var i = 0; i < header.length; i++) {
			var ele = header[i];
			lineDiv.appendChild(ele);
		}
		var messageContent = localMsg;
		for (var i = 0; i < messageContent.length; i++) {
			var msg = messageContent[i];
			var type = msg.type;
			var data = msg.data;
			if (type == "emotion") {
				var eletext = "<p3><img src='" + data + "'/></p3>";
				var ele = $(eletext);
				for (var j = 0; j < ele.length; j++) {
					lineDiv.appendChild(ele[j]);
				}
			} else if (type == "pic" || type == 'audio' || type == 'video') {
				var filename = msg.filename;
				var fileele = $("<p3>" + filename + "</p3><br>");
				for (var j = 0; j < fileele.length; j++) {
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
				for (var j = 0; j < ele.length; j++) {
					lineDiv.appendChild(ele[j]);
				}
			}
		}
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
	}
</script>
</head>
<body>
	<div id="waitLoginmodal" class="modal hide fade" data-backdrop="static">
		<img src="${pageContext.request.contextPath }/easymob-webim1.0/img/waitting.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在努力加载中...</img>
	</div>
	<div class="content" id="content" style="display: none">
		<div class="leftcontact" id="leftcontact">
			<div id="headerimg" class="leftheader">
				<span> <img src="${pageContext.request.contextPath }/easymob-webim1.0/img/head/header2.jpg" alt="logo"
					class="img-circle" width="60px" height="60px"
					style="margin-top: -40px; margin-left: 20px" /></span> <span
					id="login_user" class="login_user_title"> <a
					class="leftheader-font" href="#"></a>
				</span>
			</div>
			<div id="leftmiddle">
			</div>
			<div id="contractlist11"
				style="height: 492px; overflow-y: auto; overflow-x: auto;">
				<div class="accordion" id="accordionDiv">
					<div class="accordion-group">
						<div class="accordion-heading">
							<a id="accordion1" class="accordion-toggle"
								data-toggle="collapse" data-parent="#accordionDiv"
								href="#collapseOne">我的好友 </a>
						</div>
						<div id="collapseOne" class="accordion-body collapse in">
							<div class="accordion-inner" id="contractlist">
								<ul id="contactlistUL" class="chat03_content_ul"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="rightTop" style="height: 78px;"></div>
		<!-- 聊天页面 -->
		<div class="chatRight">
			<div id="chat01">
				<div class="chat01_title">
					<ul class="talkTo">
						<li id="talkTo"><a href="#"></a></li>
						<li id="recycle" style="float: right;"><img
							src="${pageContext.request.contextPath }/easymob-webim1.0/img/recycle.png" onclick="clearCurrentChat();"
							style="margin-right: 15px; cursor: hand; width: 18px;" title="清屏" /></li>
					</ul>
				</div>
				<div id="null-nouser" class="chat01_content"></div>
			</div>

			<div class="chat02">
				<div class="chat02_title">
					<a class="chat02_title_btn ctb01" onclick="showEmotionDialog()" title="选择表情"></a>
					<label id="chat02_title_t"></label>
					<div id="wl_faces_box" class="wl_faces_box">
						<div class="wl_faces_content">
							<div class="title">
								<ul>
									<li class="title_name">常用表情</li>
									<li class="wl_faces_close"><span
										onclick='turnoffFaces_box()'>&nbsp;</span></li>
								</ul>
							</div>
							<div id="wl_faces_main" class="wl_faces_main">
								<ul id="emotionUL">
								</ul>
							</div>
						</div>
						<div class="wlf_icon"></div>
					</div>
				</div>
				<div id="input_content" class="chat02_content">
					<textarea id="talkInputId" style="resize: none;"></textarea>
				</div>
				<div class="chat02_bar">
					<ul>
						<li style="right: 5px; top: 5px;"><img src="${pageContext.request.contextPath }/easymob-webim1.0/img/send_btn.jpg"
							onclick="sendText()" /></li>
					</ul>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
	<script >
	login();
	</script>
</body>
</html>
