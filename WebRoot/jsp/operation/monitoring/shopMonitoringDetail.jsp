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
	<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath }/easymob-webim1.0/css/webim.css?version=<%=versionNum %>" />
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
<style type="text/css">

.upload-master-face-bg{
	background:url("../../../images/public/bg-blank-60.png") repeat;
	position:fixed;
	top:0;
	width:100%;
	height:100%;
	z-index:21;
	left:0;
}
.loadingbox{
    height: 32px;
    width:32px;
    left: 50%;
    margin-left: -16px;
    margin-top: -16px;
    position: absolute;
    top: 50%;
    z-index: 22;
}
</style>
<script type="text/javascript">
	
	var communityId = window.parent.document.getElementById("community_id_index").value;
	
	var advisoryHistoryPageNum = 1;
	
	var advisoryHistoryPageSize = 10;
	
	var advisoryHistoryList;
	
	var advisoryHistoryMinTime;
	
	var advisoryHistoryMaxTime;
	
	var advisoryHistoryEmobId;
	
	var advisoryHistoryIndex;
	
	var advisoryHistoryFirst = 0;
	
	var advisoryHistoryLast = 0;
	
	var advisoryHistoryNext = 0;
	
	var advisoryHistoryPrev = 0;
	
	var advisoryHistoryPageCount = 0;
	
	var advisoryHisotrySum = 0;
	var transactionMap={};
	var messageArray;
	function getAdvisoryHistory(){
		
		advisoryStartTime = $("#advisoryStartTime").val();
		
		advisoryEndTime = $("#advisoryEndTime").val();
		
		advisoryName = $("#advisoryName").val();
		
		var path = "<%= path%>/api/v1/communities/"+communityId+"/advisoryHistory?pageNum="+advisoryHistoryPageNum+"&pageSize="+advisoryHistoryPageSize+"&startTime="+advisoryStartTime+"&endTime="+advisoryEndTime+"&name="+advisoryName;
		advisoryHisotryTable = $("#advisoryHisotryTable");
		$.ajax({

				url: path,

				type: "GET",

				dataType:"json",

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
					
						advisoryHisotrySum = data.info.rowCount; // 总条数
				
						advisoryHisotryPageFirst = data.info.first; // 首页
				
						advisoryHisotryPageLast = data.info.last; // 尾页
				
						advisoryHisotryPageNext = data.info.next; // 下一页
				
						advisoryHisotryPageNum = data.info.num; // 当前页
				
						advisoryHisotryPrev = data.info.prev; // 上一页
					
						advisoryHisotryPageCount = data.info.pageCount; // 总页数
						
						$("#advisoryHistoryPageNum").html(advisoryHisotryPageNum);
					
						$("#advisoryHistorySum").html(advisoryHisotrySum);
					
						$("#advisoryHistoryPageCount").html(advisoryHisotryPageCount);
						
						advisoryHisotryTable.html("");
						advisoryHisotryTable.append("<tr><th>用户</th><th>用户电话</th><th>咨询客服</th><th>咨询时间</th><th>详情</th></tr>");
						data = data.info.pageData;
						advisoryHistoryList = data;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							var classNmae="even";
							outTime = getSmpFormatDateByLong(data[i].advisoryTime * 1000);
							if(i%2==0){
								classNmae="even";
							}else{
								classNmae="odd";
							}
							advisoryHisotryTable.append("<tr class=\""+classNmae+"\"><td>"+data[i].userName+"</td><td>"+data[i].phone+"</td><td>"+data[i].agentName+"</td><td>"+outTime+"</td><td><a href=\"javascript:void(0);\" onclick=\"getMessageListFromUser3('"+data[i].emobIdUser+"','"+data[i].emobIdAgent+"');\">咨询详情</a></td></tr>");
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
		advisoryHistoryTopTable.append("<tr><th>用户</th><th>用户电话</th><th>咨询客服</th><th>咨询时间</th></tr>");
		advisoryHistoryTopTable.append("<tr><td>"+thisData.userName+"</td><td>"+thisData.phone+"</td><td>"+thisData.agentName+"</td><td>"+outTime+"</td></tr>");
		from = thisData.emobIdUser;
		to = thisData.emobIdAgent;
		advisoryTime = thisData.advisoryTime * 1000;
		var path = "<%= path%>/api/v1/communities/"+communityId+"/advisoryHistory?emobIdUser="+from+"&emobIdShop="+to+"&timestamp="+advisoryTime+"&isMoreThan=true";
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
			var path = "<%= path%>/api/v1/communities/"+communityId+"/advisoryHistory/messages?emobIdUser="+from+"&emobIdShop="+to;
			var text;
			if(status == "next"){
				//advisoryHistoryTableNext.after("<tr><td>11111111</td></tr>");
				path += "&timestamp="+advisoryHistoryMaxTime;
				text = "true";
			}else if(status == "last"){
				//advisoryHistoryTableFirst.before("<tr><td>22222222</td></tr>");
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
								//advisoryHistoryTableNext.after("<tr><td>11111111</td></tr>");
							}else if(status == "last"){
								if(i == len-1){
									advisoryHistoryMinTime = data[i].timestamp;
								}	
								
								
								if(data[i].messageFrom == advisoryHistoryEmobId){  // 左边
									advisoryHistoryTableFirst.before("<tr><td class=\"advisoryHistoryTextLeft\"><font class=\"advisoryHistoryUserName\">"+nickName+"</font> : "+msg+"</td></tr>");
								}else{ // 右边
									advisoryHistoryTableFirst.before("<tr><td class=\"advisoryHistoryTextRight\">"+msg+" : <font class=\"advisoryHistoryUserName\">"+nickName+"</font></td></tr>");
								}
								//advisoryHistoryTableFirst.before("<tr><td>22222222</td></tr>");
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
			//alert(advisoryHistoryTableFirst);
		}

//日历控件
    $(function () {
        $("#advisoryStartTime").calendar({
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
        $("#advisoryEndTime").calendar();
    });

  function selectRecordMainainDuan(){
  
      var startTime=document.getElementById("advisoryStartTime").value+" 00:00:00";
       var endTime=document.getElementById("advisoryEndTime").value+" 00:00:00";
       var sort = document.getElementById("repair_shops_sort").value;
       maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);
		
  }
  
  function getAdvisoryHistoryList(num){
		if(num == -1){ // 上一页
			if(parseInt(advisoryHisotryPageNum) != 1){
			
	        	advisoryHistoryPageNum=	parseInt(advisoryHisotryPageNum)-parseInt(1); 
				//advisoryHisotryPageNum = advisoryHistoryPrev;
				getAdvisoryHistory();
			}
			
		}else if(num == -2){ // 下一页
			if(parseInt(advisoryHisotryPageNum) < parseInt(advisoryHisotryPageCount)){
			
				advisoryHistoryPageNum=	parseInt(advisoryHisotryPageNum)+parseInt(1); 
				getAdvisoryHistory();
			}
			
		}else if(num == -3){ // 首页
			if(parseInt(advisoryHisotryPageNum) != 1){
				advisoryHistoryPageNum = 1;
				getAdvisoryHistory();
			}
		}else if(num == -4){ // 尾页
			if(parseInt(advisoryHisotryPageNum) !=parseInt(advisoryHisotryPageCount) ){
				advisoryHistoryPageNum = advisoryHisotryPageCount;
				getAdvisoryHistory();
			}
		}
		
	}
	
	
	
	function schedule(){
	$("#add-price-box").attr("style","display:block");
	$("#upload-master-face-bg").attr("style","display:block");
	
}

function onSchedule(){
	$("#add-price-box").attr("style","display:none");
	$("#upload-master-face-bg").attr("style","display:none");
}
	
	
	function getMessageListFromUser3(from,to){
		schedule();
		var path = "/api/v1/usersMessages?messageFrom="+from+"&messageTo="+to;
		$.ajax({
			
			url: path,
			
			type: "GET",
			
			dataType:"json",
			
			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					data = data.info;		
					var len = data.length;
					messageArray = new Array();
					for(var i = 0 ; i < len ; i++){
						var message = {
							"from":data[i].messageFrom ,
							"to":data[i].messageTo,
							"msg":data[i].msg,
							"timestamp":data[i].timestamp,
							"type":data[i].msgType,
							"data":data[i].msg,
							"url":data[i].url,
							"ext":{
								"nickname":data[i].nickname,
								"avatar":data[i].avatar,
								"content":data[i].content
							}
						};
						
						messageArray.push(message);
					}
					onSchedule();
					transactionMap[from+to] = messageArray;
					getMyTouSuMessageList3(from,to);
				}
			},
			
			error:function(er){
				onSchedule();
				
			}
			
		});
	}
	
	function getMyTouSuMessageList3(my,to) {
		var myThis = $("#advisoryHisotryTable");
		myThis.html("");
		var myClassName = my+to;
		//tousuThisUser = myClassName;
	
		if (transactionMap[myClassName] != undefined) {
			var myTouSuList = transactionMap[myClassName];
			for ( var i = 0; i < myTouSuList.length; i++) {
				var message = myTouSuList[i];
				var from = message.from;//消息的发送者
				var mestype = message.type;//消息发送的类型是群组消息还是个人消息
				var messageContent = message.data;//文本消息体
				var timestamp=message.timestamp;
				  var myDate=new Date(timestamp);
				    var sen=   myDate.getFullYear() + '-'
							+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
							+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
					myThis.append("<div class=\"chat-time\"><span>"+sen+"</span></div>");
				var ext=message.ext;
				//   根据消息体的to值去定位那个群组的聊天记录
				var room = message.to;
				if (mestype!="txt") {
					var url=message.url;
					if(mestype=="audio"){
						myThis.append("<div class=\"text-left\"><img src=\"/"+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\">" +
								"<div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\">  "+
                                  "<source src=\""+url+"\" /> "+
                                  "</audio></div> </div>" +
								"</div></div><span class=\"chat-me\">&nbsp;</span></div>");

					}else {
						myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><img src=\""+url+"\"></img></div></div><span class=\"chat-me\">&nbsp;</span></div>");

					}
			
		    } else {
					if (from == to) {
							
						myThis.append("<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\" style=\"max-width:220px;\"><div class=\"right-speak\" id=\""+to+"_"+from+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
						//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + " : 我 </font><br/>");
					} else {
						myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><div class=\"left-speak\" id=\""+from+"_"+to+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				}
				
				
				
				}
				
			}
		}
	}
	
</script>			
  </head>
  
  <body>
  
  	<div style="display: none;">
  		<jsp:include page="../../../jsp/public/nav.jsp"></jsp:include>
  	</div>
  	
  	
  		<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
  	
  	
  	
  	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/monitoring-left.jsp"/>
			<div class="right-body">
			   	<div class="advisoryMain" id="advisoryMain" align="center">
				   	<div class="advisoryMain-title">
				    	<table>
				    		<tr>
				    			<td><input type="text" class="timeText" value="" readonly="readonly" name="advisoryStartTime" id="advisoryStartTime" /> 开始时间</td>
				    			<td><input type="text" class="timeText" value="" readonly="readonly" name="advisoryEndTime" id="advisoryEndTime" />结束时间</td>
				    			<td><input type="text" placeholder="投诉人/被投诉人" value="" class="searchText" name="advisoryName" id="advisoryName" />投诉人</td>
				    			<td><input type="button" value=" 搜  索 " onclick="getAdvisoryHistory();" /></td>
				    		</tr>
				    	</table>
	    			</div>
	    			<div class="advisoryMain-content">
				    	<table id="advisoryHisotryTable">
				    		<tr>
				    			<th>用户</th>
				    			<th>用户电话</th>
				    			<th>咨询客服</th>
				    			<th>咨询时间</th>
				    			<th>详情</th>
				    			
				    		</tr>
				    	</table>
				    </div>
				    
				    	<div class="divide-page" align="center">
			<a href="javascript:void(0);" onclick="getAdvisoryHistoryList(-3);">首页</a>
			<a href="javascript:void(0);" onclick="getAdvisoryHistoryList(-1);">上一页</a>
			当前第 <font id="advisoryHistoryPageNum"></font> 页
			共 <font id="advisoryHistoryPageCount"></font> 页
			共 <font id="advisoryHistorySum"></font> 条
			
			<a href="javascript:void(0);" onclick="getAdvisoryHistoryList(-2);">下一页</a>
			<a href="javascript:void(0);" onclick="getAdvisoryHistoryList(-4);">尾页</a>
		</div>
   	 		</div><!--
    

    <div class="" id="advisoryMain2" align="center"  style="display: none;">
    	    <div class="advisoryHistoryTop">
    		<table id="advisoryHistoryTopTable">
    			<tr>
    				<th>用户</td>
    				<th>用户电话</th>
    				<th>咨询客服</th>
    				<th>咨询时间</th>
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
			--></div>
		</div>
	</section>
    <script type="text/javascript">
    getAdvisoryHistory();
    montiorin(3);
    </script>
  </body>
</html>
