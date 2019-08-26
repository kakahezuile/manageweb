var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
function getQuickShopTop(sort, startTime, endTime,type) {
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopTop?sort="
			+ sort + "&startTime=" + startTime + "&endTime=" + endTime;

	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);
	// var datNum=GetDateDiff(myDate,myDate2);
	// alert(startTime+endTime);
	// alert(dates);

	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var ord = data.orderQuantity / dates;
					document.getElementById("nearby_drugstore_num").innerHTML = data.orderQuantity;
					document.getElementById("nearby_take_out_num").innerHTML = "89%";
					
					
					document.getElementById("avg_day_turnover").innerHTML =  ord.toFixed(0);
					document.getElementById("avg_day_order").innerHTML = ord.toFixed(0);
					document.getElementById("avg_day_ratio").innerHTML = "80%";
					
					timeQuantum(startTime, endTime);

					timeDisplay(type);

				},
				error : function(er) {
				}
			});
}

function getQuickShopList(pageNum, startTime, endTime) {
	// var path = "<%=
	// path%>/api/v1/communities/${communityId}/shopsOrderHistory/getQuickShop?pageSize=10&pageNum="+pageNum+"sort="+sort+"&startTime="
	// + startTime + "&endTime=" + endTime;

	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopList?pageSize=10&pageNum="
			+ pageNum
			+ "&sort="
			+ sort
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
							.append("<tr ><th>统计时间</th><th>点击量</th><th>下单量</th><th>下单比例</th>	<th>详情	</th></tr>");

					for ( var i = 0; i < data.length; i++) {

						var liList = "";
                        if(i%2==0){
                        	liList += "<tr class=\"even\">";
                        }else{
                        	
                        	liList += "<tr class=\"odd\">";
                        }
						
						liList += "<td>" + data[i].shopName
								+ "</td><td >100000</td>";
						liList += "<td>"
								+ data[i].orderQuantity
								+ "</td><td>100000</td><td><a onclick=\"getShopsOrderDetailList('1','"
								+ data[i].shopId + "');\">详情</a></td></tr>";
						repair_overman.append(liList);

					}
				},
				error : function(er) {
				}
			});
}

function quickShopData(sort) {
	var myDate = new Date();

	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() - 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	// getQuickShopList(1, startTime, endTime);
	getUseAmountList(sort, 1, startTime, endTime);
	getQuickShopTop(sort, startTime, endTime,'日');
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
	getUseAmountList(sort, 1, startTime, endTime);
	getQuickShopTop(sort, startTime, endTime,'月');

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type=document.getElementById("date_type_get").value;
	var d=alterDate(num,type,startTime);
	var startTime=d.start;
	var endTime=d.end;
	// alert(startTime+endTime);
	// getQuickShopList(1, startTime, endTime);
	getUseAmountList(sort, 1, startTime, endTime);
	getQuickShopTop(sort, startTime, endTime,type);

}
function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	// alert(d.start+d.end);
	var startTime = d.start;
	var endTime = d.end;

	// alert(startTime+endTime);

	// getQuickShopList(sort,1, startTime, endTime);
	getUseAmountList(sort, 1, startTime, endTime);
	getQuickShopTop(sort, startTime, endTime,'周');

}

function turnover() {
	document.getElementById("statistics_id").style.display = "none";
	document.getElementById("turnover_id").style.display = "";

}
function statistics() {
	document.getElementById("statistics_id").style.display = "";
	document.getElementById("turnover_id").style.display = "none";
}

function selectStatistics() {
	var  sort=document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getUseAmountList(sort, 1, startTime, endTime);
	getQuickShopTop(sort, startTime, endTime);


}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime);
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

	getQuickShopTop(startTime, endTime);
}

function getShopsOrderDetailList(pageNum, shopId) {
	document.getElementById("shop_id_detail").value = shopId;
	document.getElementById("is_list_detail").value = "detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopDetailList?pageSize=10"
			+ "&pageNum="
			+ pageNum
			+ "&shopId="
			+ shopId
			+ "&startTime="
			+ startTime + "&endTime=" + endTime;
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
			repair_overman.append("<tr class=\"odd\"><td>" + data[0].shopName
					+ "</td><td>点击量</td><td>下单量</td><td>下单比例</td>	</tr>");

			for ( var i = 0; i < data.length; i++) {

				var myDate = new Date(data[i].startTime * 1000);
				var month = myDate.getMonth();
				var date = myDate.getDate();
				var liList = "";
				  if(i%2==0){
                  	liList += "<tr class=\"even\">";
                  }else{
                  	
                  	liList += "<tr class=\"odd\">";
                  }
				//liList += "<tr class=\"odd\">";
				liList += "<td>" + month + "月" + date + "日"
						+ "</td><td >100000</td>";
				liList += "<td>" + data[i].orderQuantity
						+ "</td><td>100000</td></tr>";
				repair_overman.append(liList);

			}
		},
		error : function(er) {
		}
	});

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

function getUseAmountList(sort, pageNum, startTime, endTime) {

	// document.getElementById("shop_sort_id").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getUseAmountList?pageSize=10&pageNum="
			+ pageNum
			+ "&sort="
			+ sort
			+ "&startTime="
			+ startTime
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
							.append("<tr class=\"odd\"><td>统计时间</td><td>点击量</td><td>下单量</td><td>下单比例</td>	<td>详情	</td></tr>");
					
					
					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate()-1;
					var da2 = myDate.getDate() - 2;
					var startTime1 = myDate.getFullYear() + "-" + month + "-"
							+ da+" 00:00:00";
					var endTime1 = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()+" 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-" + da2+" 00:00:00";
					
					
                    var startTime_day_long=stringToTime(startTime1)/1000;
                    var endTime_day_long=stringToTime(endTime1)/1000;
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
						if( data[i].startTime>startTime_day_long&& data[i].startTime<endTime_day_long){
							liList += "昨天";
						}else if( data[i].startTime>startTime2_day_long&&data[i].startTime<startTime_day_long){
							liList += "前天";
							
						}else{
							

							var myDate = new Date(data[i].startTime*1000);

							var startTimeFilter = myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
							liList+=startTimeFilter;
						}
						liList += "</td><td >100000</td>";
						liList += "<td>"
								+ data[i].orderQuantity
								+ "</td><td>100000</td><td><a onclick=\"selectData('"
								+ data[i].startTime + "')\">详情</a></td></tr>";
						repair_overman.append(liList);

					}
					document.getElementById("master_repir_sort_fei").value = sort;
					// document.getElementById("shop_sort_id").value=sort;
				},
				error : function(er) {
				}
			});

}

function selectData(tim) {

	var da = tim * 1000;
	var myDate = new Date(da);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	// getUseAmountList(sort,1,startTime,endTime);
	// getQuickShopTop(startTime, endTime);
}
