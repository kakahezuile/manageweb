<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>投诉监控</title>
    
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
			
<script type="text/javascript">


	var monitorComplaintsPageSize = 10;
	
	var monitorComplaintsPageNum = 1;
	
	var monitorComplaintsList;
	
		var monitorComplaintsMinTime;
		
		var monitorComplaintsMaxTime;
		var monitorComplaintsEmobId;
		
	var monitorComplaintsIndex;
	
	var monitorComplaintsType;
	
	function getMonitorComplaintsTop(){
	
		var path = "<%= path%>/api/v1/communities/1/monitorComplaints/top";
		
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
					data = data.info;
					serviceCount = data.serviceCount;
					cleanCount = data.cleanCount;
					shopCount = data.shopCount;
					$("#monitorComplaintsService").html(serviceCount);
					$("#monitorComplaintsClean").html(cleanCount);
					$("#monitorComplaintsShop").html(shopCount);
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	function getMonitorComplaintsCenter(sort,startTime,endTime){
		var path = "<%= path%>/api/v1/communities/1/monitorComplaints?sort="+sort+"&startTime="+startTime+"&endTime="+endTime+"&pageNum="+monitorComplaintsPageNum+"&pageSize="+monitorComplaintsPageSize;
		monitorComplaintsTextName = $("#monitorComplaintsTextName").val();
		path += "&name="+monitorComplaintsTextName;
		MonitorComplaintsCenterTable = $("#MonitorComplaintsCenterTable");
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
					data = data.info.pageData;
					monitorComplaintsList = data;
					var len = data.length;
					MonitorComplaintsCenterTable.html("");
					MonitorComplaintsCenterTable.append("<tr><td>用户</td><td>用户电话</td><td>投诉对象</td><td>投诉内容</td><td>投诉时间</td><td>投诉结果</td><td>查看对话</td></tr>");
					for(var i = 0 ; i < len ; i++){
						outTime = getSmpFormatDateByLong(data[i].complaintsTime * 1000);
						var status;
						status = data[i].complaintsStatus;
						if(status == "review"){
							status = "待处理";
						}else if(status == "ongoing"){
							status = "处理中";
						}else if(status == "ended"){
							status = "完成";
						}
						MonitorComplaintsCenterTable.append("<tr><td>"+data[i].nickName+"</td><td>"+data[i].phone+"</td><td>"+data[i].shopName+"("+data[i].sortName+")</td><td>"+data[i].complaintsDetail+"</td><td>"+outTime+"</td><td>"+status+"</td><td><a href=\"javascript:void(0);\" onclick=\"monitorComplaintsServiceClick("+i+",'complaints');\" >投诉对话</a> <a href=\"javascript:void(0);\" onclick=\"monitorComplaintsServiceClick("+i+",'service');\" >服务对话</a></td></tr>");
					}
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function monitorComplaintsClick(){
		var str=document.getElementsByName("monitorComplaintsCheckBox");
		var objarray=str.length;
		var chestr="";
		var strArray = new Array();
		for (i=0;i<objarray;i++){
			if(str[i].checked == true){
				strArray.push(str[i].value);
				chestr+=str[i].value+",";
			}
		}
		startTime = $("#monitorComplaintsStartTime").val();
		endTime = $("#monitorComplaintsEndTime").val();
		getMonitorComplaintsCenter(chestr, startTime, endTime);
	}
	
	function monitorComplaintsServiceClick(index,status){
		monitorComplaintsIndex = index;
		var from ;
		var to ;
		var data = monitorComplaintsList[index];
		var text;
		monitorComplaintsType = status;
		if(status == "service"){
			to = data.emobIdShop;
			text = "false";
		}else if(status == "complaints"){
			to = data.emobIdAgent;
			text = "true";
		}
		from = data.emobIdUser;
		monitorComplaintsEmobId = from;
		complaintsTime = data.complaintsTime;
		$("#MonitorComplaintsMain").attr("style","display:none");
		$("#MonitorComplaintsMain2").attr("style","display:block");
		status = data.complaintsStatus;
						if(status == "review"){
							status = "待处理";
						}else if(status == "ongoing"){
							status = "处理中";
						}else if(status == "ended"){
							status = "完成";
						}
		outTime = getSmpFormatDateByLong(data.complaintsTime * 1000);
		$("#MonitorComplaintsTopTable").html("");
		$("#MonitorComplaintsTopTable").append("<tr><td>用户</td><td>用户电话</td><td>投诉对象</td><td>投诉内容</td><td>投诉时间</td><td>投诉结果</td></tr>");				
		$("#MonitorComplaintsTopTable").append("<tr><td>"+data.nickName+"</td><td>"+data.phone+"</td><td>"+data.shopName+"("+data.sortName+")</td><td>"+data.complaintsDetail+"</td><td>"+outTime+"</td><td>"+status+"</td></tr>");
		
		var path = "<%= path%>/api/v1/communities/1/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to+"&timestamp="+complaintsTime+"&isMoreThan="+text;
		
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data2){
				addMonitorComplaintsTopTable(data2);
				if(data2.status == "yes"){
					addMonitorComplaintsTopTable(data2);	
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function addMonitorComplaintsTopTable(data2){
		$("#MonitorComplaintsTopTable2").html("");
					data2 = data2.info;
					var len = data2.length;
					
					for(var i = 0 ; i < len ; i++){
					
						if(i == 0){
							monitorComplaintsMinTime = data2[i].timestamp;
						}
						if(i == len-1){
							monitorComplaintsMaxTime = data2[i].timestamp;
						}
						nickName = data2[i].nickname;
						msg = data2[i].msg; 
						
						outTime = getSmpFormatDateByLong(data2[i].timestamp);
						$("#MonitorComplaintsTopTable2").append("<tr><td class=\"MonitorComplaintsTextCenter\">— "+outTime+" —</td></tr>");
						if(data2[i].messageFrom == monitorComplaintsEmobId){  // 左边
							$("#MonitorComplaintsTopTable2").append("<tr><td class=\"MonitorComplaintsTextLeft\"><font class=\"MonitorComplaintsUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
						}else{ // 右边
							$("#MonitorComplaintsTopTable2").append("<tr><td class=\"MonitorComplaintsTextRight\">"+msg+" : <font class=\"MonitorComplaintsUserName\">"+nickName+"</font></td></tr>");
						}
					}
		
	}
	
		function getMonitorComplaintsByStatus(status){
			
			var from ;
			var to ;
			var data = monitorComplaintsList[monitorComplaintsIndex];
			if(monitorComplaintsType == "service"){
				to = data.emobIdShop;
		
			}else if(monitorComplaintsType == "complaints"){
				to = data.emobIdAgent;
		
			}
			from = data.emobIdUser;
			var MonitorComplaintsTableFirst = $("#MonitorComplaintsTopTable2 tr").eq(0);
		
			var MonitorComplaintsTableNext = $("#MonitorComplaintsTopTable2 tr").eq($("#MonitorComplaintsTopTable2 tr").size() - 1);
			var path = "<%= path%>/api/v1/communities/1/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to;
			var text;
			if(status == "next"){
				//MonitorComplaintsTableNext.after("<tr><td>11111111</td></tr>");
				path += "&timestamp="+monitorComplaintsMaxTime;
				text = "true";
			}else if(status == "last"){
				//MonitorComplaintsTableFirst.before("<tr><td>22222222</td></tr>");
				path += "&timestamp="+monitorComplaintsMinTime;
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
							MonitorComplaintsTableFirst = $("#MonitorComplaintsTopTable2 tr").eq(0);
							MonitorComplaintsTableNext = $("#MonitorComplaintsTopTable2 tr").eq($("#MonitorComplaintsTopTable2 tr").size() - 1);
							nickName = data[i].nickname;
							msg = data[i].msg; 
							outTime = getSmpFormatDateByLong(data[i].timestamp);
							
							if(status == "next"){
								if(i == len-1){
									monitorComplaintsMaxTime = data[i].timestamp;
								}
								MonitorComplaintsTableNext.after("<tr><td class=\"MonitorComplaintsTextCenter\">— "+outTime+" —</td></tr>");
								MonitorComplaintsTableNext = $("#MonitorComplaintsTopTable2 tr").eq($("#MonitorComplaintsTopTable2 tr").size() - 1);
								if(data[i].messageFrom == monitorComplaintsEmobId){  // 左边
									MonitorComplaintsTableNext.after("<tr><td class=\"MonitorComplaintsTextLeft\"><font class=\"MonitorComplaintsUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									MonitorComplaintsTableNext.after("<tr><td class=\"MonitorComplaintsTextRight\">"+msg+" : <font class=\"MonitorComplaintsUserName\">"+nickName+"</font></td></tr>");
								}
								//MonitorComplaintsTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									monitorComplaintsMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == monitorComplaintsEmobId){  // 左边
									MonitorComplaintsTableFirst.before("<tr><td class=\"MonitorComplaintsTextLeft\"><font class=\"MonitorComplaintsUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									MonitorComplaintsTableFirst.before("<tr><td class=\"MonitorComplaintsTextRight\">"+msg+" : <font class=\"MonitorComplaintsUserName\">"+nickName+"</font></td></tr>");
								}
								//MonitorComplaintsTableFirst.before("<tr><td>22222222</td></tr>");
								MonitorComplaintsTableFirst = $("#MonitorComplaintsTopTable2 tr").eq(0);
								MonitorComplaintsTableFirst.before("<tr><td class=\"MonitorComplaintsTextCenter\">— "+outTime+" —</td></tr>");
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
<style type="text/css">
	.MonitorComplaintsMain{
		width: 100%;
		height: 100%;
		
	}
	.MonitorComplaintsTop{
		float: left;
		width: 100%;
		height: 20%;
		border: solid 1px #D6D6D6;
	}
	.MonitorComplaintsTop2{
		float: left;
		width: 100%;
		height: 18%;
		border: solid 1px #D6D6D6;
		margin: 20px 0 20px 0;
	}
	.MonitorComplaintsCenter{
		float: left;
		width: 100%;
		height: 60%;
		border: solid 1px #D6D6D6;
		overflow-Y:scroll;
	}
	.MonitorComplaintsTop2Top{
		height: 50%;
		width: 100%;
		float: left;
	}
	.MonitorComplaintsTop2Center{
		height: 50%;
		width: 100%;
		float: left;
	}
	.MonitorComplaintsTextLeft{
		text-align: left;
		
	}
	.MonitorComplaintsTextRight{
		text-align: right;
		
	}
	.MonitorComplaintsTextCenter{
		text-align: center;
		height: 50px;
	}
	.MonitorComplaintsUserName{
		color: #3385FF;
	}
</style>		
  </head>
  
  <body>
    <div style="display: none;">
  	<jsp:include page="../jsp/public/nav.jsp"></jsp:include>
  </div>
  		<div class="MonitorComplaintsMain" id="MonitorComplaintsMain2" style="display: none;">
  			<div class="MonitorComplaintsTop">
  				<table id="MonitorComplaintsTopTable" width="85%" align="center">
  					
  				</table>
  			</div>
  			
  			<div class="MonitorComplaintsCenter" align="center">
  				<input type="button" value=" NEXT " onclick="getMonitorComplaintsByStatus('next');" />
   	<input type="button" value=" LAST " onclick="getMonitorComplaintsByStatus('last');" />
  				<table id="MonitorComplaintsTopTable2" width="85%" align="center">
  					
  				</table>
  			</div>
  		</div>
  	  <div class="MonitorComplaintsMain" id="MonitorComplaintsMain">
  	  <input type="button" value=" SELECT " id="" name="" onclick="getMonitorComplaintsTop();" />
  	  	<div class="MonitorComplaintsTop" align="center">
  	  		<table width="80%" height="100%" >
  	  			<tr>
  	  				<td height="10" id="monitorComplaintsService"></td>
  	  				<td id="monitorComplaintsClean"></td>
  	  				<td id="monitorComplaintsShop"></td>
  	  			</tr>
  	  			
  	  			<tr>
  	  				<td  height="10">维修</td>
  	  				<td>保洁</td>
  	  				<td>店家</td>
  	  			</tr>
  	  		</table>
  	  	</div>
  	  	<div class="MonitorComplaintsTop2" align="center">
  	  		<div class="MonitorComplaintsTop2Top" align="left">
  	  			投诉类型 &nbsp;&nbsp;
  	  			<input type="checkbox" value="6" name="monitorComplaintsCheckBox" />保洁
  	  			<input type="checkbox" value="5" name="monitorComplaintsCheckBox" />维修
  	  			<input type="checkbox" value="2" name="monitorComplaintsCheckBox" />快店
  	  			<input type="checkbox" value="1" name="monitorComplaintsCheckBox" />外卖 
  	  		</div>
  	  		
  	  		<div class="MonitorComplaintsTop2Center" align="left">
  	  			开始时间 <input type="text" value="" name="" id="monitorComplaintsStartTime" />
  	  			结束时间 <input type="text" value="" name="" id="monitorComplaintsEndTime" />
  	  			
  	  			 <input type="text" value="" name="" id="monitorComplaintsTextName" style="margin-left: 150px;" /> <input onclick="monitorComplaintsClick();" type="button"" value="  搜  索  " name="" id="" />
  	  		</div>
  	  	</div>
  	  	<div class="MonitorComplaintsCenter" align="center">
  	  		<table id="MonitorComplaintsCenterTable" width="85%">
  	  		
  	  		</table>
  	  	</div>
  	  </div>
  </body>
</html>
