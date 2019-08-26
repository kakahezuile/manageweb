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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>通知全部用户_小间物业管理系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>

<script type="text/javascript">
 var curUserId = window.parent.document
			.getElementById("cur_user_id_index").value;
	var communityId = window.parent.document
			.getElementById("community_id_index").value;
			var adminId=window.parent.document
			.getElementById("admin_id_index").value;

var isAllSending = false;
function fabuPost(){
	if (isAllSending) {
		return;
	}
	isAllSending = true;
	
	var fabu_neirong = $("#bulletin_text_notice").val();
	if(fabu_neirong==""){
		alert("通知内容 不能为空！！");
		isAllSending = false;
		return;
	}
	var theme_notice_all=$("#theme_notice_all").val();
	if(theme_notice_all==""){
		alert("标题 不能为空！！");
		isAllSending = false;
		return;
	}
	
	var notice_action = $("#notice_action_all option:selected").val();
	var action_value = $("#notice_action_all option:selected").val();
	var myJson = {
		"CMD_CODE":notice_action ,
		"content":fabu_neirong ,
		"action":action_value ,
		"title":theme_notice_all 
	};
	var path = "<%= path%>/api/v1/communities/"+communityId+"/users/getListUser/11";
	scheduleAll();
	
	$.ajax({
		url : path,
		type : "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			if(data.status == 'yes'){
				addFuBuDB(fabu_neirong,theme_notice_all,data.info);
			}else{
				onScheduleAll();
			}
			isAllSending = false;
		},
		error : function(er) {
			onScheduleAll();
			isAllSending = false;
		}
	});
}

function addFuBuDB(neirong,theme_notice_all,messageId){
       var myJson = {
			"bulletinText":neirong ,
			"communityId":communityId ,
			"adminId":adminId ,
			"theme":theme_notice_all,
			"senderObject":"all" ,
			"messageId":messageId
		};
	//var myJson = "{ bulletinText: "+neirong+" , communityId:"+communityId+" , adminId:"+
	//adminId+",theme:"+theme_notice_all+",senderObject:all}";
	var path =  "<%= path%>/api/v1/communities/"+communityId+"/bulletin/add";
	
		$.ajax({
			url : path,
			type : "POST",
		data:JSON.stringify(myJson),
			dataType : 'json',
			success : function(data) {
				if(data.status == "yes"){
						
						$("#bulletin_text_notice").val("");
					
						closePreview();
						alert("发送成功");
						//getFaBuList();
						location.href="<%=path %>/jsp/estate/notice/notice_all.jsp";
					}else{
						}
					onScheduleAll();
			},
			error : function(er) {
			   onScheduleAll();
			}
		});
	/*$.post(path , myJson , function(data){
	  
		if(data.status == "yes"){
			
			$("#bulletin_text_notice").val("");
		
		
			alert("发送成功");
			//getFaBuList();
			location.href="<%=path %>/jsp/estate/notice/notice_all.jsp";
		}
		//alert(data.status);
	},"json");*/
	
}
 $(function(){
  $("#bulletin_text_notice").keyup(function(){
   var len = $(this).val().length;
   if(len > 199){
   alert("公告最多200个汉字");
    $(this).val($(this).val().substring(0,200));
   }
  });
 });
 
 function notice_action_all_clike(){
 	var action_value = $("#notice_action_all option:selected").val();
 	if(action_value=="100"){
 	 document.getElementById("theme_notice_all").value="";
 	  document.getElementById("theme_notice_all").disabled=false;
 	}else if(action_value=="107"){
 	 document.getElementById("theme_notice_all").disabled="true";
 	 document.getElementById("theme_notice_all").value="帮主来啦!";
 	
 	}
   
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
					<p class="notice-title">通知全部用户</p>
				</div>
				<div class="notice-title-input"> 
					<div class="fabu_right">
						<span>通知类型</span>
						<select id="notice_action_all" onclick="notice_action_all_clike();">
							<option value="100">普通通知</option>
							<option value="107">帮主通知</option>
						</select>
					</div>
				</div>
			<div class="notice-title-input">
					<span>通知标题</span>
					<input type="text" id="theme_notice_all" placeholder="输入通知的标题"/>
				</div>
				<div class="notice-content-input">
					<span>通知内容</span>
					<textarea placeholder="输入通知的内容" id="bulletin_text_notice"></textarea>
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
			<a href="javascript:;" id="fabu_submit" onclick="fabuPost();">确定发送</a>
		</div>
	</div>
	<div class="public-black" style="display:none;opacity:0.75;"></div>
</body>
<script type="text/javascript">
notice_left("notice_notice_all");
function showPreview(){
	var now = new Date(); 
	var myDate = new Date();
	var date=myDate.getFullYear().toString() +"-"+ (myDate.getMonth() + 1).toString() +"-"+ (myDate.getDate()).toString()+"  "+(myDate.getHours()).toString()+":"+(myDate.getSeconds()).toString();
	var title = $("#theme_notice_all").val();
	var content = $("#bulletin_text_notice").val();
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