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
		<title>维修记录_小间物业管理系统</title>
	

		<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
		<script type="text/javascript">


	function conversionRecord(id) {
		var top_data = document.getElementById("top_data_record");
		var top_month = document.getElementById("top_month_record");
		var top_section = document.getElementById("top_section_record");
		if (id == "top_data_record") {
			top_data.className = "select";
		} else {
			top_data.className = "";
		}
		if (id == "top_month_record") {
			top_month.className = "select";
		} else {
			top_month.className = "";
		}
		if (id == "top_section_record") {
			top_section.className = "select";
			document.getElementById("repair_record_top_section_time").style.display="";
		} else {
			top_section.className = "";
			document.getElementById("repair_record_top_section_time").style.display="none";
		}

	}
	function maintainDataRecord(sort) {
	if(sort==null||sort==""){
	var sort = document.getElementById("repair_shops_sort").value;
	}
	
		conversionRecord("top_data_record");
		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		myDate.getDate();
		var month = myDate.getMonth() + 1;
		var da = myDate.getDate() + 1;
		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ myDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-" + da+" 00:00:00";
		document.getElementById("repair_time_startTime").value = startTime;
		document.getElementById("repair_time_endTime").value = endTime;
		 trade_d_m_record("日");
		maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);

	}

	function maintainMonthRecord() {
		conversionRecord("top_month_record");
var sort = document.getElementById("repair_shops_sort").value;
		var myDate = new Date();
		myDate.getFullYear();
		myDate.getMonth();
		var month = myDate.getMonth() + 1;

		var firstDate = new Date();

		firstDate.setDate(1); //第一天

		var endDate = new Date(firstDate);

		endDate.setMonth(firstDate.getMonth() + 1);

		endDate.setDate(0);

		var startTime = myDate.getFullYear() + "-" + month + "-"
				+ firstDate.getDate()+" 00:00:00";
		var endTime = myDate.getFullYear() + "-" + month + "-"
				+ endDate.getDate()+" 00:00:00";
		document.getElementById("repair_time_startTime").value = startTime;
		document.getElementById("repair_time_endTime").value = endTime;
		 trade_d_m_record("月");
		maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);

	}

	function maintainRecord(startTime, endTime,sort) {
		var path = "<%= path%>/api/v1/communities/${communityId}/repairStatistics/top?startTime="
				+ startTime + "&endTime=" + endTime+"&sort="+sort;
	document.getElementById("repair_shops_sort").value = sort;
		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {
						data = data.info;
						document.getElementById("repairCount_record").innerHTML = data.repairCount;
						document.getElementById("todayComplaintsCount_record").innerHTML = data.todayComplaintsCount;
						document.getElementById("todayUnComplainsCount_record").innerHTML = data.todayUnComplaintsCount;
						if (data.repairMoney != "null") {
							document.getElementById("repairMoney").innerHTML = data.repairMoney;

						}

					},
					error : function(er) {
						alert(er);
					}
				});
	}
	
	
	function trade_d_m_record(M){
		var repairCount_d_m=document.getElementById("repair_record_weixiu_fei");
		var todayComplaintsCount_d_m=document.getElementById("repair_record_toushu_fei");
		var todayUnComplainsCount_d_m=document.getElementById("repair_record_wei_fei");
		var repairMoney_d_m=document.getElementById("repair_record_jin_fei");
		
		repairCount_d_m.innerHTML="本"+M+"维修次数";
		todayComplaintsCount_d_m.innerHTML="本"+M+"投诉次数";
		todayUnComplainsCount_d_m.innerHTML="本"+M+"未解决次数";
		repairMoney_d_m.innerHTML="本"+M+"维修金额";
	
	
	}
	
	
    function	overmanRecord(startTime,endTime,pageNum,sort){
	  var path = "<%= path%>/api/v1/communities/${communityId}/repairStatistics/getHistoryMaintainList?startTime="
				+ startTime + "&endTime=" + endTime + "&pageNum=" + pageNum+"&sort="+sort;

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {

						data = data.info;

						$("#repairRecordPageNum_get").val(data.num);
						$("#repairRecordPageSize_get").val(data.pageCount);

						$("#repairRecordPageNum").html(data.num);
						$("#repairRecordPageSize").html(data.pageCount);
						$("#repairRecordSum").html(data.rowCount);

						data = data.pageData;
						var repair_overman = $("#repair_overman_record");
						repair_overman.empty();
						//var liList='';
						if(sort==5){
						repair_overman
								.append("	<table class='repair-record-list-title'>"
										+ "<tr><th class='repair-record-master-face'>&nbsp;</th>"
										+ "<th>师傅</th>"
										+ "<th>电话</th>"
										+ "<th>维修总次数</th>"
										+ "<th>被投诉次数</th>"
										+ "<th>评价</th>" + "</tr></table>");
						}else{
						repair_overman
								.append("	<table class='repair-record-list-title'>"
										+ "<tr><th class='repair-record-master-face'>&nbsp;</th>"
										+ "<th>阿姨</th>"
										+ "<th>电话</th>"
										+ "<th>保洁总次数</th>"
										+ "<th>被投诉次数</th>"
										+ "<th>评价</th>" + "</tr></table>");
						
						}
						
						for ( var i = 0; i < data.length; i++) {

							var liList = "";

							liList += "<table ><tr>";

							liList += "<td class='repair-record-master-face'><img src='"+data[i].logo+"'/></td>";
							liList += "<td class='repair-record-master-name'>"
									+ data[i].shopName + "</td>";
							liList += "<td class='repair-record-master-name'>"
									+ data[i].phone + "</td>";
							liList += "<td class='repair-record-count'><a href='javascript:;' onclick=masterRepairDatailIdFei('"+data[i].shopId+"','"+sort+"');>"
									+ data[i].repairStatisticsCount
									+ "</a></td>";
							liList += "<td class='repair-record-count'><a href='javascript:;'  onclick=masterRepairDatailIdFei('"+data[i].shopId+"','"+sort+"','no');>"
									+ data[i].complaintsCount + "</a></td>";
							liList += "<td><img src='${pageContext.request.contextPath }/images/repair/evaluate_star_liaht"+data[i].score+".png'/></td>";
							liList += "</tr></table>";

							repair_overman.append(liList);

						}

						repair_overman
								.append("<div class='repair-record-head'>"
										+ "</div><div class='repair-record-body'>"
										+ "<ul><li></li></ul></div>");

					},
					error : function(er) {
						alert(er);
					}
				});

	}
	
	function getRepairRecordList2(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("repairRecordPageNum_get").value;
		var repairRecordPageSize = document
				.getElementById("repairRecordPageSize_get").value;
		var startTime = document.getElementById("repair_time_startTime").value;
		var endTime = document.getElementById("repair_time_endTime").value;
		var sort = document.getElementById("repair_shops_sort").value;
		if (num == -1) { // 上一页

			if (repairRecordPageNum != 1) {
				page_num = repairRecordPageNum - 1;
			} else {
				alert("已经是第一页了");
				return;
			}

		} else if (num == -2) { // 下一页
			if (repairRecordPageNum < repairRecordPageSize) {
				page_num = repairRecordPageNum + 1;
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
			if (repairRecordPageNum < repairRecordPageSize) {
				page_num = repairRecordPageSize;
			} else {
				alert("已经是尾页了");
				return;
			}
		}
		overmanRecord(startTime, endTime, page_num,sort);
	}
	
	
	//日历控件
    $(function () {
        $("#repair_record_txtBeginDate").calendar({
            //controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true,                                       // 目标对象是否设为只读，默认：true
            upperLimit: new Date(),                               // 日期上限，默认：NaN(不限制)
            lowerLimit: new Date("2011/01/01"),                   // 日期下限，默认：NaN(不限制)
            callback: function () {                               // 点击选择日期后的回调函数
             //   alert("您选择的日期是：" + $("#txtBeginDate").val());
            }
        });
        $("#repair_record_txtEndDate").calendar();
    });
	

  function selectRecordMainainDuan(){
  
      var startTime=document.getElementById("repair_record_txtBeginDate").value+" 00:00:00";
       var endTime=document.getElementById("repair_record_txtEndDate").value+" 00:00:00";
       var sort = document.getElementById("repair_shops_sort").value;
       maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);
		
  }
  
</script>
	</head>
	<body>

		<input type="hidden" id="repair_time_startTime" />
		<input type="hidden" id="repair_time_endTime" />
		<input type="hidden" id="repair_shops_sort" />

		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="../public/service-left.jsp" />
				<div class="service-right-body">
					<div class="repair-head">
						<div class="time-chose" id="repair_record_top_section_time">   
							<input readonly="readonly" id="repair_record_txtBeginDate" value="请选择开始日期">
							<input readonly="readonly" id="repair_record_txtEndDate" value="请选择结束日期">
							<a href="javascript:void(0);" onclick="selectRecordMainainDuan();">查询</a>
						</div>
						<div class="repair-title">
							<a href="javascript:;" class="select" id="top_data_record"
								onclick="maintainDataRecord();">日</a>
							<a href="javascript:;" id="top_month_record"
								onclick="maintainMonthRecord();">月</a>
							<a href="javascript:;" id="top_section_record" onclick="conversionRecord('top_section_record');">时间段</a>
						</div>
						<div class="repair-content">
							<div class="repair-date">
								<span name="to_day_fei">2015-12-26</span>
							</div>
							<div class="repair-stat clearfix">
								<div>
									<span id="repair_record_weixiu_fei">今日维修次数</span><a href="javascript:;" class="repair-count"
										id="repairCount_record">22</a>
								</div>
								<div>
									<span id="repair_record_toushu_fei">今日投诉次数</span><a href="javascript:;"
										class="complain-count" id="todayComplaintsCount_record">5</a>
									<span id="repair_record_wei_fei">未解决次数</span><a href="javascript:;" class="nosolve-count"
										id="todayUnComplainsCount_record">1</a>
								</div>
								<div>
									<span id="repair_record_jin_fei">当日维修金额</span><a href="javascript:;" class="repair-fee"
										id="repairMoney">0</a>
								</div>
							</div>
						</div>
					</div>
					<div class="repair-record-list" id="repair_overman_record">
						<table class="repair-record-list-title">
							<tr>
								<th class="repair-record-master-face">
									&nbsp;
								</th>
								<th>
									师傅
								</th>
								<th>
									电话
								</th>
								<th>
									维修总次数
								</th>
								<th>
									被投诉次数
								</th>
								<th>
									评价
								</th>
							</tr>
						</table>
						<table>
							<tr>
								<td class="repair-record-master-face">
									<img
										src="${pageContext.request.contextPath }/images/repair/master-face1.png" />
								</td>
								<td class="repair-record-master-name">
									王朝
								</td>
								<td class="repair-record-master-name">
									18512663255
								</td>
								<td class="repair-record-count">
									<a href="javascript:;">25</a>
								</td>
								<td class="repair-record-count">
									<a href="javascript:;">1</a>
								</td>
								<td>
									<img
										src="${pageContext.request.contextPath }/images/repair/evaluate_star_liaht3.png" />
								</td>
							</tr>
						</table>
						<table>
							<tr>
								<td class="repair-record-master-face">
									<img
										src="${pageContext.request.contextPath }/images/repair/master-face2.png" />
								</td>
								<td class="repair-record-master-name">
									王朝
								</td>
								<td class="repair-record-master-name">
									18512663255
								</td>
								<td class="repair-record-count">
									<a href="javascript:;">25</a>
								</td>
								<td class="repair-record-count">
									<a href="javascript:;">1</a>
								</td>
								<td>
									<img
										src="${pageContext.request.contextPath }/images/repair/evaluate_star_liaht3.png" />
								</td>
							</tr>
						</table>
						<div class="repair-record-head">

						</div>
						<div class="repair-record-body">
							<ul>
								<li></li>
							</ul>
						</div>

					</div>
				</div>
				<div class="divide-page" 
					style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


					<input type="hidden" id="repairRecordPageNum_get" />
					<input type="hidden" id="repairRecordPageSize_get" />

					<a href="javascript:void(0);" onclick="getRepairRecordList2(-3);">首页</a>
					<a href="javascript:void(0);" onclick="getRepairRecordList2(-1);">上一页</a>
					当前第
					<font id="repairRecordPageNum"></font> 页 共
					<font id="repairRecordPageSize"></font> 页 共
					<font id="repairRecordSum"></font> 条

					<a href="javascript:void(0);" onclick="getRepairRecordList2(-2);">下一页</a>
					<a href="javascript:void(0);" onclick="getRepairRecordList2(-4);">尾页</a>
				</div>
			</div>
		</section>
	</body>
	<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>