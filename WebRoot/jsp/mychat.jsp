<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
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
<script type="text/javascript">
var mychat_fa=0;
(function ($) {
	jQuery.expr[':'].Contains = function(a,i,m){
		return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
	};
	function filterList(header, list) {
		var form = $("<form>").attr({"class":"filterform","action":"#"}),
			input = $("<input class='filterinput' type='text'>");
		$(form).append(input).appendTo(header);
		$(input).change(function () {
			var str = $(this).val();
			var filter = str.split("-");
			if(filter.length==1){
				filter=str;
			}
			if(filter.length==2){
				filter=filter[0]+"号楼"+filter[1]+"单元";
			}
			if(filter.length==3){
				filter=filter[0]+"号楼"+filter[1]+"单元"+filter[2];
			}
			if(filter) {
				$matches = $(list).find('a:Contains(' + filter + ')').parent();
				$('li', list).not($matches).slideUp();
				$matches.slideDown();
			} else {
				$(list).find("li").slideDown();
			}
			return false;
		}).keyup( function () {
			$(this).change();
		});
	}
	$(function() {
		filterList($("#form_id"), $("#contactlistUL"));
	});
}(jQuery));

function schedule(){
	$("#add-price-box").attr("style","display:block");
	$("#upload-master-face-bg").attr("style","display:block");
}
function onSchedule(){
	$("#add-price-box").attr("style","display:none");
	$("#upload-master-face-bg").attr("style","display:none");
}
function scrollNouser(from){
	document.getElementById(from+"-nouser").scrollTop = document.getElementById(from+"-nouser").scrollHeight;
}
function scrollNouserTop(){
	document.getElementById("contractlist11").scrollTop = 0;
}
function addChatPages(from){
	var lishi = localData.get(from);
	if (!lishi) {
		$("#chat01").append("<div id='" + from + "-nouser' class='chat01_content' name='chat_pages_nouser' style='display:none'></div>");
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
		doms.find("[id^=tousu_img_]").each(function() {
			var _id = parseInt(this.id.substring(10, this.id.length), 10);
			if (_id > maxImgId) {
				maxImgId = _id;
			}
		});
		imgId = maxImgId == 0 ? 0 : maxImgId + 1;
		
		$("#chat01").append($("<div id='" + from + "-nouser' class='chat01_content' name='chat_pages_nouser' style='display:none'></div>").append(doms));
	}
}
function setNoneChatPages(){
	var chat_pages_nouser = document.getElementsByName("chat_pages_nouser");
	for (var i = 0; i < chat_pages_nouser.length; i++) {
		chat_pages_nouser[i].style.display="none";
	}
}
function openChatPages(from, title) {
	icon(from);
	document.getElementById("mychat_user_title").innerHTML = title;
	
	if (!advisoryHistoryMap[from] || advisoryHistoryMap[from].status == "end") {
		document.getElementById("advisory_id").style.display = "none";
		advisoryHistoryId = 1;
	} else {
		document.getElementById("advisory_id").style.display = "";
		advisoryHistoryId = advisoryHistoryMap[from].advisoryHistoryId;
	}
	
	var nouser = document.getElementById(from + "-nouser");
	if (!nouser) {
		addChatPages(from);
	}
	setNoneChatPages();
	$("#"+from+"-nouser").attr("style", "display:block");
	
	scrollNouser(from);
	document.getElementById(talkInputId).focus();
}
function icon(users){
	homeUserThisObj=users;
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
}
function messageServe(message,type){
	var from = message.from;
	var room = message.to;
	var mestype = message.type;//消息发送的类型是群组消息还是个人消息
	var homeExt = message.ext;
	var homeExtCMD_CODE = homeExt.CMD_CODE;
	var emob_id = $("#mychat_user_title_emob_id").val();
	var objSpan3 = document.getElementById("span_yezhuzixun");
	if (objSpan3 == null) {
		var headClassName = $("#yezhuzixun").attr("class");
		if (headClassName != "select") {
			$("#yezhuzixun").append("<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");
		}
		
		if (emob_id != from & emob_id != "") {
			$("#yezhuzixun").append("<span id=\"span_yezhuzixun\" class=\"pop-tip\">&nbsp;</span>");
		}
	}
	
	$("#mychat_user_title").html("");
	$("#mychat_user_title_emob_id").val(from);
	$("#mychat_user_title").html(homeExt.nickname);
	$("#accordion1").html("所有业主");
	usersOrShops = 0;
	if (from == homeUserThisObj || homeUserThisObj == null) {
		var fe=document.getElementById(from);
		if(fe!=null){
			$("#contactlistUL").prepend($("#" + from));
		}
		
		var headClassName = $("#yezhuzixun").attr("class");
		if (headClassName != "select") {
			var objSpan5 = document.getElementById("span_" + from);
			if (objSpan5 == null) {
				$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
				homeUserobjSpan++;
			}
		}
		var nouser=document.getElementById(from+"-nouser");
		if(nouser==null){
			addChatPages(from);
		}
		openChatPages(from,homeExt.nickname);
		if(type=="txt"){
			$("#"+from+"-nouser").append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
				+ homeExt.content
				+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
		}else if(type=="pic"){
			optionsPic(message,nouser);
		}else if(type=="audio"){
			optionsAudioTousu(message,nouser);
		}else if(type=="string"){
			$("#"+from+"-nouser").append(appendMsg(from, from, message));
		}
		homeUserThisObj = from;
	} else { // 获取from的内容
		var fe=document.getElementById( from);
		if(fe!=null){
			$("#contactlistUL").prepend($("#" + from));
		}else{
			$("#contactlistUL").prepend("<li id=\""+from+"\" onclick=\"openChatPages('"+from+"','"+homeExt.nickname+"');\"><a style=\"display:none;\" href=\"#/"+homeExt.nickname+"\">"+homeExt.nickname+"</a><div class=\"user-item-list clearfix\"><input id=\"complaintId\" type=\"hidden\" value=\"complaintId\"><img src=\""+homeExt.avatar+"\"></img><div class=\"chat-user-info\"><p><span class=\"username\">"+homeExt.nickname+"</span></p><p><span class=\"room\">(3号楼2单元502)</span></p>"+"</div></div></li>");
		}
		var nouser=document.getElementById(from+"-nouser");
		if(nouser==null){
			addChatPages(from);
		}
		var objSpan5 = document.getElementById("span_" + from);
		if (objSpan5 == null) {
			$("#" + from).prepend("<span id=\"span_"+from+"\" class=\"pop-tip\">&nbsp;</span>");
			homeUserobjSpan++;
		}
		if(type=="txt"){
			$("#"+from+"-nouser").append("<div class=\"text-left\"><img src=\""+homeExt.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\"><div class=\"left-speak\">"
				+ homeExt.content
				+ "</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
		}else if(type=="pic"){
			optionsPic(message,nouser);
		}else if(type=="audio"){
			optionsAudioTousu(message,nouser);
		}else if(type=="string"){
			$("#"+from+"-nouser").append( appendMsg(from, from, message));
		}
	}
	scrollNouser(from);
}
function homeSendText(){
	var sendVal = $("#talkInputId").val();
	if(!sendVal || !homeUserThisObj){
		return;
	}
	var myThis = $("#"+homeUserThisObj+"-nouser");
	
	setMychatTime(homeUserThisObj);
	var mychat_usenam=document.getElementById("mychat_usenam").value;
	var mychat_stat=document.getElementById("mychat_stat").value;
	var options = {
		to: homeUserThisObj,
		msg: sendVal,
		data: sendVal,
		type: "chat"
	};
	if(mychat_stat=="3"){
		options.ext={
			avatar:"http://wuye.ixiaojian.com/images/chat/serviceHeader.png",
			content:sendVal,
			CMD_CODE:401,
			nickname:"物业客服"
		};
	}else if(mychat_stat=="4"){
		options.ext={
			avatar:"http://wuye.ixiaojian.com/images/chat/serviceHeader.png",
			content:sendVal,
			CMD_CODE:402,
			nickname:"帮帮客服"
		};
	}
	
	var myDate = new Date();
	var sen = myDate.getFullYear() + '-'
		+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
		+ ('0' + myDate.getDate()).slice(-2)+"-"+ ('0' + myDate.getHours()).slice(-2) +"-"+('0' +myDate.getMinutes()).slice(-2) +"-"+('0' +myDate.getSeconds()).slice(-2);
	
	conn.sendTextMessage(options);
	mychat_fa++;
	myThis.append("<div class=\"text-right\"><img src=\"/images/chat/serviceHeader.png\"/><div class=\"right-speak-frame\"><div class=\"right-speak-box\"><div class=\"right-speak\" id=\""+sen+"-mychat_fa_"+mychat_fa+"\"></div></div></div><span class=\"chat-me\"></span></div>");
	
	var my=$("#"+sen+"-mychat_fa_"+mychat_fa);
	my.append(appendMsg(homeUserThisObj,homeUserThisObj,sendVal));
	
	scrollNouser(homeUserThisObj);
	$("#talkInputId").val("");
	
	setHao(homeUserThisObj);
	setContactlistUL();
}
function setMychatTime(from){
	var myDate=new Date();
	var sen=myDate.getFullYear() + '-'
		+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
		+ ('0' + myDate.getDate()).slice(-2)+" "+ ('0' + myDate.getHours()).slice(-2) +":"+('0' +myDate.getMinutes()).slice(-2) +":"+('0' +myDate.getSeconds()).slice(-2);
	
	$("#" + from + "-nouser").append("<div class=\"chat-time\"><span>" + sen + "</span></div>");
}
function setHao(from) {
	localData.set(from, $("#" + from + "-nouser").html());
}
function setContactlistUL(){
	localData.remove("contactlistUL");
	localData.set("${newUserName}contactlistUL", $("#contactlistUL").html());
}
</script>
</head>
<body>
<input type="hidden" id="mychat_stat" value="${adminStatus}"/>
<input type="hidden" id="mychat_usenam" value="${newUserName}"/>
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%=basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
	<input type="hidden" id="mychat_shop_title_emob_id">
	<div id="waitLoginmodal" class="modal hide fade" data-backdrop="static">
		<img src="${pageContext.request.contextPath }/easymob-webim1.0/img/waitting.gif">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在努力加载中...</img>
	</div>
	<div class="hx-content clearfix" id="content" style="display: none">
		<div class="hx-leftcontact" id="leftcontact">
			<div class="chat-search" id="form_id">
			</div>
			<div id="headerimg" class="leftheader">
				<span>
					<img src="${pageContext.request.contextPath }/easymob-webim1.0/img/head/header2.jpg" alt="logo" class="img-circle" width="60px" height="60px" style="margin-top: -40px;margin-left: 20px" />
				</span>
				<span id="login_user" class="login_user_title">
					<a class="leftheader-font" href="#"></a>
				</span>
			</div>
			<div id="leftmiddle">
			</div>
			<div id="contractlist11" style="height: 590px; overflow-y: auto; overflow-x: auto;">
				<div class="accordion" id="accordionDiv">
					<div class="accordion-group">
						<div class="accordion-heading">
							<a id="accordion12" class="accordion-toggle" data-toggle="collapse" data-parent="#accordionDiv" href="#collapseOne">所有业主 </a>
						</div>
						<div id="collapseOne" class="accordion-body collapse in">
							<div class="accordion-inner" id="contractlist">
								<ul id="contactlistUL" class="chat03_content_ul">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="mychat_user_title_emob_id">
		<div id="rightTop" style="height:0;"></div>
		<!-- 聊天页面 -->
		<div class="chatRight" style="width:643px;">
			<div id="chat01">
				<div class="chat01_title">
					<ul class="talkTo clearfix">
						<li id="talkTo"><a href="#"></a><span id="mychat_user_title"></span></li>
						<li class="chat-history">
							<div class="ask-record">
								<a id="advisory_id" onclick="upAdvisoryHistory();" href="javascript:void(0);" class="ask-history" style="right:90px;display:none;">结束客服 </a>
								<a onclick="isHistoryKeFuMap();" href="javascript:void(0);" class="ask-history">聊天历史 </a>
							</div>
						</li>
					</ul>
				</div>
				<div id="null-nouser" class="chat01_content" name="chat_pages_nouser"></div>
			</div>
			<div class="chat02">
				<div class="chat02_title">
					<a class="chat02_title_btn1" onclick="showEmotionDialog()" title="选择表情"></a>
					<label id="chat02_title_t"></label>
					<div id="wl_faces_box" class="wl_faces_box">
						<div class="wl_faces_content">
							<div class="title">
								<ul>
									<li class="title_name">常用表情</li>
									<li class="wl_faces_close"><span onclick='turnoffFaces_box()'>&nbsp;</span></li>
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
					<textarea id="talkInputId" style="resize: none;" onkeydown="if(event.keyCode==13){homeSendText();}"></textarea>
				</div>
				<div class="chat02_bar">
					<ul>
						<li style="right: 15px;"><img src="${pageContext.request.contextPath }/easymob-webim1.0/img/send_btn.jpg" onclick="homeSendText();" /></li>
					</ul>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
	<div class="chat-bottom-info">&nbsp;</div>
</body>
</html>