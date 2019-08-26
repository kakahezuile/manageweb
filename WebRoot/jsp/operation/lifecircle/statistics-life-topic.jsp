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
<title>生活圈_小间运营系统</title>
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
<script src="${pageContext.request.contextPath }/js/lifecircle/statistics-life-topic.js?version=<%=versionNum %>"></script>

</head>
<body>
		<input type="hidden" id="time_day" value="0"/>
		<input type="hidden" id="master_repir_sort_fei" value="0"/>
		<input type="hidden" id="master_repir_startTime" />
		<input type="hidden" id="master_repir_endTime" />
		<input type="hidden" id="is_list_detail" />
		<input type="hidden" id="shop_id_detail" />
		<input type="hidden" id="date_type_get" />
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../../public/statistics-left.jsp"/>
			<div class="right-body">
				<div class="shop-dosage-box">
					<div class="shop-dosage">
						<ul>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/lifecircle/statistics-life-circle.jsp" >使用量</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/lifecircle/statistics-life-topic.jsp" class="select">话题</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/operation/lifecircle/statistics-life-ranking.jsp">人品排行</a></li>
						</ul>
					</div>
					
				</div>
				<div class="shop-dosage-detail-box">
					<div class="shop-dosage-detail">
						<div class="time-chose" id="top_section_time"  style="left:300px;">   
							<input readonly="readonly" id="txtBeginDate" value="请选择开始日期"/>
							<input readonly="readonly" id="txtEndDate" value="请选择结束日期"/>
							<a href="javascript:void(0);" onclick="selectStatistics();">查询</a>
						</div>
						<div class="repair-title">
						    <a href="javascript:;" class="select" id="data_clik"
								onclick="timeClick('data_clik');">今日</a>
							<a href="javascript:;" id="week_clik"
								onclick="timeClick('week_clik');">上周</a>
							<a href="javascript:;" id="month_clik"
								onclick="timeClick('month_clik');">上月</a>
						</div>
					<div class="shop-dosage-total" style="display: none;">
						<ul>
							<li><a href="javascript:;" onclick="alterMonth('1');" id="date_type_1">上一月</a></li>
							<li><span><b>统计时间</b></span><span id="statistics_date_1">2月20日</span><span>到</span><span id="statistics_date_2">2月26日</span></li>
							<li><span style="display: none;"><b>总点击量</b> </span><span id="avg_day_turnover">450</span></li>
							<li><span style="display: none;"><b>日均点击量 </b></span><span id="avg_day_order">450</span></li>
							<li><span style="display: none;"><b>增加人数</b> </span><span id="avg_day_ratio">450</span></li>
							<li><a href="javascript:;" onclick="alterMonth('2');" id="date_type_2">下一月</a></li>
						</ul>
					</div>
						<div class="order-condition">
							<span>排序：</span>
							<a href="javascript:;" id="time_id" class="select" onclick="sortRank('time');">按时间</a>
							<a href="javascript:;" id="praise_id"  onclick="sortRank('praise');">点赞</a>
							<a href="javascript:;" id="content_id" onclick="sortRank('content');">评论</a>
						</div>
					</div>
					<div class="shop-dosage-list">
						<table id="statistics_list_id">
						</table>
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
		

	quickShopData();
	
	</script>
</body>

<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>



















