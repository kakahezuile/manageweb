var pathName = window.document.location.pathname;
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
function getQuickShopTop(startTime, endTime,type) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path =  "/api/v1/communities/1/express/getExpressTop?startTime=" + startTime + "&endTime=" + endTime;
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					document.getElementById("nearby_cvs_num").innerHTML = data.expressNum;
					document.getElementById("nearby_drugstore_num").innerHTML = data.appMessage;
					document.getElementById("nearby_take_out_num").innerHTML = data.shortNote;


					var myDate = new Date(stringToTime(startTime));
					var myDate2 = new Date(stringToTime(endTime));
					timeQuantum(startTime, endTime);

					timeDisplay(type);


				},
				error : function(er) {
				}
			});
}

function getQuickShopList(pageNum, startTime, endTime) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path =  "/api/v1/communities/1/express/getExpressList?pageSize=10&pageNum="
			+ pageNum +  "&startTime=" + startTime
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
							.append("<tr class=\"odd\"><td>统计时间	</td><td>总代收量</td><td>短信代收	</td><td>app代收</td></tr>");
				/*	repair_overman.append("<tr class=\"even\"><td>合计	</td><td>"
							+ avg_score_get + "</td><td>" + comments_num_get
							+ "</td><td>"
							+ (comments_num_get - good_comments_get)
							+ "</td><td>" + top_bag + "</td><td>"
							+ complaints_num_get + "</td><td>" + top_comp
							+ "</td></tr>");*/

					for ( var i = 0; i < data.length; i++) {

						var liList = "";

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>" + data[i].expressTime + "</td><td >"
								+ data[i].expressNum + "</td>";
						liList += "<td>"
								+ data[i].appMessage + "</td>";
				
						liList += "<td>" + data[i].shortNote + "</td></tr>";

						repair_overman.append(liList);

					}

				},
				error : function(er) {
				}
			});
}


function getExpressList(type,pageNum, startTime, endTime){
	var path =  "/api/v1/communities/1/express/getExpressDetail?pageNum="+pageNum+"&pageSize=10&startTime=" + startTime
			+ "&endTime=" + endTime+"&type="+type;

	$.ajax({

		url: path,

		type: "GET",

		dataType:'json',

		success:function(data){
			
			if(data.status == "yes"){
				
				data=data.info;
				$("#master_repair_datai_PageNum_get").val(data.num);
				$("#master_repair_datai_PageSize_get").val(data.pageCount);

				$("#master_repair_datai_PageNum").html(data.num);
				$("#master_repair_datai_PageSize").html(data.pageCount);
				$("#master_repair_datai_sum").html(data.rowCount);
				var pageData = data.pageData;
			
				var repair_overman = $("#statistics_list_id");
				repair_overman.empty();
				repair_overman.append("<tr><th>电话</th><th>快递单号</th><th>收件人</th><th>物品</th><th>通知结果</th><th>用户类型</th><th>收件时间</th></tr>");
				
				var len = pageData.length;
				for(var i = 0 ; i < len ; i++){
					var expressResult = "";
					if(pageData[i].expressResult == 1){
						expressResult = "<span class='success'>成功</span>";
						
					}else{
						expressResult = "<span class='fail'>失败</span>";
					}
					var expressUserType = "";
					if(pageData[i].expressUserType == 1){
						expressUserType = "普通用户";
					}else{
						expressUserType = "APP用户";
					}
					var expressCollectionTime = pageData[i].expressCollectionTime;
					expressCollectionTime =getStringTime( parseInt(expressCollectionTime) * 1000);
					
				/*	var express_status_html = "";
					if(pageData[i].expressStatus == 1){
						express_status_html = "已取件";
						repair_overman.append("<tr class='odd'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+expressCollectionTime+"</td><td>"+express_status_html+"</td></tr>");
			
					}else{
						express_status_html = "	<a href=\"javascript:;\"  onclick=\"updateExpress("+pageData[i].expressId+");\">确认收件</a>";
						repair_overman.append("<tr class='even'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+expressCollectionTime+"</td><td>"+express_status_html+"</td></tr>");
			
					}*/
					repair_overman.append("<tr class='even'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+expressCollectionTime+"</td></tr>");
					
					}
				
			}else if(data.status == "no"){
				alert("查询失败");
			}
			document.getElementById("express_type").value = type;
			
		},

		error:function(er){
		
	
		}

	});
}

function updateThisSearchType(type) {
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;

	getExpressList(type,1,startTime, endTime);
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
	//getQuickShopList(1, startTime, endTime);
	getExpressList(-1,1,startTime, endTime);
	getQuickShopTop(startTime, endTime,'日');
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
	//getQuickShopList(1, startTime, endTime);
	getExpressList(-1,1,startTime, endTime);
	getQuickShopTop(startTime, endTime,'月');

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
	var type=document.getElementById("express_type").value ;
	getExpressList(type,page_num,startTime, endTime);

	getQuickShopTop(startTime, endTime);
}
function weekMonth() {

	var d = getPreviousWeekStartEnd();
	// alert(d.start+d.end);
	var startTime = d.start;
	var endTime = d.end;

	getExpressList(-1,1, startTime, endTime);
	getQuickShopTop(startTime, endTime,'周');

}
function selectStatistics() {
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getExpressList(-1,1, startTime, endTime);
	getQuickShopTop(startTime, endTime);

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
	getExpressList(-1,1, startTime, endTime);
	getQuickShopTop(startTime, endTime,type);

}


function getExpressListByText(){
	
	var express_search = $("#express_search_fei").val();
	var express_type=document.getElementById("express_type").value ;
	if(express_search == ""){
		alert("搜索内容不能为空");
		return false;
	}
	
	var path = "/api/v1/communities/1/express/like/"+express_search+"?pageNum=1&pageSize=10&expressType="+express_type;

	$.ajax({

		url: path,

		type: "GET",

		dataType:'json',

		success:function(data){
			
	if(data.status == "yes"){
				
				data=data.info;
				$("#master_repair_datai_PageNum_get").val(data.num);
				$("#master_repair_datai_PageSize_get").val(data.pageCount);

				$("#master_repair_datai_PageNum").html(data.num);
				$("#master_repair_datai_PageSize").html(data.pageCount);
				$("#master_repair_datai_sum").html(data.rowCount);
				var pageData = data.pageData;
			
				var repair_overman = $("#statistics_list_id");
				repair_overman.empty();
				repair_overman.append("<tr><th>电话</th><th>快递单号</th><th>收件人</th><th>物品</th><th>通知结果</th><th>用户类型</th><th>收件时间</th></tr>");
				
				var len = pageData.length;
				for(var i = 0 ; i < len ; i++){
					var expressResult = "";
					if(pageData[i].expressResult == 1){
						expressResult = "<span class='success'>成功</span>";
						
					}else{
						expressResult = "<span class='fail'>失败</span>";
					}
					var expressUserType = "";
					if(pageData[i].expressUserType == 1){
						expressUserType = "普通用户";
					}else{
						expressUserType = "APP用户";
					}
					var expressCollectionTime = pageData[i].expressCollectionTime;
					expressCollectionTime = parseInt(expressCollectionTime) * 1000;
					
				/*	var express_status_html = "";
					if(pageData[i].expressStatus == 1){
						express_status_html = "已取件";
						repair_overman.append("<tr class='odd'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+expressCollectionTime+"</td><td>"+express_status_html+"</td></tr>");
			
					}else{
						express_status_html = "	<a href=\"javascript:;\"  onclick=\"updateExpress("+pageData[i].expressId+");\">确认收件</a>";
						repair_overman.append("<tr class='even'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+expressCollectionTime+"</td><td>"+express_status_html+"</td></tr>");
			
					}*/
					repair_overman.append("<tr class='even'><td>"+pageData[i].expressPhone+"</td><td>"+pageData[i].expressNo+"</td><td>"+pageData[i].expressName+"</td><td>"+pageData[i].expressGoods+"</td><td>"+expressResult+"</td><td>"+expressUserType+"</td><td>"+expressCollectionTime+"</td></tr>");
					
					}
				
			}else if(data.status == "no"){
				alert("查询失败");
			}
		},

		error:function(er){
		
	
		}

	});
}
