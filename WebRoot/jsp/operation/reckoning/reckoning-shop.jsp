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


String sort=request.getParameter("sort");
String start=request.getParameter("startTime");
String endTime=request.getParameter("endTime");

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
		<title>用户统计_小间运营系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">


		<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
		<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
	</head>


	<script type="text/javascript">
	
	var communityId = window.parent.document
			.getElementById("community_id_index").value;
	
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
			
				alert(er);
			
			}

		});
	}
	</script>
	<body>

		<input id="shop_sort" type="hidden" value="<%=sort %>" />
		<input id="master_repir_startTime" type="hidden" />
		<input id="master_repir_endTime" type="hidden" />
		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../../public/reckoning-left.jsp" />
				<div class="right-body">
					<div class="reckoning-title">
						
						<font id="test_day">3月第4周</font>
						<b>快店</b>结款明细
						<a
							href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-history.jsp?sort=<%=sort %>">结款历史</a>
					</div>
					<div class="reckoning-body">
						<table id="reckonign_shop">
						</table>
					</div>
					<div class="divide-page" 
						style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


						<input type="hidden" id="master_repair_datai_PageNum_get" />
						<input type="hidden" id="master_repair_datai_PageSize_get" />

						<a href="javascript:void(0);"
							onclick="getMasterRepairDetailPage(-3);">首页</a>
						<a href="javascript:void(0);"
							onclick="getMasterRepairDetailPage(-1);">上一页</a> 当前第
						<font id="master_repair_datai_PageNum"></font> 页 共
						<font id="master_repair_datai_PageSize"></font> 页 共
						<font id="master_repair_datai_sum"></font> 条

						<a href="javascript:void(0);"
							onclick="getMasterRepairDetailPage(-2);">下一页</a>
						<a href="javascript:void(0);"
							onclick="getMasterRepairDetailPage(-4);">尾页</a>
					</div>
				</div>
			</div>
		</section>
	</body>


	<script type="text/javascript">
	var communityId=window.parent.document.getElementById("community_id_index").value;
	function getReckoning(sort, pageNum, startTime, endTime,myDate) {
	var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getPayOff?pageSize=10&pageNum="
			+ pageNum
			+ "&sort="
			+ sort
			+ "&startTime="
			+ startTime+"&myTime="+myDate
			+ "&endTime=" + endTime;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			$("#master_repair_datai_PageNum_get").val(data.num);
			$("#master_repair_datai_PageSize_get").val(data.pageCount);
			$("#master_repair_datai_PageNum").html(data.num);
			$("#master_repair_datai_PageSize").html(data.pageCount);
			$("#master_repair_datai_sum").html(data.rowCount);
			data = data.pageData;
			var repair_overman = $("#reckonign_shop");
			repair_overman.empty();
			repair_overman.append("<tr><th>店家名称</th>" + "<th>银行卡号</th>"
					+ "<th>联系方式</th>" + "<th>在线支付</th><th>帮帮券支付</th>" + "<th>扣款申请</th>"
					+ "<th>扣款金额</th>" + "<th>结款金额</th>" + "<th>交易记录</th>"
					+ "<th><a href=\"#\"></a>结款</a></th></tr>");

			for ( var i = 0; i < data.length; i++) {
				var list="";
				 list+="<tr class=\"odd\">";
				 list+="<td>"+data[i].shopName+"</td>";
				 list+="<td>"+data[i].cardNo+"</td>";
				 list+="<td>"+data[i].phone+"</td>";
				 list+="<td>"+(data[i].priceSum-data[i].bonusSum)+"</td>";
				 list+="<td>"+data[i].bonusSum+"</td>";
				 list+="<td><a class=\"reckoning\" href=\"/jsp/operation/reckoning/reckoning-deduct.jsp?emobId="+data[i].emobId+"\">"+data[i].charge+"</a></td>";
				 list+="<td>"+data[i].deductionPrice+"</td>";
				 list+="<td>"+(data[i].priceSum-data[i].deductionPrice)+"</td>";
				 list+="<td><a href=\"/jsp/operation/reckoning/reckoning-record.jsp?shopId="+data[i].shopId+"&startTime="+startTime+"&endTime="+endTime+"\">交易记录</a></td>";
				 if(data[i].status=="ended"){
					 list+="<td>已结</td>";
				 }else{
					 list+="<td><a class=\"reckoning\" href=\"javascript:;\" onclick=\"jikuan('"+data[i].emobId+"','"+data[i].cardNo+"','"+(data[i].priceSum-data[i].charge)+"','"+data[i].timeNum+"');\" >结款</a></td></tr>";
					 // list+="<td><a class=\"reckoning\" href=\"javascript:;\"  >结款</a></td></tr>";
				 }
				repair_overman.append(list);

			}
			 document.getElementById("master_repir_startTime").value=startTime;
			 document.getElementById("master_repir_endTime").value=endTime;
		},
		error : function(er) {
		}
	});
}


function getMasterRepairDetailPage(num) {
	var page_num = 1;
	var repairRecordPageNum = document
			.getElementById("master_repair_datai_PageNum_get").value;
	var repairRecordPageSize = document
			.getElementById("master_repair_datai_PageSize_get").value;

	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;

	if (num == -1) { // 上一页

		if (repairRecordPageNum != 1) {
			page_num = repairRecordPageNum - 1;
		} else {
			alert("已经是第一页了");
			return;
		}

	} else if (num == -2) { // 下一页
		if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
			page_num = parseInt(repairRecordPageNum) + parseInt(1);
		} else {
			alert("已经是最后一页了");
			return;
		}

	} else if (num == -3) { // 首页
		if (repairRecordPageNum != 1) {
			page_num = 1;
		} else {
			alert("已经是首页了");
			return;
		}
	} else if (num == -4) { // 尾页
		if (parseInt(repairRecordPageNum) < parseInt(repairRecordPageSize)) {
			page_num = repairRecordPageSize;
		} else {
			alert("已经是尾页了");
			return;
		}
	}
       var myDate=new Date();
	   var yes= myDate.getFullYear();
	   var month= myDate.getMonth()+1;
	   var day= myDate.getDate();
	   var i=getMonthWeek(yes,month,day);
	   document.getElementById("test_day").innerHTML=month+"月"+i+"周";
       document.getElementById("reckoning_"+"<%=sort %>").className="select";
		var d = getPreviousWeekStartEnd();
		var startTime="<%=start%>";
		var endTime="<%=endTime%>";
		if(startTime=="null"||startTime==""){
		   var dat=myDate.getFullYear()+"-"+month+"-"+day+" 00:00:00";
		  //var st= stringToTime(day);
		 // st=st/1000;
		   getReckoning("<%=sort %>", page_num, d.start, d.end,dat);
		}else{
		 var myDate2=new Date(startTime*1000);
		  var yes2= myDate2.getFullYear();
	      var month2= myDate2.getMonth()+1;
	      var day2= myDate2.getDate();
	     var dat2=yes2+"-"+month2+"-"+day2+" 00:00:00";
		 // var dd=  getWeekUp(startTime*1000);
		  getReckoning("<%=sort %>", page_num, dd.start, dat2,dat2);
		}
		

}


function jikuan(emobIdShop,cardNo,onlinePrice,timeNum) {
	if(confirm('确定要结款吗？')){
		var communityId = window.parent.document
		.getElementById("community_id_index").value;

		var emobIdAgent = window.parent.document
		.getElementById("emobId_id_index").value;
		var startTime= document.getElementById("master_repir_startTime").value;
		var startTime2=(stringToTime(startTime)/1000);
		var  endTime =document.getElementById("master_repir_endTime").value;
    	var  endTime2=(stringToTime(endTime)/1000);
    	
    	
    	
		var path = "/api/v1/communities/"+communityId+"/deduction/addClearing";
		  var myJson="{\"emobIdShop\":\""+emobIdShop+"\",\"emobIdAgent\":\""+emobIdAgent+"\",\"cardNo\":\""+cardNo+
		  "\",\"onlinePrice\":\""+onlinePrice+"\",\"startTime\":\""+startTime2+"\",\"endTime\":\""+endTime2+"\",\"communityId\":\""+communityId+"\"}";
	$.ajax({
		url : path,
		type : "POST",
		 data : myJson ,
		dataType : 'json',
		success : function(data) {
			if(data.status=="yes"){
				alert("成功");
			
			}
			getReckoning(2, 1, startTime, endTime);
		},
		error : function(er) {
		}
	});	
	}
	
}
		
function reckoning() {
	  var myDate=new Date();
	  var yes= myDate.getFullYear();
	  var month= myDate.getMonth()+1;
	  var day= myDate.getDate();
	 
        document.getElementById("reckoning_"+"<%=sort %>").className="select";
		var d = getPreviousWeekStartEnd();
		var startTime="<%=start%>";
		var endTime="<%=endTime%>";
		if(startTime=="null"||startTime==""){
		  var ddd=getPayWeekBenUp(new Date().getTime);
		  var dat=myDate.getFullYear()+"-"+month+"-"+day+" 00:00:00";
		  var i=getMonthWeek(yes,month,day);
	      document.getElementById("test_day").innerHTML=getStringTime(stringToTime(ddd.start))+"到"+getStringTime(stringToTime(ddd.end));
		//  alert(ddd.start+"--"+ddd.end);
		  getReckoning("<%=sort %>", 1, ddd.start, ddd.end,dat);
		}else{
		   var myDate2=new Date(startTime*1000);
		   var myDate3=new Date(endTime*1000);
		   var yes2= myDate2.getFullYear();
	       var month2= myDate2.getMonth()+1;
	       var day2= myDate2.getDate();
	       
	       
		   var yes3= myDate3.getFullYear();
	       var month3= myDate3.getMonth()+1;
	       var day3= myDate3.getDate();
	       var dat2=yes2+"-"+month2+"-"+day2+" 00:00:00";
	       var dat3=yes3+"-"+month3+"-"+day3+" 00:00:00";
	    //   var i=getMonthWeek(yes2,month2,day2);
	     //  document.getElementById("test_day").innerHTML=month2+"月"+i+"周";
	       document.getElementById("test_day").innerHTML=getStringTime(stringToTime(dat2))+"到"+getStringTime(stringToTime(dat3));
	     
		 //  var dd=  getWeekUp(startTime*1000);
		   getReckoning("<%=sort %>", 1, dat2, dat3,dat2);
		}
		

	}
	
	reckoning();
</script>
	<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>