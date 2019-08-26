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
<title>快店使用量_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">


<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/calendar.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/maintain/statistics-maintain-dosage.js?version=<%=versionNum %>"></script>

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
		<input type="hidden" id="master_repir_sort_fei" />
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
								<li><a href="${pageContext.request.contextPath }/jsp/operation/maintain/statistics-maintain-dosage.jsp" class="select">使用量</a></li>
								<li><a href="${pageContext.request.contextPath }/jsp/operation/maintain/statistics-maintain-turnover.jsp" >营业额</a></li>
								<li><a href="${pageContext.request.contextPath }/jsp/operation/maintain/statistics-maintain-comment.jsp" >投诉与评价</a></li>
								<li><a href="${pageContext.request.contextPath }/jsp/operation/maintain/statistics-maintain-time.jsp" >服务时间段</a></li>
						</ul>
					</div>
					
					
					
						<div class="statistics-public clearfix">
							<table>
								<tr>
									<th>时间</th>
									<th>点击量</th>
									<th>模块点击占比</th>
									<th>下单量</th>
									<th>下单占比</th>
									<th>下单人数</th>
									<th>成单量</th>
									<th>下单成单率</th>
								</tr>
								<tr class="even">
									<td>今日</td>
									<td>
										<span id="this_data_click"></span>
										<span class="green" id="this_data_click_test"></span>
									</td>
									<td>
										<span id="this_data_click_ratio"></span>
									</td>
									<td>
										<span id="this_data_place"></span>
										<span class="green" id="this_data_place_test"></span>
									</td>
									<td>
										<span id="this_data_place_ratio"></span>
										<span class="green" id="this_data_place_ratio_test"></span>
									</td>
									<td>
										<span id="this_data_place_user"></span>
										<span class="green" id="this_data_place_user_test"></span>
									</td>
									<td>
										<span id="this_data_end_place"></span>
										<span class="green" id="this_data_end_place_test"></span>
									</td>
									<td>
										<span id="this_data_end_place_ratio"></span>
										<span class="green" id="this_data_end_place_ratio_test"></span>
									</td>
									
								</tr>
								<tr class="odd">
									<td>昨日</td>
									
									<td>
										<span id="to_day_click"></span>
										<span class="green" id="to_day_click_test"></span>
									</td>
									<td>
										<span id="to_day_click_ratio"></span>
									</td>
									<td>
										<span id="to_day_place"></span>
										<span class="green" id="to_day_place_test"></span>
									</td>
									<td>
										<span id="to_day_place_ratio"></span>
									</td>
									<td>
										<span id="to_day_place_user"></span>
										<span class="green" id="to_day_place_user_test"></span>
									</td>
									<td>
										<span id="to_day_end_place"></span>
										<span class="green" id="to_day_end_place_test"></span>
									</td>
									<td>
										<span id="to_day_end_place_ratio"></span>
										<span class="green" id="to_day_end_place_ratio_test"></span>
									</td>
									
									
									
								</tr>
								<tr class="even">
									<td>本周</td>
									
									<td>
										<span id="this_week_click"></span>
										<span class="green" id="this_week_click_test"></span>
									</td>
									<td>
										<span id="this_week_click_ratio"></span>
									</td>
									<td>
										<span id="this_week_place"></span>
										<span class="green" id="this_week_place_test"></span>
									</td>
									<td>
										<span id="this_week_place_ratio"></span>
										<span class="green" id="this_week_place_ratio_test"></span>
									</td>
									<td>
										<span id="this_week_place_user"></span>
										<span class="green" id="this_week_place_user_test"></span>
									</td>
									<td>
										<span id="this_week_end_place"></span>
										<span class="green" id="this_week_end_place_test"></span>
									</td>
									<td>
										<span id="this_week_end_place_ratio"></span>
										<span class="green" id="this_week_end_place_ratio_test"></span>
									</td>
									
								</tr>
								<tr class="odd">
									<td>上周</td>
									
									
									<td>
										<span id="last_week_click"></span>
										<span class="green" id="last_week_click_test"></span>
									</td>
									<td>
										<span id="last_week_click_ratio"></span>
									</td>
									<td>
										<span id="last_week_place"></span>
										<span class="green" id="last_week_place_test"></span>
									</td>
									<td>
										<span id="last_week_place_ratio"></span>
										<span class="green" id="last_week_place_ratio_test"></span>
									</td>
									<td>
										<span id="last_week_place_user"></span>
										<span class="green" id="last_week_place_user_test"></span>
									</td>
									<td>
										<span id="last_week_end_place"></span>
										<span class="green" id="last_week_end_place_test"></span>
									</td>
									<td>
										<span id="last_week_end_place_ratio"></span>
										<span class="green" id="last_week_end_place_ratio_test"></span>
									</td>
									
									
									
								</tr>
								<tr class="even">
									<td >本月</td>
									
									
									<td>
										<span id="this_month_click"></span>
										<span class="green" id="this_month_click_test"></span>
									</td>
									<td>
										<span id="this_month_click_ratio"></span>
									</td>
									<td>
										<span id="this_month_place"></span>
										<span class="green" id="this_month_place_test"></span>
									</td>
									<td>
										<span id="this_month_place_ratio"></span>
										<span class="green" id="this_month_place_ratio_test"></span>
									</td>
									<td>
										<span id="this_month_place_user"></span>
										<span class="green" id="this_month_place_user_test"></span>
									</td>
									<td>
										<span id="this_month_end_place"></span>
										<span class="green" id="this_month_end_place_test"></span>
									</td>
									<td>
										<span id="this_month_end_place_ratio"></span>
										<span class="green" id="this_month_end_place_ratio_test"></span>
									</td>
									
									
									
								</tr>
								<tr class="odd">
									<td>上月</td>
									
									<td>
										<span id="last_month_click"></span>
										<span class="green" id="last_month_click_test"></span>
									</td>
									<td>
										<span id="last_month_click_ratio"></span>
									</td>
									<td>
										<span id="last_month_place"></span>
										<span class="green" id="last_month_place_test"></span>
									</td>
									<td>
										<span id="last_month_place_ratio"></span>
										<span class="green" id="last_month_place_ratio_test"></span>
									</td>
									<td>
										<span id="last_month_place_user"></span>
										<span class="green" id="last_month_place_user_test"></span>
									</td>
									<td>
										<span id="last_month_end_place"></span>
										<span class="green" id="last_month_end_place_test"></span>
									</td>
									<td>
										<span id="last_month_end_place_ratio"></span>
										<span class="green" id="last_month_end_place_ratio_test"></span>
									</td>
									
								</tr>
								<tr class="total_id">
									<td>上线至今</td>
									
									
									<td>
										<span id="total_id_click"></span>
										<span class="green" id="total_id_click_test"></span>
									</td>
									<td>
										<span id="total_id_click_ratio"></span>
									</td>
									<td>
										<span id="total_id_place"></span>
										<span class="green" id="total_id_place_test"></span>
									</td>
									<td>
										<span id="total_id_place_ratio"></span>
										<span class="green" id="total_id_place_ratio_test"></span>
									</td>
									<td>
										<span id="total_id_place_user"></span>
										<span class="green" id="total_id_place_user_test"></span>
									</td>
									<td>
										<span id="total_id_end_place"></span>
										<span class="green" id="total_id_end_place_test"></span>
									</td>
									<td>
										<span id="total_id_end_place_ratio"></span>
										<span class="green" id="total_id_end_place_ratio_test"></span>
									</td>
									
								</tr>
							</table>
						</div>
					
				</div>
				<div class="shop-dosage-detail-box">
					<div class="shop-dosage-detail">
						<div class="time-chose" id="top_section_time">   
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
					</div>
					<div class="shop-dosage-total">
						<ul>
							<li><a href="javascript:;" onclick="alterMonth('1');" id="date_type_1">上一月</a></li>
							<li><span><b>统计时间</b></span><span id="statistics_date_1">2月20日</span><span>到</span><span id="statistics_date_2">2月26日</span></li>
							<li style="display: none;"><span><b>日均点击量</b> </span><span id="avg_day_turnover">450</span></li>
							<li style="display: none;"><span><b>日均下单量 </b></span><span id="avg_day_order">450</span></li>
							<li style="display: none;"><span><b>下单占比</b> </span><span id="avg_day_ratio">450</span></li>
							<li><a href="javascript:;" onclick="alterMonth('2');" id="date_type_2">下一月</a></li>
						</ul>
					</div>
					<div class="shop-dosage-list">
						<table id="statistics_list_id">
						
						
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
		getTryOut();
		    
	
	</script>
</body>
<script src="${pageContext.request.contextPath }/js/maintain/statistics-maintain.js"></script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>



















