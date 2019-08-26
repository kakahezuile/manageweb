var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
function getPreviousWeekStartEnd(date) {

	var date = new Date() || date, day, start, end, dayMSec = 24 * 3600 * 1000;

	today = date.getDay() - 1;

	end = date.getTime() - today * dayMSec;

	start = end - 7 * dayMSec;

	return {
		start : getFormatTime(start),
		end : getFormatTime(end)
	};

	function getFormatTime(time) {

		date.setTime(time);

		return date.getFullYear() + '-'
				+ ('0' + (date.getMonth() + 1)).slice(-2) + '-'
				+ ('0' + date.getDate()).slice(-2) + ' 00:00:00';

	}

}
function GetDateDiff(startDate, endDate) {
	var startTime = new Date(Date.parse(startDate.replace(/-/g, "/")))
			.getTime();
	var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
	var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
	return dates;
}
function stringToTime(string) {
	var f = string.split(' ', 2);
	var d = (f[0] ? f[0] : '').split('-', 3);
	var t = (f[1] ? f[1] : '').split(':', 3);
	return (new Date(parseInt(d[0], 10) || null, (parseInt(d[1], 10) || 1) - 1,
			parseInt(d[2], 10) || null, parseInt(t[0], 10) || null, parseInt(
					t[1], 10)
					|| null, parseInt(t[2], 10) || null)).getTime();

}
function getPayTop( startTime, endTime) {
	var path = projectName+"/api/v1/communities/1/pay/getPayTop?startTime=" + startTime + "&endTime=" + endTime;

	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
	var endTimeFilter = myDate2.getMonth() + 1 + "月" + myDate2.getDate() + "日";
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var ord = data.orderPay / dates;
					document.getElementById("nearby_drugstore_num").innerHTML = data.orderPay;
					
					
					document.getElementById("avg_day_turnover").innerHTML =  ord.toFixed(0);
					document.getElementById("avg_day_order").innerHTML = ord.toFixed(0);
					document.getElementById("statistics_date_1").innerHTML = startTimeFilter;
					document.getElementById("statistics_date_2").innerHTML = endTimeFilter;
				},
				error : function(er) {
					alert(er);
				}
			});
}


function quickPayData() {
	var myDate = new Date();

	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() - 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	getPayList( 1, startTime, endTime);
	getPayTop( startTime, endTime);
}

function maintainMonth() {

	var myDate = new Date();
	var month = myDate.getMonth();

	var firstDate = new Date();

	firstDate.setDate(1); // 第一天

	var endDate = new Date(firstDate);

	endDate.setMonth(firstDate.getMonth());

	endDate.setDate(0);

	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + endDate.getDate()
			+ " 00:00:00";
	getPayList( 1, startTime, endTime);
	getPayTop( startTime, endTime);

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var myDate = new Date(stringToTime(startTime));
	myDate.getMonth();
	var startTime = "";
	var endTime = "";
	if (num == 1) {
		var month = myDate.getMonth() + 1;
		startTime = myDate.getFullYear() + "-" + myDate.getMonth() + "-" + 1
				+ " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	} else if (num == 2) {
		var month = myDate.getMonth() + 2;
		var month2 = month + 1;
		startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month2 + "-" + 1 + " 00:00:00";

	} else {
		var month = myDate.getMonth() + 1;
		startTime = myDate.getFullYear() + "-" + myDate.getMonth() + "-" + 1
				+ " 00:00:00";
		endTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";

	}
	getPayList( 1, startTime, endTime);
	getPayTop(startTime, endTime);

}
function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getPayList( 1, startTime, endTime);
	getPayTop( startTime, endTime);

}


function selectStatistics() {
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getPayList( 1, startTime, endTime);
	getPayTop( startTime, endTime);
}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getPayList(1, startTime, endTime);
	getPayTop(startTime, endTime);
}

function getMasterRepairDetailPage(num) {
	var page_num = 1;
	var repairRecordPageNum = document
			.getElementById("master_repair_datai_PageNum_get").value;
	var repairRecordPageSize = document
			.getElementById("master_repair_datai_PageSize_get").value;

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
	getPayList(page_num, startTime, endTime);
}


function timeClick(clickId) {
	if (clickId == "data_clik") {
		document.getElementById("data_clik").className = "select";
		quickPayData();
	} else {
		document.getElementById("data_clik").className = "";
	}
	if (clickId == "week_clik") {
		document.getElementById("week_clik").className = "select";
		weekMonth();
	} else {
		document.getElementById("week_clik").className = "";
	}
	if (clickId == "month_clik") {
		document.getElementById("month_clik").className = "select";
		maintainMonth();
	} else {
		document.getElementById("month_clik").className = "";
	}
}

function getPayList(pageNum, startTime, endTime) {
	var path = projectName+"/api/v1/communities/1/pay/getPayList?pageSize=10&pageNum="
			+ pageNum
			
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

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					// var liList='';
					repair_overman
							.append("<tr class=\"odd\"><td>统计时间</td><td>点击量</td><td>下单量</td><td>下单比例</td>	</tr>");
					
					
					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate()-1;
					var da2 = myDate.getDate() - 2;
					var startTime = myDate.getFullYear() + "-" + month + "-"
							+ da+" 00:00:00";
					var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()+" 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-" + da2+" 00:00:00";
					
					
                    var startTime_day_long=stringToTime(startTime)/1000;
                    var endTime_day_long=stringToTime(endTime)/1000;
                    var startTime2_day_long=stringToTime(startTime2)/1000;
                    
					for ( var i = 0; i < data.length; i++) {
						
						var liList = "";
						  if(i%2==0){
			                  	liList += "<tr class=\"even\">";
			                  }else{
			                  	
			                  	liList += "<tr class=\"odd\">";
			               }
						//liList += "<tr class=\"odd\">";
						liList += "<td>" ;
						if( data[i].createTime>startTime_day_long&& data[i].createTime<endTime_day_long){
							liList += "昨天";
						}else if( data[i].createTime>startTime2_day_long&&data[i].createTime<startTime_day_long){
							liList += "前天";
							
						}else{
							

							var myDate = new Date(data[i].createTime*1000);

							var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
							liList+=startTimeFilter;
						}
						liList += "</td><td >100000</td>";
						liList += "<td>"
								+ data[i].orderPay
								+ "</td><td>100000</td></tr>";
						repair_overman.append(liList);

					}
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
				},
				error : function(er) {
					alert(er);
				}
			});

}


