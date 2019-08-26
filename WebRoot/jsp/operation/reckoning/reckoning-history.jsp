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
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/operation.js"></script>
</head>
<body>
<input type="hidden" id="master_repir_startTime" /> 
<input type="hidden" id="master_repir_endTime" /> 
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/reckoning-left.jsp"/>
			<div class="right-body">
				<div class="reckoning-title">快店结款历史</div>
				<div class="reckoning-body">
					<table id="reckonign_history">
						<!--<tr>
							<th>日期</th>
							<th>已结店家</th>
							<th>未结店家</th>
							<th>结款金额</th>
							<th>扣款店家</th>
							<th>扣款金额</th>
							<th>详单</th>
						</tr>
						<tr class="odd">
							<td>3月第3周</td>
							<td>3</td>
							<td>0</td>
							<td>320</td>
							<td>1</td>
							<td>20</td>
							<td><a href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp">详单</a></td>
						</tr>
						<tr  class="even">
							<td>3月第2周</td>
							<td>3</td>
							<td>0</td>
							<td>320</td>
							<td>1</td>
							<td>20</td>
							<td><a href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp">详单</a></td>
						</tr>
						<tr  class="odd">
							<td>3月第1周</td>
							<td>3</td>
							<td>0</td>
							<td>320</td>
							<td>1</td>
							<td>20</td>
							<td><a href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp">详单</a></td>
						</tr>
					--></table>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
var communityId=window.parent.document.getElementById("community_id_index").value;
	function reckoning() {
		var d = getPreviousWeekStartEnd();
		getTradingRecord( 1, "2013-1-1 00:00:00", d.end);

	}
	function getTradingRecord( pageNum, startTime, endTime) {
	var path = "/api/v1/communities/"+communityId+"/deduction/getReckoningHistory?sort=<%=sort%>&pageSize=10&pageNum="
			+ pageNum;
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
			var repair_overman = $("#reckonign_history");
			repair_overman.empty();
			repair_overman.append("<tr><th>日期</th>	<th>已结店家</th><th>结款金额</th><th>扣款金额</th><th>详单</th>	</tr>");

			for ( var i = 0; i < data.length; i++) {
			    var myDate=new Date(data[i].timeGroup*1000);
			    
	            var yes= myDate.getFullYear();
	            var month= myDate.getMonth()+1;
	            var day= myDate.getDate();
	          //  var j=getMonthWeek(yes,month,day);
				var list="<tr class=\"odd\">";
				 list+="<td>"+yes+"-"+month+"-"+day+"</td>";
				 list+="<td>"+data[i].endedNum+"</td>";
				
				 list+="<td>"+data[i].reckoningPrice+"</td>";
				// list+="<td>"+data[i].deductionShopNum+"</td>";
				 list+="<td>"+data[i].deductionPrice+"</td>";
				 list+="<td><a href=\"${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-shop.jsp?sort=2&startTime="+data[i].startTime+"&endTime="+data[i].endTime+"\">详单</a></td></tr>";
				
				repair_overman.append(list);

			}
			 document.getElementById("master_repir_startTime").value=startTime;
			 document.getElementById("master_repir_endTime").value=endTime;
		},
		error : function(er) {
			alert(er);
		}
	});
}


function getReckoningRecordDetailPage(num) {
	var page_num = 1;
	var repairRecordPageNum = document
			.getElementById("master_repair_datai_PageNum_get").value;
	var repairRecordPageSize = document
			.getElementById("master_repair_datai_PageSize_get").value;
    var shop_id=  document.getElementById("shop_id").value;
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
 getTradingRecord(shop_id, page_num, startTime, endTime);

}
	reckoning();
</script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>