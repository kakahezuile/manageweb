<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Properties properties = PropertyTool
			.getPropertites("/configure.properties");
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
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
		<meta name="keywords" content="小间物业,小间物业管理系统" />
		<meta name="Description" content="小间物业,小间物业管理系统" />
		<title>通知指定用户_小间物业管理系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
		<script type="text/javascript">
		 var curUserId = 111;
			var communityId = window.parent.document
			.getElementById("community_id_index").value;
			var adminId=window.parent.document
			.getElementById("admin_id_index").value;
	function selectUserList() {
		$("#notice-search-result").show();
		var phone = document.getElementById("phone_room").value;
		var path = "<%=path%>/api/v1/communities/"+communityId+"/users/selectUser?phone="
				+ phone;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var repair_overman = $("#user_list");
				repair_overman.empty();
				for ( var i = 0; i < data.length; i++) {
				
                  var userList="<li id=\"add_"+data[i].emobId+"\" onclick=addObject(\""+data[i].userId+"\",\""+data[i].emobId+"\",\""+data[i].nickname+"\",\""+data[i].username+"\",\""+data[i].userFloor+"\",\""+data[i].userUnit+"\",\""+data[i].room+"\");><div class=\"notice-item\"><img 	src=\""+data[i].avatar+"\">";
                  userList+="<span>"+data[i].nickname+"</span>";
                  userList+="<span>"+data[i].username+"</span>";
                  userList+="<span>"+data[i].userFloor+data[i].userUnit+data[i].room+"</span>";
                  userList+="<a href=\"javascript:;\" >&nbsp;</a></div></li>";
                  repair_overman.append(userList);
				}
				

			},
			error : function(er) {
			}
		});
	}
	function getfocus(){
		document.getElementById("phone_room").style.width = "415px";
		document.getElementById("phone_room").placeholder="搜索手机号/房间号   如：182XXXXXXXX/501";
	}
	function lostfocus(){
		if(document.getElementById("phone_room").value==""){
			document.getElementById("phone_room").style.width = "120px";
			document.getElementById("phone_room").placeholder="搜索指定用户";
		}
	}

var isSpecialSending = false;
function publishPost(){
	if (isSpecialSending) {
		return;
	}
	isSpecialSending = true;
	
	var fabu_neirong = $("#bulletin_text_notice_special").val();
	if(!fabu_neirong){
		alert("通知内容 不能为空！！");
		isSpecialSending = false;
		return;
	}
	var theme_notice_all=$("#theme_notice_special_special").val();
	if(!theme_notice_all){
		alert("标题 不能为空！！");
		isSpecialSending = false;
		return;
	}
	
	var notice_action = $("#notice_action_special option:selected").val();
	var action_value = $("#notice_action_special option:selected").val();
	var emob=document.getElementsByName("emob_id_notice");
	var usId=document.getElementsByName("user_id_notice");
	var em="";
	var us="";
	for ( var i = 0; i < usId.length; i++) {
		us+="user_id="+usId[i].value+"&";
	}
	for ( var i = 0; i < emob.length; i++) {
		em+="emobId="+emob[i].value+"&";
	}
	var myJson = {
		"CMD_CODE":notice_action ,
		"content":fabu_neirong ,
		"action":action_value ,
		"title":theme_notice_all 
	};
	//环信id没有
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/getExceptionUser/11?"+us;
	scheduleAll();
	
	$.ajax({
		url : path,
		type : "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			if(data.status == 'yes'){
				addPublishDB(fabu_neirong,theme_notice_all,em,data.info);
			}else{
				onScheduleAll();
			}
			isSpecialSending = false;
		},
		error : function(er) {
			onScheduleAll();
			isSpecialSending = false;
		}
	});
}

function addPublishDB(neirong,theme_notice_all,em,messageId){
	var myJson = {
		"bulletinText":neirong ,
		"communityId":communityId ,
		"adminId":adminId ,
		"theme":theme_notice_all,
		"senderObject":"portion", 
		"messageId":messageId
	};

	var path =  "<%=path%>/api/v1/communities/"+communityId+"/bulletin/inform?"+ em;
	$.ajax({
		url : path,
		type : "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			onScheduleAll();
			if (data.status == "yes") {
				$("#bulletin_text_notice_special").val("");
				var repair_overman = $("#add_user_list");
				repair_overman.empty();
				var repair_overman2 = $("#user_list");
				repair_overman2.empty();
				closePreview();
				alert("发送成功");
				location.href="<%=path %>/jsp/estate/notice/notice_special.jsp";
			}
		},
		error : function(er) {
			onScheduleAll();
		}
	});
}

	function addObject(userId, emobId, nickname, username, userFloor,userUnit,room) {
		noticeUserListShow();
		var obj = document.getElementById("notice_special_"+emobId);
		var addObj=document.getElementById("add_"+emobId);
		
		if(addObj!=null){
		addObj.parentNode.removeChild(addObj);
		}
		if (obj != null) {
			obj.parentNode.removeChild(obj);
		}
		var repair_overman = $("#add_user_list");
		//repair_overman.remove();
		//   $("add_user_list").remove("li[class=="+emobId+"]");
		var users = "<li id=\"notice_special_"+emobId+"\"><input type=\"hidden\" name=\"user_id_notice\" value=\""+userId+"\"/><input type=\"hidden\" name=\"emob_id_notice\" value=\""+emobId+"\"/><span class=\"user-nickname\">"
				+ nickname + "</span>";
		users += "<span>" + username + "</span>";
		users += "<span>" + userFloor + userUnit + room + "</span>";
		users += "<a href=\"javascript:;\" onclick=\"delObject('" + emobId
				+ "');\">&nbsp;</a></li>";
				
		repair_overman.append(users);
	}

	function delObject(emobId) {
		var obj = document.getElementById("notice_special_"+emobId);
		if (obj != null) {
			obj.parentNode.removeChild(obj);
			if($("#add_user_list").find("li").length==0){
				$("#notice-user-list").hide();
			}
		}
		
	}
	function emptyObject(emobId) {
			var repair_overman = $("#add_user_list");
			repair_overman.empty();
			noticeUserListHidden();
	}

/*	function checkNotice() {
	    var t1=document.getElementById("bulletin_text_notice_special");
		var regC = /[^ -~]+/g;
		var regE = /\D+/g;
		var str = t1.value;

		if (regC.test(str)) {
			t1.value = t1.value.substr(0, 800);
		}

		if (regE.test(str)) {
			t1.value = t1.value.substr(0, 1200);
		}
	}*/
	
$(function(){
  $("#bulletin_text_notice_special").keyup(function(){
   var len = $(this).val().length;
   if(len >199){
   alert("公告最多200个汉字");
    $(this).val($(this).val().substring(0,200));
   }
  });
 });
  function notice_action_special_clike(){
    var action_value = $("#notice_action_special option:selected").val();
 	if(action_value=="100"){
 	  document.getElementById("theme_notice_special_special").value="";
 	  document.getElementById("theme_notice_special_special").disabled=false;
 	}else if(action_value=="107"){
 	  document.getElementById("theme_notice_special_special").disabled="true";
 	  document.getElementById("theme_notice_special_special").value="帮主来啦!";
 	}
   
 }
 
 function notice_special_select_floor() {
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/selectUserFloor";
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data=data.info;
				var repair_overman = $("#notice_special_select_floor");
				repair_overman.empty();
				 repair_overman.append("<option value=\"\">请选择楼</option>");
				for ( var i = 0; i < data.length; i++) {
                 
                  if(data[i]!=null&&data[i]!=""){
                    var floor="<option value=\""+data[i]+"\">"+data[i]+"</option>";
                    repair_overman.append(floor);
                  }
                 
				}
				

			},
			error : function(er) {
			}
		});
 }
 
 function notice_special_select_unit() {
    var floor=$("#notice_special_select_floor").val();
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/selectUserUnit?userFloor="+floor;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data=data.info;
				var repair_overman = $("#notice_special_select_unit");
				repair_overman.empty();
				 repair_overman.append("<option value=\"\">请选择单元</option>");
				for ( var i = 0; i < data.length; i++) {
                  var floor="<option value=\""+data[i]+"\">"+data[i]+"</option>";
                  repair_overman.append(floor);
				}
				

			},
			error : function(er) {
			}
		});
 }
 function notice_special_select_room() {
   
    var floor=$("#notice_special_select_floor").val();
    var unit=$("#notice_special_select_unit").val();
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/selectUserRoom?userFloor="+floor+"&userUnit="+unit;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
			data=data.info;
				var repair_overman = $("#notice_special_select_room");
				repair_overman.empty();
				 repair_overman.append("<option value=\"\">请选择房间</option>");
				for ( var i = 0; i < data.length; i++) {
                  var floor="<option value=\""+data[i]+"\">"+data[i]+"</option>";
                  repair_overman.append(floor);
				}
				

			},
			error : function(er) {
			}
		});
 }
 
 function searchFloorUnitRoom() {
     scheduleAll();
	 var floor=$("#notice_special_select_floor").val();
     var unit=$("#notice_special_select_unit").val();
     var room=$("#notice_special_select_room").val();
     noticeUserListShow();
     var path = "<%=path%>/api/v1/communities/"+communityId+"/users/selectFloorUnitUser?room="
				+ room+"&userFloor="+floor+"&userUnit="+unit;
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				onScheduleAll();
				data = data.info;
				for ( var i = 0; i < data.length; i++) {
				
                 addObject(data[i].userId,data[i].emobId, data[i].nickname,data[i].username,data[i].userFloor,data[i].userUnit,data[i].room);
				}
			

			},
			error : function(er) {
			    onScheduleAll();
			}
		});
 }
 
 function noticeUserListShow(){
 	$("#notice-user-list").show();
 }
  function noticeUserListHidden(){
 	$("#notice-user-list").hide();
 }
 
  function scheduleAll(){
		$("#schedule_all").attr("style","display:block");
		$("#bg_schedule").attr("style","display:block");
		
	}
	
	function onScheduleAll(){
		$("#schedule_all").attr("style","display:none");
		$("#bg_schedule").attr("style","display:none");
	}
</script>
<style type="text/css">
h6{color:blue;}
textarea{resize:none;}
#word{color:red;}
</style>
	</head>
	<body>
	
	
	 	<div class="loadingbox" id="schedule_all" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="bg_schedule" style="display: none;">&nbsp;</div>
	
	
	
		<section>

			<div class="content clearfix">
				<jsp:include flush="true" page="../common/notice-left.jsp"></jsp:include>
				<div class="notice-right-body">
					<div class="repair-title-line">
						<p class="notice-title">
							通知指定用户
						</p>
						<p class="notice-search">
							<input type="text" placeholder="搜索指定用户" id="phone_room" onfocus="getfocus()" onkeydown="if(event.keyCode==13){selectUserList();}"   onblur="lostfocus()"/><a href="javascript:;" onclick="selectUserList();">搜索</a>
						</p>
						<div class="notice-search-result" id="notice-search-result" style="display:none;">
							<ul id="user_list">
							</ul>
						
						</div>
					</div>
					
					<div id="notice-user-list" style="display:none;">
						<div class="notice-user clearfix">
							<span class="notice-user-title">通知对象</span>
							<ul id="add_user_list">
							</ul>
		
		     			</div>
		     			<div class="clear-notice-list" id="clear-notice-list"><a href="javascript:;" onclick="emptyObject();">清空通知列表</a></div>
						
					</div>
					
					<div class="notice-title-input">
						<span>通知类型</span>
						<select id="notice_action_special" onclick="notice_action_special_clike();">
						<option value="100"> 普通通知 </option>
						<option value="107"> 帮主通知 </option>
							<!-- <option value="100">
								普通通知
							</option>
							<option value="101">
								电费
							</option>
							<option value="102">
								燃气费
							</option>
							<option value="103">
								水费
							</option> -->
						</select>
					</div>
					<div class="notice-group clearfix">
						<span>选择用户</span>
						<select id="notice_special_select_floor" onFocus="notice_special_select_floor();">
							
							<option value=""  selected = "selected">
								请选择楼
							</option>
						</select>
						<select id="notice_special_select_unit" onFocus="notice_special_select_unit();">
							<option value=""  selected = "selected">
								全部
							</option>
							
						</select>
						<select id="notice_special_select_room" onFocus="notice_special_select_room();">
							<option value=""  selected = "selected">
								全部
							</option>
							
						</select>
						<a href="javascript:;" onclick="searchFloorUnitRoom();">添加</a>
					</div>
					
					<div class="notice-title-input">
						<span>通知标题</span>
						<input type="text" id="theme_notice_special_special" placeholder="输入通知的标题" />
					</div>
					<div class="notice-content-input">
						<span>通知内容</span>
						<textarea placeholder="输入通知的内容" id="bulletin_text_notice_special"></textarea>
					</div>
					<div class="notice-send">
						<a href="javascript:showPreview();" id="">发送</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</div>
			</div>
		</section>
		<div class="notice-preview" id="notice-preview" style="display:none;">
			<div class="notice-close"><a href="javascript:closePreview();">&nbsp;</a></div>
			<div class="notice-head-title" id="pre_notice_all">社区通知</div>
			<div class="notice-body-content" >
				<textarea rows="" cols="" id="pre_text_notice">各位帮主：小区近期将更换车辆门禁系统，由原来保安识别停车证改为全自动拍照车辆门禁，让您开车进出更加方便，同时也能杜绝外来车辆混入。</textarea>
			</div>
			<div class="notice-date-right" id="pre-time">2015-07-02 08:44</div>
			<div class="notice-submit">
				<a href="javascript:;" id="fabu_submit" onclick="publishPost();">确定发送</a>
			</div>
		</div>
		<div class="public-black" style="display:none;opacity:0.75;"></div>
	</body>
	<script type="text/javascript">
	notice_left("notice_notice_special");
	
	function showPreview(){
	var now = new Date(); 
	var myDate = new Date();
	var date=myDate.getFullYear().toString() +"-"+ (myDate.getMonth() + 1).toString() +"-"+ (myDate.getDate()).toString()+"  "+(myDate.getHours()).toString()+":"+(myDate.getSeconds()).toString();
	var title = $("#theme_notice_special_special").val();
	var content = $("#bulletin_text_notice_special").val();
	$("#notice-preview").show();
	$(".public-black").show();
	$("#pre_notice_all").html(title);
	$("#pre_text_notice").html(content);
	$("#pre-time").html(date);
}
function closePreview(){
	$("#notice-preview").hide();
	$(".public-black").hide();
}
	</script>
</html>