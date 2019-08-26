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
<title>用户统计_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<%


String shopId=request.getParameter("shopId");
String startTime=request.getParameter("startTime");
String endTime=request.getParameter("endTime");

 %>
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/operation.js"></script>
</head>
<body>

    <input id="shop_id" type="hidden" value="<%=shopId %>"/>
    <input id="master_repir_startTime" type="hidden"/>
	<input id="master_repir_endTime" type="hidden"/>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/reckoning-left.jsp"/>
			<div class="right-body">
				<div class="reckoning-title" id="">交易记录</div>
				<div class="reckoning-body">
					<table id="reckonign_record">
						<tr>
							<th>用户</th>
							<th>地址</th>
							<th>线上交易金额</th>
							<th>是否投诉</th>
						</tr>
						<tr class="odd">
							<td>李明</td>
							<td>狮子城3号楼2单元102</td>
							<td>20</td>
							<td>&nbsp;</td>
						</tr>
						<tr  class="even">
							<td>张小军</td>
							<td>狮子城3号楼2单元102</td>
							<td>30</td>
							<td>&nbsp;</td>
						</tr>
						<tr  class="odd">
							<td>王二小</td>
							<td>狮子城3号楼2单元102</td>
							<td>50</td>
							<td>是</td>
						</tr>
					</table>
				</div>
				<div class="divide-page" 
							style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


							<input type="hidden" id="master_repair_datai_PageNum_get" />
							<input type="hidden" id="master_repair_datai_PageSize_get" />

							<a href="javascript:void(0);"
								onclick="getReckoningRecordDetailPage(-3);">首页</a>
							<a href="javascript:void(0);"
								onclick="getReckoningRecordDetailPage(-1);">上一页</a> 当前第
							<font id="master_repair_datai_PageNum"></font> 页 共
							<font id="master_repair_datai_PageSize"></font> 页 共
							<font id="master_repair_datai_sum"></font> 条

							<a href="javascript:void(0);"
								onclick="getReckoningRecordDetailPage(-2);">下一页</a>
							<a href="javascript:void(0);"
								onclick="getReckoningRecordDetailPage(-4);">尾页</a>
						</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
var communityId=window.parent.document.getElementById("community_id_index").value;
	function reckoning() {
        var shop_id=  document.getElementById("shop_id").value;
		var d = getPreviousWeekStartEnd();
		getTradingRecord(shop_id, 1,'<%=startTime%>','<%=endTime%>');

	}
	function getTradingRecord(shopId, pageNum, startTime, endTime) {
	var path = "/api/v1/communities/"+communityId+"/users/1/orderHistories/getTradingRecord?pageSize=10&pageNum="
			+ pageNum
			+ "&shopId="
			+ shopId
			+ "&startTime="
			+ startTime
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
			var repair_overman = $("#reckonign_record");
			repair_overman.empty();
			repair_overman.append("<tr><th>用户</th>	<th>地址</th><th>交易金额</th><th>帮帮券交易金额</th><th>是否投诉</th></tr>");

			for ( var i = 0; i < data.length; i++) {
				var list="";
				 list+="<tr class=\"odd\">";
				 list+="<td>"+data[i].nickname+"</td>";
				 list+="<td>"+data[i].communityName+data[i].userFloor+data[i].userUnit+data[i].room+"</td>";
				 list+="<td>"+data[i].orderPrice+"</td>";
				 list+="<td>"+data[i].bonusPar+"</td>";
				
				 if(data[i].isComplaint=="yes"){
					 list+="<td>是</td>";
				 }else{
					 list+="<td>没有被投诉</tr>";
				 }
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