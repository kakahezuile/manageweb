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




	//构造当前聊天记录的窗口div
	var getContactChatDiv = function(chatUserId) {
		return document.getElementById(curUserId + "-" + chatUserId);
	};


	var cleanListRoomDiv = function cleanListRoomDiv() {
		$('#contactgrouplistUL').empty();
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

	

</script>
</head>
<body>
	



	<div class="hx-content" id="content" style="display: block">
		<div class="hx-leftcontact" id="leftcontact" style="display:;">
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
			
			
		</div>
		<div id="rightTop" style="height:0;"></div>
		<!-- 聊天页面 -->
		<div class="chatRight">
			<div id="chat01">
				<div class="chat01_title" style="height:1px;overflow:hidden;margin-left:0;">
					<ul class="talkTo">
						<li id="talkTo"><a href="#"></a><span>（1号楼2单元1603）</span></li>
						<li id="recycle" style="float: right;"><img
							src="${pageContext.request.contextPath }/easymob-webim1.0/img/recycle.png" onclick="clearCurrentChat();"
							style="margin-right: 15px; cursor: hand; width: 18px;" title="清屏" /></li>
						<li id="roomInfo" style="float: right;"><img
							id="roomMemberImg"
							src="${pageContext.request.contextPath }/easymob-webim1.0/img/head/find_more_friend_addfriend_icon.png"
							onclick="showRoomMember();"
							style="margin-right: 15px; cursor: hand; width: 18px; display: none"
							title="群组成员" /></li>
					</ul>
				</div>
				<div id="null-nouser" class="chat01_content" style="margin-left:0"></div>
			</div>

			<div class="chat02" style="display:none;">
				<div class="chat02_title">
					<a class="chat02_title_btn ctb01" onclick="showEmotionDialog()"
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
						<li style="right: 5px; top: 5px;"><img src="${pageContext.request.contextPath }/easymob-webim1.0/img/send_btn.jpg"
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
