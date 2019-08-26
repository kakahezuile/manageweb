var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

function getQuickShopTop(startTime, endTime) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = projectName
			+ "/api/v1/communities/1/shopsOrderHistory/getQuickShopCommentsTop?sort="
			+ sort + "&startTime=" + startTime + "&endTime=" + endTime;
	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					document.getElementById("nearby_cvs_num").innerHTML = data.commentsNum;
					document.getElementById("nearby_drugstore_num").innerHTML = data.goodComments;
					document.getElementById("nearby_take_out_num").innerHTML = data.commentsNum
							- data.goodComments;

					document.getElementById("avg_score_get").value = data.score;
					document.getElementById("complaints_num_get").value = data.complaintsNum;
					document.getElementById("orders_num_get").value = data.ordersNum;
					document.getElementById("comments_num_get").value = data.commentsNum;
					document.getElementById("good_comments_get").value = data.goodComments;

					var myDate = new Date(stringToTime(startTime));
					var myDate2 = new Date(stringToTime(endTime));
					var startTimeFilter = myDate.getMonth() + 1 + "月"
							+ myDate.getDate() + "日";
					var endTimeFilter = myDate2.getMonth() + 1 + "月"
							+ myDate2.getDate() + "日";

					document.getElementById("statistics_date_1").innerHTML = startTimeFilter;
					document.getElementById("statistics_date_2").innerHTML = endTimeFilter;

				},
				error : function(er) {
					alert(er);
				}
			});
}

function getQuickShopList(pageNum, startTime, endTime) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = projectName
			+ "/api/v1/communities/1/shopsOrderHistory/getQuickShopCommentsList?pageSize=10&pageNum="
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

					var avg_score_get = document
							.getElementById("avg_score_get").value;
					var complaints_num_get = document
							.getElementById("complaints_num_get").value;
					var orders_num_get = document
							.getElementById("orders_num_get").value;
					var comments_num_get = document
							.getElementById("comments_num_get").value;
					var good_comments_get = document
							.getElementById("good_comments_get").value;
					var top_bag = 0;
					var top_comp = 0;
					if (comments_num_get != 0) {
						top_bag = (((comments_num_get - good_comments_get) / comments_num_get) * 100)
								.toFixed(2);
						top_comp = ((complaints_num_get / comments_num_get) * 100)
								.toFixed(2);
					}
					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();
					// var liList='';
					repair_overman
							.append("<tr class=\"odd\"><td>统计时间	</td><td>评分</td><td>评价量	</td><td>差评（低于3）</td><td>差评占比</td><td>投诉量</td><td>投诉率</td></tr>");
					repair_overman.append("<tr class=\"even\"><td>合计	</td><td>"
							+ avg_score_get + "</td><td>" + comments_num_get
							+ "</td><td>"
							+ (comments_num_get - good_comments_get)
							+ "</td><td>" + top_bag + "</td><td>"
							+ complaints_num_get + "</td><td>" + top_comp
							+ "</td></tr>");

					for ( var i = 0; i < data.length; i++) {
						var bag = 0;
						var comp = 0;
						if (data[i].commentsNum != 0) {
							bag = ((data[i].badComments / data[i].commentsNum) * 100)
									.toFixed(2);
							comp = ((data[i].complaintsNum / data[i].commentsNum) * 100)
									.toFixed(2);
						}

						var liList = "";

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>" + data[i].shopName + "</td><td >"
								+ data[i].score + "</td>";
						liList += "<td><a onclick=\"getCommentDetailList('1','"
								+ data[i].shopId + "')\">"
								+ data[i].commentsNum + "</a></td><td>"
								+ data[i].badComments + "</td><td>" + bag
								+ "</td>";
						liList += "<td> <a onclick=\"getComplaintsDetailList('1','"
								+ data[i].shopId
								+ "')\">"
								+ data[i].complaintsNum + "</a></td>";
						liList += "<td>" + comp + "</td></tr>";

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

function quickShopData() {
	var myDate = new Date();
	myDate.getFullYear();
	myDate.getMonth();
	myDate.getDate();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime);
}

function maintainMonth() {

	var myDate = new Date();
	myDate.getFullYear();
	myDate.getMonth();
	var month = myDate.getMonth();

	var firstDate = new Date();

	firstDate.setDate(1); // 第一天

	var endDate = new Date(firstDate);

	endDate.setMonth(firstDate.getMonth());

	endDate.setDate(0);

	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + endDate.getDate()
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
		getCommentDetailList(page_num, shopId);
	} else if (is_list_detail == "compl_detail") {
		getComplaintsDetailList(page_num, shopId);
	} else {
		getQuickShopList(page_num, startTime, endTime);
	}

	getQuickShopTop(startTime, endTime);
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
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime);

}
function selectStatistics() {
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime);

}


function getCommentDetailList(pageNum, shopId) {

	// var path = "<%=
	// path%>/api/v1/communities/1/shopsOrderHistory/getQuickShopCommentsDetail?pageSize=10&pageNum="
	// + 1
	// + "&shopId="+shopId;

	document.getElementById("shop_id_detail").value = shopId;
	document.getElementById("is_list_detail").value = "detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = projectName
			+ "/api/v1/communities/1/shopsOrderHistory/getQuickShopCommentsDetail?pageSize=10"
			+ "&pageNum=" + pageNum + "&shopId=" + shopId + "&startTime="
			+ startTime + "&endTime=" + endTime;
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
							.append("<tr class=\"odd\"><td>用户	</td><td>用户地址</td><td>评分</td><td>评价内容</td><td>评价时间</td></tr>");

					for ( var i = 0; i < data.length; i++) {

						var liList = "";
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}
						liList += "<td>" + data[i].nickname + "</td><td >"
								+ data[i].userFloor + data[i].userUnit
								+ "</td>";
						liList += "<td>" + data[i].score + "</td><td>"
								+ data[i].content + "</td><td>"
								+ data[i].createTime + "</td></tr>";

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
function getComplaintsDetailList(pageNum, shopId) {

	// var path = "<%=
	// path%>/api/v1/communities/1/shopsOrderHistory/getComplaintsDetailList?pageSize=10&pageNum="
	// + 1
	// + "&shopId="+shopId;

	document.getElementById("shop_id_detail").value = shopId;
	document.getElementById("is_list_detail").value = "compl_detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = projectName
			+ "/api/v1/communities/1/shopsOrderHistory/getComplaintsDetailList?pageSize=10"
			+ "&pageNum=" + pageNum + "&shopId=" + shopId + "&startTime="
			+ startTime + "&endTime=" + endTime;
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
							.append("<tr class=\"odd\"><td>用户	</td><td>用户地址</td>><td>投诉内容</td><td>投诉时间</td><td>是否解决</td</tr>");

					for ( var i = 0; i < data.length; i++) {

						var liList = "";

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}
						liList += "<td>" + data[i].nickname + "</td><td >"
								+ data[i].userFloor + data[i].userUnit
								+ "</td>";
						liList += "<td>" + data[i].detail + "</td><td>"
								+ data[i].occurTime + "</td><td>"
								+ data[i].status + "</td></tr>";

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

function timeClick(clickId) {
	if (clickId == "data_clik") {
		document.getElementById("data_clik").className = "select";
	} else {
		document.getElementById("data_clik").className = "";
	}
	if (clickId == "week_clik") {
		document.getElementById("week_clik").className = "select";
	} else {
		document.getElementById("week_clik").className = "";
	}
	if (clickId == "month_clik") {
		document.getElementById("month_clik").className = "select";
	} else {
		document.getElementById("month_clik").className = "";
	}

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
	// alert(startTime+endTime);
	// getQuickShopList(1, startTime, endTime);
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime);

}