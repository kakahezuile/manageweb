<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Properties properties = PropertyTool
			.getPropertites("/configure.properties");
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
		<title>历史通知_小间物业管理系统</title>
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
		<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
		<script type="text/javascript">
	var communityId = window.parent.document
			.getElementById("community_id_index").value;
function getPublishList(pageNum,startTime,endTime){
	$("#gonggao_referesh").html("");
	if(startTime==null||startTime==""){
	     startTime="2014-1-1 00:00:00";
	     var myCreate = new Date();
	     var endTime = myCreate.getFullYear() + "-" + (myCreate.getMonth()+1) + "-" + (myCreate.getDate()+1)+" 00:00:00";
	}
	var path = "<%=path%>/api/v1/communities/"+communityId+"/bulletin/getTimeBullentinList?pageNum="
				+ pageNum
				+ "&pageSize=10&startTime="
				+ startTime
				+ "&endTime="
				+ endTime;
		$
				.post(
						path,
						function(data) {

							$("#notice_datai_PageNum_get").val(data.num);
							$("#notice_datai_PageSize_get").val(data.pageCount);

							$("#notice_datai_PageNum").html(data.num);
							$("#notice_datai_PageSize").html(data.pageCount);
							$("#notice_datai_sum").html(data.rowCount);
							var dataList = data.pageData;
							var repair_overman = $("#notice_history");
							repair_overman.empty();
							repair_overman
									.append("<tr><th>主题</th><th>经手人</th><th>时间</th><th>送达对象</th><th>主要内容</th></tr>");
							for ( var i = 0; i < dataList.length; i++) {

								var tempText = dataList[i].bulletinText;
								var createTime = dataList[i].createTime;
								var theme = dataList[i].theme;
								var adminId = dataList[i].adminId;
								var userName = dataList[i].userName;
								var senderObject = dataList[i].senderObject;
								var myCreateDate = new Date(createTime * 1000);
								//	console.info(myCreateDate.Format("yyyy-MM-dd "));
								var sysDate = myCreateDate.getFullYear()
										+ "-"
										+ (myCreateDate.getMonth() + 1)
										+ "-"
										+ myCreateDate.getDate();
								var notice_list = "<tr class=\"odd\">";
								notice_list += "<td>" + theme + "</td>";
								notice_list += "<td>" + userName + "</td>";
								notice_list += "<td>" + sysDate + "</td>";
								if (senderObject == "portion") {
									notice_list += "<td>" + "指定用户" + "</td>";
								} else {
									notice_list += "<td>" + "小区全部用户" + "</td>";
								}

								notice_list += "<td><div class=\"detail\"><a href=\"<%=path %>/jsp/estate/notice/notice_detail.jsp?bulletinId="+dataList[i].id+"\">"
										+ tempText
										+ "</a></div></td>";
								repair_overman.append(notice_list);

							}

							document.getElementById("notice_startTime").value = startTime;
							document.getElementById("notice_endTime").value = endTime;

						}, "json");
	}

	function notice_notice_detail_jsp(bulletinId) {
		setNone();
		$("#notice_notice_detail_jsp").attr("style", "display:block");
		getPublishDetail(bulletinId);

	}

	function getNoticeDetailPage(num) {
		var page_num = 1;
		var repairRecordPageNum = document
				.getElementById("notice_datai_PageNum_get").value;
		var repairRecordPageSize = document
				.getElementById("notice_datai_PageSize_get").value;

		var startTime = document.getElementById("notice_startTime").value;
		var endTime = document.getElementById("notice_endTime").value;

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
		getPublishList(page_num, startTime, endTime);

	}

	// 日历控件
	$(function() {
		$("#noticeBeginDate").calendar({
			//controlId : "divDate", // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
			speed : 200, // 三种预定速度之一的字符串("slow", "normal", or
			// "fast")或表示动画时长的毫秒数值(如：1000),默认：200
			complement : true, // 是否显示日期或年空白处的前后月的补充,默认：true
			readonly : true, // 目标对象是否设为只读，默认：true
			upperLimit : new Date(), // 日期上限，默认：NaN(不限制)
			lowerLimit : new Date("2011/01/01"), // 日期下限，默认：NaN(不限制)
			callback : function() { // 点击选择日期后的回调函数
				// alert("您选择的日期是：" + $("#txtBeginDate").val());
			}
		});
		$("#noticeEndDate").calendar();
	});
	

	

	function getTimeList() {

		var noticeBeginDate = document.getElementById("noticeBeginDate").value;
		var noticeEndDate = document.getElementById("noticeEndDate").value;
		getPublishList(1,noticeBeginDate,noticeEndDate);
		
	}
	getPublishList(1);
</script>

	</head>
	<body>

		<input id="notice_startTime" type="hidden" value="" />
		<input id="notice_endTime" type="hidden" value="" />

		<section>
			<div class="content clearfix">
				<jsp:include flush="true" page="../common/notice-left.jsp"></jsp:include>
				<div class="notice-right-body">
					<div class="repair-title-line">
						<p class="notice-title">
							历史通知
						</p>
					</div>
					<div class="notice-history-search">
						<input type="text" id="noticeBeginDate" placeholder="开始时间" />
						<span>至</span>
						<input type="text" id="noticeEndDate" placeholder="结束时间" />
						<a href="javascript:;" onclick="getTimeList();">查询</a>
					</div>
					<div class="notice-history-content">
						<table id="notice_history">
							<tr>
								<th>
									主题
								</th>
								<th>
									经手人
								</th>
								<th>
									时间
								</th>
								<th>
									送达对象
								</th>
								<th>
									主要内容
								</th>
							</tr>
							<tr class="odd">
								<td>
									停水公告
								</td>
								<td>
									王班长
								</td>
								<td>
									2015-2-25 17:25
								</td>
								<td>
									小区全部用户
								</td>
								<td class="notcie-history_content">
									<a href="javascript:;">由于大面积停电，影响面较广，所发为正式通知，格式规范。原因，时间，范围应尽量详细...</a>
								</td>
							</tr>
							<tr class="even">
								<td>
									停水公告
								</td>
								<td>
									王班长
								</td>
								<td>
									2015-2-25 17:25
								</td>
								<td>
									小区全部用户
								</td>
								<td class="notcie-history_content">
									<a href="javascript:;" onclick="notice_notice_detail_jsp();">由于大面积停电，影响面较广，所发为正式通知，格式规范。原因，时间，范围应尽量详细...</a>
								</td>
							</tr>
						</table>

					</div>
					<div class="divide-page" style="float: left; width: 100%; height: 50px; clear: both; text-align: center;">


						<input type="hidden" id="notice_datai_PageNum_get" />
						<input type="hidden" id="notice_datai_PageSize_get" />

						<a href="javascript:void(0);" onclick="getNoticeDetailPage(-3);">首页</a>
						<a href="javascript:void(0);" onclick="getNoticeDetailPage(-1);">上一页</a>
						当前第
						<font id="notice_datai_PageNum"></font> 页 共
						<font id="notice_datai_PageSize"></font> 页 共
						<font id="notice_datai_sum"></font> 条

						<a href="javascript:void(0);" onclick="getNoticeDetailPage(-2);">下一页</a>
						<a href="javascript:void(0);" onclick="getNoticeDetailPage(-4);">尾页</a>
					</div>
				</div>
			</div>
		</section>
	</body>
	<script type="text/javascript">
	
	notice_left("notice_notice_history");
	</script>
</html>