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

<script type="text/javascript">

  var curUserId = window.parent.document
			.getElementById("cur_user_id_index").value;
function fabuPost(){
    var notice_action = $("#notice_action_all option:selected") .val();
	var action_value = $("#notice_action_all option:selected").val();
	var fabu_neirong = $("#bulletin_text_notice").val();
	var theme_notice_all=$("#theme_notice_all").val();
	
	if(fabu_neirong==""){
	 alert("通知内容 不能为空！！");
	}
	if(theme_notice_all==""){
	 alert("标题 不能为空！！");
	}
	var myJson = "{\"CMD_CODE\":"+notice_action+",\"content\":\""+fabu_neirong+"\" , \"action\":\""+action_value+"\",\"title\":\""+theme_notice_all+"\"}";
	var path = "<%= path%>/api/v1/communities/${communityId}/users/getListUser/"+curUserId;
	$.post(path,myJson,function(data){
		console.info(data);
		if(data.status == 'yes'){
			addFuBuDB(fabu_neirong,theme_notice_all);
		}
		
	},"json");
}

function addFuBuDB(neirong,theme_notice_all){
	var myJson = "{ \"bulletinText\": \""+neirong+"\" , \"communityId\":"+${communityId}+" , \"adminId\":"+
	${adminId}+",\"theme\":\""+theme_notice_all+"\",\"senderObject\":\"all1231\"}";
	var path =  "<%= path%>/api/v1/communities/${communityId}/bulletin/add";
	$.post(path , myJson , function(data){
		if(data.status == "yes"){
			
			$("#bulletin_text_notice").val("");
			
			//getFaBuList();
		}
	},"json");
}
 $(function(){
  $("#bulletin_text_notice").keyup(function(){
   var len = $(this).val().length;
   if(len > 799){
   alert("公告最多800个汉字");
    $(this).val($(this).val().substring(0,800));
   }
  });
 });
 
 function notice_action_all_clike(){
 	var action_value = $("#notice_action_all option:selected").val();
 	if(action_value=="100"){
 	 document.getElementById("theme_notice_all").value="";
 	  document.getElementById("theme_notice_all").disabled=false;
 	}else{
 	 document.getElementById("theme_notice_all").disabled="true";
 	 document.getElementById("theme_notice_all").value="缴费通知";
 	
 	}
   
 }
</script>
</head>
<body>
	
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/notice-left.jsp"></jsp:include>
			<div class="notice-right-body">
				<div class="repair-title-line">
					<p class="notice-title">通知全部用户</p>
				</div>
				<div class="notice-title-input">
					<div class="fabu_right">
						<span>通知类型</span>
						<select id="notice_action_all" onclick="notice_action_all_clike();">
							<option value="100">普通通知</option>
							<option value="101">电费</option>
							<option value="102">燃气费</option>
							<option value="103">水费</option>
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
					<a href="javascript:;" id="fabu_submit" onclick="fabuPost();">确定发送</a>
				</div>
			</div>
		</div>
	</section>
</body>
</html>