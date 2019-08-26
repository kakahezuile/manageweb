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
<title>投诉_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js"></script>
<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"	rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
			
<script type="text/javascript">
	$(document).ready(function () {
		getMonitorComplaintsTop();
	});
	
	var communityId = window.parent.document.getElementById("community_id_index").value;
	
	var monitorComplaintsPageSize = 10;
	
	var monitorComplaintsPageNum = 1;
	
	var monitorComplaintsList;
	
	var monitorComplaintsMinTime;
		
	var monitorComplaintsMaxTime;
	
	var monitorComplaintsEmobId;
		
	var monitorComplaintsIndex;
	
	var monitorComplaintsType;
	
	var monitorComplaintsFirst = 0;
	
	var monitorComplaintsLast = 0;
	
	var monitorComplaintsNext = 0;
	
	var monitorComplaintsPrev = 0;
	
	var monitorComplaintsPageCount = 0;
	
	var monitorComplaintsSum = 0;
	
	function getMonitorComplaintsTop(){
	
		var path = "<%= path%>/api/v1/communities/"+communityId+"/monitorComplaints/top";
		
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
				//	$("#monitorComplaintsClean").html(cleanCount);
					$("#monitorComplaintsShop").html(shopCount);
				}
			},

			error:function(er){
			
			
			}

		});
	}
	function getMonitorComplaintsCenter(sort,startTime,endTime){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/monitorComplaints?sort="+sort+"&startTime="+startTime+"&endTime="+endTime+"&pageNum="+monitorComplaintsPageNum+"&pageSize="+monitorComplaintsPageSize;
		monitorComplaintsTextName = $("#monitorComplaintsTextName").val();
		path += "&name="+monitorComplaintsTextName;
		MonitorComplaintsCenterTable = $("#MonitorComplaintsCenterTable");
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
				
					monitorComplaintsSum = data.info.rowCount; // 总条数
				
					monitorComplaintsPageFirst = data.info.first; // 首页
				
					monitorComplaintsPageLast = data.info.last; // 尾页
				
					monitorComplaintsPageNext = data.info.next; // 下一页
				
					monitorComplaintsPageNum = data.info.num; // 当前页
				
					monitorComplaintsPrev = data.info.prev; // 上一页
					
					monitorComplaintsPageCount = data.info.pageCount; // 总页数
					
					$("#monitorComplaintsPageNum").html(monitorComplaintsPageNum);
					
					$("#monitorComplaintsSum").html(monitorComplaintsSum);
					
					$("#monitorComplaintsPageCount").html(monitorComplaintsPageCount);
					
					data = data.info.pageData;
					monitorComplaintsList = data;
					var len = data.length;
					MonitorComplaintsCenterTable.html("");
					MonitorComplaintsCenterTable.append("<tr><th>用户</th><th>用户电话</th><th>投诉对象</th><th>投诉内容</th><th>投诉时间</th><th>投诉结果</th><th>查看对话</th></tr>");
					for(var i = 0 ; i < len ; i++){
						outTime = getSmpFormatDateByLong(data[i].complaintsTime * 1000);
						var status;
						var className;
						status = data[i].complaintsStatus;
						if(status == "review"){
							status = "待处理";
						}else if(status == "ongoing"){
							status = "处理中";
						}else if(status == "ended"){
							status = "完成";
						}
						if(i%2==0){
							className="even";
						}else{
							className="odd";
						}
						MonitorComplaintsCenterTable.append("<tr class=\""+className+"\"><td>"+data[i].nickName+"</td><td>"+data[i].phone+"</td><td>"+data[i].shopName+"("+data[i].sortName+")</td><td>"+data[i].complaintsDetail+"</td><td>"+outTime+"</td><td>"+status+"</td><td><a href=\"javascript:void(0);\" onclick=\"monitorComplaintsServiceClick("+i+",'complaints');\" >投诉对话</a> <a href=\"javascript:void(0);\" onclick=\"monitorComplaintsServiceClick("+i+",'service');\" >服务对话</a></td></tr>");
						
					}
				}
			},

			error:function(er){
			
			
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
		$("#MonitorComplaintsTopTable").append("<tr class=\"monitoring-detail-th\"><td>用户</td><td>用户电话</td><td>投诉对象</td><td>投诉内容</td><td>投诉时间</td><td>投诉结果</td></tr>");				
		$("#MonitorComplaintsTopTable").append("<tr><td>"+data.nickName+"</td><td>"+data.phone+"</td><td>"+data.shopName+"("+data.sortName+")</td><td>"+data.complaintsDetail+"</td><td>"+outTime+"</td><td>"+status+"</td></tr>");
		
		var path = "<%= path%>/api/v1/communities/"+communityId+"/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to+"&timestamp="+complaintsTime+"&isMoreThan="+text;
		
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
			var path = "<%= path%>/api/v1/communities/"+communityId+"/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to;
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
			
			
				}

			});
			//alert(MonitorComplaintsTableFirst);
		}

//日历控件
    $(function () {
        $("#monitorComplaintsStartTime").calendar({
            controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true,                                       // 目标对象是否设为只读，默认：true
            upperLimit: new Date(),                               // 日期上限，默认：NaN(不限制)
            lowerLimit: new Date("2011/01/01"),                   // 日期下限，默认：NaN(不限制)
            callback: function () {                               // 点击选择日期后的回调函数
             //   alert("您选择的日期是：" + $("#txtBeginDate").val());
            }
        });
        $("#monitorComplaintsEndTime").calendar();
    });

  function selectRecordMainainDuan(){
  
      var startTime=document.getElementById("monitorComplaintsStartTime").value+" 00:00:00";
       var endTime=document.getElementById("monitorComplaintsEndTime").value+" 00:00:00";
       var sort = document.getElementById("repair_shops_sort").value;
       maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);
		
  }


	function getMonitorComplaintsList(num){
		if(num == -1){ // 上一页
			if(monitorComplaintsPageNum != 1){
				monitorComplaintsPageNum = monitorComplaintsPrev;
				monitorComplaintsClick();
			}
			
		}else if(num == -2){ // 下一页
			if(monitorComplaintsPageNum < monitorComplaintsPageSize){
				monitorComplaintsPageNum = monitorComplaintsPageNext;
				monitorComplaintsClick();
			}
			
		}else if(num == -3){ // 首页
			if(monitorComplaintsPageNum != monitorComplaintsPageFirst){
				monitorComplaintsPageNum = monitorComplaintsPageFirst;
				monitorComplaintsClick();
			}
		}else if(num == -4){ // 尾页
			if(monitorComplaintsPageNum != monitorComplaintsPageLast){
				monitorComplaintsPageNum = monitorComplaintsPageLast;
				monitorComplaintsClick();
			}
		}
		
	}
</script>	
	
  </head>
  
  <body>
	<div style="display: none;">
		<jsp:include page="../../../jsp/public/nav.jsp"></jsp:include>
	</div>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/monitoring-left.jsp"/>
			<div class="right-body">
			<div class="MonitorComplaintsMain" id="MonitorComplaintsMain">
		  	  	<div class="monitoring-totle" align="center">
		  	  		<table>
		  	  			<tr class="monitor-date">
		  	  				<td id="monitorComplaintsService"></td>
		  	  				<td id="monitorComplaintsShop"></td>
		  	  			</tr>
		  	  			
		  	  			<tr class="monitor-type">
		  	  				<td>维修</td>
		  	  				<td>店家</td>
		  	  			</tr>
		  	  		</table>
		  	  	</div>
		  	  	<div class="monitoring-filter">
		  	  		<div class="monitoring-type" align="left">
		  	  			投诉类型 &nbsp;&nbsp;
		  	  			<input type="checkbox" value="6" name="monitorComplaintsCheckBox" />保洁
		  	  			<input type="checkbox" value="5" name="monitorComplaintsCheckBox" />维修
		  	  			<input type="checkbox" value="2" name="monitorComplaintsCheckBox" />快店
		  	  			<input type="checkbox" value="1" name="monitorComplaintsCheckBox" />外卖 
		  	  		</div>
		  	  		
		  	  		<div class="monitoring-time" align="left">
		  	  			<label>开始时间</label> <input readonly="readonly" id="monitorComplaintsStartTime" >
		  	  			<label>结束时间</label><input readonly="readonly" id="monitorComplaintsEndTime">
		  	  			<input type="text" value="" name="" placeholder="用户名字/客服名字" id="monitorComplaintsTextName" style="margin-left: 150px;" /> <input onclick="monitorComplaintsClick();" type="button"" value="  搜  索  " name="" id="" />
		  	  		</div>
		  	  	</div>
		  	  	<div class="monitoring-list">
		  	  		<table id="MonitorComplaintsCenterTable">
		  	  		
		  	  		</table>
		  	  	</div>
		  	  	
		  	  	<div class="divide-page" align="center">
			<a href="javascript:void(0);" onclick="getMonitorComplaintsList(-3);">首页</a>
			<a href="javascript:void(0);" onclick="getMonitorComplaintsList(-1);">上一页</a>
			当前第 <font id="monitorComplaintsPageNum"></font> 页
			共 <font id="monitorComplaintsPageCount"></font> 页
			共 <font id="monitorComplaintsSum"></font> 条
			
			<a href="javascript:void(0);" onclick="getMonitorComplaintsList(-2);">下一页</a>
			<a href="javascript:void(0);" onclick="getMonitorComplaintsList(-4);">尾页</a>
		</div>
		  	</div>
			<div class="MonitorComplaintsMain" id="MonitorComplaintsMain2" style="display: block;">
	  			<div class="monitoring-detail-title">
	  				<table id="MonitorComplaintsTopTable" width="85%" align="center">
	  					
	  				</table>
	  			</div>
	  			
	  			<!--<div class="MonitorComplaintsCenter" align="center">
	  				<input type="button" value=" NEXT " onclick="getMonitorComplaintsByStatus('next');" />
	   				<input type="button" value=" LAST " onclick="getMonitorComplaintsByStatus('last');" />
	  				<table id="MonitorComplaintsTopTable2" width="85%" align="center">
	  					
	  				</table>
	  			</div>
  			--></div>
  	  
			</div>
		</div>
	</section>
  		<script type="text/javascript">
  		monitorComplaintsClick();
  		montiorin(2);
  		</script>
  </body>
</html>
