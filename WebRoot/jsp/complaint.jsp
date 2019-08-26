<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉处理页</title>
<script type="text/javascript">
	$(function(){
	
	});
	
	function getComplaintList(){
		var detail = "";
		var complaintId = 0;
		var emobIdFrom = "";
		var emobIdTo = "";
		var nickname = "";
		var orderId = 0;
		var room = "";
		var title = "";
		var avatar = "";
		var occurTime = 0;
		var path = "<%= path%>/api/v1/communities/${communityId}/complaints/webIm/getEmobIdList?pageNum=1&pageSize=10";
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
				var dataList = data.pageData;
				
				for(var i = 0 ; i < dataList.length ; i++){
					detail = dataList[i].detail;
					complaintId = dataList[i].complaintId;
					emobIdFrom = dataList[i].emobIdFrom;
					emobIdTo = dataList[i].emobIdTo;
					nickname = dataList[i].nickname;
					orderId = dataList[i].orderId;
					room = dataList[i].room;
					title = dataList[i].title;
					avatar = dataList[i].avatar;
					occurTime = dataList[i].occurTime;
					tousuListAddLi(orderId, emobIdFrom, emobIdTo, room, nickname, avatar , detail , occurTime);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function tousuListAddLi(orderId , emobIdFrom , emobIdTo , room , nickname , avatar , detail , occurTime){
		console.info(orderId);
		$("#tousu_list").append("<li><a href =\"javascript:void(0);\"  onclick = \"getComplaintShop("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+detail+"','"+occurTime+"');\"  >"+nickname+" " +room+"</a></li>");
	}
	
	function getComplaintShop(orderId , emobIdFrom , emobIdTo , detail,occurTime){
		var path = "<%= path%>/api/v1/communities/${communityId}/complaints/webIm/"+emobIdTo;
		var sumOrder = 0;
		var nickname = "";
		var phone = "";
		var haoping = 0;
		var chaping = 0;
		var zhongping = 0;
		var avatar = "";
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){

					sumOrder = data.sumOrder;
					nickname = data.nickname;
					phone = data.phone;
					haoping = data.haoping;
					chaping = data.chaping;
					zhongping = data.zhongping;
					avatar = data.avatar;
				
					addTouSuMainRight(sumOrder, nickname, phone, haoping, chaping, zhongping, avatar , detail , orderId , emobIdFrom , emobIdTo , occurTime);
					
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function addTouSuMainRight(sumOrder,nickname,phone,haoping,chaping,zhongping,avatar,detail , orderId , emobIdFrom , emobIdTo , occurTime){
	
		$("#weixiu_shifu_name").html(nickname);
		$("#chengjiao_danshu").html("成交： "+sumOrder);
		$("#tousu_pingjia").html("好评："+haoping + " 中评："+zhongping + " 差评："+chaping);
		$("#tousu_phone").html("电话： "+phone);
		$("#tousu_detail").html("投诉内容："+detail);
		$("#tousu_submit").attr("onclick","getOrderMessages("+orderId+",'"+emobIdFrom+"','"+emobIdTo+"','"+occurTime+"')");
	}
	
	function getOrderMessages(orderId , emobIdFrom , emobIdTo , occurTime){ 
		var path = "<%= path%>/api/v1/communities/${communityId}/complaints/webIm/history/"+orderId+"/"+emobIdFrom+"/"+emobIdTo+"?occurTime="+occurTime+"&pageNum=1&pageSize=10";
		
		var msgType = "";
		
		var msg = "";
		
		var url = "";
		
		var filename = "";
		
		var secret = "";
		
		var videoLength = 0;
		
		var timestamp = 0;
		
		var messageFrom = "";
		
		var messageTo = "";
		
		$.ajax({

			url: path,

			type: "GET",

			dataType:"json",

			success:function(data){
				var dataList = data.pageData;
			
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
					
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
</script>
<style type="text/css">
.tousu_main{
	width: 100%;
	height: 100%;
}
.tousumain_left{
	width: 25%;
	height : 100%;
	float: left;
}

.tousumain_right{
	width: 74%;
	float: left;
	height : 100%;
}
</style>
</head>
<body>
投诉处理

<div class="tousu_main">
	<div class="tousumain_left">
		<ul class="tousu_list" id="tousu_list">
		</ul>
	</div>
	
	<div class="tousumain_right">
		<table>
			<tr>
				<td id="weixiu_shifu_name">
				</td>
			</tr>
			
			<tr>
				<td id="chengjiao_danshu">
				</td>
			</tr>
			
			<tr>
				<td id="tousu_phone">
				</td>
			</tr>
			
			<tr>
				<td id="tousu_pingjia">
					
				</td>
			</tr>
			
			<tr>
				<td id="tousu_detail">
					
				</td>
			
				
			</tr>
			
			<tr>
				<td>
					<input type="button" value=" 查 看 交 易 实 录 " id="tousu_submit" />
				</td>
			
				
			</tr>
		</table>
	</div>
	
</div>
</body>
</html>