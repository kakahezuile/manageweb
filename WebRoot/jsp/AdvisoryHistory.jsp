<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>监控咨询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	.advisoryMain{
		float: left;
		width: 100%;
		border: solid 1px #D6D6D6;
		height: 85%;
		height:auto !important;
		overflow-Y:scroll;
	}
	.timeText{
		height: 30px;
		width: 180px;
	}
	.searchText{
		width: 270px;
		height: 30px;
	}
	.advisoryHistoryTop{
		float: left;
		width: 100%;
		height: 10%;
		border-bottom: solid 1px #D6D6D6;
	}
	.advisoryHistoryTextLeft{
		text-align: left;
		
	}
	.advisoryHistoryTextRight{
		text-align: right;
		
	}
	.advisoryHistoryTextCenter{
		text-align: center;
		height: 50px;
	}
	.advisoryHistoryUserName{
		color: #3385FF;
	}
</style>
<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js"></script>
<script type="text/javascript">
	
	var advisoryHistoryPageNum = 1;
	
	var advisoryHistoryPageSize = 10;
	
	var advisoryHistoryList;
	
	var advisoryHistoryMinTime;
	
	var advisoryHistoryMaxTime;
	
	var advisoryHistoryEmobId;
	
	var advisoryHistoryIndex;
	
	function getAdvisoryHistory(){
		
		advisoryStartTime = $("#advisoryStartTime").val();
		
		advisoryEndTime = $("#advisoryEndTime").val();
		
		advisoryName = $("#advisoryName").val();
		
		var path = "<%= path%>/api/v1/communities/1/advisoryHistory?pageNum="+advisoryHistoryPageNum+"&pageSize="+advisoryHistoryPageSize+"&startTime="+advisoryStartTime+"&endTime="+advisoryEndTime+"&name="+advisoryName;
		advisoryHisotryTable = $("#advisoryHisotryTable");
		$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						advisoryHisotryTable.html("");
						advisoryHisotryTable.append("<tr><td>用户</td><td>用户电话</td><td>咨询客服</td><td>咨询时间</td><td>详情</td></tr>");
						data = data.info.pageData;
						advisoryHistoryList = data;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							outTime = getSmpFormatDateByLong(data[i].advisoryTime * 1000);
							advisoryHisotryTable.append("<tr><td>"+data[i].userName+"</td><td>"+data[i].phone+"</td><td>"+data[i].agentName+"</td><td>"+outTime+"</td><td><a href=\"javascript:void(0);\" onclick=\"advisoryHistoryClick("+i+");\">咨询详情</a></td></tr>");
						}
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
	}
	
	function advisoryHistoryClick(index){
		advisoryHistoryIndex = index;
		thisData = advisoryHistoryList[index];
		$("#advisoryMain").attr("style","display:none");
		$("#advisoryMain2").attr("style","display:block");
		advisoryHistoryTopTable = $("#advisoryHistoryTopTable");
		advisoryHistoryTopTable.html("");
		outTime = getSmpFormatDateByLong(thisData.advisoryTime * 1000);
		advisoryHistoryTopTable.append("<tr><td>用户</td><td>用户电话</td><td>咨询客服</td><td>咨询时间</td></tr>");
		advisoryHistoryTopTable.append("<tr><td>"+thisData.userName+"</td><td>"+thisData.phone+"</td><td>"+thisData.agentName+"</td><td>"+outTime+"</td></tr>");
		from = thisData.emobIdUser;
		to = thisData.emobIdAgent;
		advisoryTime = thisData.advisoryTime * 1000;
		var path = "<%= path%>/api/v1/communities/1/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to+"&timestamp="+advisoryTime+"&isMoreThan=true";
		
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data2){
				
				if(data2.status == "yes"){
					addAdvisoryMainTable(data2);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
		function addAdvisoryMainTable(data2){
		$("#advisoryHisotryTable2").html("");
					data2 = data2.info;
					var len = data2.length;
					
					for(var i = 0 ; i < len ; i++){
					
						if(i == 0){
							advisoryHistoryMinTime = data2[i].timestamp;
						}
						if(i == len-1){
							advisoryHistoryMaxTime = data2[i].timestamp;
						}
						nickName = data2[i].nickname;
						msg = data2[i].msg; 
						
						outTime = getSmpFormatDateByLong(data2[i].timestamp);
						$("#advisoryHisotryTable2").append("<tr><td class=\"advisoryHistoryTextCenter\">— "+outTime+" —</td></tr>");
						if(data2[i].messageFrom == advisoryHistoryEmobId){  // 左边
							$("#advisoryHisotryTable2").append("<tr><td class=\"advisoryHistoryTextLeft\"><font class=\"advisoryHistoryUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
						}else{ // 右边
							$("#advisoryHisotryTable2").append("<tr><td class=\"advisoryHistoryTextRight\">"+msg+" : <font class=\"advisoryHistoryUserName\">"+nickName+"</font></td></tr>");
						}
					}
		
	}
	
	
	function getAdvisoryHistoryByStatus(status){
			
			var from ;
			var to ;
			var data = advisoryHistoryList[advisoryHistoryIndex];
			to = data.emobIdAgent;	
			from = data.emobIdUser;
			var advisoryHistoryTableFirst = $("#advisoryHisotryTable2 tr").eq(0);
		
			var advisoryHistoryTableNext = $("#advisoryHisotryTable2 tr").eq($("#advisoryHisotryTable2 tr").size() - 1);
			var path = "<%= path%>/api/v1/communities/1/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to;
			var text;
			if(status == "next"){
				//MonitorComplaintsTableNext.after("<tr><td>11111111</td></tr>");
				path += "&timestamp="+advisoryHistoryMaxTime;
				text = "true";
			}else if(status == "last"){
				//MonitorComplaintsTableFirst.before("<tr><td>22222222</td></tr>");
				path += "&timestamp="+advisoryHistoryMinTime;
				text = "false";
			}
			path += "&isMoreThan="+text;
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
							advisoryHistoryTableFirst = $("#advisoryHisotryTable2 tr").eq(0);
							advisoryHistoryTableNext = $("#advisoryHisotryTable2 tr").eq($("#advisoryHisotryTable2 tr").size() - 1);
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
							
							if(status == "next"){
								if(i == len-1){
									advisoryHistoryMaxTime = data[i].timestamp;
								}
								advisoryHistoryTableNext.after("<tr><td class=\"advisoryHistoryTextCenter\">— "+outTime+" —</td></tr>");
								advisoryHistoryTableNext = $("#advisoryHisotryTable2 tr").eq($("#advisoryHisotryTable2 tr").size() - 1);
								if(data[i].messageFrom == advisoryHistoryEmobId){  // 左边
									advisoryHistoryTableNext.after("<tr><td class=\"advisoryHistoryTextLeft\"><font class=\"advisoryHistoryUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									advisoryHistoryTableNext.after("<tr><td class=\"advisoryHistoryTextRight\">"+msg+" : <font class=\"advisoryHistoryUserName\">"+nickName+"</font></td></tr>");
								}
								//MonitorComplaintsTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									advisoryHistoryMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == advisoryHistoryEmobId){  // 左边
									advisoryHistoryTableFirst.before("<tr><td class=\"advisoryHistoryTextLeft\"><font class=\"advisoryHistoryUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									advisoryHistoryTableFirst.before("<tr><td class=\"advisoryHistoryTextRight\">"+msg+" : <font class=\"advisoryHistoryUserName\">"+nickName+"</font></td></tr>");
								}
								//MonitorComplaintsTableFirst.before("<tr><td>22222222</td></tr>");
								advisoryHistoryTableFirst = $("#advisoryHisotryTable2 tr").eq(0);
								advisoryHistoryTableFirst.before("<tr><td class=\"advisoryHistoryTextCenter\">— "+outTime+" —</td></tr>");
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
			//alert(MonitorComplaintsTableFirst);
		}
</script>			
  </head>
  
  <body>
  	<div style="display: none;">
  		<jsp:include page="../jsp/public/nav.jsp"></jsp:include>
  	</div>
    <div class="advisoryMain" id="advisoryMain" align="center">
    	<table height="10%" width="100%" align="center">
    		<tr>
    			<td><input type="text" class="timeText" value="" name="advisoryStartTime" id="advisoryStartTime" /></td>
    			<td><input type="text" class="timeText" value="" name="advisoryEndTime" id="advisoryEndTime" /></td>
    			<td><input type="text" value="" class="searchText" name="advisoryName" id="advisoryName" /></td>
    			<td><input type="button" value=" 搜  索 " onclick="getAdvisoryHistory();" /></td>
    		</tr>
    	</table>
    	
    	<table height="90%" width="100%" align="center" id="advisoryHisotryTable">
    		<tr>
    			<td>用户</td>
    			<td>用户电话</td>
    			<td>咨询客服</td>
    			<td>咨询时间</td>
    			<td>详情</td>
    			
    		</tr>
    	</table>
    </div>
    

    <div class="" id="advisoryMain2" align="center"  style="display: none;width: 100%">
    	    <div class="advisoryHistoryTop">
    		<table id="advisoryHistoryTopTable" width="100%" align="center">
    			<tr>
    				<td>用户</td>
    				<td>用户电话</td>
    				<td>咨询客服</td>
    				<td>咨询时间</td>
    			</tr>
    		</table>
    	</div>
    	<div class="advisoryMain">
    	<input type="button" value=" NEXT " onclick="getAdvisoryHistoryByStatus('next');" />
   	<input type="button" value=" LAST " onclick="getAdvisoryHistoryByStatus('last');" />
    	<table height="90%" width="100%" align="center" id="advisoryHisotryTable2" >
    		
    	</table>
    	</div>
    </div>
  </body>
</html>
