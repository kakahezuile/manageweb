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
<title>活动使用量_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/calendar.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/activity/statistics-activity-detail.js?version=<%=versionNum %>"></script>

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
</head>
<body>
		<input type="hidden" id="time_day" value="0"/>
		<input type="hidden" id="master_repir_sort_fei" value="0"/>
		<input type="hidden" id="master_repir_startTime" />
		<input type="hidden" id="master_repir_endTime" />
		<input type="hidden" id="is_list_detail" />
		<input type="hidden" id="shop_id_detail" />
		<input type="hidden" id="date_type_get" />
		
		
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<ul>
							<li><span>7月1日</span><span>-</span><span>7月10日</span><span>发言详情</span></li>
						</ul>
					</div>
				</div>
				<div class="shop-dosage-detail-box">
					
					<div class="shop-dosage-list">
						<table id="statistics_list_id">
							<tr>
								<th>群组名称</th>
								<th>发言人数</th>
								<th>群组总人数</th>
							</tr>
							<tr class="odd">
								<td>吃火锅反弹</td>
								<td>12</td>
								<td>30</td>
							</tr>
							<tr class="even">
								<td>再建一个</td>
								<td>10</td>
								<td>23</td>
							</tr>
							<tr class="odd">
								<td>亲们：端午有自家活动不</td>
								<td>52</td>
								<td>24</td>
							</tr>
						</table>
					</div>
					
						<div class="divide-page"
							style="float: left; width: 100%; height: 50px; clear: both; text-align: center;display: none;">
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
		</div>
	</section>
		<script type="text/javascript">
		
function getMasterDetailPage2(type,pageNum,pageSize) {
	var page_num=0;
	if (type == -1) { // 上一页

		if (pageNum != 1) {
			page_num = pageNum - 1;
		} else {
			alert("已经是第一页了");
			return 0;
		}

	} else if (type == -2) { // 下一页
		if (parseInt(pageNum) < parseInt(pageSize)) {
			page_num = parseInt(pageNum) + parseInt(1);
		} else {
			alert("已经是最后一页了");
			return 0;
		}

	} else if (type == -3) { // 首页
		if (pageNum != 1) {
			page_num = 1;
		} else {
			alert("已经是首页了");
			return 0;
		}
	} else if (type == -4) { // 尾页
		if (parseInt(pageNum) < parseInt(pageSize)) {
			page_num = pageSize;
		} else {
			alert("已经是尾页了");
			return 0;
		}
	}
	return page_num;
}
		

	getTryOut();
	
	</script>
</body>
<script src="${pageContext.request.contextPath }/js/activity/statistics-activity.js?version=<%=versionNum %>"></script>

<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>



















