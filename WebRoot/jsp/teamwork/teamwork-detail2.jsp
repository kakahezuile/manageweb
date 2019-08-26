<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
	
	String timeType=request.getParameter("timeType");
	String month2=request.getParameter("month");
	String community_id=request.getParameter("community_id");
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
		<title>注册用户数_小间运营系统</title>
		
		<link
			href="${pageContext.request.contextPath }/easymob-webim1.0/css/webim.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/teamwork/teamwork-detail.js?version=<%=versionNum %>"></script>
		
		
		<script type="text/javascript">
	
</script>
	</head>
	<body>
	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${communityId }">
	<input type="hidden" id="month_id" value="<%=month2 %>"/>
			<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/teamwork-head.jsp" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../public/teamwork-left.jsp" />
				<div class="right-body" style="border:none;">
					<div class="static-type">
						<ul>
							<li><a href="${pageContext.request.contextPath }/stat/teamwork/stat.do?modules=3&shopType=2&communityId=${communityId}" class="select" >快店</a></li>
							<!--<li class="static-more"><a href="${pageContext.request.contextPath }/jsp/teamwork/teamwork-more.jsp?communityId=${communityId }" >查看更多...</a></li>
						--></ul>
					</div>
					<div class="teamwork-title" style="padding:10px 0 10px 15px;">
						<div id="teamwork_detail_id">快店>>交易详情</div>
					</div>	
					<div class="teamwork-table">
						<table id="statistics_list_id">
							<tr>
								<th>下单时间</th>
								<th>买家昵称(待)</th>
								<th>交易金额</th>
								<th>结单时间</th>
								<th>订单状态</th>
								<th>支付方式</th>
								<th>分成比例</th>
								<th>收益</th>
								<th>交易记录</th>
							</tr>
							
							<c:forEach items="${orderList }" var="order" varStatus="status"  >
							
							<tr class="<c:if test="${status.index%2==0 }">odd</c:if><c:if test="${status.index%2==1 }">even</c:if>" >
								<td>
									<p>${order.startDateY}</p>
									<p>${order.startDateH}</p>
								</td>
								<td>
									<p>${order.nickname }</p>
									<p> ${order.userFloor }${order.userUnit}${order.room}</p>
								</td>
								<td>${order.orderPrice}</td>
								<td>${order.endTimeH}</td>
								<td>
								
									<c:choose>  
									  
									   <c:when test="${order.status=='new'}">    
									            新建订单  
									   </c:when>  
									   <c:when test="${order.status=='canceled'}">    
									           取消
									   </c:when>  
									   <c:when test="${order.status=='renounce'}">    
									         放弃
									   </c:when>  
									   <c:when test="${order.status=='paid'}">    
									         已支付
									   </c:when>  
									   <c:when test="${order.status=='ended'}">    
									         已结单
									   </c:when>  
									     
									   <c:otherwise> 
									        进行中  
									   </c:otherwise>  
									</c:choose>
                                 

                                </td>
								<td>
								
								
								<c:choose>  
									   <c:when test="${order.online=='yes'}">    
									            在线支付  
									   </c:when>  
									   <c:when test="${order.online=='no'}">    
									           线下支付
									   </c:when>  
									     
									   <c:otherwise> 
									      未支付
									   </c:otherwise>  
									</c:choose>
								</td>
								<td>${rate }%</td>
								<td>
								
								  <c:choose>  
									   <c:when test="${order.status=='ended'}">  
									     
									          <fmt:formatNumber type="number" value="${order.orderPrice*(rate/100)}" maxFractionDigits="2"/>

									   </c:when>  
									     
									   <c:otherwise> 
									   0
									   </c:otherwise>  
									</c:choose>
								
								
								</td>
								<td><a href="javascript:;" onclick="getMessageListFromUser3('${order.emobIdUser}','${order.emobIdShop}')">交易记录</a></td>
							</tr>
							
							</c:forEach>
							
							
						</table>
						
						<div class="teamwork-chat-list" id="statistics_list_id_2" style="display: none;">
						</div>
					</div>

				</div>	
			</div>
		</section>
	</body>
	<script type="text/javascript">
	
	
	
	
	
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
	
	
	
	
	function detail(){
	 document.getElementById("statistics_list_id_2").style.display="none";
     document.getElementById("statistics_list_id").style.display="";
	
	 document.getElementById("statistics_list_id").innerHTML="快店>>交易详情";
	
	}
	
	
	
	function getMessageListFromUser3(from,to){
	schedule();
	//var path = "/api/v1/usersMessages?messageFrom="+from+"&messageTo="+to;
 var path = "/api/v1/usersMessages/test?messageFrom="+from+"&messageTo="+to;
//	var path = "/api/v1/usersMessages/"+from+"/"+to+"/1438704000000/1440172800000";
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
						    "EXPKey_address":data[i].EXPKey_address,
							"serial":data[i].serial,
							"nickname":data[i].nickname,
							"avatar":data[i].avatar,
							"content":data[i].content,
							"CMD_CODE":data[i].CMD_CODE,
							"CMD_DETAIL":data[i].CMD_DETAIL
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
  document.getElementById("statistics_list_id_2").style.display="";
  document.getElementById("statistics_list_id").style.display="none";
  document.getElementById("statistics_list_id").innerHTML="快店>><a  href=\"javascript:;\"  onclick=\"detail();\">交易详情</a>>交易记录";


	var myThis = $("#statistics_list_id_2");
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
				if(ext.CMD_CODE=='200'||ext.CMD_CODE=='201'||ext.CMD_CODE=='202'||ext.CMD_CODE=='203'||ext.CMD_CODE=='204'||ext.CMD_CODE=='205'||ext.CMD_CODE=='210'){
					var li2=ext.CMD_DETAIL;
					try {
		                var li3= JSON.parse(li2);
		                var li4=li3.orderDetailBeanList;
		                var lilL="";
		                lilL+="<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box order-bg\" style=\"max-width:220px;\">	<div class=\"right-speak\" >订单号："+ext.serial+"</div>";
                        lilL+="<div class=\"order-tip\">"+messageContent+"</div>";
		                for ( var j = 0; j <li4.length; j++) {
		                			                	lilL+="<div class=\"order-list\"><b class=\"name\">"+li4[j].serviceName+"</b><b class=\"count\">X"+li4[j].count+"</b><b class=\"price\">"+li4[j].price
		                	+"</b></div>";
						}
						
						if(ext.CMD_CODE=='203'){
						   lilL+="<div>客户："+li3.nickname+"</div>";
						   lilL+="<div>地址："+li3.address+"</div>";
						   
						   if(li3.payMethod=='0'){
						      lilL+="<div>支付方式：在线支付</div>";
						   }else if((li3.payMethod=='1')){
						      lilL+="<div>支付方式：线下支付</div>";
						   }
						    var myDate=new Date(timestamp);
		                	var sen=   myDate.getFullYear() + '-'
						+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
						+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
						   lilL+="<div>时间："+sen+"</div>";
						}
		                lilL+=" <div class=\"order-totle\"> 总计:<b>" +li3.totalPrice+
		                			"</b></div>" +
		                			"" +
		                			"</div></div><span class=\"chat-me\">&nbsp;</span></div> ";
		                myThis.append(lilL);
					}catch (e) {
					} 
				}else{
					myThis.append("<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\" style=\"max-width:220px;\"><div class=\"right-speak\" id=\""+to+"_"+from+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				}
				
				
	
											//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + " : 我 </font><br/>");
			} else {
				if(ext.CMD_CODE=='200'||ext.CMD_CODE=='201'||ext.CMD_CODE=='202'||ext.CMD_CODE=='203'||ext.CMD_CODE=='204'||ext.CMD_CODE=='205'||ext.CMD_CODE=='206'||ext.CMD_CODE=='207'||ext.CMD_CODE=='208'||ext.CMD_CODE=='209'||ext.CMD_CODE=='210'){
					var li2=ext.CMD_DETAIL;
					try {
		                var li3= JSON.parse(li2);
		                var li4=li3.orderDetailBeanList;
		                var lilL="";
		                lilL+="<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box order-bg\" style=\"max-width:220px;\">	<div class=\"left-speak\" >订单号："+ext.serial+"</div>";
                        lilL+="<div class=\"order-tip\">"+messageContent+"</div>";
		                for ( var j = 0; j <li4.length; j++) {
		                			                	lilL+="<div class=\"order-list\"><b class=\"name\">"+li4[j].serviceName+"</b><b class=\"count\">X"+li4[j].count+"</b><b class=\"price\">"+li4[j].price
		                	+"</b></div>" ;
						}
						
						
						if(ext.CMD_CODE=='203'){
						   lilL+="<div class=\"order-list\">客户："+li3.nickname+"</div>";
						   lilL+="<div class=\"order-list\">地址："+li3.address+"</div>";
						   
						   if(li3.payMethod=='0'){
						      lilL+="<div class=\"order-list\">支付方式：在线支付</div>";
						   }else if((li3.payMethod=='1')){
						      lilL+="<div class=\"order-list\">支付方式：线下支付</div>";
						   }
						    var myDate=new Date(timestamp);
		                	var sen=   myDate.getFullYear() + '-'
						+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
						+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
						   lilL+="<div class=\"order-list\">时间："+sen+"</div>";
						}
		                lilL+="<div class=\"order-totle\"> 总计:<b>" +li3.totalPrice+
		                			"</b></div>" +
		                			"" +
		                			"</div></div><span class=\"chat-me\">&nbsp;</span></div> ";
		                myThis.append(lilL);
					}catch (e) {
					} 
				}else{
				 myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><div class=\"left-speak\" id=\""+from+"_"+to+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				
				}
				
				  				
									
			}
			
			
			
			}
			
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
	
	
	
	/* test('<%=timeType%>');*/
	
	</script>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>
