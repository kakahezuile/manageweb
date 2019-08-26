<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>通知类编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript">
	function getSendMessageEdit(){
		
		var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments/getPayMessage";
	
		$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					console.info(data);
					data = data.info;
					var len = data.length;
					
					for(var i = 0 ; i < len ; i++){
						messageType = data[i].messageType;
						if(messageType == 1){
							document.getElementById("send_message_edit_last_textarea").value = "";
							document.getElementById("send_message_edit_last_textarea").value = data[i].messageValue;
						}else if(messageType == 2){
							document.getElementById("send_message_edit_next_textarea").value = "";
							document.getElementById("send_message_edit_next_textarea").value = data[i].messageValue;
						}
						
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function sendMessageEditPost(type){
		var path = "<%= path%>/api/v1/communities/${communityId}/users/admin/payments/addPayMessage";
		
		var sendMessageValue = "";
		
		if(type == 1){
			sendMessageValue = $("#send_message_edit_last_textarea").val();
		}else if(type == 2){
			sendMessageValue = $("#send_message_edit_next_textarea").val();
		}

		
		var myJson = {
			"messageType":type ,
			"messageValue":sendMessageValue
		};
	
		$.ajax({

				url: path,

				type: "POST",
				
				data: JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						alert("保存成功");
					}else{
						getSendMessageEdit();
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
</script>
  </head>
  <script type="text/javascript">
  

  </script>
  <body>
    	<div class="send_message_edit_main">
    		<div class="send_message_edit_top">
    			<div class="send_message_edit_top_top">
    				充值前通知
    			</div>
    			<div class="send_message_edit_top_center">
    			
    				<textarea style="width: 900px;height: 220px;" id="send_message_edit_last_textarea" >报告帮主，TTT点SSS分已收到FFF号楼UUU单元RRR电费充值请求，缴费MMM元，额度为NNN，我们将在三小时内完成充电，请帮主放心！</textarea>
    				
    			</div>
    			<div class="payment-send-first">
    				<a href="javascript:void(0);" id="send_message_edit_last_sub" onclick="sendMessageEditPost(1);">发送</a>
    			</div>
    			
    		</div>
    		<div class="send_message_edit_center">
    			<div class="send_message_edit_center_top">
    				充值后通知
    			</div>
    			
    			<div class="send_message_edit_center_center">
    				<textarea style="width: 900px;height: 220px;" id="send_message_edit_next_textarea" >报告帮主，YYY月DDD日TTT为贵府FFF号楼UUU单元RRR室成功续电NNN度，充值前额度N1，充值后额度N2，请帮主放心使用！</textarea>
    			</div>
    			<div class="payment-send-last">
    				<a href="javascript:void(0);" id="send_message_edit_next_sub" onclick="sendMessageEditPost(2);">发送</a>
    			</div>
    		</div>
    	</div>
  </body>
</html>
