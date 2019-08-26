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
				<div class="reckoning-title" id="">交易详单</div>
				<div class="reckoning-body">
					<table id="reckonign_record1">
						<tr>
								<th>
									<input type="checkbox">
								</th>
								<th>
									用户
								</th>
								<th>
									电话
								</th>
								<th>
									真实住址
								</th>
								<th>
									下单时间
								</th>
								<th>
									付款时间
								</th>
								<th>
									实际付款
								</th>
								<th>
									帮券付款
								</th>
								<th>
									扣款申请
								</th>
								<th>
									扣款金额
								</th>
								<th>
									结款金额
								</th>
								<th>
									结款
								</th>
							</tr>
							<tr>
								<td><input type="checkbox"></td>
								<td>李明</td>
								<td>18500125211</td>
								<td>3号楼2单元1302</td>
								<td>15-06-30 15:00</td>
								<td>15-06-30 15:12</td>
								<td>25.00</td>
								<td>0.00</td>
								<td>无</td>
								<td>0.00</td>
								<td>25.00</td>
								<td>未结</td>
							</tr>
							<tr>
								<td><input type="checkbox"></td>
								<td>张晓雷</td>
								<td>13611254525</td>
								<td>1号楼1单元602</td>
								<td>15-06-29 10:21</td>
								<td>15-06-29 10:31</td>
								<td>21.00</td>
								<td>3.00</td>
								<td>无</td>
								<td>0.00</td>
								<td>24.00</td>
								<td>未结</td>
							</tr>
							<tr>
								<td><input type="checkbox"></td>
								<td>王明明</td>
								<td>13945126611</td>
								<td>1号楼3单元608</td>
								<td>15-06-29 08:01</td>
								<td>15-06-29 08:21</td>
								<td>48.00</td>
								<td>3.00</td>
								<td><a href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-deduct.jsp?emobId=1fb83f5875c78af4992fe83771ab4c2e" class="withhold2">已扣款</a></td>
								<td>0.00</td>
								<td>24.00</td>
								<td>未结</td>
							</tr>
							<tr>
								<td><input type="checkbox"></td>
								<td>王明明</td>
								<td>13945126611</td>
								<td>1号楼3单元608</td>
								<td>15-06-29 08:01</td>
								<td>15-06-29 08:11</td>
								<td>48.00</td>
								<td>3.00</td>
								<td><a href="${pageContext.request.contextPath }/jsp/operation/reckoning/reckoning-deduct.jsp?emobId=1fb83f5875c78af4992fe83771ab4c2e" class="withhold2">已扣款</a></td>
								<td>1.00</td>
								<td>23.00</td>
								<td>未结</td>
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