<%@ page language="java" pageEncoding="utf-8"%>

<script type="text/javascript">
var mychat_fa=0;
$.expr[':'].Contains = function(a, i, m) {
	return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
};

$(function() {
	var localContactList = localData.get("ufbContactList");
	if (localContactList) {
		$("#ufbContactlistUL").html(localContactList);
	}
	
	$("#ufbFilterInput").change(function() {
		var str = $(this).val(),
			filter = str.split("-");
		if (filter.length == 1) {
			filter = str;
		} else if (filter.length == 2) {
			filter = filter[0] + "号楼" + filter[1] + "单元";
		} else if (filter.length == 3) {
			filter = filter[0] + "号楼" + filter[1] + "单元" + filter[2];
		}
		
		var list = $("#ufbContactlistUL");
		if (filter) {
			$matches = list.find("a:Contains(" + filter + ")").parent();
			$("li", list).not($matches).slideUp();
			$matches.slideDown();
		} else {
			list.find("li").slideDown();
		}
		return false;
	}).keyup( function () {
		$(this).change();
	});
});

function schedule(){
	$("#ufb-upload-master-face-bg").show();
	$("#ufb-add-price-box").show();
}
function onSchedule(){
	$("#ufb-add-price-box").hide();
	$("#ufb-upload-master-face-bg").hide();
}
function scrollUfbNouser(from){
	var el = document.getElementById(from + "-nouser-ufb");
	el.scrollTop = el.scrollHeight;
}
function addUfbChatPages(from){
	var lishi = localData.get("ufb_" + from);
	if (!lishi) {
		$("#chat01-ufb").append("<div id='" + from + "-nouser-ufb' class='chat01_content' name='chat_pages_nouser' style='display:none;height:503px;'></div>");
	} else {
		var doms = $(lishi),
			imgs = {}
			audios = {},
			msgIds = "",
			now = parseInt(new Date().getTime()/1000);
		doms.find("[msgId]").each(function() {
			var el = this,
				me = $(el),
				msgId = me.attr("msgId"),
				time = parseInt(me.attr("time"));
			
			if (now > time + 1000 * 60 * 10) {
				if (el.nodeName == "AUDIO") {
					audios[msgId] = el;
				} else {
					imgs[msgId] = me;
				}
				msgIds += msgId + ",";
			} else {
				if (el.nodeName == "AUDIO") {
					var options = eval("(" + localData.get(msgId) + ")") || {};
					options.onFileDownloadComplete = function(response, xhr) {
						el.src = window.URL.createObjectURL(response);
						el.onload = function() {
							el.onload = null;
							window.URL.revokeObjectURL(el.src);
						};
					};
					options.headers = {
						"Accept" : "audio/mp3"
					};
					options.onFileDownloadError = function(e) {
						console.log(e.msg);
					};
					Easemob.im.Helper.download(options);
				}
			}
		});
		
		if (msgIds) {
			msgIds = msgIds.substring(0, msgIds.length - 1);
			$.ajax({
				type: "GET",
				dataType: "json",
				url: "/api/v1/usersMessages/medias?msgId=" + msgIds,
				success: function(data) {
					data = data.info;
					
					for (var i = 0; i < data.length; i++) {
						var msgId = data[i].msgId;
						if (imgs[msgId]) {
							$(imgs[msgId]).attr("src", data[i].url);
						} else {
							$(audios[msgId]).attr("src", data[i].url);
						}
					}
				}
			});
		}
		
		var maxImgId = 0;
		doms.find("[id^=img_]").each(function() {
			var _id = parseInt(this.id.substring(10, this.id.length), 10);
			if (_id > maxImgId) {
				maxImgId = _id;
			}
		});
		imgId = maxImgId == 0 ? 0 : maxImgId + 1;
		
		$("#chat01-ufb").append($("<div id='" + from + "-nouser-ufb' class='chat01_content' name='chat_pages_nouser' style='display:none;height:503px;'></div>").append(doms));
	}
}
function openUfbChatPages(from, title) {
	var nouser = $("#" + from + "-nouser-ufb");
	if (!nouser.length) {
		addUfbChatPages(from);
		nouser = $("#" + from + "-nouser-ufb");
	}
	
	homeUserThisObj = from;
	curChatUserId = from;
	
	$("#" + from + "-ufb").addClass("select").siblings("li").attr("class", "");
	$("#talkTo_title_ufb").html(title);
	$("#talkTo_ufb").val(from);
	$("[name=chat_pages_nouser]").hide();
	nouser.show();
	scrollUfbNouser(from);
	$("#ufbTalkInputId").focus();
}

function setUserFeedBackTime(from) {
	var myDate=new Date();
	var sen=myDate.getFullYear() + '-' + ('0' + (myDate.getMonth() + 1)).slice(-2) + '-' + ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
	$("#" + from + "-nouser-ufb").append("<div class=\"chat-time\"><span>"+sen+"</span></div>");
}
function sendUFBText() {
	if (textSending) {
		return;
	}

	var msgInput = document.getElementById("ufbTalkInputId"),
		msg = msgInput.value,
		to = curChatUserId;
	if (!msg || !to) {
		return;
	}
	
	textSending = true;
	conn.sendTextMessage({
		to : to,
		msg : msg,
		type : "chat",
		ext : {
			avatar: "http://7d9lcl.com2.z0.glb.qiniucdn.com/common_pic_bangbang_feedback.png",
			content: msg,
			CMD_CODE: 404,
			nickname: "帮帮客服"
		}
	});
	
	$("#ufbContactlistUL").prepend($("#" + to + "-ufb"));
	setUserFeedBackTime(to);
	$("#" + to + "-nouser-ufb").append("<div class=\"text-right\"><img src=\"http://7d9lcl.com2.z0.glb.qiniucdn.com/common_pic_bangbang_feedback.png\"/><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\">" + msg + "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
	scrollUfbNouser(to);
	localData.set("ufb_" + to, $("#" + to + "-nouser-ufb").html());
	localData.set("ufbContactList", $("#ufbContactlistUL").html());
	
	turnoffFaces_box_ufb();
	msgInput.value = "";
	msgInput.focus();
	
	setTimeout(function() {
		textSending = false;
	}, 500);
}
function showAllUser() {
	loadCommunity();
	
	$("#userFeedBack_div").hide();
	$("#userFeedBackAll_div").show();
}
function loadCommunity() {
	var communityContainer = $("#community-list");
	if (communityContainer.attr("loaded")) {
		return;
	}
	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "/api/v1/communities/summary/getListCommunityQ",
		success: function(data) {
			data = data.info;
			
			var html = "";
			for (var i = 0; i < data.length; i++) {
				var id = data[i].communityId;
				var name = data[i].communityName;
				
				html += "<a href=\"javascript:void(0)\" onclick=\"showCommunityUser('" + id + "','" + name + "')\" key=\"" + name + "\">" + name + "</a>";
			}
			
			communityContainer.html(html).attr("loaded", "true");
		}
	});
}

function showCommunityUser(communityId, communityName) {
	var currentCommunityId = $("#community-id").val();
	if (currentCommunityId == communityId) {
		return;
	}
	
	$("#community-user-search").val("");
	$("#community-id").val(communityId)
	$("#community-name").html(communityName);
	
	var communityUserContainer = $("#community-user-list"),
		users = communityUserContainer.find("[communityId=" + communityId + "]");
	if (users.length) {
		users.siblings().hide();
		users.show();
		return;
	}
	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "/api/v1/communities/" + communityId + "/users/simple",
		success: function(data) {
			data = data.info;
			
			var html = "<ul communityId='" + communityId + "'>";
			for (var i = 0; i < data.length; i++) {
				var user = data[i],
					name = user.nickname || "",
					avatar = user.avatar || "",
					address = (user.userFloor || "") + (user.userUnit || "") + (user.room || "");
				
				html += "<li onclick=\"showUserChat('" + user.emobId + "', '" + name + "', '" + avatar + "', '" + address + "', '" + communityName + "')\" class='clearfix' key='" + name + address + "'><img src='" + (avatar || "") + "'><div class='community-user-info'><span class='nice-name'>" + name + "</span><span class='user-room'>(" + address + ")</span></div></li>";
			}
			html += "</ul>";
			
			communityUserContainer.find("ul").hide();
			communityUserContainer.append(html);
		}
	});
}
function searchCommunity(el) {
	var key = $(el).val(),
		as = $("#community-list a");
	if (!key) {
		as.show();
		return;
	}
	
	as.each(function() {
		var a = $(this);
		
		if (a.attr("key").indexOf(key) != -1) {
			a.slideDown();
		} else {
			a.slideUp();
		}
	});
}
function searchCommunityUser(el) {
	var key = $(el).val(),
		lis = $("ul[communityId=" + $("#community-id").val() + "]").find("li");
	if (!key) {
		lis.show();
		return;
	}
	
	lis.each(function() {
		var li = $(this);
		
		if (li.attr("key").indexOf(key) != -1) {
			li.slideDown();
		} else {
			li.slideUp();
		}
	});
}
function showUserChat(emobId, nickname, avatar, address, communityName) {
	$("#userFeedBackAll_div").hide();
	$("#userFeedBack_div").show();
	
	var contact = $("#" + emobId + "-ufb");
	if (contact.length) {
		$("#ufbContactlistUL").prepend(contact);
	} else {
		$("#ufbContactlistUL").prepend("<li id=\"" + emobId + "-ufb\" onclick=\"openUfbChatPages('" + emobId + "','" + nickname + "')\"><a style=\"display:none;\" href=\"#/" + nickname + "\">" + nickname + "</a><div class=\"user-item-list clearfix\"><img src=\"" + avatar + "\"><div class=\"chat-user-info\"><p><span class=\"username\">" + nickname + "(" + communityName + ")</span></p><p><span class=\"room\">" + address + "</span></p></div></div><input type=\"hidden\" id=\"avatar_" + emobId + "\" value=\"" + avatar + "\" /></li>");
	}
	
	openUfbChatPages(emobId, nickname);
	
	localData.set("ufb_" + emobId, $("#" + emobId + "-nouser-ufb").html());
	localData.set("ufbContactList", $("#ufbContactlistUL").html());
}
function showChatHistroy(){
	var emob_id = $("#talkTo_ufb").val();
	if (!emob_id) {
		return;
	}
	
	if (historyKeFuMap[emob_id]) {
		openUfbChatPages(emob_id, $("#talkTo_title_ufb").val());
	} else {
		schedule();
		$.ajax({
			url: "/api/v1/usersMessages?messageFrom=" + emob_id + "&messageTo=" + curUserId,
			type: "GET",
			dataType: "json",
			success: function(data) {
				if(data.status != "yes"){
					return;
				}
				
				data = data.info;
				var messageArray = [];
				for(var i = 0 ; i < data.length; i++){
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
				historyKeFuMap[emob_id] = messageArray;
				homeUsersMessageMap={};
				onSchedule();
				getUfbMessageList(emob_id);
			},
			error:function(er){
				alert("网络连接错误...");
				onSchedule();
			}
		});
	}
}
function getUfbMessageList(users){
	var talkTo=$("#" + users + "-ufb");
	if (talkTo.length) {
		talkTo.siblings("li").attr("class","");
		talkTo.className = "select";
	}
	
	var myThis = $("#" + users + "-nouser-ufb")
	var histkefu = historyKeFuMap[users];
	if (!histkefu) {
		return;
	}
	
	var len = histkefu.length-1;
	for(var i = len ; i >=0 ; i--){
		var message = histkefu[i],
			from = message.from,//消息的发送者
			mestype = message.type,//消息发送的类型是群组消息还是个人消息
			messageContent = message.data,//文本消息体
			homeExt = message.ext,
			timestamp = message.timestamp,
			myDate = new Date(timestamp),
			sen=myDate.getFullYear() + '-' + ('0' + (myDate.getMonth() + 1)).slice(-2) + '-' + ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
		
		if (mestype != "txt") {
			var url = message.url;
			if (mestype == "audio") {
				myThis.prepend("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\"><source src=\""+url+"\"/></audio></div></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			} else {
				myThis.prepend("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><img src=\""+url+"\"></img></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			}
		} else {
			if( from == curUserId) {
				myThis.prepend("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" id=\"history_ufb_" + from + "_" + i + "\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				$("#history_ufb_" + from + "_" + i).append(appendMsg(from, from, messageContent));
			} else {
				myThis.prepend("<div class=\"text-left\"><img src=\"" + homeExt.avatar + "\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\" id=\"history_ufb_" + from + "_" + i + "\"></div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				$("#history_ufb_" + from + "_" + i).append(appendMsg(from, from, messageContent));
			}
		}
		myThis.prepend("<div class=\"chat-time\"><span>"+sen+"</span></div>");
	}
}
</script>

<div class="loadingbox" id="ufb-add-price-box" style="display: none;">
	<img alt="" src="/images/chat/loading.gif">
</div>
<div class="upload-master-face-bg" id="ufb-upload-master-face-bg" style="display: none;">&nbsp;</div>
<div class="hx-content clearfix" id="content">
	<div class="hx-leftcontact" id="leftcontact">
		<div class="chat-search">
			<form class="filterform" action="#"><input class="filterinput" id="ufbFilterInput" type="text"></form>
		</div>
		<div id="ufbContractListDiv" style="height: 590px; overflow-y: auto; overflow-x: auto;">
			<div class="accordion">
				<div class="accordion-group">
					<div id="collapseOne" class="accordion-body collapse in">
						<div class="accordion-inner" id="contractlist">
							<ul id="ufbContactlistUL" class="chat03_content_ul">
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 聊天页面 -->
	<div class="chatRight" style="width:643px">
		<div id="chat01-ufb">
			<div class="chat01_title">
				<ul class="talkTo clearfix">
					<li id="talkTo">
						<a href="#"></a>
						<span id="talkTo_title_ufb"></span>
						<input type="hidden" id="talkTo_ufb">
					</li>
					<li class="chat-history">
						<div class="ask-record">
							<a onclick="showAllUser();" href="javascript:void(0);" class="chat-search">全部用户 </a>
							<a onclick="showChatHistroy();" href="javascript:void(0);" class="ask-history">聊天历史 </a>
						</div>
					</li>
				</ul>
			</div>
			<div id="null-nouser-ufb" class="chat01_content" name="chat_pages_nouser" style="height:503px"></div>
		</div>
		<div class="chat02-ufb">
			<div class="chat02_title">
				<a class="chat02_title_btn1" onclick="showEmotionDialogUFB()" title="选择表情"></a>
				<div id="ufb_wl_faces_box" class="wl_faces_box">
					<div class="wl_faces_content">
						<div class="title">
							<ul>
								<li class="title_name">常用表情</li>
								<li class="wl_faces_close">
									<span onclick="turnoffFaces_box_ufb()">&nbsp;</span>
								</li>
							</ul>
						</div>
						<div id="ufb_wl_faces_main" class="wl_faces_main">
							<ul id="ufb_emotionUL">
							</ul>
						</div>
					</div>
					<div class="wlf_icon"></div>
				</div>
			</div>
			<div class="chat02_content">
				<textarea id="ufbTalkInputId" style="resize:none;" onkeydown="if(event.keyCode==13){sendUFBText();}"></textarea>
			</div>
			<div class="chat02_bar">
				<ul>
					<li style="right: 15px;"><img src="${pageContext.request.contextPath}/easymob-webim1.0/img/send_btn.jpg" onclick="sendUFBText();" /></li>
				</ul>
			</div>
		</div>
	</div>
</div>