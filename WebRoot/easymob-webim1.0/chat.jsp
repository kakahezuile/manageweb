<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WebIM-DEMO</title>

<script type="text/javascript">

	

	
	
	

	

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
	var buildContactDiv = function(contactlistDivId, roster , isUser) {
		
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
			if(isUser == 1){
				if(userName == '2'){
					continue;
				}
			}else{
				if(userName != '2'){
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
			$('<img>').attr("src", "${pageContext.request.contextPath }/easymob-webim1.0/img/head/contact_normal.png").appendTo(
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
		console.info(new Date().getTime());
	};

	//构造群组列表
	var buildListRoomDiv = function(contactlistDivId, rooms) {
		var uielem = document.getElementById("contactgrouplistUL");
		var cache = {};
		for (i = 0; i < rooms.length; i++) {
			var roomsName = rooms[i].name;
			var roomId = rooms[i].roomId;
			listRoomId.push(roomId);
			if (roomId in cache) {
				continue;
			}
			cache[roomId] = true;
			var lielem = $('<li>').attr({
				'id' : groupFlagMark + roomId,
				'class' : 'offline',
				'className' : 'offline',
				'type' : 'groupchat',
				'displayName' : roomsName,
				'roomId' : roomId,
				'joined' : 'false'
			}).click(function() {
				chooseContactDivClick(this);
			});
			$('<img>').attr({
				'src' : 'img/head/group_normal.png'
			}).appendTo(lielem);
			$('<span>').html(roomsName).appendTo(lielem);
			$('#contactgrouplistUL').append(lielem);
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
		contactLi.style.backgroundColor = "#fff";
		var dispalyTitle = null;//聊天窗口显示当前对话人名称
		if (chatUserId.indexOf(groupFlagMark) >= 0) {
			dispalyTitle = "群组" + $(contactLi).attr('displayname') + "聊天中";
			curRoomId = $(contactLi).attr('roomid');
			$("#roomMemberImg").css('display', 'block');
		} else {
			dispalyTitle =  chatUserId;
			$("#roomMemberImg").css('display', 'none');
		}

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
		var badgespanGroup = $(li).parent().parent().parent()
				.find(".badge");
		if (badgespanGroup && badgespanGroup.length == 0) {
			$(li).parent().parent().parent().prev().children().children()
					.remove();
		}
	};

	var clearContactUI = function(contactlistUL, contactgrouplistUL,
			momogrouplistUL, contactChatDiv) {
		//清除左侧联系人内容
		$('#contactlistUL').empty();
		$('#contactgrouplistUL').empty();
		$('#momogrouplistUL').empty();

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
	var showSendPic = function() {
		$('#fileModal').modal('toggle');
		$('#sendfiletype').val('pic');
		$('#send-file-warning').html("");
	};
	var showSendAudio = function() {
		$('#fileModal').modal('toggle');
		$('#sendfiletype').val('audio');
		$('#send-file-warning').html("");
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
		// 群组消息和个人消息的判断分支
		if (curChatUserId.indexOf(groupFlagMark) >= 0) {
			options.type = 'groupchat';
			options.to = curRoomId;
		}
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

	var pictype = {
		"jpg" : true,
		"gif" : true,
		"png" : true,
		"bmp" : true
	};
	var sendFile = function() {
		var type = $("#sendfiletype").val();
		if (type == 'pic') {
			sendPic();
		} else {
			sendAudio();
		}
	};
	//发送图片消息时调用方法
	var sendPic = function() {
		var to = curChatUserId;
		if (to == null) {
			return;
		}
		// Easemob.im.Helper.getFileUrl为easemobwebim-sdk获取发送文件对象的方法，fileInputId为 input 标签的id值
		var fileObj = Easemob.im.Helper.getFileUrl(fileInputId);
		if (fileObj.url == null || fileObj.url == '') {
			$('#send-file-warning')
					.html("<font color='#FF0000'>请选择发送图片</font>");
			return;
		}
		var filetype = fileObj.filetype;
		var filename = fileObj.filename;
		if (filetype in pictype) {
			document.getElementById("fileSend").disabled = true;
			document.getElementById("cancelfileSend").disabled = true;
			var opt = {
				type : 'chat',
				fileInputId : fileInputId,
				to : to,
				onFileUploadError : function(error) {
					$('#fileModal').modal('hide');
					var messageContent = error.msg + ",发送图片文件失败:" + filename;
					appendMsg(curUserId, to, messageContent);
				},
				onFileUploadComplete : function(data) {
					$('#fileModal').modal('hide');
					var file = document.getElementById(fileInputId);
					if (file && file.files) {
						var objUrl = getObjectURL(file.files[0]);
						if (objUrl) {
							var img = document.createElement("img");
							img.src = objUrl;
							img.width = maxWidth;
						}
					}
					appendMsg(curUserId, to, {
						data : [ {
							type : 'pic',
							filename : filename,
							data : img
						} ]
					});
				}
			};

			if (curChatUserId.indexOf(groupFlagMark) >= 0) {
				opt.type = 'groupchat';
				opt.to = curRoomId;
			}
			opt.apiUrl = apiURL;
			conn.sendPicture(opt);
			return;
		}
		$('#send-file-warning').html(
				"<font color='#FF0000'>不支持此图片类型" + filetype + "</font>");
	};
	var audtype = {
		"mp3" : true,
		"wma" : true,
		"wav" : true,
		"amr" : true,
		"avi" : true
	};
	//发送音频消息时调用的方法
	var sendAudio = function() {
		var to = curChatUserId;
		if (to == null) {
			return;
		}
		//利用easemobwebim-sdk提供的方法来构造一个file对象
		var fileObj = Easemob.im.Helper.getFileUrl(fileInputId);
		if (fileObj.url == null || fileObj.url == '') {
			$('#send-file-warning')
					.html("<font color='#FF0000'>请选择发送音频</font>");
			return;
		}
		var filetype = fileObj.filetype;
		var filename = fileObj.filename;
		if (filetype in audtype) {
			document.getElementById("fileSend").disabled = true;
			document.getElementById("cancelfileSend").disabled = true;
			var opt = {
				type : "chat",
				fileInputId : fileInputId,
				to : to,//发给谁
				onFileUploadError : function(error) {
					$('#fileModal').modal('hide');
					var messageContent = error.msg + ",发送音频失败:" + filename;
					appendMsg(curUserId, to, messageContent);
				},
				onFileUploadComplete : function(data) {
					var messageContent = "发送音频" + filename;
					$('#fileModal').modal('hide');
					appendMsg(curUserId, to, messageContent);
				}
			};
			//构造完opt对象后调用easemobwebim-sdk中发送音频的方法
			if (curChatUserId.indexOf(groupFlagMark) >= 0) {
				opt.type = 'groupchat';
				opt.to = curRoomId;
			}
			opt.apiUrl = apiURL;
			conn.sendAudio(opt);
			return;
		}
		$('#send-file-warning').html(
				"<font color='#FF0000'>不支持此音频类型" + filetype + "</font>");
	};
	//easemobwebim-sdk收到文本消息的回调方法的实现
	var handleTextMessage = function(message) {
		var from = message.from;//消息的发送者
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var messageContent = message.data;//文本消息体
		//   根据消息体的to值去定位那个群组的聊天记录
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
		if (mestype == 'groupchat') {
			contactDivId = groupFlagMark + message.to;
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
		if (mestype == 'groupchat') {
			contactDivId = groupFlagMark + message.to;
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

	var handleInviteMessage = function(message) {
		var type = message.type;
		var from = message.from;
		var roomId = message.roomid;

		//获取当前登录人的群组列表
		conn.listRooms({
			success : function(rooms) {
				if (rooms) {
					for (i = 0; i < rooms.length; i++) {
						var roomsName = rooms[i].name;
						var roomId = rooms[i].roomId;
						var existRoom = $('#contactgrouplistUL').children(
								'#group--' + roomId);
						if (existRoom && existRoom.length == 0) {
							var lielem = $('<li>').attr({
								'id' : groupFlagMark + roomId,
								'class' : 'offline',
								'className' : 'offline',
								'type' : 'groupchat',
								'displayName' : roomsName,
								'roomId' : roomId,
								'joined' : 'false'
							}).click(function() {
								chooseContactDivClick(this);
							});
							$('<img>').attr({
								'src' : 'img/head/group_normal.png'
							}).appendTo(lielem);
							$('<span>').html(roomsName).appendTo(lielem);
							$('#contactgrouplistUL').append(lielem);
							//return;
						}
					}
					//cleanListRoomDiv();//先将原群组列表中的内容清除，再将最新的群组列表加入
					//buildListRoomDiv("contracgrouplist", rooms);//群组列表页面处理
				}
			},
			error : function(e) {
			}
		});

	};
	var cleanListRoomDiv = function cleanListRoomDiv() {
		$('#contactgrouplistUL').empty();
	};

	//收到陌生人消息时创建陌生人列表
	var createMomogrouplistUL = function createMomogrouplistUL(who, message) {
		var momogrouplistUL = document.getElementById("momogrouplistUL");
		var cache = {};

		if (who in cache) {
			return;
		}
		cache[who] = true;
		var lielem = document.createElement("li");
		$(lielem).attr({
			'id' : who,
			'class' : 'offline',
			'className' : 'offline',
			'type' : 'chat',
			'displayName' : who
		});
		lielem.onclick = function() {
			chooseContactDivClick(this);
		};
		var imgelem = document.createElement("img");
		imgelem.setAttribute("src", "img/head/contact_normal.png");
		lielem.appendChild(imgelem);

		var spanelem = document.createElement("span");
		spanelem.innerHTML = who;
		lielem.appendChild(spanelem);

		momogrouplistUL.appendChild(lielem);
	};

	//显示聊天记录的统一处理方法
	var appendMsg = function(who, contact, message, chattype) {
		var contactUL = document.getElementById("contactlistUL");
		var contactDivId = contact;
		if (chattype && chattype == 'groupchat') {
			contactDivId = groupFlagMark + contact;
		}
		var contactLi = getContactLi(contactDivId);
		if (contactLi == null) {
			createMomogrouplistUL(who, message);
		}

		// 消息体 {isemotion:true;body:[{type:txt,msg:ssss}{type:emotion,msg:imgdata}]}
		var localMsg = null;
		if (typeof message == 'string') {
			localMsg = Easemob.im.Helper.parseTextMessage(message);
			localMsg = localMsg.body;
		} else {
			localMsg = message.data;
		}
		var headstr = [ "<p1>" + who + "   <span></span>" + "   </p1>",
				"<p2>" + getLoacalTimeString() + "<b></b><br/></p2>" ];
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
			} else if (type == "pic") {
				var filename = msg.filename;
				var fileele = $("<p3>" + filename + "</p3><br>");
				for (var j = 0; j < fileele.length; j++) {
					lineDiv.appendChild(fileele[j]);
				}
				lineDiv.appendChild(data);
			} else if (type == 'audio') {
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

	var showAddFriend = function() {
		$('#addFridentModal').modal('toggle');
		$('#addfridentId').val('好友账号');//输入好友账号
		$('#add-frident-warning').html("");
	};

	//添加输入框鼠标焦点进入时清空输入框中的内容
	var clearInputValue = function(inputId) {
		$('#' + inputId).val('');
	};

	var showDelFriend = function() {
		$('#delFridentModal').modal('toggle');
		$('#delfridentId').val('好友账号');//输入好友账号
		$('#del-frident-warning').html("");
	};

	//消息通知操作时条用的方法
	var showNewNotice = function(message) {
		$('#confirm-block-div-modal').modal('toggle');
		$('#confirm-block-footer-body').html(message);
	};

	var showWarning = function(message) {
		$('#notice-block-div').modal('toggle');
		$('#notice-block-body').html(message);
	};

	//主动添加好友操作的实现方法
	var startAddFriend = function() {
		var user = $('#addfridentId').val();
		if (user == '') {
			$('#add-frident-warning').html(
					"<font color='#FF0000'> 请输入好友名称</font>");
			return;
		}
		if (bothRoster)
			for (var i = 0; i < bothRoster.length; i++) {
				if (bothRoster[i].name == user) {
					$('#add-frident-warning').html(
							"<font color='#FF0000'> 已是您的好友</font>");
					return;
				}
			}
		//发送添加好友请求
		conn.subscribe({
			to : user,
			message : "加个好友呗-" + getLoacalTimeString()
		});
		$('#addFridentModal').modal('hide');
		return;
	};

	//回调方法执行时同意添加好友操作的实现方法
	var agreeAddFriend = function(user) {
		conn.subscribed({
			to : user,
			message : "[resp:true]"
		});
	};
	//拒绝添加好友的方法处理
	var rejectAddFriend = function(user) {
		conn.unsubscribed({
			to : user,
			message : getLoacalTimeString()
		});
	};

	//直接调用删除操作时的调用方法
	var directDelFriend = function() {
		var user = $('#delfridentId').val();
		if (validateFriend(user, bothRoster)) {
			conn.removeRoster({
				to : user,
				success : function() {
					conn.unsubscribed({
						to : user
					});
					//删除操作成功时隐藏掉dialog
					$('#delFridentModal').modal('hide');
				},
				error : function() {
					$('#del-frident-warning').html(
							"<font color='#FF0000'>删除联系人失败!</font>");
				}
			});
		} else {
			$('#del-frident-warning').html(
					"<font color='#FF0000'>该用户不是你的好友!</font>");
		}
	};
	//判断要删除的好友是否在当前好友列表中
	var validateFriend = function(optionuser, bothRoster) {
		for ( var deluser in bothRoster) {
			if (optionuser == bothRoster[deluser].name) {
				return true;
			}
		}
		return false;
	};

	//回调方法执行时删除好友操作的方法处理
	var delFriend = function(user) {
		conn.removeRoster({
			to : user,
			groups : [ 'default' ],
			success : function() {
				conn.unsubscribed({
					to : user
				});
			}
		});
	};
	var removeFriendDomElement = function(userToDel, local) {
		var contactToDel;
		if (bothRoster.length > 0) {
			for (var i = 0; i < bothRoster.length; i++) {
				if (bothRoster[i].name == userToDel) {
					contactToDel = bothRoster[i];
					break;
				}
			}
		}
		if (contactToDel) {
			bothRoster.remove(contactToDel);
		}
		// 隐藏删除好友窗口
		if (local) {
			$('#delFridentModal').modal('hide');
		}
		//删除通讯录
		$('#' + userToDel).remove();
		//删除聊天
		var chatDivId = curUserId + "-" + userToDel;
		var chatDiv = $('#' + chatDivId);
		if (chatDiv) {
			chatDiv.remove();
		}
		if (curChatUserId != userToDel) {
			return;
		} else {
			var displayName = '';
			//将第一个联系人作为当前聊天div
			if (bothRoster.length > 0) {
				curChatUserId = bothRoster[0].name;
				$('#' + curChatUserId).css({
					"background-color" : "#fff"
				});
				var currentDiv = getContactChatDiv(curChatUserId)
						|| createContactChatDiv(curChatUserId);
				document.getElementById(msgCardDivId).appendChild(currentDiv);
				$(currentDiv).css({
					"display" : "block"
				});
				displayName = '与' + curChatUserId + '聊天中';
			} else {
				$('#null-nouser').css({
					"display" : "block"
				});
				displayName = '';
			}
			$('#talkTo').html('<a href="#">' + displayName + '</a>');
		}
	};

	//清除聊天记录
	var clearCurrentChat = function clearCurrentChat() {
		var currentDiv = getContactChatDiv(curChatUserId)
				|| createContactChatDiv(curChatUserId);
		currentDiv.innerHTML = "";
	};

	//显示成员列表
	var showRoomMember = function showRoomMember() {
		if (groupQuering) {
			return;
		}
		groupQuering = true;
		queryOccupants(curRoomId);
	};

	//根据roomId查询room成员列表
	var queryOccupants = function queryOccupants(roomId) {
		var occupants = [];
		conn.queryRoomInfo({
			roomId : roomId,
			success : function(occs) {
				if (occs) {
					for (var i = 0; i < occs.length; i++) {
						occupants.push(occs[i]);
					}
				}
				conn.queryRoomMember({
					roomId : roomId,
					success : function(members) {
						if (members) {
							for (var i = 0; i < members.length; i++) {
								occupants.push(members[i]);
							}
						}
						showRoomMemberList(occupants);
						groupQuering = false;
					},
					error : function() {
						groupQuering = false;
					}
				});
			},
			error : function() {
				groupQuering = false;
			}
		});
	};

	var showRoomMemberList = function showRoomMemberList(occupants) {
		var list = $('#room-member-list')[0];
		var childs = list.childNodes;
		for (var i = childs.length - 1; i >= 0; i--) {
			list.removeChild(childs.item(i));
		}
		for (i = 0; i < occupants.length; i++) {
			var jid = occupants[i].jid;
			var userName = jid.substring(jid.indexOf("_") + 1).split("@")[0];
			var txt = $("<p></p>").text(userName);
			$('#room-member-list').append(txt);
		}
		$('#option-room-div-modal').modal('toggle');
	};

	var showRegist = function showRegist() {
		$('#loginmodal').modal('hide');
		$('#regist-div-modal').modal('toggle');
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
		var time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
		return time;
	};
</script>
</head>
<body>
	<div id="loginmodal" class="modal hide fade in" role="dialog"
		aria-hidden="true" data-backdrop="static">
		<div class="modal-header">
			<h3>用户登录</h3>
		</div>
		<div class="modal-body">
			<table>
				<tr>
					<td width="65%"><label for="username">用户名:</label> <input
						type="text" name="username" value="" id="username" tabindex="1" />
						<label for="password">密码:</label> <input type="password" value=""
						name="password" id="password" tabindex="2" /></td>
				</tr>
			</table>
		</div>
		<div class="modal-footer">
			<button class="flatbtn-blu" onclick="login()" tabindex="3">登录</button>
			<button class="flatbtn-blu" onclick="showRegist()" tabindex="4">注册</button>
		</div>
	</div>

	<!-- 注册操作界面 -->
	<div id="regist-div-modal" class="alert modal fade hide" role="dialog"
		aria-hidden="true" data-backdrop="static">
		<div class="modal-header">
			<h3>用户注册</h3>
		</div>
		<div class="modal-body">
			<div id="regist_div" style="overflow-y: auto">
				<table>
					<tr>
						<td width="65%"><label>用户名:</label> <input type="text"
							value="" id="regist_username" tabindex="1" /> <label>密码:</label>
							<input type="password" value="" id="regist_password" tabindex="2" />
							<label>昵称:</label> <input type="text" value=""
							id="regist_nickname" tabindex="3" /></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<button id="confirm-regist-confirmButton" class="btn btn-primary"
				onclick="regist()">完成</button>
			<button id="confirm-regist-cancelButton" class="btn"
				onclick="showlogin()">返回</button>
		</div>
	</div>

	<div id="waitLoginmodal" class="modal hide fade" data-backdrop="static">
		<img src="${pageContext.request.contextPath }/easymob-webim1.0/img/waitting.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在努力加载中...</img>
	</div>
	<div class="hx-content" id="content" style="display: none">
		<div class="hx-leftcontact" id="leftcontact">
			<div class="chat-search">
				<input class="search" type="text" />
			</div>
			<div id="headerimg" class="leftheader">
				<span> <img src="${pageContext.request.contextPath }/easymob-webim1.0/img/head/header2.jpg" alt="logo"
					class="img-circle" width="60px" height="60px"
					style="margin-top: -40px;margin-left: 20px" /></span> <span
					id="login_user" class="login_user_title"> <a
					class="leftheader-font" href="#"></a>
				</span> <span>
					<div class="btn-group" style="margin-left: 5px;">
						<button class="btn btn-inverse dropdown-toggle"
							data-toggle="dropdown">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#" onclick="showAddFriend()">添加好友</a></li>
							<li><a href="#" onclick="showDelFriend()">删除好友</a></li>
							<li class="divider"></li>
							<li><a href="#" onclick="logout()">退出</a></li>
						</ul>
					</div>
				</span>
			</div>
			<div id="leftmiddle">
				<!--
				<input style="width: 120px; color: #999999; margin-top: 8px;"
					type="text" id="searchfriend" value="搜索"
					onFocus="if(value==defaultValue){value='';this.style.color='#000'}"
					onBlur="if(!value){value=defaultValue;this.style.color='#999'}" />
				<button id="searchFriend" style="background: #cccccc">查询</button>
			-->
			</div>
			<div id="contractlist11"
				style="height: 492px; overflow-y: auto; overflow-x: auto;">
				
				<div class="accordion" id="accordionDiv">
					<div class="accordion-group">
						<div class="accordion-heading">
							<a id="accordion1" class="accordion-toggle"
								data-toggle="collapse" data-parent="#accordionDiv"
								href="#collapseOne">所有业主 </a>
						</div>
						<div id="collapseOne" class="accordion-body collapse in">
							<div class="accordion-inner" id="contractlist">
								<ul id="contactlistUL" class="chat03_content_ul"></ul>
							</div>
						</div>
					</div>
					<!--<div class="accordion-group">
						<div class="accordion-heading">
							<a id="accordion2" class="accordion-toggle"
								data-toggle="collapse" data-parent="#accordionDiv"
								href="#collapseTwo"> 我的群组</a>
						</div>
						<div id="collapseTwo" class="accordion-body collapse">
							<div class="accordion-inner" id="contracgrouplist">
								<ul id="contactgrouplistUL" class="chat03_content_ul"></ul>
							</div>
						</div>
					</div>
					<div class="accordion-group">
						<div class="accordion-heading">
							<a id="accordion3" class="accordion-toggle"
								data-toggle="collapse" data-parent="#accordionDiv"
								href="#collapseThree"> 陌生人</a>
						</div>
						<div id="collapseThree" class="accordion-body collapse">
							<div class="accordion-inner" id="momogrouplist">
								<ul id="momogrouplistUL" class="chat03_content_ul"></ul>
							</div>
						</div>
					</div>
				--></div>

			</div>
		</div>
		<div id="rightTop" style="height:0;"></div>
		<!-- 聊天页面 -->
		<div class="chatRight">
			<div id="chat01">
				<div class="chat01_title">
					<ul class="talkTo">
						<li id="talkTo"><a href="#"></a><span>（1号楼2单元1603）</span></li>
						<!--<li id="recycle" style="float: right;"><img
							src="${pageContext.request.contextPath }/easymob-webim1.0/img/recycle.png" onclick="clearCurrentChat();"
							style="margin-right: 15px; cursor: hand; width: 18px;" title="清屏" /></li>
						--><li id="roomInfo" style="float: right;"><img
							id="roomMemberImg"
							src="${pageContext.request.contextPath }/easymob-webim1.0/img/head/find_more_friend_addfriend_icon.png"
							onclick="showRoomMember();"
							style="margin-right: 15px; cursor: hand; width: 18px; display: none"
							title="群组成员" /></li>
					</ul>
				</div>
				<div id="null-nouser" class="chat01_content"></div>
			</div>

			<div class="chat02">
				<div class="chat02_title">
					<a class="chat02_title_btn1" onclick="showEmotionDialog()"
						title="选择表情"></a> <a class="chat02_title_btn ctb03" title="选择图片"
						onclick="showSendPic()" href="#"> </a> <a
						class="chat02_title_btn ctb02" onclick="showSendAudio()" href="#"
						title="选择语音"><span></span></a> <label id="chat02_title_t"></label>
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
						<li style="right: 15px;"><img src="${pageContext.request.contextPath }/easymob-webim1.0/img/send_btn.jpg"
							onclick="sendText()" /></li>
					</ul>
				</div>

				<div style="clear: both;"></div>
			</div>
			<div id="fileModal" class="modal hide fade" role="dialog"
				aria-hidden="true" data-backdrop="static">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>文件选择框</h3>
				</div>
				<div class="modal-body">
					<input type='file' id="fileInput" /> <input type='hidden'
						id="sendfiletype" />
					<div id="send-file-warning"></div>
				</div>
				<div class="modal-footer">
					<button id="fileSend" class="btn btn-primary" onclick="sendFile()">发送</button>
					<button id="cancelfileSend" class="btn" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>

		<div id="addFridentModal" class="modal hide fade" role="dialog"
			aria-hidden="true" data-backdrop="static">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3>添加好友</h3>
			</div>
			<div class="modal-body">
				<input id="addfridentId" onfocus='clearInputValue("addfridentId")' />
				<div id="add-frident-warning"></div>
			</div>
			<div class="modal-footer">
				<button id="addFridend" class="btn btn-primary"
					onclick="startAddFriend()">添加</button>
				<button id="cancelAddFridend" class="btn" data-dismiss="modal">取消</button>
			</div>
		</div>

		<div id="delFridentModal" class="modal hide fade" role="dialog"
			aria-hidden="true" data-backdrop="static">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3>删除好友</h3>
			</div>
			<div class="modal-body">
				<input id="delfridentId" onfocus='clearInputValue("delfridentId")' />
				<div id="del-frident-warning"></div>
			</div>
			<div class="modal-footer">
				<button id="delFridend" class="btn btn-primary"
					onclick="directDelFriend()">删除</button>
				<button id="canceldelFridend" class="btn" data-dismiss="modal">取消</button>
			</div>
		</div>

		<!-- 一般消息通知 -->
		<div id="notice-block-div" class="modal fade hide">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<div class="modal-body">
				<h4>Warning!</h4>
				<div id="notice-block-body"></div>
			</div>
		</div>

		<!-- 确认消息通知 -->
		<div id="confirm-block-div-modal" class="modal fade hide"
			role="dialog" aria-hidden="true" data-backdrop="static">
			<div class="modal-header">
				<h3>订阅通知</h3>
			</div>
			<div class="modal-body">
				<div id="confirm-block-footer-body"></div>
			</div>
			<div class="modal-footer">
				<button id="confirm-block-footer-confirmButton"
					class="btn btn-primary">同意</button>
				<button id="confirm-block-footer-cancelButton" class="btn"
					data-dismiss="modal">拒绝</button>
			</div>
		</div>

		<!-- 群组成员操作界面 -->
		<div id="option-room-div-modal" class="alert modal fade hide"
			role="dialog" aria-hidden="true" data-backdrop="static">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<div class="modal-header">
				<h3>群组成员</h3>
			</div>
			<div class="modal-body">
				<div id="room-member-list" style="height: 100px; overflow-y: auto"></div>
			</div>
		</div>
	</div>
</body>
</html>
