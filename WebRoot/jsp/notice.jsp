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
<title>公告通知_小间物业管理系统</title>


<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/notice.js"></script>

<script type="text/javascript">
$(function(){
	
	$("#fabu_submit").on("click",function(){
		fabuPost();
	});
});

function getFaBuList(){
	$("#gonggao_referesh").html("");
	var path = "<%= path%>/api/v1/communities/${communityId}/bulletin?pageNum=1&pageSize=6";
	$.post(path,function(data){
		console.info(data);
		var dataList = data.pageData;
		for(var i = 0 ; i < dataList.length ; i++){
			
			var tempText = dataList[i].bulletinText;
			var createTime = dataList[i].createTime;
			var myCreateDate = new Date(createTime*1000);
		//	console.info(myCreateDate.Format("yyyy-MM-dd "));
			var sysDate = myCreateDate.getFullYear() + "-" + DoubleOrOne(myCreateDate.getMonth()+1) + "-" + DoubleOrOne(myCreateDate.getDate());
			
			console.info(tempText);
			if($("#gonggao_referesh").html() == ""){
				$("#gonggao_referesh").html("<li><p class=\"notice-date\">"+sysDate+"</p><p class=\"notice-summary\"><a href=\"javascript:;\">"+tempText+"</a></p></li>");
			}else{
				$("#gonggao_referesh").append("<li><p class=\"notice-date\">"+sysDate+"</p><p class=\"notice-summary\"><a href=\"javascript:;\">"+tempText+"</a></p></li>");
			}
			
		}
	},"json");
}
function DoubleOrOne(dataText){
	if(dataText < 10){
		return "0"+dataText;
	}
	return dataText;
}

function fabuPost(){
var notice_action = $("#notice_action option:selected") .val();
	var action_value = $("#notice_action option:selected").val();
	var fabu_neirong = $("#fabu_neirong").val();
	var myJson = "{\"CMD_CODE\":"+notice_action+",\"content\":\""+fabu_neirong+"\" , \"action\":\""+action_value+"\"}";
	var path = "<%= path%>/api/v1/communities/${communityId}/users/getListUser/"+curUserId;
	$.post(path,myJson,function(data){
		console.info(data);
		if(data.status == 'yes'){
			addFuBuDB(fabu_neirong);
		}
		
	},"json");
}

function addFuBuDB(neirong){
	var myJson = "{ \"bulletinText\": \""+neirong+"\" , \"communityId\": 1 , \"adminId\":1}";
	var path =  "<%= path%>/api/v1/communities/${communityId}/bulletin/add";
	$.post(path , myJson , function(data){
		if(data.status == "yes"){
			
			$("#fabu_neirong").val("");
			
			getFaBuList();
		}
	},"json");
}
</script>
</head>
<body>
<div class="notice-public clearfix">
	<div class="notice-edit">
		<textarea rows="" cols="" id="fabu_neirong"></textarea>
	</div>
	<div class="notice-calendar" id="#calendarDiv">
			<div class="fabu_right">
			<select id="notice_action">
				<option value="100">普通通知</option>
				<option value="101">电费</option>
				<option value="102">燃气费</option>
				<option value="103">水费</option>
			</select>
			<input type="button" id="fabu_submit" value="发布" />
		</div>
	</div>
</div>
<div class="notice-title">已发布公告</div>
<div class="notice-list" >
	<ul id="gonggao_referesh">
		<li>
			<p class="notice-date">2014-12-13</p>
			<p class="notice-summary"><a href="javascript:;">ddd业主你们好！现本小区已进入装修阶段，进出苑区人员日益增多，管理处为加强小区人员进出管理，确保苑区的安全和谐，须对苑区住户办理业主卡 ...</a></p>
		</li>
		<li>
			<p class="notice-date">2014-12-11</p>
			<p class="notice-summary"><a href="javascript:;">为加强养犬管理，保障业主的健康及人身安全，维护小区环境及公共秩序，根据《xx市养犬管理规定》并结合xx小区的实际情况，现对小区养犬人提 ...</a></p>
		</li>
		<li>
			<p class="notice-date">2014-12-10</p>
			<p class="notice-summary"><a href="javascript:;">昨天本小区6座、8座各发生一起入室盗窃案件，犯罪分子是利用白天上班家中无人的时间使用技术手段开锁入室盗窃。据初步侦查，犯罪分子有可能 ...</a></p>
		</li>
	</ul>
</div>
</body>
</html>