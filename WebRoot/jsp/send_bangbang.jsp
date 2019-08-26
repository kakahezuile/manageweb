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
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>发放帮帮券_间物业管理系统</title>

	
<script type="text/javascript">
	
	var bangBangMap = {};
	
	function getBonusList(){

		var path = "<%= path%>/api/v1/communities/${communityId}/bonuses";
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					console.info(data);
					if(data.status == "yes"){
						bangBangGetUserCount();
						$("#bangbang_select").html("");
						data = data.info;
						for(var i = 0 ; i < data.length ; i++){
							bonusId = data[i].bonusId;
							bonusName = data[i].bonusName;
							bonusDetail = data[i].bonusDetail;
							bangBangMap[bonusId] = data[i].bonusPar;
							$("#bangbang_select").append("<option value = \""+bonusId+"\">"+bonusName+" "+bonusDetail+"</option>");
						/*	if(i == 0){
								document.getElementById("bangbang_send_par").value = "";
								document.getElementById("bangbang_send_par").value = data[0].bonusPar;
								$("#bangbang_send_par_sum").html("");
								$("#bangbang_send_par_sum").html(data[0].bonusPar);
								
								$("#bangbang_send_bangbang_name").html("");
								$("#bangbang_send_bangbang_name").html(bonusName+ " "+bonusDetail);
							}*/
						}
					}
					
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function bangBangGetUserCount(){
		
		var path = "<%= path%>/api/v1/communities/${communityId}/bonuses/getUserCount";
	
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						$("#bangbang_send_user_sum").html("");
						$("#bangbang_send_user_sum").html(data);
					}
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function bangbang_select_onchange(){
		var send_bangbang_select_value = $("#bangbang_select option:selected").val();
		document.getElementById("bangbang_send_par").value = bangBangMap[send_bangbang_select_value];
		$("#bangbang_send_par_sum").html("");
		$("#bangbang_send_par_sum").html(bangBangMap[send_bangbang_select_value]);
		var bangbang_send_bangbang_name = $("#bangbang_select option:selected").html();
		$("#bangbang_send_bangbang_name").html("");
		$("#bangbang_send_bangbang_name").html(bangbang_send_bangbang_name);
		
		
	}
	
	function send_bangbang_submit(){
	    bangbang_send_bangbang_sub();
		var path = "<%= path%>/api/v1/communities/${communityId}/bonuses/userBonus";
		
		bangbang_send_start_time = $("#bangbang_send_start_time").val();
		
		if(bangbang_send_start_time == ""){
			alert("开始时间不能为空");
			return false;
		}
		
		bangbang_send_end_time = $("#bangbang_send_end_time").val();
		
		if(bangbang_send_end_time == ""){
			alert("结束时间不能为空");
			return false;
		}
		
		var bangbang_message_radio = $(':radio[name="bangbang_message_radio"]:checked').val(); 
		
		var send_bangbang_select_value = $("#bangbang_select option:selected").val();
	   var	messageValue = $("#send_bangbang_message_value").val();
		if(messageValue==""){
		messageValue="活动：报告帮主，在本次活动中，您获得了一张价值为5元的帮帮券一张，您可以通过点击屏幕下方“我的”中的“帮帮券”一项进行查看，还请帮主速速使用！ ";
		}
		
		var myJson = {
			"bonusId":send_bangbang_select_value ,
			"startTimeStr":bangbang_send_start_time ,
			"expireTimeStr":bangbang_send_end_time ,
			"messageValue":messageValue ,
			"messageIsSend":bangbang_message_radio
		};
		
		$.ajax({

				url: path,

				type: "POST",
				
				data:JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						alert("成功");
					}
					
				},

				error:function(er){
			
					alert(er);
			
				}

			});	
	}
</script>
</head>
<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="./public/bangbang_left.jsp"/>
			<div class="express-right-body">
				<div class="repair-title-line">
					<p class="bangbang-ticket-title">发放帮帮券</p>
				</div>
				<div class="send_bangbang">
					<div>
						<span>类型面额</span>
				    	<select name="bangbang_select" id="bangbang_select" onchange="bangbang_select_onchange();">
			    					
			    		</select>
					</div>
					<div>
						<span>使用期限</span>
						<input type="text" value="" id="bangbang_send_start_time" placeholder="开始时间" name="bangbang_send_start_time" />
						<label>至</label>
						<input type="text" value="" id="bangbang_send_end_time" placeholder="结束时间" name="bangbang_send_end_time" />
					</div>
					<div>
						<span>发放对象</span>
						<input type="radio" value="1" name="bangbang_send_radio" id="bangbang_send_radio" checked="checked" /><label>全部用户</label>
					</div>
					<div>
						<div>
							<span>是否显示消息通知</span>
							<input type="radio" value="1" name="bangbang_message_radio" id="bangbang_message_radio" checked="checked" /><label>是</label>
							<input type="radio" value="0" name="bangbang_message_radio" id="bangbang_message_radio" /><label>否</label>
						</div>
						<div>
							<textarea class="send_bangbang_message" id="send_bangbang_message_value" placeholder="活动：报告帮主，在本次活动中，您获得了一张价值为5元的帮帮券一张，您可以通过点击屏幕下方“我的”中的“帮帮券”一项进行查看，还请帮主速速使用！ "></textarea>
						</div>
					</div>
					<div class="send_bangbang_btn">
						<a href="javascript:void(0);" onclick="send_bangbang_submit();" id="send_bangbang_sutmit" name="send_bangbang_sutmit">立即发放</a>
					</div>
				</div>
				<!--
				<div>
					<table>
			    		<tr>
			    			<td>发送</td>
			    		</tr>
			    		
			    		<tr>
			    			<td>
			    				<select name="bangbang_select" id="bangbang_select" onchange="bangbang_select_onchange();">
			    					
			    				</select>
			    			</td>
			    		</tr>
			    		
			    		<tr>
			    			<td colspan="2">发放明细</td>
			    			<td colspan="3">使用期限</td>
			    		</tr>
			    		
			    		<tr>
			    			<td>发放面额<input type="text" value="2" id="bangbang_send_par" style="width: 50px;" name="bangbang_send_par" readonly="readonly" /></td>
			    		
			    			
			    			<td>开始时间</td>
			    			<td><input type="text" value="" id="bangbang_send_start_time" name="bangbang_send_start_time" /></td>
			    			
			    			<td>结束时间</td>
			    			<td><input type="text" value="" id="bangbang_send_end_time" name="bangbang_send_end_time" /></td>
			    		</tr>
			    		
			    		<tr>
			    			<td>发放对象</td>
			    		</tr>
			    		
			    		<tr>
			    			<td><input type="radio" value="1" name="bangbang_send_radio" id="bangbang_send_radio" checked="checked" />全部用户</td>
			    		</tr>
			    		
			    		<tr>
			    			<td>发放人数</td>
			    			<td id="bangbang_send_user_sum">加载中...</td>
			    			
			    			<td>面额</td>
			    			<td id="bangbang_send_par_sum"></td>
			    			
			    			<td id="bangbang_send_bangbang_name">通用卷</td>
			    		</tr>
			    		
			    		<tr>
			    			<td>是否显示消息通知</td>
			    		</tr>
			    		
			    		<tr>
			    			<td><input type="radio" value="1" name="bangbang_message_radio" id="bangbang_message_radio" checked="checked" />是 <input type="radio" value="0" name="bangbang_message_radio" id="bangbang_message_radio" />否</td>
			    		</tr>
			    		
			    		<tr>
			    			<td>
			    				<textarea rows="10" cols="30" name="send_bangbang_message_value" id="send_bangbang_message_value"></textarea>
			    			</td>
			    		</tr>
			    		
			    		<tr>
			    			<td><input type="button" value="立即发送" onclick="send_bangbang_submit();" id="send_bangbang_sutmit" name="send_bangbang_sutmit" /></td>
			    		</tr>
			    	</table>
				</div>
			-->
			</div>
		</div>
	</section>
  </body>
</html>
