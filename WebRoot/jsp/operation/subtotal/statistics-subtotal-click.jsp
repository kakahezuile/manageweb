<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>快店营业额_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/calendar.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/respond.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/calendar.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/subtotal/statistics-subtotal-click.js?version=<%=versionNum%>"></script> 
<%-- <script src="${pageContext.request.contextPath}/js/statistics-subtotal.js?version=<%=versionNum%>"></script>  --%>
<style type="text/css">
.upload-master-face-bg{background:url("../../../images/public/bg-blank-60.png") repeat;position:fixed;top:0;width:100%;height:100%;z-index:21;left:0;}
.loadingbox{height: 32px;width:32px;left: 50%;margin-left: -16px;margin-top: -16px;position: absolute;top: 50%;z-index: 22;}
</style>
</head>

<body>
<input type="hidden" id="master_repir_sort_fei" />
<input type="hidden" id="master_repir_startTime" />
<input type="hidden" id="is_list_detail" />
<input type="hidden" id="shop_id_detail" />
<input type="hidden" id="master_repir_endTime" />
<input id="turnover_zong_get" type="hidden" />
<input type="hidden" id="date_type_get" />
<div class="loadingbox" id="add-price-box" style="display: none;">
	<img alt="" src="<%= basePath %>/images/chat/loading.gif">
</div>
<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
<section>
	<div class="content clearfix">
		<jsp:include flush="true" page="../../public/statistics-left.jsp" />
		<div class="right-body">
			<div class="shop-dosage-box">
				<div class="shop-dosage">
					<ul>
						<li><a href="${pageContext.request.contextPath}/jsp/operation/subtotal/statistics-subtotal-click.jsp" class="select">点击占比</a></li>
						<li><a href="${pageContext.request.contextPath}/jsp/operation/subtotal/statistics-subtotal-user.jsp">使用人数</a></li>
					</ul>
				</div>
				<div class="statistics-public clearfix">
					<table>
						<tr>
							<th><span style="font-weight:normal;">占比(点击次数)</span></th>
							<th>生活圈</th>
							<th>快店</th>
							<th>活动/话题</th>
							<th>维修</th>
							<th>物业客服</th>
							<th>快递</th>
							<th>便捷号码</th>
							<th>送水</th>
							<th>公告通知</th>
						</tr>
						<tr class="even">
							<td>今日</td>
							<td>
								<p><span id="this_data16_ratio"></span><span  id="this_data16"></span></p>
								<p><span class="green"></span><span class="green" id="this_data16_test"></span></p>
							</td>
							<td>
								<p><span id="this_data3_ratio"></span><span  id="this_data3"></span></p>
								<p><span class="green"></span><span class="green" id="this_data3_test"></span></p>
							</td>
							<td>
								<p><span id="this_data1_ratio"></span><span id="this_data1"></span></p>
								<p><span class="green"></span><span class="green" id="this_data1_test"></span></p>
							</td>
							<td>
								<p><span id="this_data9_ratio"></span><span id="this_data9"></span></p>
								<p><span class="green"></span><span class="green" id="this_data9_test"></span></p>
							</td>
							<td>
								<p><span id="this_data7_ratio"></span><span id="this_data7"></span></p>
								<p><span class="green"></span><span class="green" id="this_data7_test"></span></p>
							</td>
							<td>
								<p><span id="this_data8_ratio"></span><span id="this_data8"></span></p>
								<p><span class="green"></span><span class="green" id="this_data8_test"></span></p>
							</td>
							<td>
								<p><span id="this_data10_ratio"></span><span id="this_data10"></span></p>
								<p><span class="green"></span><span class="green" id="this_data10_test"></span></p>
							</td>
							<td>
								<p><span id="this_data6_ratio"></span><span id="this_data6"></span></p>
								<p><span class="green"></span><span class="green" id="this_data6_test"></span></p>
							</td>
							<td>
								<p><span id="this_data0_ratio"></span><span id="this_data0"></span></p>
								<p><span class="green"></span><span class="green" id="this_data0_test"></span></p>
							</td>
							
						</tr>
						<tr class="odd">
							<td>昨日</td>
							<td>
								<p><span id="to_day16_ratio"></span><span  id="to_day16"></span></p>
								<p><span class="green"></span><span class="green" id="to_day16_test"></span></p>
							</td>
							<td>
								<p><span id="to_day3_ratio"></span><span  id="to_day3"></span></p>
								<p><span class="green"></span><span class="green" id="to_day3_test"></span></p>
							</td>
							<td>
								<p><span id="to_day1_ratio"></span><span id="to_day1"></span></p>
								<p><span class="green"></span><span class="green" id="to_day1_test"></span></p>
							</td>
							<td>
								<p><span id="to_day9_ratio"></span><span id="to_day9"></span></p>
								<p><span class="green"></span><span class="green" id="to_day9_test"></span></p>
							</td>
							<td>
								<p><span id="to_day7_ratio"></span><span id="to_day7"></span></p>
								<p><span class="green"></span><span class="green" id="to_day7_test"></span></p>
							</td>
							<td>
								<p><span id="to_day8_ratio"></span><span id="to_day8"></span></p>
								<p><span class="green"></span><span class="green" id="to_day8_test"></span></p>
							</td>
							<td>
								<p><span id="to_day10_ratio"></span><span id="to_day10"></span></p>
								<p><span class="green"></span><span class="green" id="to_day10_test"></span></p>
							</td>
							<td>
								<p><span id="to_day6_ratio"></span><span id="to_day6"></span></p>
								<p><span class="green"></span><span class="green" id="to_day6_test"></span></p>
							</td>
							<td>
								<p><span id="to_day0_ratio"></span><span id="to_day0"></span></p>
								<p><span class="green"></span><span class="green" id="to_day0_test"></span></p>
							</td>
						</tr>
						<tr class="even">
							<td>本周</td>
							<td>
								<p><span id="this_week16_ratio"></span><span  id="this_week16"></span></p>
								<p><span class="green"></span><span class="green" id="this_week16_test"></span></p>
							</td>
							<td>
								<p><span id="this_week3_ratio"></span><span  id="this_week3"></span></p>
								<p><span class="green"></span><span class="green" id="this_week3_test"></span></p>
							</td>
							<td>
								<p><span id="this_week1_ratio"></span><span id="this_week1"></span></p>
								<p><span class="green"></span><span class="green" id="this_week1_test"></span></p>
							</td>
							<td>
								<p><span id="this_week9_ratio"></span><span id="this_week9"></span></p>
								<p><span class="green"></span><span class="green" id="this_week9_test"></span></p>
							</td>
							<td>
								<p><span id="this_week7_ratio"></span><span id="this_week7"></span></p>
								<p><span class="green"></span><span class="green" id="this_week7_test"></span></p>
							</td>
							<td>
								<p><span id="this_week8_ratio"></span><span id="this_week8"></span></p>
								<p><span class="green"></span><span class="green" id="this_week8_test"></span></p>
							</td>
							<td>
								<p><span id="this_week10_ratio"></span><span id="this_week10"></span></p>
								<p><span class="green"></span><span class="green" id="this_week10_test"></span></p>
							</td>
							<td>
								<p><span id="this_week6_ratio"></span><span id="this_week6"></span></p>
								<p><span class="green"></span><span class="green" id="this_week6_test"></span></p>
							</td>
							<td>
								<p><span id="this_week0_ratio"></span><span id="this_week0"></span></p>
								<p><span class="green"></span><span class="green" id="this_week0_test"></span></p>
							</td>
						</tr>
						<tr class="odd">
							<td>上周</td>
							<td>
								<p><span id="last_week16_ratio"></span><span  id="last_week16"></span></p>
								<p><span class="green"></span><span class="green" id="last_week16_test"></span></p>
							</td>
							<td>
								<p><span id="last_week3_ratio"></span><span  id="last_week3"></span></p>
								<p><span class="green"></span><span class="green" id="last_week3_test"></span></p>
							</td>
							<td>
								<p><span id="last_week1_ratio"></span><span id="last_week1"></span></p>
								<p><span class="green"></span><span class="green" id="last_week1_test"></span></p>
							</td>
							<td>
								<p><span id="last_week9_ratio"></span><span id="last_week9"></span></p>
								<p><span class="green"></span><span class="green" id="last_week9_test"></span></p>
							</td>
							<td>
								<p><span id="last_week7_ratio"></span><span id="last_week7"></span></p>
								<p><span class="green"></span><span class="green" id="last_week7_test"></span></p>
							</td>
							<td>
								<p><span id="last_week8_ratio"></span><span id="last_week8"></span></p>
								<p><span class="green"></span><span class="green" id="last_week8_test"></span></p>
							</td>
							<td>
								<p><span id="last_week10_ratio"></span><span id="last_week10"></span></p>
								<p><span class="green"></span><span class="green" id="last_week10_test"></span></p>
							</td>
							<td>
								<p><span id="last_week6_ratio"></span><span id="last_week6"></span></p>
								<p><span class="green"></span><span class="green" id="last_week6_test"></span></p>
							</td>
							<td>
								<p><span id="last_week0_ratio"></span><span id="last_week0"></span></p>
								<p><span class="green"></span><span class="green" id="last_week0_test"></span></p>
							</td>
						</tr>
						<tr class="even">
							<td >本月</td>
							<td>
								<p><span id="this_month16_ratio"></span><span  id="this_month16"></span></p>
								<p><span class="green"></span><span class="green" id="this_month16_test"></span></p>
							</td>
							<td>
								<p><span id="this_month3_ratio"></span><span  id="this_month3"></span></p>
								<p><span class="green"></span><span class="green" id="this_month3_test"></span></p>
							</td>
							<td>
								<p><span id="this_month1_ratio"></span><span id="this_month1"></span></p>
								<p><span class="green"></span><span class="green" id="this_month1_test"></span></p>
							</td>
							<td>
								<p><span id="this_month9_ratio"></span><span id="this_month9"></span></p>
								<p><span class="green"></span><span class="green" id="this_month9_test"></span></p>
							</td>
							<td>
								<p><span id="this_month7_ratio"></span><span id="this_month7"></span></p>
								<p><span class="green"></span><span class="green" id="this_month7_test"></span></p>
							</td>
							<td>
								<p><span id="this_month8_ratio"></span><span id="this_month8"></span></p>
								<p><span class="green"></span><span class="green" id="this_month8_test"></span></p>
							</td>
							<td>
								<p><span id="this_month10_ratio"></span><span id="this_month10"></span></p>
								<p><span class="green"></span><span class="green" id="this_month10_test"></span></p>
							</td>
							<td>
								<p><span id="this_month6_ratio"></span><span id="this_month6"></span></p>
								<p><span class="green"></span><span class="green" id="this_month6_test"></span></p>
							</td>
							<td>
								<p><span id="this_month0_ratio"></span><span id="this_month0"></span></p>
								<p><span class="green"></span><span class="green" id="this_month0_test"></span></p>
							</td>
						</tr>
						<tr class="odd">
							<td>上月</td>
									<td>
								<p><span id="last_month16_ratio"></span><span  id="last_month16"></span></p>
								<p><span class="green"></span><span class="green" id="last_month16_test"></span></p>
							</td>
							<td>
								<p><span id="last_month3_ratio"></span><span  id="last_month3"></span></p>
								<p><span class="green"></span><span class="green" id="last_month3_test"></span></p>
							</td>
							<td>
								<p><span id="last_month1_ratio"></span><span id="last_month1"></span></p>
								<p><span class="green"></span><span class="green" id="last_month1_test"></span></p>
							</td>
							<td>
								<p><span id="last_month9_ratio"></span><span id="last_month9"></span></p>
								<p><span class="green"></span><span class="green" id="last_month9_test"></span></p>
							</td>
							<td>
								<p><span id="last_month7_ratio"></span><span id="last_month7"></span></p>
								<p><span class="green"></span><span class="green" id="last_month7_test"></span></p>
							</td>
							<td>
								<p><span id="last_month8_ratio"></span><span id="last_month8"></span></p>
								<p><span class="green"></span><span class="green" id="last_month8_test"></span></p>
							</td>
							<td>
								<p><span id="last_month10_ratio"></span><span id="last_month10"></span></p>
								<p><span class="green"></span><span class="green" id="last_month10_test"></span></p>
							</td>
							<td>
								<p><span id="last_month6_ratio"> </span><span id="last_month6"></span></p>
								<p><span class="green"></span><span class="green" id="last_month6_test"></span></p>
							</td>
							<td>
								<p><span id="last_month0_ratio"></span><span id="last_month0"></span></p>
								<p><span class="green"></span><span class="green" id="last_month0_test"></span></p>
							</td>
						</tr>
						<tr class="total_id">
							<td>上线至今</td>
							<td>
								<p><span id="total_id16_ratio"></span><span  id="total_id16"></span></p>
								<p><span class="green"></span><span class="green" id="total_id16_test"></span></p>
							</td>
								<td>
								<p><span id="total_id3_ratio"></span><span  id="total_id3"></span></p>
								<p><span class="green"></span><span class="green" id="total_id3_test"></span></p>
							</td>
							<td>
								<p><span id="total_id1_ratio"></span><span id="total_id1"></span></p>
								<p><span class="green"></span><span class="green" id="total_id1_test"></span></p>
							</td>
							<td>
								<p><span id="total_id9_ratio"></span><span id="total_id9"></span></p>
								<p><span class="green"></span><span class="green" id="total_id9_test"></span></p>
							</td>
							<td>
								<p><span id="total_id7_ratio"></span><span id="total_id7"></span></p>
								<p><span class="green"></span><span class="green" id="total_id7_test"></span></p>
							</td>
							<td>
								<p><span id="total_id8_ratio"></span><span id="total_id8"></span></p>
								<p><span class="green"></span><span class="green" id="total_id8_test"></span></p>
							</td>
							<td>
								<p><span id="total_id10_ratio"></span><span id="total_id10"></span></p>
								<p><span class="green"></span><span class="green" id="total_id10_test"></span></p>
							</td>
							<td>
								<p><span id="total_id6_ratio"></span><span id="total_id6"></span></p>
								<p><span class="green"></span><span class="green" id="total_id6_test"></span></p>
							</td>
							<td>
								<p><span id="total_id0_ratio"></span><span id="total_id0"></span></p>
								<p><span class="green"></span><span class="green" id="total_id0_test"></span></p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="shop-dosage-detail-box">
				<div class="shop-dosage-detail">
					<div class="time-chose" id="top_section_time">
						<input readonly="readonly" id="txtBeginDate" value="请选择开始日期" />
						<input readonly="readonly" id="txtEndDate" value="请选择结束日期" />
						<a href="javascript:void(0);" onclick="selectStatistics();">查询</a>
					</div>
					<div class="repair-title">
						<a href="javascript:;" class="select" id="data_clik" onclick="timeClick('data_clik');">今日</a>
						<a href="javascript:;" id="week_clik" onclick="timeClick('week_clik');">上周</a>
						<a href="javascript:;" id="month_clik" onclick="timeClick('month_clik');">上月</a>
					</div>
					<div class="statistics-public-date">
						<ul>
							<li>
								<a href="javascript:;" onclick="alterMonth('1');" id="date_type_1">上一月</a>
							</li>
							<li>
								<span id="statistics_date_1">2月20日</span><span>到</span><span id="statistics_date_2">2月26日</span>
							</li>
							
							<li>
								<a href="javascript:;"  onclick="alterMonth('2');" id="date_type_2">下一月</a>
							</li>
						</ul>
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
/* 
  $(document).ready(function() {
	window.subtotal.communityId = window.parent.document.getElementById("community_id_index").value;
	window.subtotal.summary(); 
	selectMenu("subtotal");
	
}); */
  

  getTryOut(); 

</script>
</body>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/subtotal/statistics-subtotal.js?version=<%=versionNum%>"></script>
</html>