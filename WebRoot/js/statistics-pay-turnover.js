var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
function stringToTime(string) {
	var f = string.split(' ', 2);
	var d = (f[0] ? f[0] : '').split('-', 3);
	var t = (f[1] ? f[1] : '').split(':', 3);
	return (new Date(parseInt(d[0], 10) || null, (parseInt(d[1], 10) || 1) - 1,
			parseInt(d[2], 10) || null, parseInt(t[0], 10) || null, parseInt(
					t[1], 10)
					|| null, parseInt(t[2], 10) || null)).getTime();

}

function getPayTurnoverTop(startTime, endTime) {
	var path = projectName
			+ "/api/v1/communities/1/pay/getPayTurnoverTop?startTime="
			+ startTime + "&endTime=" + endTime;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					document.getElementById("nearby_cvs_num").innerHTML = data.paySum;
					document.getElementById("pay_sum").value = data.paySum;
					if (data.payAmount != 0) {
						document.getElementById("nearby_drugstore_num").innerHTML = (data.paySum / data.payAmount)
								.toFixed(2);

					}else{
						
						document.getElementById("nearby_drugstore_num").innerHTML =0;

					}

					document.getElementById("nearby_take_out_num").innerHTML = data.payAmount;

					var myDate = new Date(stringToTime(startTime));
					var myDate2 = new Date(stringToTime(endTime));
					var dates = Math.abs((myDate2 - myDate))
							/ (1000 * 60 * 60 * 24);
					// var datNum=GetDateDiff(myDate,myDate2);
					// alert(startTime+endTime);
					// alert(dates);

					var startTimeFilter = myDate.getMonth() + 1 + "月"
							+ myDate.getDate() + "日";
					var endTimeFilter = myDate2.getMonth() + 1 + "月"
							+ myDate2.getDate() + "日";

					var orderPrice = data.paySum / dates;
					var onlinePrice = data.payAmount / dates;
					document.getElementById("statistics_date_1").innerHTML = startTimeFilter;
					document.getElementById("statistics_date_2").innerHTML = endTimeFilter;
					//document.getElementById("avg_day_turnover").innerHTML = orderPrice
					//		.toFixed(2);
					document.getElementById("avg_day_order").innerHTML = onlinePrice
							.toFixed(2);

				},
				error : function(er) {
					alert(er);
				}
			});
}

function quickPayData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	// getQuickShopList(1,startTime, endTime);
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime);
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
	// getQuickShopList(1, startTime, endTime);
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime);

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
	getTurnoverList(page_num, startTime, endTime);
}
function weekMonth() {

	var myDate = new Date(); // 当前日期

	myDate.getFullYear();
	myDate.getMonth();
	myDate.getDate();
	var month = myDate.getMonth() + 1;
	var nowDayOfWeek = myDate.getDay();
	var da = myDate.getDate() - nowDayOfWeek;
	var daa = da - 6;
	var startTime = myDate.getFullYear() + "-" + month + "-" + daa
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime);

}
function selectStatistics() {
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime);

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
function getTurnoverList(pageNum, startTime, endTime) {
	var path = projectName
			+ "/api/v1/communities/1/pay/getPayTurnoverList?pageSize=10&pageNum="
			+ pageNum + "&startTime=" + startTime + "&endTime=" + endTime;

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

					repair_overman
							.append("<tr class=\"odd\"><td>类别</td><td>次数</td>	<td>合计	</td><td>缴费占比</td>	</tr>");
                    var paySum=document.getElementById("pay_sum").value;
					for ( var i = 0; i < data.length; i++) {

						var liList = "";

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>";

						if( data[i].type ==1){
							liList+="电费";
						}else{
							liList+= data[i].type ;
						}
						
						liList += "</td><td >"
								+ data[i].payAmount + "</td>";

						liList += "<td>" + data[i].paySum + "</td><td>" ;
						
						if(paySum!=""&&paySum!=0){
							liList +=(data[i].paySum/paySum);
							
						}
					
						liList	+= "</td></tr>";

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

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
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
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime);
}