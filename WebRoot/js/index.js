	var mychat=0;
	var ufbNum=0;//用户返回聊天记录计数
	var imgId=0;
	var tousuUserEmob;
	var historyKeFuMap={};
	var transactionMap={};
	var historyMap={};
	var homeShopsMessageMap = {};
	var homeUsersMessageMap = {};
	var homeUsersList;
	var homeShopsList;
	var homeUserThisObj;
	var homeUserobjSpan=0;
	var homeUserTousuSpan=0;
	var usersOrShops = 0; // 0 - users 1 - shops
	var isHomeInit = 0;
	var advisoryHistoryMap={};
	var advisoryHistoryIs=0;
	var advisoryHistoryId=1;
	
	function sleep(numberMillis) {
		var now = new Date();
		var exitTime = now.getTime() + numberMillis;
		while (true) { 
			now = new Date(); 
			if (now.getTime() > exitTime) return;
		}
	}
	
	function isHistoryKeFuMap(){
		var emob_id = $("#mychat_user_title_emob_id").val();
		if (emob_id) {
			if (historyKeFuMap[emob_id]) {
				openChatPages(homeUserThisObj, $("#mychat_user_title").val());
			} else {
				getMessageListFromUser(emob_id, curUserId);
			}
		}
	}
	
	function addHomeUserList(){
		if (!homeUsersList) {
			return;
		}
		for(var i = 0; i < homeUsersList.length; i++){
			message = homeUsersList[i];
			var avata= message.ext.avatar;
			if(avata==""){
				message.ext.avatar="/images/head_photo.png";
			}
			
			from = message.from;
			homeExt = message.ext;
			
			var ff=$("#"+from);
			if(ff.length==0){
				$("#contactlistUL").append("<li id=\""+from+"\" onclick=openChatPages(\""+from+"\",\""+trim(homeExt.nickname)+"\");><a style=\"display:none;\" href=\"#/"+homeExt.nickname+"\">"+homeExt.nickname+"</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p><p></div></div><input type=\"hidden\" id=\"avatar_"+from+"\" value=\""+homeExt.avatar+"\" /></li>");
			} else {
				ff.html("<a style=\"display:none;\" href=\"#/"+ homeExt.nickname+"\">"+homeExt.nickname+ "</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p>"+ "</div></div><input type=\"hidden\" id=\"avatar_"+from+"\" value=\""+homeExt.avatar+"\" />");
			}
		}
	}
	function trim(str) { 
		return str.replace(/\s+/g,"");
	}
	function getHomeMessageList(users){
		var talkTo=$("#" + talkToDivId);
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
		
		var myThis = $("#"+users+"-nouser");
		var histkefu=historyKeFuMap[users];
		if(histkefu != null && histkefu != undefined){
			var len = histkefu.length-1;
			for(var i = len ; i >=0 ; i--){
				var message = histkefu[i];
				var from = message.from;//消息的发送者
				var mestype = message.type;//消息发送的类型是群组消息还是个人消息
				var messageContent = message.data;//文本消息体
				var homeExt = message.ext;
				var timestamp=message.timestamp;
				var myDate=new Date(timestamp);
				var sen=myDate.getFullYear() + '-' + ('0' + (myDate.getMonth() + 1)).slice(-2) + '-' + ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
				myThis.prepend("<div class=\"chat-time\"><span>"+sen+"</span></div>");
				if(i == 0){
					$("#mychat_user_title").html(homeExt.nickname);
				}
				if (mestype!="txt") {
					var url=message.url;
					if(mestype=="audio"){
						myThis.prepend("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\">" +
							"<div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\">"+
							"<source src=\""+url+"\" /> "+
							"</audio></div> </div>" +
							"</div></div><span class=\"chat-me\">&nbsp;</span></div>");
					}else {
						myThis.prepend("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><img src=\""+url+"\"></img></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					}
				} else {
					if(from == curUserId){
						myThis.prepend("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" id=\""+from+"_"+i+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
						var my=$("#"+from+"_"+i);
						my.append(appendMsg(from,from,messageContent));
					}else{
						myThis.prepend("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\" id=\""+from+"_"+i+"\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
						var my=$("#"+from+"_"+i);
						my.append(appendMsg(from,from,messageContent));
					}
				}
			}
		}
	}
	
	function optionsAudioTousu(message,msgContentDiv){
		var filename = message.url;
		var from = message.from;
		var ext=message.ext;
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
				msgContentDiv.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\">" +
					"<div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\" src=\""+objectURL+"\">  </audio></div> </div>" +
					"</div></div><span class=\"chat-me\">&nbsp;</span></div>");
				if(ext.CMD_CODE=="401"||ext.CMD_CODE=="402"){
					scrollNouser(from);
				}else{
					scroll(from);
				}
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
	}
	
	function optionsPic(message,msgContentDiv){
		imgId++;
		msgContentDiv.append("<div class=\"text-left\"><img src=\""+message.ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box send-image\"><img id=\"img_" + imgId + "\" msgId=\"" + message.id + "\" style=\"max-width:" + maxWidth + "px\" src=\""+message.url+"\"></div></div><span class=\"chat-me\">&nbsp;</span></div>");
	}
	
	function optionsAudio(message, msgContentDiv){
		localData.set(message.id, JSON.stringify(message));
		
		var el = $("<div class=\"text-left\"><img src=\""+message.ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio msgId=\"" + message.id + "\" controls=\"controls\"/></div></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
		msgContentDiv.append(el);
		
		message.onFileDownloadComplete = function(response, xhr) {
			el.find("audio").attr("src", window.URL.createObjectURL(response));
		};
		message.onFileDownloadError = function(e) {
			el.remove();
			appendMsg(from, msgContentDiv, e.msg + ",下载音频" + filename + "失败");
		};
		message.headers = {
			"Accept" : "audio/mp3"
		};
		Easemob.im.Helper.download(message);
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
	
	var apiURL = null;
	var curUserId = null;
	var curChatUserId = null;
	var conn = null;
	var msgCardDivId = "chat01";
	var talkToDivId = "talkTo";
	var talkInputId = "talkInputId";
	var tousu_talkInputId="tousu_talkInputId";
	var ufb_talkInputId="ufbTalkInputId";
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

	var logout = function() {
		conn.close();
	};
	
	var showChatUI = function() {
		$('#content').show();
		
		var login_user = document.getElementById("login_user");
		if (!login_user) {
			return;
		}
		
		var login_userEle = login_user.children[0];
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
		document.getElementById("cur_user_id_index").value=curUserId;
		
		hiddenWaitLoginedUI();
		showChatUI();
		conn.setPresence();//设置用户上线状态，必须调用
	
		//启动心跳
		if (conn.isOpened()) {
			conn.heartBeat(conn);
		}
	};

	//连接中断时的处理，主要是对页面进行处理
	var handleClosed = function() {
		alert("连接中断了，请确认您的账号是否在别处登录!");
		top.location='/';
	};
	//异常情况下的处理方法
	var handleError = function(e) {
		if (curUserId == null) {
			hiddenWaitLoginedUI();
			alert(e.msg + ",请重新登录");
			top.location='/';
		} else {
			var msg = e.msg;
			if(e.type==7){
				islogin2();
				return ;
			}
			
			if (e.type == EASEMOB_IM_CONNCTION_SERVER_CLOSE_ERROR) {
				if (msg == "" || msg == 'unknown' ) {
					alert("服务器断开连接,可能是因为在别处登录");
					top.location='/';
				} else {
					alert("服务器断开连接");
					top.location='/';
				}
			} else if (e.type === EASEMOB_IM_CONNCTION_SERVER_ERROR) {
				if (msg.toLowerCase().indexOf("user removed") != -1) {
					alert("用户已经在管理后台删除");
					top.location='/';
				}
			} else {
				alert(msg);
				top.location='/';
			}
		}
		conn.stopHeartBeat(conn);
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
			$('#null-nouser').hide();
			$('#null-nouser-ufb').hide();
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
			$('<img>').attr("src", "${pageContext.request.contextPath }/easymob-webim1.0/img/head/contact_normal.png").appendTo(lielem);
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
		if ($(li).attr("type") == 'groupchat' && ('true' != $(li).attr("joined"))) {
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
		$('#null-nouser').hide();
		$('#null-nouser-ufb').hide();
		var badgespan = $(li).children(".badge");
		if (badgespan && badgespan.length > 0) {
			li.removeChild(li.children[2]);
		}

		//点击有未读消息对象时对未读消息提醒的处理
		var badgespanGroup = $(li).parent().parent().parent().find(".badge");
		if (badgespanGroup && badgespanGroup.length == 0) {
			$(li).parent().parent().parent().prev().children().children().remove();
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
		$('#null-nouser').show();
		$('#null-nouser-ufb').show();
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
	var showEmotionDialogTousu = function() {
		if (emotionFlag) {
			$('#tousu_wl_faces_box').css({
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
				selectEmotionImgTousu(this);
			});
			$('<li>').append(emotions).appendTo($('#tousu_emotionUL'));
		}
		$('#tousu_wl_faces_box').css({
			"display" : "block"
		});
	};
	var showEmotionDialogUFB = function() {
		if (emotionFlag) {
			$('#ufb_wl_faces_box').css({
				"display" : "block"
			});
			return;
		}
		emotionFlag = true;
		var sjson = Easemob.im.Helper.EmotionPicData;
		for (var key in sjson) {
			var emotions = $('<img>').attr({
				"id" : key,
				"src" : sjson[key],
				"style" : "cursor:pointer;"
			}).click(function() {
				selectEmotionImgUFB(this);
			});
			$('<li>').append(emotions).appendTo($('#ufb_emotionUL'));
		}
		$('#ufb_wl_faces_box').css({
			"display" : "block"
		});
	};
	//表情选择div的关闭方法
	var turnoffFaces_box = function() {
		$("#wl_faces_box").fadeOut("slow");
	};
	var turnoffFaces_box_tousu = function() {
		$("#tousu_wl_faces_box").fadeOut("slow");
	};
	var turnoffFaces_box_ufb = function() {
		$("#ufb_wl_faces_box").fadeOut("slow");
	};
	
	var selectEmotionImg = function(selImg) {
		var txt = document.getElementById(talkInputId);
		txt.value = txt.value + selImg.id;
		txt.focus();
	};
	var selectEmotionImgTousu = function(selImg) {
		var txt = document.getElementById(tousu_talkInputId);
		txt.value = txt.value + selImg.id;
		txt.focus();
	};
	var selectEmotionImgUFB = function(selImg) {
		var txt = document.getElementById(ufb_talkInputId);
		txt.value = txt.value + selImg.id;
		txt.focus();
	};

	var sendText = function() {
		if (textSending) {
			return;
		}

		var msgInput = document.getElementById(talkInputId),
			msg = msgInput.value,
			to = curChatUserId;
		if (!msg || !to) {
			return;
		}
		
		textSending = true;
		var options = {
			to : to,
			msg : msg,
			type : "chat"
		};
		//easemobwebim-sdk发送文本消息的方法 to为发送给谁，meg为文本消息对象
		conn.sendTextMessage(options);
		
		appendMsg(curUserId, to, msg.replace(/\n/g, '<br>'))
		
		turnoffFaces_box();
		msgInput.value = "";
		msgInput.focus();
		
		setTimeout(function() {
			textSending = false;
		}, 500);
	};
	
	function tileUp(homeExtCMD_CODE){
		var bo = true;
		var title = document.title;
		
		window.setInterval(function() {
			if($(':focus').length==0) {
				if(homeExtCMD_CODE==403){
					if(homeUserTousuSpan>0){
						document.title = (bo?'【您有新消息】':title);
						bo = !bo;
					}else{
						document.title = title;
					}
				}else{
					if(homeUserobjSpan>0){
						document.title = (bo?'【您有新消息】':title);
						bo = !bo;
					}else{
						document.title = title;
					}
				}
			}else{
				document.title = title;
			}
		},500);
	}
	//easemobwebim-sdk收到文本消息的回调方法的实现
	var handleTextMessage = function(message) {
		var avata= message.ext.avatar;
		if(avata==""){
			message.ext.avatar="/images/head_photo.png";
		}
		var from = message.from;//消息的发送者
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var messageContent = message.data;//文本消息体
		var room = message.to;//根据消息体的to值去定位那个群组的聊天记录
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		tileUp(homeExtCMD_CODE);
		if (homeExtCMD_CODE == "403") { // 投诉
			var tousu_emob=$("#tousu_index").val(); 
			getComplaintList2();
			var tousu_emob_id_from = document.getElementById("tousu_emob_id_from").value;
			var objSpan403 = document.getElementById("span_tousu");
			if (objSpan403 == null) {
				var tousuClassName = $("#zhou_bian").attr("class");
				if (tousuClassName != "select") {
					$("#zhou_bian").append("<span id=\"span_tousu\" class=\"pop-tip\">&nbsp;</span>");
				}
			}
			var objSpan4032 = document.getElementById("tousu_span_" + from);
			if (objSpan4032 == null) {
				var tousuClassName2 = $("#zhou_bian").attr("class");
				if (tousuClassName2 != "select" || tousu_emob_id_from != from) {
					var tou_from=$("#tousu_" + from);
					var tou_list=$("#compl_"+from);
					$("#tousu_contactlistUL").prepend(tou_list);
					tou_from.prepend("<span id=\"tousu_span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					$("#tousu_contactlistUL").prepend(tou_from);
					homeUserTousuSpan++;
				}
			}
			
			if (tousu_emob == from || tousu_emob == "") {
				if (tousu_emob_id_from == from) {
					var nouser=document.getElementById(from+"-tousu-nouser");
					if(nouser==null){
						addChatPagesTousu(from);
					}
					openChatPagesTousu(from,homeExt.nickname);
					setTime(from);
					$("#tousu_index").val(tousu_emob); 
					var myThis = $("#"+from+"-tousu-nouser");
					myThis.append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
						+ myUserMap[from]
						+ " ："
						+ messageContent
						+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
					scroll(from);
				}else{
					var tou_from=$("#tousu_" + from);
					var tou_list=$("#compl_"+from);
					$("#tousu_contactlistUL").prepend(tou_list);
					$("#tousu_contactlistUL").prepend(tou_from);
					
					var nouser=document.getElementById(from+"-tousu-nouser");
					if(nouser==null){
						addChatPagesTousu(from);
					}
					setTime(from);
					$("#tousu_index").val(tousu_emob); 
					var myThis = $("#"+from+"-tousu-nouser");
					myThis.append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
						+ myUserMap[from]
						+ " ："
						+ messageContent
						+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				}
				
				setHao(from);
				setContactlistUL();
			} else {
				var tou_from=$("#tousu_" + from);
				tou_from.append("<span id=\"tousu_span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
				homeUserTousuSpan++;
			}
			
			scrollTop();
			setHao(from);
			setContactlistUL();
			return;
		} else if (homeExtCMD_CODE == "401" ||homeExtCMD_CODE == "402") { // 业主咨询
			var emob_id = $("#mychat_user_title_emob_id").val();
			var objSpan3 = document.getElementById("span_yezhuzixun");
			if (objSpan3 == null) {
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					$("#ke_fu").append("<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");
					homeUserobjSpan++;
				}
			}
			$("#mychat_user_title_emob_id").val(from);
			if(homeExtCMD_CODE == "401"){
				$("#accordion1").html("所有业主");
			}else{
				$("#accordion1").html("店家咨询");
			}
			
			if (from == homeUserThisObj || homeUserThisObj == null) {
				var emob_id = $("#mychat_user_title_emob_id").val(from);
				var fe=document.getElementById(from);
				var nouser=document.getElementById(from+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				openChatPages(from,homeExt.nickname);
				setMychatTime(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}
				
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					var objSpan5 = document.getElementById("span_" + from);
					if (objSpan5 == null) {
						$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					}
				}
				
				var dom = $("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				dom.find(".left-speak").append(appendMsg(from,from,messageContent));
				$("#"+from+"-nouser").append(dom);
				
				homeUserThisObj = from;
				setHao(from);
				setContactlistUL();
			} else { // 获取from的内容\
				var fe=document.getElementById( from);
				if(fe!=null){
					 $("#contactlistUL").prepend($("#" + from));
				}else{
					$("#contactlistUL").prepend("<li id=\""+from+"\" onclick=\"openChatPages('"+from+"','"+homeExt.nickname+"');\"><a style=\"display:none;\" href=\"#/"+homeExt.nickname+"\">"+homeExt.nickname+"</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p>"+ "</div></div></li>");
				}
				
				var nouser=document.getElementById(from+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				var objSpan5 = document.getElementById("span_" + from);
				if (objSpan5 == null) {
					$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					var headClassName = $("#ke_fu").attr("class");
					if (headClassName != "select") {
					
					}else{
						homeUserobjSpan++;
					}
				}
				setMychatTime(from);
				$("#"+from+"-nouser").append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
					+ homeExt.content
					+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			}
			scrollNouser(from);
			scrollNouserTop();
			setHao(from);
			setContactlistUL();
		} else if (homeExtCMD_CODE == "404" || !homeExtCMD_CODE) {//FIXME 用户反馈  || !homeExtCMD_CODE 这个判断是兼容手机端的一个bug，android1.2.6升级后请删除
			var fromContact = $("#" + from + "-ufb");
			if (fromContact.length) {
				$("#ufbContactlistUL").prepend(fromContact);
			} else {
				fromContact = $("<li id=\"" + from + "-ufb\" onclick=\"openUfbChatPages('" + from + "','" + trim(homeExt.nickname) + "')\"><a style=\"display:none;\" href=\"#/" + homeExt.nickname + "\">" + homeExt.nickname + "</a><div class=\"user-item-list clearfix\"><img src=\"" + homeExt.avatar + "\"><div class=\"chat-user-info\"><p><span class=\"username\">" + homeExt.nickname + "</span></p><p><span class=\"room\">" + (homeExt.address || "") + "</span></p></div></div><input type=\"hidden\" id=\"avatar_" + from + "\" value=\"" + homeExt.avatar + "\" /></li>");
				$("#ufbContactlistUL").prepend(fromContact);
			}
			
			openUfbChatPages(from, homeExt.nickname);
			setUserFeedBackTime(from);
			$("#"+from+"-nouser-ufb").append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">" + messageContent + "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			scrollUfbNouser(from);
			localData.set("ufb_" + from, $("#" + from + "-nouser-ufb").html());
			
			if (fromContact.find(".username").attr("as")) {
				localData.set("ufbContactList", $("#ufbContactlistUL").html());
			} else {
				$.ajax({
					url : "/api/v1/communities/1/users/" + from + "/address",
					type : "GET",
					dataType : 'json',
					success : function(data) {
						if (data.info) {
							fromContact.find(".username").html(homeExt.nickname + "(" + data.info.username + ")").attr("as", "1");
							fromContact.find(".room").html(data.info.communityName + "&nbsp;&nbsp;" + data.info.userFloor + data.info.userUnit + data.info.room);
							
							localData.set("ufbContactList", $("#ufbContactlistUL").html());
						}
					}
				});
			}
		} else if (homeExtCMD_CODE == "10086"){ // 关键词
			var jiangkong=document.getElementById("span_jiangkong");
			if(document.getElementById("huodong") != null && document.getElementById("huodong") != undefined&jiangkong==null){
				$("#huodong").append("<span id=\"span_jiangkong\" class=\"pop-tip\">&nbsp;</span>");
			}
		}
	};
	//easemobwebim-sdk收到表情消息的回调方法的实现，message为表情符号和文本的消息对象，文本和表情符号sdk中做了
	//统一的处理，不需要用户自己区别字符是文本还是表情符号。
	var handleEmotion = function(message) {
		var avata= message.ext.avatar;
		if(avata==""){
			message.ext.avatar="/images/head_photo.png";
		}
		var from = message.from;
		var room = message.to;
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "401"||homeExtCMD_CODE == "402") { // 业主咨询表情处理
			var emob_id = $("#mychat_user_title_emob_id").val();
			var objSpan3 = document.getElementById("span_yezhuzixun");
			if (objSpan3 == null) {
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					$("#ke_fu").append("<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");
					homeUserobjSpan++;
				}
			}
			
			$("#mychat_user_title_emob_id").val(from);
			$("#accordion1").html("所有业主");
			if (from == homeUserThisObj || homeUserThisObj == null) {
				var nouser=document.getElementById(+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				openChatPages(from,homeExt.nickname);
				var fe=document.getElementById(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					var objSpan5 = document.getElementById("span_" + from);
					if (objSpan5 == null) {
						$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					}
				}
				setMychatTime(from);
				
				var dom = $("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				dom.find(".left-speak").append(appendMsg(from,from,message));
				$("#"+from+"-nouser").append(dom);
				
				homeUserThisObj = from;
				setHao(from);
				setContactlistUL();
			} else { // 获取from的内容
				var fe=document.getElementById(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}else{
					$("#contactlistUL").prepend("<li id=\""+from+"\" onclick=\"openChatPages('"+from+"','"+homeExt.nickname+"');\"><a style=\"display:none;\" href=\"#/"+homeExt.nickname+"\">"+homeExt.nickname+"</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p></div></div></li>");
				}
				$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
				var nouser=document.getElementById(+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				setMychatTime(from);
				
				var dom = $("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				dom.find(".left-speak").append(appendMsg(from,from,message));
				$("#"+from+"-nouser").append(dom);
			}
			scrollNouser(from);
			scrollNouserTop();
			setHao(from);
			setContactlistUL();
		} else if (homeExtCMD_CODE == "403") { // 投诉表情处理
			var tousu_emob_id_from = document.getElementById("tousu_emob_id_from").value;
			var objSpan403 = document.getElementById("span_tousu");
			if (objSpan403 == null) {
				var tousuClassName = $("#zhou_bian").attr("class");
				if (tousuClassName != "select") {
					$("#zhou_bian").append("<span id=\"span_tousu\" class=\"pop-tip\">&nbsp;</span>");
				}
			}
			
			var objSpan4032 = document.getElementById("tousu_span_" + from);
			if (objSpan4032 == null) {
				var tousuClassName2 = $("#zhou_bian").attr("class");
				if (tousuClassName2 != "select" || tousu_emob_id_from != from) {
					var tou_from=$("#tousu_" + from);
					var tou_list=$("#compl_"+from);
					$("#tousu_contactlistUL").prepend(tou_list);
					tou_from.prepend("<span id=\"tousu_span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					$("#tousu_contactlistUL").prepend(tou_from);
					homeUserTousuSpan++;
				}
			}
			
			if (tousu_emob_id_from == from) {
				var nouser=document.getElementById(from+"-tousu-nouser");
				if(nouser==null){
					addChatPagesTousu(from);
				}
				var myThis=$("#"+from+"-tousu-nouser");
				openChatPagesTousu(from,homeExt.nickname);
				setTime(from);
				
				var dom = $("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				dom.find(".left-speak").append(appendMsg(from,from,message));
				$("#"+from+"-tousu-nouser").append(dom);
			}else{
				var tou_from=$("#tousu_" + from);
				var tou_list=$("#compl_"+from);
				$("#tousu_contactlistUL").prepend(tou_list);
				$("#tousu_contactlistUL").prepend(tou_from);
				var nouser=document.getElementById(from+"-tousu-nouser");
				if(nouser==null){
					addChatPagesTousu(from);
				}
				var myThis=$("#"+from+"-tousu-nouser");
				setTime(from);
				
				var dom = $("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				dom.find(".left-speak").append(appendMsg(from,from,message));
				$("#"+from+"-tousu-nouser").append(dom);
			}
			scroll(from);
			scrollTop();
			setHao(from);
			setContactlistUL();
			return;
		} else if (homeExtCMD_CODE == "404" || !homeExtCMD_CODE) {//用户反馈  || !homeExtCMD_CODE 这个判断是兼容手机端的一个bug，android1.2.6升级后请删除
			var fromContact = $("#" + from + "-ufb");
			if (fromContact.length) {
				$("#ufbContactlistUL").prepend(fromContact);
			} else {
				fromContact = $("<li id=\"" + from + "-ufb\" onclick=\"openUfbChatPages('" + from + "','" + trim(homeExt.nickname) + "')\"><a style=\"display:none;\" href=\"#/" + homeExt.nickname + "\">" + homeExt.nickname + "</a><div class=\"user-item-list clearfix\"><img src=\"" + homeExt.avatar + "\"><div class=\"chat-user-info\"><p><span class=\"username\">" + homeExt.nickname + "(" + (homeExt.communityName || "") + ")</span></p><p><span class=\"room\">" + (homeExt.address || "") + "</span></p></div></div><input type=\"hidden\" id=\"avatar_" + from + "\" value=\"" + homeExt.avatar + "\" /></li>");
				$("#ufbContactlistUL").prepend(fromContact);
			}
			
			openUfbChatPages(from, homeExt.nickname);
			
			ufbNum++;
			setUserFeedBackTime(from);
			$("#" + from + "-nouser-ufb").append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\" id=\"emotion_ufb_" + from + "_" + ufbNum + "\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			$("#emotion_ufb_" + from + "_" + ufbNum).append(appendMsg(from, from, message));
			scrollUfbNouser(from);
			localData.set("ufb_" + from, $("#" + from + "-nouser-ufb").html());
			localData.set("ufbContactList", $("#ufbContactlistUL").html());
		}
	};
	//easemobwebim-sdk收到图片消息的回调方法的实现
	var handlePictureMessage = function(message) {
		var from = message.from;//文件的发送者
		var avata= message.ext.avatar;
		if(avata==""){
			message.ext.avatar="/images/head_photo.png";
		}
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "401"||homeExtCMD_CODE == "402") {
			var emob_id = $("#mychat_user_title_emob_id").val();
			var objSpan3 = document.getElementById("span_yezhuzixun");
			if (objSpan3 == null) {
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					$("#ke_fu").append("<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");
					homeUserobjSpan++;
				}
			}
			$("#mychat_user_title_emob_id").val(from);
			$("#accordion1").html("所有业主");
			if (from == homeUserThisObj || homeUserThisObj == null) {
				var nouser=document.getElementById(+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				openChatPages(from,homeExt.nickname);
				var fe=document.getElementById(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					var objSpan5 = document.getElementById("span_" + from);
					if (objSpan5 == null) {
						$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					}
				}
				setMychatTime(from);
				var nouser=$("#"+from+"-nouser");
				optionsPic(message,nouser);
				homeUserThisObj = from;
				scrollNouser(from);
				setHao(from);
				setContactlistUL();
			} else { // 获取from的内容
				var fe=document.getElementById(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}else{
					$("#contactlistUL").prepend("<li id=\""+from+"\" onclick=\"openChatPages('"+from+"','"+homeExt.nickname+"');\"><a style=\"display:none;\" href=\"#/"+homeExt.nickname+"\">"+homeExt.nickname+"</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p></div></div></li>");
				}
				$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
				}else{
					homeUserobjSpan++;
				}
				var nouser=document.getElementById(+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				setMychatTime(from);
				optionsPic(message,$("#"+from+"-nouser"));
			}
			scrollNouser(from);
			scrollNouserTop();
			setHao(from);
			setContactlistUL();
		}else if(homeExtCMD_CODE == "403"){
			var tousu_emob_id_from = document.getElementById("tousu_emob_id_from").value;
			var objSpan403 = document.getElementById("span_tousu");
			if (objSpan403 == null) {
				var tousuClassName = $("#zhou_bian").attr("class");
				if (tousuClassName != "select") {
					$("#zhou_bian").append("<span id=\"span_tousu\" class=\"pop-tip\">&nbsp;</span>");
				}
			}
			
			var objSpan4032 = document.getElementById("tousu_span_" + from);
			if (objSpan4032 == null) {
				var tousuClassName2 = $("#zhou_bian").attr("class");
				if (tousuClassName2 != "select" || tousu_emob_id_from != from) {
					var tou_from=$("#tousu_" + from);
					var tou_list=$("#compl_"+from);
					$("#tousu_contactlistUL").prepend(tou_list);
					tou_from.prepend("<span id=\"tousu_span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					$("#tousu_contactlistUL").prepend(tou_from);
					homeUserTousuSpan++;
				}
			}
			if (tousu_emob_id_from == from) {
				var nouser=document.getElementById(from+"-tousu-nouser");
				if(nouser==null){
					addChatPagesTousu(from);
				}
				var myThis=$("#"+from+"-tousu-nouser");
				openChatPagesTousu(from,homeExt.nickname);
				setTime(from);
				optionsPic(message,myThis);
				scroll(from);
			}else{
				var tou_from=$("#tousu_" + from);
				var tou_list=$("#compl_"+from);
				$("#tousu_contactlistUL").prepend(tou_list);
				$("#tousu_contactlistUL").prepend(tou_from);
				var nouser=document.getElementById(from+"-tousu-nouser");
				if(nouser==null){
					addChatPagesTousu(from);
				}
				var myThis=$("#"+from+"-tousu-nouser");
				setTime(from);
				optionsPic(message,myThis);
			}
			scrollTop();
			setHao(from);
			setContactlistUL();
			return;
		} else if (homeExtCMD_CODE == "404" || !homeExtCMD_CODE) {//用户反馈  || !homeExtCMD_CODE 这个判断是兼容手机端的一个bug，android1.2.6升级后请删除
			var fromContact = $("#" + from + "-ufb");
			if (fromContact.length) {
				$("#ufbContactlistUL").prepend(fromContact);
			} else {
				fromContact = $("<li id=\"" + from + "-ufb\" onclick=\"openUfbChatPages('" + from + "','" + trim(homeExt.nickname) + "')\"><a style=\"display:none;\" href=\"#/" + homeExt.nickname + "\">" + homeExt.nickname + "</a><div class=\"user-item-list clearfix\"><img src=\"" + homeExt.avatar + "\"><div class=\"chat-user-info\"><p><span class=\"username\">" + homeExt.nickname + "(" + (homeExt.communityName || "") + ")</span></p><p><span class=\"room\">" + (homeExt.address || "") + "</span></p></div></div><input type=\"hidden\" id=\"avatar_" + from + "\" value=\"" + homeExt.avatar + "\" /></li>");
				$("#ufbContactlistUL").prepend(fromContact);
			}
			
			openUfbChatPages(from, homeExt.nickname);
			
			setUserFeedBackTime(from);
			optionsPic(message, $("#" + from + "-nouser-ufb"));
			scrollUfbNouser(from);
			localData.set("ufb_" + from, $("#" + from + "-nouser-ufb").html());
			localData.set("ufbContactList", $("#ufbContactlistUL").html());
		}
	};

	//easemobwebim-sdk收到音频消息回调方法的实现
	var handleAudioMessage = function(message) {
		var from = message.from;//文件的发送者
		var avata= message.ext.avatar;
		if(avata==""){
			message.ext.avatar="/images/head_photo.png";
		}
		var homeExt = message.ext;
		var homeExtCMD_CODE = homeExt.CMD_CODE;
		if (homeExtCMD_CODE == "401"||homeExtCMD_CODE == "402") {
			var emob_id = $("#mychat_user_title_emob_id").val();
			var objSpan3 = document.getElementById("span_yezhuzixun");
			if (objSpan3 == null) {
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					$("#ke_fu").append("<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");
					homeUserobjSpan++;
				}
			}
			$("#mychat_user_title_emob_id").val(from);
			$("#accordion1").html("所有业主");
			
			if (from == homeUserThisObj || homeUserThisObj == null) {
				var nouser=document.getElementById(+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				openChatPages(from,homeExt.nickname);
				var fe=document.getElementById(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}
				var headClassName = $("#ke_fu").attr("class");
				if (headClassName != "select") {
					var objSpan5 = document.getElementById("span_" + from);
					if (objSpan5 == null) {
						$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					}
				}
				setMychatTime(from);
				var nouser=$("#"+from+"-nouser");
				optionsAudioTousu(message,nouser);
				homeUserThisObj = from;
				setHao(from);
				setContactlistUL();
			} else { // 获取from的内容
				var fe=document.getElementById(from);
				if(fe!=null){
					$("#contactlistUL").prepend($("#" + from));
				}else{
					$("#contactlistUL").prepend("<li id=\""+from+"\" onclick=\"openChatPages('"+from+"','"+homeExt.nickname+"');\"><a style=\"display:none;\" href=\"#/"+homeExt.nickname+"\">"+homeExt.nickname+"</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p></div></div></li>");
				}
				$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
				var nouser=document.getElementById(+"-nouser");
				if(nouser==null){
					addChatPages(from);
				}
				setMychatTime(from);
				var nouser=$("#"+from+"-nouser");
				optionsAudioTousu(message,nouser);
			}
			scrollNouser(from);
			scrollNouserTop();
			setHao(from);
			setContactlistUL();
		}else if(homeExtCMD_CODE == "403"){
			var tousu_emob_id_from = document.getElementById("tousu_emob_id_from").value;
			var objSpan403 = document.getElementById("span_tousu");
			if (objSpan403 == null) {
				var tousuClassName = $("#zhou_bian").attr("class");
				if (tousuClassName != "select") {
					$("#zhou_bian").append("<span id=\"span_tousu\" class=\"pop-tip\">&nbsp;</span>");
				}
			}
			$("#tousu_contactlistUL").find("li").css("background", "#FFFFFF");
			var objSpan4032 = document.getElementById("tousu_span_" + from);
			if (objSpan4032 == null) {
				var tousuClassName2 = $("#zhou_bian").attr("class");
				if (tousuClassName2 != "select" || tousu_emob_id_from != from) {
					var tou_from=$("#tousu_" + from);
					var tou_list=$("#compl_"+from);
					$("#tousu_contactlistUL").prepend(tou_list);
					tou_from.prepend("<span id=\"tousu_span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
					$("#tousu_contactlistUL").prepend(tou_from);
					homeUserTousuSpan++;
				}
			}
			if (tousu_emob_id_from == from) {
				var nouser=document.getElementById(from+"-tousu-nouser");
				if(nouser==null){
					addChatPagesTousu(from);
				}
				var myThis =$("#"+from+"-tousu-nouser");
				openChatPagesTousu(from,homeExt.nickname);
				setTime(from);
				optionsAudioTousu(message,myThis);
				scroll(from);
			}else{
				var tou_from=$("#tousu_" + from);
				var tou_list=$("#compl_"+from);
				$("#tousu_contactlistUL").prepend(tou_list);
				$("#tousu_contactlistUL").prepend(tou_from);
				
				var nouser=document.getElementById(from+"-tousu-nouser");
				if(nouser==null){
					addChatPagesTousu(from);
				}
				var myThis =$("#"+from+"-tousu-nouser");
				setTime(from);
				optionsAudioTousu(message,myThis);
			}
			scrollTop();
			setHao(from);
			setContactlistUL();
			return;
		} else if (homeExtCMD_CODE == "404" || !homeExtCMD_CODE) {//用户反馈  || !homeExtCMD_CODE 这个判断是兼容手机端的一个bug，android1.2.6升级后请删除
			var fromContact = $("#" + from + "-ufb");
			if (fromContact.length) {
				$("#ufbContactlistUL").prepend(fromContact);
			} else {
				fromContact = $("<li id=\"" + from + "-ufb\" onclick=\"openUfbChatPages('" + from + "','" + trim(homeExt.nickname) + "')\"><a style=\"display:none;\" href=\"#/" + homeExt.nickname + "\">" + homeExt.nickname + "</a><div class=\"user-item-list clearfix\"><img src=\"" + homeExt.avatar + "\"><div class=\"chat-user-info\"><p><span class=\"username\">" + homeExt.nickname + "(" + (homeExt.communityName || "") + ")</span></p><p><span class=\"room\">" + (homeExt.address || "") + "</span></p></div></div><input type=\"hidden\" id=\"avatar_" + from + "\" value=\"" + homeExt.avatar + "\" /></li>");
				$("#ufbContactlistUL").prepend(fromContact);
			}
			
			openUfbChatPages(from, homeExt.nickname);
			
			setUserFeedBackTime(from);
			optionsAudio(message, $("#" + from + "-nouser-ufb"));
			scrollUfbNouser(from);
			localData.set("ufb_" + from, $("#" + from + "-nouser-ufb").html());
			localData.set("ufbContactList", $("#ufbContactlistUL").html());
		}
	};

	var handleLocationMessage = function(message) {
		var from = message.from;
		var addr = message.addr;
		var ele = appendMsg(from, from, addr);
		return ele;
	};

	//显示聊天记录的统一处理方法
	var appendMsg = function(who, contact, message, chattype,avatar) {
		var ext=message.ext;
		var localMsg = null;
		if (typeof message == 'string') {
			localMsg = Easemob.im.Helper.parseTextMessage(message);
			localMsg = localMsg.body;
		} else {
			localMsg = message.data;
		}
		var headstr = [ "","" ];
		var header = $(headstr.join(''));

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
				var eletext = "<p3 class='faceimg'><img src='" + data + "'/></p3>";
				var ele = $(eletext);
				for (var j = 0; j < ele.length; j++) {
					lineDiv.appendChild(ele[j]);
				}
			} else if (type == "pic" || type == 'audio' || type == 'video') {
				lineDiv.appendChild(data);
			} else {
				var eletext = "<p3>" + data + "</p3>";
				var ele = $(eletext);
				ele[0].setAttribute("class", "chat-content-p3");
				ele[0].setAttribute("className", "chat-content-p3");
				if (curUserId == who) {
				//	ele[0].style.backgroundColor = "#EBEBEB";
				}
				for (var j = 0; j < ele.length; j++) {
					lineDiv.appendChild(ele[j]);
				}
			}
		}
		
		return lineDiv;
	};

	//显示聊天记录的统一处理方法
	var appendMsg2 = function(who, contact, message, chattype,avatar) {
		var ext=message.ext;

		// 消息体 {isemotion:true;body:[{type:txt,msg:ssss}{type:emotion,msg:imgdata}]}
		var localMsg = null;
		if (typeof message == 'string') {
			localMsg = Easemob.im.Helper.parseTextMessage(message);
			localMsg = localMsg.body;
		} else {
			localMsg = message.data;
		}
		var headstr = [ "","" ];
		var header = $(headstr.join(''));

		var lineDiv = document.createElement("div");
		for (var i = 0; i < header.length; i++) {
			var ele = header[i];
			lineDiv.appendChild(ele);
		}
		var messageContent = localMsg;
		//var test= new Array();
		var test=null;
		for (var i = 0; i < messageContent.length; i++) {
			var msg = messageContent[i];
			var type = msg.type;
			var data = msg.data;
			if (type == "emotion") {
				var eletext = "<p3><img src='" + data + "'/></p3>";
				var ele = $(eletext);
				
				for (var j = 0; j < ele.length; j++) {
					lineDiv.insertBefore(ele[j],test);
					test=ele[j];
				}
				
			} else if (type == "pic" || type == 'audio' || type == 'video') {
				lineDiv.appendChild(data);
			} else {
				var eletext = "<p3>" + data + "</p3>";
				var ele = $(eletext);
				ele[0].setAttribute("class", "chat-content-p3");
				ele[0].setAttribute("className", "chat-content-p3");
				if (curUserId == who) {
				//	ele[0].style.backgroundColor = "#EBEBEB";
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
		var time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
		return time;
	};