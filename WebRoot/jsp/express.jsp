<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>快递</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		
		var expressThisPageNum = 1;
	
		var expressThisPageSize = 10;
	
		var expressIsgoing = 0;
	
		var expressIsreview = 0;
	
		var expressEnded = 0;
	
		var expressSum = 0;
	
		var expressPageSize = 0;
	
		var expressPageNum = 0;
	
		var expressPageFirst = 0;
	
		var expressPageLast = 0;
	
		var expressPageNext = 0;
	
		var expressPrev = 0;
		
		var thisExpressSearchType = "-1";
		
		function expressSubmit(){
		
			var path = "<%= path%>/api/v1/communities/${communityId}/express";
			
			var expressName = $("#express_name").val();
			var expressGoods = $("#express_goods").val();
			var expressPhone = $("#express_phone").val();
			var expressNo = $("#express_no").val();
			var expressStationInform = $("#express_station_inform").val();
			var expressMessageInform = $("#express_message_inform").val();
			
			var myJson = {
				"expressName":expressName , 
				"expressGoods":expressGoods ,
				"expressPhone":expressPhone ,
				"expressNo":expressNo ,
				"expressStationInform":expressStationInform ,
				"expressMessageInform":expressMessageInform
			};
			
			$.ajax({

				url: path,

				type: "POST",
			
				data: JSON.stringify(myJson) ,

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
					   if(data.resultId>=0){
					   	alert("保存并消息发送成功");
					   }else{
					    alert(data.message);
					   }
						
						document.getElementById("express_name").value = "";
						document.getElementById("express_goods").value = "";
						document.getElementById("express_phone").value = "";
						document.getElementById("express_no").value = "";
					}else if(data.status == "empty"){
						alert("当前订单已存在请验证后进行保存");
					}else if(data.status == "no"){
						alert("保存失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		function getExpressList(express_type){
			
			var path = "<%= path%>/api/v1/communities/${communityId}/express?pageNum="+expressThisPageNum+"&pageSize="+expressThisPageSize+"&expressType="+express_type;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					if(data.status == "yes"){
						console.info(data);
						var pageData = data.info.pageData;
						
						expressSum = data.info.rowCount; // 总条数
				
						expressPageFirst = data.info.first; // 首页
				
						expressPageLast = data.info.last; // 尾页
				
						expressPageNext = data.info.next; // 下一页
				
						expressPageNum = data.info.num; // 当前页
				
						expressPrev = data.info.prev; // 上一页
				
						expressPageSize = data.info.pageCount; // 总页数
						
						$("#expressPageNum").html(expressPageNum);
				
						$("#expressPageSize").html(expressPageSize);
				
						$("#expressSum").html(expressSum);
						
						var len = pageData.length;
						for(var i = 0 ; i < len ; i++){
							var expressResult = "";
							console.info(pageData[i].expressResult);
							if(pageData[i].expressResult == 1){
								expressResult = "成功";
								
							}else{
								expressResult = "失败";
							}
							var expressUserType = "";
							if(pageData[i].expressUserType == 1){
								expressUserType = "普通用户";
							}else{
								expressUserType = "APP用户";
							}
							var expressCollectionTime = pageData[i].expressCollectionTime;
							expressCollectionTime = parseInt(expressCollectionTime) * 1000;
							var express_status_html = "";
							if(pageData[i].expressStatus == 1){
								express_status_html = "已取件";
							}else{
								express_status_html = "<input type=\"button\" value=\"确认取件\" onclick=\"updateExpress("+pageData[i].expressId+");\" />";
							}
							$("#express_table").append("<tr><td width=\"12.5%\">"+pageData[i].expressPhone+"</td><td width=\"12.5%\">"+pageData[i].expressNo+"</td><td width=\"12.5%\">"+pageData[i].expressName+"</td><td width=\"12.5%\">"+pageData[i].expressGoods+"</td><td width=\"12.5%\">"+expressResult+"</td><td width=\"12.5%\">"+expressUserType+"</td><td width=\"12.5%\">"+getSmpFormatDateByLong(expressCollectionTime,false)+"</td><td width=\"12.5%\">"+express_status_html+"</td></tr>");
						}
					}else if(data.status == "no"){
						alert("查询失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		function getExpressListByText(express_type){
		
			var express_search = $("#express_search").val();
			
			if(express_search == ""){
				alert("搜索内容不能为空");
				return false;
			}
			
			var path = "<%= path%>/api/v1/communities/${communityId}/express/like/"+express_search+"?pageNum="+expressThisPageNum+"&pageSize="+expressThisPageSize+"&expressType="+express_type;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					
					if(data.status == "yes"){
						console.info(data);
						var pageData = data.info.pageData;
						
						expressSum = data.info.rowCount; // 总条数
				
						expressPageFirst = data.info.first; // 首页
				
						expressPageLast = data.info.last; // 尾页
				
						expressPageNext = data.info.next; // 下一页
				
						expressPageNum = data.info.num; // 当前页
				
						expressPrev = data.info.prev; // 上一页
				
						expressPageSize = data.info.pageCount; // 总页数
						
						$("#expressPageNum").html(expressPageNum);
				
						$("#expressPageSize").html(expressPageSize);
				
						$("#expressSum").html(expressSum);
						
						var len = pageData.length;
						for(var i = 0 ; i < len ; i++){
							var expressResult = "";
							console.info(pageData[i].expressResult);
							if(pageData[i].expressResult == 1){
								expressResult = "成功";
								
							}else{
								expressResult = "失败";
							}
							var expressUserType = "";
							if(pageData[i].expressUserType == 1){
								expressUserType = "普通用户";
							}else{
								expressUserType = "APP用户";
							}
							var expressCollectionTime = pageData[i].expressCollectionTime;
							expressCollectionTime = parseInt(expressCollectionTime) * 1000;
							var express_status_html = "";
							if(pageData[i].expressStatus == 1){
								express_status_html = "已取件";
							}else{
								express_status_html = "<input type=\"button\" value=\"确认取件\" onclick=\"updateExpress("+pageData[i].expressId+");\" />";
							}
							$("#express_table").append("<tr><td width=\"12.5%\">"+pageData[i].expressPhone+"</td><td width=\"12.5%\">"+pageData[i].expressNo+"</td><td width=\"12.5%\">"+pageData[i].expressName+"</td><td width=\"12.5%\">"+pageData[i].expressGoods+"</td><td width=\"12.5%\">"+expressResult+"</td><td width=\"12.5%\">"+expressUserType+"</td><td width=\"12.5%\">"+getSmpFormatDateByLong(expressCollectionTime,false)+"</td><td width=\"12.5%\">"+express_status_html+"</td></tr>");
						}
					}else if(data.status == "no"){
						alert("查询失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		
		function updateExpress(express_id){
			
			var myJson = {
				"expressStatus":1
			};
			
			var path = "<%= path%>/api/v1/communities/${communityId}/express/"+express_id;
		
			$.ajax({

				url: path,

				type: "PUT",
				
				data:JSON.stringify(myJson),

				dataType:'json',

				success:function(data){
					
					if(data.status == "yes"){
						$("#express_table").html("");
						getExpressList("-1");
					}else if(data.status == "no"){
						alert("修改失败");
					}
					
				},

				error:function(er){
				
					alert(er);
			
				}

			});
		}
		
		function getExpressListWithPage(num){
		
		if(num == -1){ // 上一页
			if(expressThisPageNum != 1){
				expressThisPageNum = expressPrev;
				$("#express_table").html("");
				getExpressList(thisExpressSearchType);
			}
			
		}else if(num == -2){ // 下一页
			if(expressThisPageNum < expressPageSize){
				expressThisPageNum = expressPageNext;
				$("#express_table").html("");
				getExpressList(thisExpressSearchType);
			}
			
		}else if(num == -3){ // 首页
			if(expressThisPageNum != expressPageFirst){
				expressThisPageNum = expressPageFirst;
				$("#express_table").html("");
				getExpressList(thisExpressSearchType);
			}
		}else if(num == -4){ // 尾页
			if(expressThisPageNum != expressPageLast){
				expressThisPageNum = expressPageLast;
				$("#express_table").html("");
				getExpressList(thisExpressSearchType);
			}
		}
	}
	
	function updateThisSearchType(type){
		thisExpressSearchType = type;
		$("#express_table").html("");
		getExpressList(thisExpressSearchType);
	}
	
	function express_search_by_text(){
		$("#express_table").html("");
		getExpressListByText(thisExpressSearchType);
	}
	</script>
  </head>
  
  <body>
  	<table>
  	<tr>
  			<td>
  				<a href="javascript:void(0);" onclick="weiXiuClick();">维修</a>
  			</td>
  			
  			<td>
  				<a href="javascript:void(0);" onclick="baoJieClick();" >保洁</a>
  			</td>
  			
  			<td>
  				<a href="javascript:void(0);" onclick="kuaiDiClick();" >快递</a>
  			</td>
  		</tr>
  		<tr>
  			<td colspan="1">
  				站内通知:
  			</td>
  			
  			<td colspan="7">
  				<input type="text" value="尊敬的 XXX 用户：您好，您的快递已接受，请及时到物业领取，谢谢" readonly="readonly" id="express_station_inform" width="100" style="width: 90%;height: 35px;" />
  			</td>
  		</tr>
  		
  		<tr>
  			<td colspan="1">
  				短信通知:
  			</td>
  			
  			<td colspan="7">
  				<input type="text" value="尊敬的 XXX 用户：您好，您的快递已接受，请及时到物业领取，谢谢" readonly="readonly" id="express_message_inform" style="width: 90% ;height: 35px;"  />
  			</td>
  		</tr>
  		
  		<tr>
  			<td>
  				快递单号:
  			</td>
  			<td>
  				<input type="text" id="express_no" value="" style="height: 35px;" />
  			</td>
  			
  			<td>
  				手机号:
  			</td>
  			<td>
  				<input type="text" id="express_phone" value="" style="height: 35px;" />
  			</td>
  			
  			<td>
  				物品:
  			</td>
  			<td>
  				<input type="text" id="express_goods" value="" style="height: 35px;" />
  			</td>
  			
  			<td>
  				收件人:
  			</td>
  			<td>
  				<input type="text" id="express_name" value="" style="height: 35px;" />
  			</td>
  			
  		</tr>
  		
  		<tr>
  			<td colspan="8" align="center" height="70">
  				<input type="button" value="收件并通知" id="express_submit" onclick="expressSubmit();" style="height: 35px;" />
  			</td>
  		</tr>
  	</table>
  	
  	<table width="70%">
  		<tr>
  			<td align="left" colspan="4">
  				<a href="javascript:void(0);" onclick="updateThisSearchType('-1')">默认</a>
  				<a href="javascript:void(0);" onclick="updateThisSearchType('1')">已取件</a>
  				<a href="javascript:void(0);" onclick="updateThisSearchType('2')">未取件</a>
  			</td>
  			
  			<td align="right" colspan="4">
  				<input type="text" value="" id="express_search" />
  				<input type="button" value=" 搜 索 " id="express_search_submit" onclick="express_search_by_text();" />
  			</td>
  		</tr>
  		
  		<tr>
  			<td width="12.5%">电话</td>
  			<td width="12.5%">快递单号</td>
  			<td width="12.5%">收件人</td>
  			<td width="12.5%">物品</td>
  			<td width="12.5%">通知结果</td>
  			<td width="12.5%">用户类型</td>
  			<td width="12.5%">收件时间</td>
  			<td width="12.5%">取件</td>
  		</tr>
  	</table>
  	
  	<table  width="70%" id="express_table">
  	
  	</table>
  	
  	<table class="divide-page">
  		<tr>
  			<td>
  				<a href="javascript:void(0);" onclick="getExpressListWithPage(-3);">首页</a>
						<a href="javascript:void(0);" onclick="getExpressListWithPage(-1);">上一页</a>
						当前第 <font id="expressPageNum"></font> 页
						共 <font id="expressPageSize"></font> 页
						共 <font id="expressSum"></font> 条
				
						<a href="javascript:void(0);" onclick="getExpressListWithPage(-2);">下一页</a>
						<a href="javascript:void(0);" onclick="getExpressListWithPage(-4);">尾页</a>
  			</td>
  		</tr>
  	</table>
  </body>
</html>
