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
String type=request.getParameter("type");
String startTime=request.getParameter("startTime");
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
		<title>维修记录_小间物业管理系统</title>
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
			href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>




<script type="text/javascript">

	var communityId = window.parent.document
			.getElementById("community_id_index").value;
 
	function conversionRecord(id) {
		/*var top_data = document.getElementById("top_data_record");
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
		}*/

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
		 document.getElementById("top_day_type").innerHTML="本日维修统计";
		maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);

	}

	function maintainMonthRecord(sort) {
		conversionRecord("top_month_record");
       // var sort = document.getElementById("repair_shops_sort").value;
		var myDate = new Date();
		var month = myDate.getMonth() + 1;



		var startTime = myDate.getFullYear() + "-" + month + "-1 00:00:00";
		var endTime = myDate.getFullYear() + "-" + (month+1) + "-1 00:00:00";
		document.getElementById("repair_time_startTime").value = startTime;
		document.getElementById("repair_time_endTime").value = endTime;
		 trade_d_m_record("月");
		  document.getElementById("top_day_type").innerHTML="本月维修统计";
		maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);

	}

	function maintainRecord(startTime, endTime,sort) {
	  scheduleAll();
		var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/top?startTime="
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
						document.getElementById("abandonCount_record").innerHTML = data.abandonCount;
						document.getElementById("dataDay").innerHTML = data.endOrder;
						if (data.repairMoney != "null") {
							document.getElementById("repairMoney").innerHTML = data.repairMoney;

						}
						onScheduleAll();

					},
					error : function(er) {
					}
				});
	}
	
	
	function trade_d_m_record(M){
		var repairCount_d_m=document.getElementById("repair_record_weixiu_fei");
		var todayComplaintsCount_d_m=document.getElementById("repair_record_toushu_fei");
		var todayUnComplainsCount_d_m=document.getElementById("repair_record_wei_fei");
		var abandonCount=document.getElementById("abandonCount");
		var dataDay_get=document.getElementById("dataDay_get");
		var repairMoney_d_m=document.getElementById("repair_record_jin_fei");
		
		repairCount_d_m.innerHTML="本"+M+"报修次数";
		dataDay_get.innerHTML="本"+M+"结单次数";
		todayUnComplainsCount_d_m.innerHTML="本"+M+"拒绝次数";
		abandonCount.innerHTML="本"+M+"放弃次数";
		repairMoney_d_m.innerHTML="本"+M+"维修金额";
		todayComplaintsCount_d_m.innerHTML="本"+M+"投诉次数";
		
		
	
	
	}
	
	
    function	overmanRecord(startTime,endTime,pageNum,sort){
    document.getElementById("type_repair").value="overman";
	  var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/getHistoryMaintainList?startTime="
				+ startTime + "&endTime=" + endTime + "&pageNum=" + pageNum+"&sort="+sort;

		$.ajax({
					url : path,
					type : "GET",
					dataType : 'json',
					success : function(data) {

						data = data.info;
/*
						$("#repairRecordPageNum_get").val(data.num);
						$("#repairRecordPageSize_get").val(data.pageCount);

						$("#repairRecordPageNum").html(data.num);
						$("#repairRecordPageSize").html(data.pageCount);
						$("#repairRecordSum").html(data.rowCount);

						data = data.pageData;*/
						var repair_overman = $("#repair_overman_record");
						repair_overman.empty();
						//var liList='';
						if(sort==5){
						repair_overman
								.append(""
										+ "<tr><th class='repair-record-master-face'>&nbsp;</th>"
										+ "<th>师傅</th>"
										+ "<th>电话</th>"
										+ "<th>维修次数</th>"
										+ "<th>结单次数</th>"
										+ "<th>拒绝次数</th>"
										+ "<th>放弃次数</th>"
										+ "<th>维修金额</th>"
										+ "<th>投诉次数</th></tr>");
						}else{
						repair_overman
								.append(""
										+ "<tr><th class='repair-record-master-face'>&nbsp;</th>"
										+ "<th>阿姨</th>"
										+ "<th>电话</th>"
										+ "<th>保洁次数</th>"
										+ "<th>被投诉次数</th>"
										+ "<th>评价</th>" + "</tr>");
						
						}
						
						for ( var i = 0; i < data.length; i++) {

							var liList = "";

							liList += "<tr>";

							liList += "<td class='repair-record-master-face'><img src='"+data[i].logo+"'/></td>";
							liList += "<td class='repair-record-master-name'>" + data[i].shopName + "</td>";
							liList += "<td class='repair-record-master-name'>" + data[i].phone + "</td>";
							liList += "<td class='repair-record-count'><a href=\"javascript:;\" onclick=\"repairDetail('"+data[i].shopId+"');\">"
									+ data[i].repairStatisticsCount
									+ "</a></td>";
									
							liList += "<td class='repair-record-master-face'>" + data[i].endOrder + "</td>";
							liList += "<td class='repair-record-master-name'>" + data[i].unsolvedOrder + "</td>";
							liList += "<td class='repair-record-master-name'>" + data[i].abandonCount + "</td>";
							if(data[i].orderPric=="null"){
							  liList += "<td class='repair-record-master-name'>0.0</td>";
							}else{
							  liList += "<td class='repair-record-master-name'>" + data[i].orderPric + "</td>";
							}
							
							liList += "<td class='repair-record-count'><a href=\"javascript:;\" onclick=\"repairComplain('"+data[i].shopId+"');\">"
									+ data[i].tousu
									+ "</a></td></tr>";

							repair_overman.append(liList);

						}

						repair_overman
								.append("<div class='repair-record-head'>"
										+ "</div><div class='repair-record-body'>"
										+ "<ul><li></li></ul></div>");
										onScheduleAll();

					},
					error : function(er) {
					}
				});

	}
	
	function repairDetail(shopId){
	     var startTime=document.getElementById("repair_time_startTime").value;
	     var endTime=document.getElementById("repair_time_endTime").value;
	     window.location="<%=path %>/jsp/estate/property/master_repair_detail.jsp?&shopId="+shopId+"&sort=5&type=<%=type%>&startTime="+startTime+"&endTime="+endTime;
	
	}
	
	function repairComplain(shopId){
	     var startTime=document.getElementById("repair_time_startTime").value;
	     var endTime=document.getElementById("repair_time_endTime").value;
	     window.location="<%=path %>/jsp/estate/property/repair_complain.jsp?sort=5&type=<%=type%>&shopId="+shopId+"&startTime="+startTime+"&endTime="+endTime;
	
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
		var type=document.getElementById("type_repair").value;
		
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
	   if(type=="overman"){
	     overmanRecord(startTime, endTime, page_num,sort);
	   }else if(type=="complaints"){
	     getComplaintsList(startTime, endTime, page_num);
	   }else if(type=="comments"){
	      getCommentsList(startTime, endTime, page_num);
	   }
		
	}
	
	
	

  function selectRecordMainainDuan(startTime,endTime,sort){
  
    //  var startTime=document.getElementById("repair_record_txtBeginDate").value+" 00:00:00";
 //      var endTime=document.getElementById("repair_record_txtEndDate").value+" 00:00:00";
    //   var sort = document.getElementById("repair_shops_sort").value;
    
        var sta=startTime.replace(" 00:00:00","");
        var end=endTime.replace(" 00:00:00","");
        document.getElementById("top_day_type").innerHTML=sta + "至"+end + "维修统计";
       
       maintainRecord(startTime, endTime,sort);
		overmanRecord(startTime, endTime, 1,sort);
		
  }
  
  	function getComplaintsList(startTime, endTime, page_num){
  		document.getElementById("type_repair").value="complaints";
  		if(startTime==""){
	  		var startTime = document.getElementById("repair_time_startTime").value;
			var endTime = document.getElementById("repair_time_endTime").value;
  		}
		
		var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/getComplaintsList?startTime="
						+ startTime + "&endTime=" + endTime +"&pageNum=1";
		
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
							repair_overman
								.append("	<table class='repair-record-list-title'>"
										+ "<tr><th class='repair-record-master-face'>&nbsp;</th>"
										+ "<th>师傅</th>"
										+ "<th>电话</th>"
										+ "<th>被投诉次数</th>");
							for ( var i = 0; i < data.length; i++) {
	
								var liList = "";
	
								liList += "<table ><tr>";
	
								liList += "<td class='repair-record-master-face'><img src='"+data[i].logo+"'/></td>";
								liList += "<td class='repair-record-master-name'>"
										+ data[i].shopName + "</td>";
								liList += "<td class='repair-record-master-name'>"
										+ data[i].phone + "</td>";
								liList += "<td class='repair-record-count'>"
										+ data[i].complaintsCount
										+ "</td>";
	
								repair_overman.append(liList);
	
							}
								
					},
					error : function(er) {
					}
				});
					
	}
  
  	function getCommentsList(startTime, endTime, page_num){
     	document.getElementById("type_repair").value="comments";
		if(startTime==""){
	  		var startTime = document.getElementById("repair_time_startTime").value;
			var endTime = document.getElementById("repair_time_endTime").value;
  		}
		var path = "<%= path%>/api/v1/communities/"+communityId+"/repairStatistics/getCommentsList?startTime="
						+ startTime + "&endTime=" + endTime +"&pageNum=1";
		
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
							repair_overman
								.append("	<table class='repair-record-list-title'>"
										+ "<tr><th class='repair-record-master-face'>&nbsp;</th>"
										+ "<th>师傅</th>"
										+ "<th>电话</th>"
										+ "<th>评分</th>");
							for ( var i = 0; i < data.length; i++) {
	
								var liList = "";
	
								liList += "<table ><tr>";
	
								liList += "<td class='repair-record-master-face'><img src='"+data[i].logo+"'/></td>";
								liList += "<td class='repair-record-master-name'>"
										+ data[i].shopName + "</td>";
								liList += "<td class='repair-record-master-name'>"
										+ data[i].phone + "</td>";
								liList += "<td><img src='${pageContext.request.contextPath }/images/repair/evaluate_star_liaht"+data[i].score+".png'/></td>";
	
								repair_overman.append(liList);
	
							}
								
					},
					error : function(er) {
					}
				});
					
	}
  
</script>
	</head>
	<body>
        <input type="hidden" id="top_data_type"/>
        <input type="hidden" id="type_repair"/>
		<input type="hidden" id="repair_time_startTime" />
		<input type="hidden" id="repair_time_endTime" />
		<input type="hidden" id="repair_shops_sort" />

		<section>
			<div class="content clearfix center-personal-info-content">
				<jsp:include flush="true" page="../common/service-left.jsp" />
				<div class="service-right-body">
					<div class="repair-head">

					  <!--
						<div class="time-chose" id="repair_record_top_section_time">   
							<input readonly="readonly" id="repair_record_txtBeginDate" value="请选择开始日期">
							<input readonly="readonly" id="repair_record_txtEndDate" value="请选择结束日期">
							<a href="javascript:void(0);" onclick="selectRecordMainainDuan();">查询</a>
						</div>
						-->
						<div class="repair-title">
						  
							<!--<a href="javascript:;" class="select" id="top_data_record"
								onclick="maintainDataRecord();">日</a>
							<a href="javascript:;" id="top_month_record"
								onclick="maintainMonthRecord();">月</a>
							<a href="javascript:;" id="top_section_record" onclick="conversionRecord('top_section_record');">时间段</a>
						  -->
						</div>
						<div class="repair-content" style="margin-top:10px;">
							<div class="repair-date">
								<span id="top_day_type">&nbsp;</span>
							</div>
							<div class="repair-stat-detal clearfix">
								<div>
									<p><a href="javascript:;" class="repair-count" id="repairCount_record">0</a></p>
									<p><span id="repair_record_weixiu_fei">今日维修次数</span></p>
								</div>
								
								<div>
									<p><a href="javascript:;" class="nosolve-count" id="dataDay">0</a></p>
									<p><span id="dataDay_get">结单次数</span></p>
								</div>
								
								<div>
									<p><a href="javascript:;" class="nosolve-count" id="todayUnComplainsCount_record">0</a></p>
									<p><span id="repair_record_wei_fei">未解决次数</span></p>
								</div>
								
								<div>
									<p><a href="javascript:;" class="nosolve-count" id="abandonCount_record">0</a></p>
									<p><span id="abandonCount">未解决次数</span></p>
								</div>
								<div>
									<p><a href="javascript:;" class="repair-fee" id="repairMoney">0</a></p>
									<p><span id="repair_record_jin_fei">当日维修金额</span></p>
								</div>
								<div>
									<p><a href="javascript:;" class="complain-count"  id="todayComplaintsCount_record">0</a></p>
									<p><span id="repair_record_toushu_fei" >今日投诉次数</span></p>
								</div>
								<!--<div><a class="see-evaluation" onclick="getCommentsList('','','1')">查看评价</a></div>-->
								
							</div>
						</div>
					</div>
					<div class="repair-record-list" >
					
					<table id="repair_overman_record" class="repair-record-list-title"></table>
						<div class="repair-record-head">

						</div>
						<div class="repair-record-body">
							<ul>
								<li></li>
							</ul>
						</div>

					</div>
					<!--<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">
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
				--></div>
				
			</div>
		</section>
		
				<div class="loadingbox" id="schedule_all" style="display: none;">
		<img alt="" src="<%= basePath %>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="bg_schedule" style="display: none;">&nbsp;</div>
		
	</body>
	<script type="text/javascript">
			 function scheduleAll(){
		$("#schedule_all").attr("style","display:block");
		$("#bg_schedule").attr("style","display:block");
		
	}
	
	function onScheduleAll(){
		$("#schedule_all").attr("style","display:none");
		$("#bg_schedule").attr("style","display:none");
	}
	function isDay(type){
	  if(type=="month"){
	   maintainMonthRecord("<%=sort%>");
	  }else if(type=="duan"){
	    selectRecordMainainDuan("<%=startTime%>","<%=endTime%>","<%=sort%>");
	  }else {
	  	maintainDataRecord("<%=sort%>");
	  
	  }
	  
	
	}
	isDay("<%=type%>");
	function leftSort(sort){
	   if(sort==5){
	     leftClikCss("left_repair_fei");
	   }else{
	     leftClikCss("left_clean_fei");
	   }
	}
		leftSort("<%=sort%>");
	</script>
</html>