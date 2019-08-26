var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
function getQuickShopTop(startTime, endTime, type) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopCommentsTop?sort=6&startTime="
			+ startTime + "&endTime=" + endTime;
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

					timeQuantum(startTime, endTime);

					timeDisplay(type);
				},
				error : function(er) {
				}
			});
}

function getQuickShopList(pageNum, startTime, endTime) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopCommentsList?pageSize=10&pageNum="
			+ pageNum
			+ "&sort=6&startTime="
			+ startTime
			+ "&endTime="
			+ endTime;
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
							+ "</td><td>" + top_bag + "%</td><td>"
							+ complaints_num_get + "</td><td>" + top_comp
							+ "%</td></tr>");

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
								+ "%</td>";
						liList += "<td> <a onclick=\"getComplaintsDetailList('1','"
								+ data[i].shopId
								+ "')\">"
								+ data[i].complaintsNum + "</a></td>";
						liList += "<td>" + comp + "%</td></tr>";

						repair_overman.append(liList);

					}

				},
				error : function(er) {
				}
			});
}

function quickShopData() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime, '日');
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
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime, '月');

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
	page_num = getMasterDetailPage(type, pageNum, pageSize);
	if (page_num != 0) {
		if (is_list_detail == "detail") {
			getCommentDetailList(page_num, shopId);
		} else if (is_list_detail == "compl_detail") {
			getComplaintsDetailList(page_num, shopId);
		} else {
			getQuickShopList(page_num, startTime, endTime);
		}

		getQuickShopTop(startTime, endTime);

	}

}
function weekMonth() {

	 var d=getPreviousWeekStartEnd();
	var startTime = d.start;
	
	var endTime = d.end;
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime, '周');

}
function selectStatistics() {
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime);

}



function getCommentDetailList(pageNum, shopId) {

	// var path = "<%=
	// path%>/api/v1/communities/1/users/1/orderHistories/getQuickShopCommentsDetail?pageSize=10&pageNum="
	// + 1
	// + "&shopId="+shopId;

	document.getElementById("shop_id_detail").value = shopId;
	document.getElementById("is_list_detail").value = "detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getQuickShopCommentsDetail?pageSize=10"
			+ "&pageNum="
			+ pageNum
			+ "&shopId="
			+ shopId
			+ "&startTime="
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
				},
				error : function(er) {
				}
			});

}
function getComplaintsDetailList(pageNum, shopId) {

	// var path = "<%=
	// path%>/api/v1/communities/1/users/1/orderHistories/getComplaintsDetailList?pageSize=10&pageNum="
	// + 1
	// + "&shopId="+shopId;

	document.getElementById("shop_id_detail").value = shopId;
	document.getElementById("is_list_detail").value = "compl_detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = "/api/v1/communities/1/users/1/orderHistories/getComplaintsDetailList?pageSize=10"
			+ "&pageNum="
			+ pageNum
			+ "&shopId="
			+ shopId
			+ "&startTime="
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
				},
				error : function(er) {
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
	var type = document.getElementById("date_type_get").value;
	
	var d=alterDate(num,type,startTime);
	var startTime=d.start;
	var endTime=d.end;
	// alert(startTime+endTime);
	// getQuickShopList(1, startTime, endTime);
	getQuickShopList(1, startTime, endTime);
	getQuickShopTop(startTime, endTime, type);

}