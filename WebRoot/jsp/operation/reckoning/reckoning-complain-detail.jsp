<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<%


String to=request.getParameter("to");
String from=request.getParameter("from");
String isMoreThan=request.getParameter("isMoreThan");
String complaint_id=request.getParameter("complaint_id");

 %>
<!doctype html>
<html class="">

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
<title>用户统计_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">


<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/operation.js"></script>
</head>
<body>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/reckoning-left.jsp"/>
			<div class="right-body">
				<div class="monitoring-detail-title">
	  				<table width="85%" align="center" id="MonitorComplaintsTopTable">
						<tr class="monitoring-detail-th" id="complaint_id">
							<td>用户</td>
							<td>用户电话</td>
							<td>投诉对象</td>
							<td>投诉内容</td>
							<td>处理客服</td>
						</tr>
						<tr>
							<td>曲亮</td>
							<td></td>
							<td>0小马哥(保洁)</td>
							<td>不满意</td>
							<td>李小静</td>
						</tr>
					</table>
	  			</div>
				<div class="reckoning-body" >
					<div class="sensitiveCenter">
						<table align="center" id="MonitorComplaintsTopTable2">
							<tr>
								<td class="MonitorComplaintsTextCenter">&mdash; 2015-04-08 11:03:19 &mdash;</td>
							</tr>
							<tr>
								<td class="MonitorComplaintsTextLeft"><font class="MonitorComplaintsUserName">曲亮</font> : 图图</td></tr>
							<tr>
								<td class="MonitorComplaintsTextCenter">&mdash; 2015-04-08 11:03:32 &mdash;</td>
							</tr>
							<tr>
								<td class="sensitiveTextRight">看看 : <font class="sensitiveUserName">老干妈</font></td>
							</tr>
							<tr>
								<td class="MonitorComplaintsTextLeft"><font class="MonitorComplaintsUserName">曲亮</font> : 图裂了</td>
							</tr>
							<tr>
								<td class="MonitorComplaintsTextCenter">&mdash; 2015-04-08 11:03:59 &mdash;</td>
							</tr>
							<tr>
								<td class="MonitorComplaintsTextLeft"><font class="MonitorComplaintsUserName">曲亮</font> : 图裂了</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script type="text/javascript">
		var communityId=window.parent.document.getElementById("community_id_index").value;
	var transactionMap={};
	var messageArray;
	function monitorComplaintsServiceClick(){
	
	    var ph="<%= path%>/api/v1/communities/"+communityId+"/complaints/webIm/getComplain?complaint_id=<%=complaint_id%>";
		$.ajax({

			url: ph,

			type: "GET",

			dataType:'json',

			success:function(data2){
			    data=data2.info;
				var repair_overman = $("#complaint_id");
			    repair_overman.empty();
			    repair_overman.append("<tr class=\"monitoring-detail-th\" id=\"complaint_id\"><td>用户</td><td>用户电话</td>	<td>投诉对象</td><td>投诉内容</td><td>处理客服</td></tr>");
			    repair_overman.append("<tr class=\"monitoring-detail-th\" id=\"complaint_id\"><td>"+data.userName+"</td><td>"+data.phone+"</td>	<td>"+data.shopName+"</td><td>"+data.detail+"</td><td>"+data.nickname+"</td></tr>");
			 test(data.emobIdFrom,data.emobIdTo,(data.occurTime*1000));
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
		function test(from,to,complaintsTime){
			var path = "<%= path%>/api/v1/communities/"+communityId+"/monitorComplaints/messages?emobIdUser="+from+"&emobIdShop="+to+"&timestamp="+complaintsTime+"&isMoreThan=false";
		
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
	function getMessageListFromUser3(from,to){
		
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
					transactionMap[from+to] = messageArray;
					getMyTouSuMessageList3(from,to);
				}
			},
			
			error:function(er){
				
				
			}
			
		});
	}
	
	function getMyTouSuMessageList3(my,to) {
		var myThis = $("#MonitorComplaintsTopTable2");
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
	//getMessageListFromUser3("<%=from%>","c84258e9c39059a89ab77d846ddab909");
	monitorComplaintsServiceClick();
</script>
</body>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>