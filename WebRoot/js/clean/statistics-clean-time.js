var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
function getQuickShopTop(sort, startTime, endTime) {
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopTop?sort="
			+ sort + "&startTime=" + startTime + "&endTime=" + endTime;

	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var startTimeFilter = myDate.getMonth() + "月" + myDate.getDate() + "日";
	var endTimeFilter = myDate2.getMonth() + "月" + myDate2.getDate() + "日";
	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					document.getElementById("nearby_drugstore_num").innerHTML = data.orderQuantity;
					document.getElementById("statistics_date").innerHTML = "统计时间："
							+ startTimeFilter + "-" + endTimeFilter;
				},
				error : function(er) {
				}
			});
}

function getTimeQuantumTop(sort, startTime, endTime,type) {
	var path = "/api/v1/communities/1/users/1/orderHistories/getTimeQuantumTop?sort="
			+ sort + "&startTime=" + startTime + "&endTime=" + endTime;
	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var min = "45min以上";
					var max = data.avgServeTime;

					if (parseInt(data.serveTime15) > parseInt(max)) {
						max = data.serveTime15;
						min = "1-15min";
					}
					if (parseInt(data.serveTime30) > parseInt(max)) {
						max = data.serveTime30;
						min = "15-30min";
					}
					if (parseInt(data.serveTime45) > parseInt(max)) {
						max = data.serveTime45;
						min = "30-45min";
					}
					var zhong = parseInt(data.serveTime)
							+ parseInt(data.serveTime30)
							+ parseInt(data.serveTime45)
							+ parseInt(data.serveTime15);
					var ratio = 0;
					if(zhong!=0){
						var ratio = ((max / zhong) * 100).toFixed(2);
					}
					
					document.getElementById("turnover_xianxia").innerHTML = data.avgServeTime;
					document.getElementById("turnover_xianshang").innerHTML = data.serveTime15;
					document.getElementById("turnover_zong").innerHTML = data.serveTime30;
					document.getElementById("turnover_zong_get").value = data.serveTime45;
					document.getElementById("max_serve_time").innerHTML = min;
					document.getElementById("ratio_serve_time").innerHTML = ratio;
					
					var myDate = new Date(stringToTime(startTime));
					var myDate2 = new Date(stringToTime(endTime));
					var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
					// var datNum=GetDateDiff(myDate,myDate2);
					// alert(startTime+endTime);
					// alert(dates);

					timeQuantum(startTime, endTime);

					timeDisplay(type);
					
					
					
				},
				error : function(er) {
				}
			});
}

function quickShopData(sort) {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	// getQuickShopList(1,startTime, endTime);
	getTimeQuantumList(sort, 1, startTime, endTime);
	getTimeQuantumTop(sort, startTime, endTime,'日');
}

function maintainMonth(sort) {

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
	getTimeQuantumList(sort, 1, startTime, endTime);
	getTimeQuantumTop(sort, startTime, endTime,'月');

}

function getMasterRepairDetailPage(num) {
	var page_num = 1;
	var repairRecordPageNum = document
			.getElementById("master_repair_datai_PageNum_get").value;
	var repairRecordPageSize = document
			.getElementById("master_repair_datai_PageSize_get").value;

	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;

	var is_list_detail = document.getElementById("is_list_detail").value;
	var shopId = document.getElementById("shop_id_detail").value;

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

	if (is_list_detail == "detail") {
		getShopsOrderDetailList(page_num, shopId);
	} else {
		getQuickShopList(page_num, startTime, endTime);
	}

	getTimeQuantumTop(startTime, endTime);
}
function weekMonth(sort) {

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
	// getQuickShopList(1, startTime, endTime);
	getTimeQuantumList(sort, 1, startTime, endTime);
	getTimeQuantumTop(sort, startTime, endTime,'周');

}


function timeClick(clickId) {
	if (clickId == "data_clik") {
		document.getElementById("data_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		quickShopData(master_repir_sort);
	} else {
		document.getElementById("data_clik").className = "";
	}
	if (clickId == "week_clik") {
		document.getElementById("week_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;   
		weekMonth(master_repir_sort);
	} else {
		document.getElementById("week_clik").className = "";
	}
	if (clickId == "month_clik") {
		document.getElementById("month_clik").className = "select";
		var master_repir_sort = document
				.getElementById("master_repir_sort_fei").value;
		maintainMonth(master_repir_sort);
	} else {
		document.getElementById("month_clik").className = "";
	}
}
function getTimeQuantumList(sort, pageNum, startTime, endTime) {

	// document.getElementById("shop_sort_id").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getTimeQuantumList?pageSize=10&pageNum="
			+ pageNum + "&sort=" + sort + "&startTime=" + startTime
			+ "&endTime=" + endTime;

	$
			.ajax({
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
							.append("<tr class=\"odd\"><td>服务时间</td><td>0-15min</td>	<td>15-30min</td><td>30-45min</td>	<td>45min以上</td></tr>");

					for ( var i = 0; i < data.length; i++) {

						var liList = "";

						   if(i%2==0){
	                        	liList += "<tr class=\"even\">";
	                        }else{
	                        	
	                        	liList += "<tr class=\"odd\">";
	                        }
							

						liList += "<td>" + data[i].shopName + "</td><td >"
								+ data[i].serveTime15 + "</td>";

						liList += "<td>" + data[i].serveTime30 + "</td><td>"
								+ data[i].serveTime45 + "</td><td>"
								+ data[i].serveTime + "</td></tr>";

						repair_overman.append(liList);

					}
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
					document.getElementById("master_repir_sort_fei").value = sort;
				},
				error : function(er) {
				}
			});

}


function selectStatistics(){
	   var  sort=document.getElementById("master_repir_sort_fei").value;
	   var startTime= document.getElementById("txtBeginDate").value+" 00:00:00";
	   var endTime= document.getElementById("txtEndDate").value+" 00:00:00";
	   getTimeQuantumList(sort, 1, startTime, endTime);
		getTimeQuantumTop(sort, startTime, endTime);
	   
	}


function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d=alterDate(num,type,startTime);
	var startTime=d.start;
	var endTime=d.end;
	// alert(startTime+endTime);
	// getQuickShopList(1, startTime, endTime);
	getTimeQuantumList(sort, 1, startTime, endTime);
	getTimeQuantumTop(sort, startTime, endTime,type);
   

}
