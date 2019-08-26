<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% String path = request.getContextPath();
	path = "http://localhost:8080"+path; %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告通知页</title>
<script type="text/javascript">


		

		
$(function(){
	getFaBuList();
	$("#fabu_submit").on("click",function(){
		fabuPost();
	});
});
// 公文编号 006525 2015.1.9 长沙市 芙蓉区 人民东路168号 东南亚 一个包裹   1068
function fabuPost(){

	alert(1111);
	var fabu_neirong = $("#fabu_neirong").val();
	var notice_action = $("#notice_action option:selected") .val();
	alert(notice_action);
	var myJson = "{ \"CMD_CODE\": "+notice_action+", \"content\":fabu_neirong }";
	var path = "<%= path%>/api/v1/communities/1/users/getListUser/"+curUserId;
	$.post(path,myJson,function(data){
		console.info(data);
		if(data.status == 'yes'){
			addFuBuDB(fabu_neirong);
		}
		
	},"json");
}

function getFaBuList(){
	$("#gonggao_referesh").html("");
	var path = "<%= path%>/api/v1/communities/1/bulletin";
	$.post(path,function(data){
		console.info(data);
		for(var i = 0 ; i < data.length ; i++){
			
			var tempText = data[i].bulletinText;
			console.info(tempText);
			if($("#gonggao_referesh").html() == ""){
				$("#gonggao_referesh").html("<p>"+tempText+"</p></br>");
			}else{
				$("#gonggao_referesh").append("<p>"+tempText+"</p></br>");
			}
			
		}
	},"json");
}

function addFuBuDB(neirong){
	var myJson = '{ "bulletinText": '+neirong+', "communityId": 1 , "adminId":1}';
	var path =  "<%= path%>/api/v1/communities/1/bulletin/add";
	$.post(path , myJson , function(data){
		if(data.status == "yes"){
			if($("#gonggao_referesh").html() == ""){
				$("#gonggao_referesh").html("<p>"+neirong+"</p></br>");
			}else{
				$("#gonggao_referesh").append("<p>"+neirong+"</p></br>");
			}
			$("#fabu_neirong").val("");
		}
	},"json");
}


</script>

<style type="text/css">
.fabu_neirong{
	height: 250px;
	width: 70%;
}

.gonggao_referesh{
	margin-left: 10px;
	margin-top: 10px;
}
.fabu_left{

	width: 70%;
	height: 100%;
	float: left;
	margin: 5px 0px 0px 5px;
}
.fabu_right{
	width: 29%;
	height: 100%;
	float: left;
}
.fabu_top{
	width: 100%;
	height: 250px;
}
.yifagonggao_title{
	width: 100%;
	height: 15px;
	float: left;
}

.heng_xian{
	width: 97%;
	height: 2px;
	float: left;
	margin: 15px 0 15px 0;
}

.gonggao_referesh{
	width: 95%;
	height: 300px;
	float: left;
	overflow:auto; 

	padding-top: 30px;
	margin: 30px 10px 0 30px;
}
.yifagonggao_title{
	margin-left: 5px;
}
</style>
</head>
<body>
	<div class="gonggao_top">
		<div class="fabu_left">
			<textarea rows="30" cols="120" id="fabu_neirong" class="fabu_neirong" ></textarea>
		</div>
		
		<div class="fabu_right">
			<input type="button" id="fabu_submit" value="发布" />
		</div>
	
	</div>
		
	<div class="yifagonggao_title">
		已发公告:
	</div>
	
	<div class="heng_xian">
		<hr style="height:2px;border:none;border-top:2px solid #ECEDE8; margin: 0px 5px 0 5px" />
	</div>
			
			<div id="gonggao_referesh" class="gonggao_referesh" >
				
			</div>
	
</body>
</html>