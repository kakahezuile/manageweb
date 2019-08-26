<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息记录页</title>

<script type="text/javascript">

	var complaintsMaxTime = null;
	
	var complaintsMinTime = null;
	
	var complaintsEmobId;
	
	var complaintsEmobIdFrom;
	var complaintsEmobIdTo;

	function getOrderMessages(orderId , emobIdFrom , emobIdTo , occurTime){ 
	
		complaintsEmobId = emobIdFrom; 
		
		var path = "<%= path%>/api/v1/communities/${communityId}/complaints/webIm/history?emobIdUser="+emobIdFrom+"&emobIdShop="+emobIdTo+"&occurTime="+occurTime+"&status=this";
		
		var msgType = "";
		
		var msg = "";
		
		var url = "";
		
		var filename = "";
		
		var secret = "";
		
		var videoLength = 0;
		
		var timestamp = 0;
		
		var messageFrom = "";
		
		var messageTo = "";
		
		var fromName = "";
		
		var toName = "";
		
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
				var dataList = data;
			
				console.info(dataList);
				for(var i = 0 ; i < dataList.length ; i++){
				
					
					msgType = dataList[i].msgType;
					msg = dataList[i].msg;
					url = dataList[i].url;
					filename = dataList[i].filename;
					secret = dataList[i].secret;
					videoLength = dataList[i].videoLength;
					timestamp = dataList[i].timestamp;
					messageFrom = dataList[i].messageFrom;
					messageTo = dataList[i].messageTo;
					fromName = dataList[i].fromName;
					toName = dataList[i].toName;
					outTime = getSmpFormatDateByLong(data[i].timestamp);
					nickName = data[i].nickname;
					if(i == 0){
						complaintsMinTime = timestamp;
					}
					
					if(i == len-1){
						complaintsMaxTime = timestamp;
					}
					$("#message_table").append("<tr><td class=\"complaintsTextCenter\">— "+outTime+" —</td></tr>");
					if(msgType == "txt"){
						if(emobIdFrom == messageFrom){	
							$("#message_table").append("<tr><td align=\"left\" class=\"complaintsTextLeft\"><font class=\"complaintsUserName\">"+nickName+"</font>："+msg+"</td></tr>");
						}else{
							$("#message_table").append("<tr><td align=\"right\" class=\"complaintsTextRight\">"+msg+"： <font class=\"complaintsUserName\">"+nickName+"</font></td></tr>");
						}
					}else if(msgType == "audio"){
					
						if(emobIdFrom == messageFrom){	
							$("#message_table").append("<tr><td align=\"left\" class=\"complaintsTextLeft\"><font class=\"complaintsUserName\">"+nickName+"</font>： <video controls=\"controls\" width=\"20\" height=\"20\" src=\""+url+"\" > </video></td></tr>");
						}else{
							$("#message_table").append("<tr><td align=\"right\" class=\"complaintsTextRight\"> <video controls=\"controls\" width=\"30\" height=\"30\" src=\""+url+"\" > </video> ：<font class=\"complaintsUserName\">"+nickName+"</font></td></tr>");
						}
				


					}else if(msgType == "img"){
						if(emobIdFrom == messageFrom){	
							$("#message_table").append("<tr><td align=\"left\" class=\"complaintsTextLeft\"><font class=\"complaintsUserName\">"+nickName+"</font>：<img src=\""+url+"\" ></td></tr>");
						}else{
							$("#message_table").append("<tr><td align=\"right\" class=\"complaintsTextRight\"><img src=\""+url+"\" >：<font class=\"complaintsUserName\">"+nickName+"</font></td></tr>");
						}
					}
					
					
				}
			},

			error:function(er){
			
				alert(er);
			
			}
	
		});
		setNone();
			$("#fuwu_yinchang_id").attr("style", "display:block");
		$("#tousu_lie_div_id").attr("style","display:block");
	}
	
	
	function getComplaintsMessageByStatus(status){
			if(complaintsMaxTime == null || complaintsMinTime == null){
				alert("没有更多的记录的了");
				return;
			}
			var complaintsTableFirst = $("#message_table tr").eq(0);
			console.info(complaintsTableFirst);
			var complaintsTableNext = $("#message_table tr").eq($("#message_table tr").size() - 1);
			var occurTime;
			if(status == "next"){
				//complaintsTableNext.after("<tr><td>11111111</td></tr>");
				occurTime = complaintsMaxTime;
			}else if(status == "last"){
				//complaintsTableFirst.before("<tr><td>22222222</td></tr>");
				occurTime = complaintsMinTime;
			}
			var path = "<%= path%>/api/v1/communities/${communityId}/complaints/webIm/history?emobIdUser="+complaintsEmobIdFrom+"&emobIdShop="+complaintsEmobIdTo+"&occurTime="+occurTime+"&status="+status;
			
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						var len = data.length;
						if(len < 1){
							alert("没有更多的记录的了");
							return;
						}
						for(var i = 0 ; i < len ; i++){
							complaintsTableFirst = $("#message_table tr").eq(0);
							complaintsTableNext = $("#message_table tr").eq($("#message_table tr").size() - 1);
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
							
							if(status == "next"){
								if(i == len-1){
									complaintsMaxTime = data[i].timestamp;
								}
								complaintsTableNext.after("<tr><td class=\"complaintsTextCenter\">— "+outTime+" —</td></tr>");
								complaintsTableNext = $("#message_table tr").eq($("#message_table tr").size() - 1);
								if(data[i].messageFrom == complaintsEmobId){  // 左边
									complaintsTableNext.after("<tr><td class=\"complaintsTextLeft\"><font class=\"complaintsUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									complaintsTableNext.after("<tr><td class=\"complaintsTextRight\">"+msg+" : <font class=\"complaintsUserName\">"+nickName+"</font></td></tr>");
								}
								//complaintsTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									complaintsMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == complaintsEmobId){  // 左边
									complaintsTableFirst.before("<tr><td class=\"complaintsTextLeft\"><font class=\"complaintsUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									complaintsTableFirst.before("<tr><td class=\"complaintsTextRight\">"+msg+" : <font class=\"complaintsUserName\">"+nickName+"</font></td></tr>");
								}
								//complaintsTableFirst.before("<tr><td>22222222</td></tr>");
								complaintsTableFirst = $("#message_table tr").eq(0);
								complaintsTableFirst.before("<tr><td class=\"complaintsTextCenter\">— "+outTime+" —</td></tr>");
							}
						}
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
			//alert(complaintsTableFirst);
		}
</script>

<style type="text/css">
.to_user_text{
	text-align: right;
}
.from_user_text{
	text-align: left;
}
.textColor{
		color: #FF6600;
	}
	.complaintsTextLeft{
		text-align: left;
		
	}
	.complaintsTextRight{
		text-align: right;
		
	}
	.complaintsTextCenter{
		text-align: center;
		height: 50px;
	}
	.complaintsUserName{
		color: #3385FF;
	}
</style>
</head>
<body>
		<div style="width: 100%; height: 100%;" align="center">
			<input type="button" value=" NEXT "
				onclick="getComplaintsMessageByStatus('next');" />
			<input type="button" value=" LAST "
				onclick="getComplaintsMessageByStatus('last');" />
			<table align="center" width="55%" height="100%"
				style="margin: 70px 0 0 0; border: 1px solid #d3d7d4;"
				id="message_table">
				<tr align="center">
					<td align="center" style="text-align: center; font-size: 20px;">
						用户与商家的消息记录
					</td>
				</tr>
				<tr>
					<td height="50">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>