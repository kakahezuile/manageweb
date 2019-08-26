<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>敏感词</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js"></script>
	<style type="text/css">
	.sensitiveMain{
		float: left;
		width: 60%;
		border: solid 1px #D6D6D6;
		height: 85%;
		height:auto !important;
	}
	.sensitiveTop{
		float: left;
		width: 100%;
		height: 10%;
		border-bottom: solid 1px #D6D6D6;
	}
	.sensitiveCenter{
		float: left;
		width: 100%;
		height: 65%;
		overflow-Y:scroll;
	}
	.textColor{
		color: #FF6600;
	}
	.sensitiveTextLeft{
		text-align: left;
		
	}
	.sensitiveTextRight{
		text-align: right;
		
	}
	.sensitiveTextCenter{
		text-align: center;
		height: 50px;
	}
	.sensitiveUserName{
		color: #3385FF;
	}
	</style>		
	<script type="text/javascript">
		
		var sensitiveData;
		
		var sensitiveWordsPageNum = 1;
		
		var sensitiveWordsPageSize = 10;
		
		var sensitiveEmobId;
		
		var sensitiveMinTime;
		
		var sensitiveMaxTime;
		
		var sensitiveGroupId;
		
		
		
		function getSensitiveWords(){
		
			var path = "<%= path%>/api/v1/communities/1/sensitiveWordsHistory?pageNum="+sensitiveWordsPageNum+"&pageSize="+sensitiveWordsPageSize;
		
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						$("#sensitiveWordsTable").html("");
						$("#sensitiveWordsTable").append("<tr><td>敏感词</td><td>群组名称</td><td>所属物业</td><td>出现时间</td><td>处理结果</td><td>定位敏感词</td></tr>");
						data = data.info.pageData;
						sensitiveData = data;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							outTime = getSmpFormatDateByLong(data[i].historyTime * 1000);
							var status = "";
							if(data[i].status == "untreated"){
								status = "未处理";
							}
							$("#sensitiveWordsTable").append("<tr><td>"+data[i].sensitiveWords+"</td><td>"+data[i].groupName+"</td><td>"+data[i].communityName+"</td><td>"+outTime+"</td><td>"+status+"</td><td><a href=\"javascript:void(0);\" onclick=\"sensitiveWordsSelect("+i+");\" >查看</a></td></tr>");
						}
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
		}
		
		function getSensitiveWordsByStatus(status){
			
			var sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
			console.info(sensitiveTableFirst);
			var sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
			var path = "<%= path%>/api/v1/communities/1/sensitiveWordsHistory/"+sensitiveGroupId+"/messages/"+status;
			if(status == "next"){
				//sensitiveTableNext.after("<tr><td>11111111</td></tr>");
				path += "?time="+sensitiveMaxTime;
			}else if(status == "last"){
				//sensitiveTableFirst.before("<tr><td>22222222</td></tr>");
				path += "?time="+sensitiveMinTime;
			}
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
							sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
							sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
							
							if(status == "next"){
								if(i == len-1){
									sensitiveMaxTime = data[i].timestamp;
								}
								sensitiveTableNext.after("<tr><td class=\"sensitiveTextCenter\">— "+outTime+" —</td></tr>");
								sensitiveTableNext = $("#sensitiveCenterTable tr").eq($("#sensitiveCenterTable tr").size() - 1);
								if(data[i].messageFrom == sensitiveEmobId){  // 左边
									sensitiveTableNext.after("<tr><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									sensitiveTableNext.after("<tr><td class=\"sensitiveTextRight\">"+msg+" : <font class=\"sensitiveUserName\">"+nickName+"</font></td></tr>");
								}
								//sensitiveTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									sensitiveMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == sensitiveEmobId){  // 左边
									sensitiveTableFirst.before("<tr><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									sensitiveTableFirst.before("<tr><td class=\"sensitiveTextRight\">"+msg+" : <font class=\"sensitiveUserName\">"+nickName+"</font></td></tr>");
								}
								//sensitiveTableFirst.before("<tr><td>22222222</td></tr>");
								sensitiveTableFirst = $("#sensitiveCenterTable tr").eq(0);
								sensitiveTableFirst.before("<tr><td class=\"sensitiveTextCenter\">— "+outTime+" —</td></tr>");
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
			//alert(sensitiveTableFirst);
		}
		
		function sensitiveWordsSelect(index){
			$("#sensitiveWordsTable").attr("style","display:none");
			$("#sensitiveMain").attr("style","display:block");
			var data = sensitiveData[index];
			sensitiveGroupId = data.groupId;
			outTime = getSmpFormatDateByLong(data.historyTime * 1000);
			var status;
			if(data.status == "untreated"){
				status = "未处理";
			}
			var uuid = data.uuid;
			sensitiveEmobId = data.emobId;
			$("#sensitiveTop").append("<table align=\"center\" width=\"65%\" height=\"100%\" ><tr><td>敏感词</td><td class=\"textColor\">"+data.sensitiveWords+"</td><td>群组名称</td><td class=\"textColor\">"+data.groupName+"</td><td>所属物业</td><td class=\"textColor\">"+data.communityName+"</td><td>出现时间</td><td class=\"textColor\">"+outTime+"</td><td>处理结果</td><td class=\"textColor\">"+status+"</td></tr></table>");
			getMessageByUuid(uuid);
		}
		
		function getMessageByUuid(uuid){
			
			var path = "<%= path%>/api/v1/communities/1/sensitiveWordsHistory/"+uuid+"/messages";
			
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						var sensitiveCenterTable = $("#sensitiveCenterTable");
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							if(i == 0){
								sensitiveMinTime = data[i].timestamp;
							}
							if(i == len-1){
								sensitiveMaxTime = data[i].timestamp;
							}
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
							sensitiveCenterTable.append("<tr><td class=\"sensitiveTextCenter\">— "+outTime+" —</td></tr>");
							if(data[i].messageFrom == sensitiveEmobId){  // 左边
								sensitiveCenterTable.append("<tr><td class=\"sensitiveTextLeft\"><font class=\"sensitiveUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
							}else{ // 右边
								sensitiveCenterTable.append("<tr><td class=\"sensitiveTextRight\">"+msg+" : <font class=\"sensitiveUserName\">"+nickName+"</font></td></tr>");
							}
						}
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
		}
	</script>
  </head>
  <div style="display: none;">
  	<jsp:include page="../jsp/public/nav.jsp"></jsp:include>
  </div>
  <body>
  	<input type="button" value=" SELECT " onclick="getSensitiveWords();" />
  	
   	<table width="65%" id="sensitiveWordsTable" align="center">
   		<tr>
   			<td>敏感词</td>
   			<td>群组名称</td>
   			<td>所属物业</td>
   			<td>出现时间</td>
   			<td>处理结果</td>
   			<td>定位敏感词</td>
   		</tr>
   	</table>
   	<div align="center" class="sensitiveMain" id="sensitiveMain" style="display: none;">
   	<input type="button" value=" NEXT " onclick="getSensitiveWordsByStatus('next');" />
   	<input type="button" value=" LAST " onclick="getSensitiveWordsByStatus('last');" />
   		<div class="sensitiveTop" id="sensitiveTop"></div>
   		<div class="sensitiveCenter" id="sensitiveCenter" >
   			<table id="sensitiveCenterTable" align="center" width="80%">
   			
   			</table>
   		</div>
   	</div>
   	
  </body>
</html>
