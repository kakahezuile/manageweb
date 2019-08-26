var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

function getPayTurnoverTop(startTime, endTime,type) {
	var path =  "/api/v1/communities/1/users/1/payments/getPayTurnoverTop?startTime="
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
					}

					document.getElementById("nearby_take_out_num").innerHTML = data.payAmount;

					var myDate = new Date(stringToTime(startTime));
					var myDate2 = new Date(stringToTime(endTime));
					var dates = Math.abs((myDate2 - myDate))
							/ (1000 * 60 * 60 * 24);
					// var datNum=GetDateDiff(myDate,myDate2);
					// alert(startTime+endTime);
					// alert(dates);


					var orderPrice = data.paySum / dates;
					var onlinePrice = data.payAmount / dates;
				//	document.getElementById("avg_day_turnover").innerHTML = orderPrice
				//			.toFixed(2);
//					document.getElementById("avg_day_order").innerHTML = onlinePrice
//							.toFixed(2);
					
					timeQuantum(startTime, endTime);
					timeDisplay(type);

				},
				error : function(er) {
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
	getPayTurnoverTop(startTime, endTime,'日');
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
	getPayTurnoverTop(startTime, endTime,'日');

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

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime,'周');

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
		quickShopData();
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
	var path =  "/api/v1/communities/1/users/1/payments/getPayTurnoverList?pageSize=10&pageNum="
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
				},
				error : function(er) {
				}
			});

}

function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type=document.getElementById("date_type_get").value;
	var d=alterDate(num,type,startTime);
	var startTime=d.start;
	var endTime=d.end;
	getTurnoverList(1, startTime, endTime);
	getPayTurnoverTop(startTime, endTime,type);
}