var map = {};
var communityId = window.parent.document.getElementById("community_id_index").value;
var mapTryOut =window.parent.mapTryOut;
var clickObj=window.parent.clickObj;

var mapUser=window.parent.mapUser;


function testTop(clickNum,clickObc) {
	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		
		if(value==9){
			var obj=clickObj[key];
			
			
			var obj2=clickObj["this_data9"];
			var clickAddNum=(clickNum-obj2.clickNum);
		
			var clickAddUser=clickObc.clickUser-obj2.clickUser;
			var clickAddTest=clickObc.testClick-obj2.testClick;
			var clickAddUserTest=clickObc.testClickUser-obj2.testClickUser;
			var clickAdd=clickObc.click-obj2.click;
			biao=key.replace(/[0-9]/ig,"");
			if("this_data"==biao||"this_week"==biao||"this_month"==biao||"total_id"==biao){
			   var clickNumTop= parseInt(obj.clickNum)+parseInt(clickAddNum);
			   var clickTop= parseInt(obj.click)+parseInt(clickAdd);
				var clickBi=0;
				if(clickNumTop>0){
					clickBi=(clickTop/clickNumTop)*100;
				}
				
				
				document.getElementById(biao+"_click").innerHTML=parseInt(obj.click)+parseInt(clickAdd);
				document.getElementById(biao+"_click_test").innerHTML ="("+(parseInt(obj.testClick)+ parseInt(clickAddTest))+")";
				document.getElementById(biao+"_click_ratio").innerHTML =clickBi.toFixed(2)+ "%";
				
				
				
			}else{
					biao=key.replace(/[0-9]/ig,"");
					
					if(obj.click==0){
						document.getElementById(biao+"_click").innerHTML="(无明细["+obj.total+"])";
					}else{
						document.getElementById(biao+"_click").innerHTML=obj.click;
					}
					document.getElementById(biao+"_click_test").innerHTML ="("+ obj.testClick+")";
					document.getElementById(biao+"_click_ratio").innerHTML = obj.clickRatio+"%";
				
			}
			
			
		
		}
		
	}
	onSchedule();
}


function thisClickNum() {
	var myDate=new Date();
	var month=myDate.getMonth()+1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate()   
	+ " 00:00:00";
    var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
    var start = stringToTime(startTime) / 1000;
    var end = stringToTime(endTime) / 1000;
	
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/modules/statistics/"+start+"/" + (end-1);
$.ajax({
	url : path,
	type : "GET",
	dataType : 'json',
	success : function(data) {
		data = data.info;
		var info;
		var click = 0;
		var clickNum = 0;
		var clickTime=0;
		for ( var i = 0; i < data.length; i++) {
			
			info = data[i].info;

			for ( var j = 0; j < info.length; j++) {
				var clickList=info[j].userClick;
				if(info[j].serviceId=="0"){
					if(info[j].serviceName!="通知公告"){
						
						continue;
					}
				}
				if(info[j].serviceId=="17"){
					continue;
			   }
				for ( var k = 0; k < clickList.length; k++) {
					if(mapTryOut[clickList[k].emobId] == "1"){
						
						
					}else if(mapUser[clickList[k].emobId] =="1"){
						clickTime+= clickList[k].click;
							clickNum += clickList[k].click;
					}
				}
				
			}
			
		}
		thisClick(clickNum);
	},
	error : function(er) {
	}
});
}


function  thisClick(clickNum) {
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	
	
	
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/9/" + start
		+ "/" + (end - 1);
	//alert(path);
       $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;

				var info;
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
				var total=0;
				// info = data.userClick;
				for ( var i = 0; i < data.length; i++) {
					var clickList = data[i].userClick;
					total+=data[i].total;
					for ( var j = 0; j < clickList.length; j++) {
					
						if (mapTryOut[clickList[j].emobId] == "1") {
							testClickUserList[testClickUser++] = clickList[j].emobId;
							testClick += clickList[j].click;
							// alert(clickList[j].emobId);
						} else {
							if(mapUser[clickList[j].emobId] =="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
							}
						
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				//var clickNum=clickNumMap[biao];
				var clickBi=0;
				if(clickNum>0){
					clickBi=(click/clickNum)*100;
				}
				var clickUser=clickUserList.length;
				if(clickUser==0){
				//	clickUser="["+total+"]";
					click=0;
					clickUser=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"testClick":testClick,
						"testClickUser":testClickUserList.length,
						"clickRatio":clickBi.toFixed(2)
						
				};
				testTop(clickNum,clickObc);
			},
			error : function(er) {
				onSchedule();
			}
		});
}





function getTryOut() {
	
	if(window.parent.isClick==63){
	//	testTop();
		thisClickNum();
		getTop();
		quickShopData(5);
	}else{
		schedule();
		setTimeout("getTryOut()", 2000);
		 //setInterval("alert(100)",5000);
	}
	
}









//function getTryOut() {
//	
//	if(window.parent.isClick==63){
//		testTop();
//		getTop();
//		quickShopData();
//	}else{
//		schedule();
//		setTimeout("getTryOut()", 2000);
//		 //setInterval("alert(100)",5000);
//	}
//	
//}
//
//function testTop() {
//	for ( var key in clickObj) {
//		var value = key.replace(/[^0-9]/ig,""); 
//		var biao="";
//		
//		if(value==9){
//			var obj=clickObj[key];
//			
//			biao=key.replace(/[0-9]/ig,"");
//			document.getElementById(biao+"_click").innerHTML=obj.click;
//			document.getElementById(biao+"_click_test").innerHTML ="("+ obj.testClick+")";
//			document.getElementById(biao+"_click_ratio").innerHTML = obj.clickRatio+"%";
//			
//		}
//		
//	}
//	onSchedule();
//}


function getTryOut2() {
	schedule();
	var path = "/api/v1/communities/" + communityId
			+ "/userStatistics/getUserList";

	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var tryOut = data.listTryOut;
			var listUsers = data.listUsers;
			for ( var i = 0; i < tryOut.length; i++) {
				mapTryOut[tryOut[i].emobId] = "1";
			}
			
			
			  quickShopData(5);
			  getTop();
			// getClickAmountTop2(startTime, endTime, type);
			
		},
		error : function(er) {
			
			// alert("网络连接失败...");
			// top.location='/';
		}
	});
}

function getClickAmountTop(biao,sort, startTime, endTime) {
	schedule();
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/modules/statistics/" + start+ "/" + (end-1);

	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var info;
			var click = 0;
			var clickNum = 0;
			for ( var i = 0; i < data.length; i++) {
				
				info = data[i].info;

				for ( var j = 0; j < info.length; j++) {
					var clickList=info[j].userClick;
					if(info[j].serviceId=="0"){
						if(info[j].serviceName!="通知公告"){
							
							continue;
						}
					}
					if(info[j].serviceId=="17"){
						continue;
				   }
					for ( var k = 0; k < clickList.length; k++) {
						if(mapTryOut[clickList[k].emobId] == "1"){
							
							
						}else if(mapUser[clickList[k].emobId] =="1"){
						
								clickNum += clickList[k].click;
						}
					}
					//alert(info[j].serviceName+"---"+i);
				}

			}
			getClick(biao,sort, startTime, endTime,   clickNum);
		},
		error : function(er) {
			onSchedule();
		//	alert("网络连接失败...");
		}
	});
}

function  getClick(biao,sort, startTime, endTime,   clickNum) {
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/9/" + start
		+ "/" + (end - 1);
        $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;

				var info;
				var click = 0;
				var testClick = 0;
				var clickUser = 0;
				var clickUserList = new Array();
				var testClickUser = 0;
				var testClickUserList = new Array();
			    var total=0;
				// info = data.userClick;
				for ( var i = 0; i < data.length; i++) {
					var clickList = data[i].userClick;
					total+=data[i].total;
					for ( var j = 0; j < clickList.length; j++) {
						if (mapTryOut[clickList[j].emobId] == "1") {
							
							testClickUserList[testClickUser++] = clickList[j].emobId;
							testClick += clickList[j].click;
							// alert(clickList[j].emobId);
						} else {
							if(mapUser[clickList[j].emobId] =="1"){
								clickUserList[clickUser++] =clickList[j].emobId ;
								click += clickList[j].click;
						}
						}

					}
				}
				
				// alert(testClickUserList.unique2().length);
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
					
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				
				
				// alert(i);
				getQuickShopTop(biao,sort, startTime, endTime,   clickNum,click,testClick,clickUserList.length,testClickUserList.length,total);
			},
			error : function(er) {
				onSchedule();
			}
		});
}





function getQuickShopTop(biao,sort, startTime, endTime, clickNum,click,testClick,clickUser,testClickUser,total) {
	var path = "/api/v1/communities/" + communityId
			+ "/users/1/orderHistories/getQuickShopTop?sort=5&startTime=" + startTime + "&endTime=" + endTime;
	var myDate = new Date(stringToTime(startTime));
	var myDate2 = new Date(stringToTime(endTime));
	var dates = Math.abs((myDate2 - myDate)) / (1000 * 60 * 60 * 24);

	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;
					var ord = data.orderQuantity / dates;
					var endBi = 0;
					if (data.orderQuantity != 0) {
						endBi = (data.endOrderQuantity / data.orderQuantity) * 100;
					}
					
					document.getElementById(biao+"_place").innerHTML = data.orderQuantity;
					document.getElementById(biao+"_place_test").innerHTML ="("+ data.testOrderQuantity+")";
					
					document.getElementById(biao+"_place_user").innerHTML = data.userNum;
					document.getElementById(biao+"_place_user_test").innerHTML = "("
							+ data.testUserNum + ")";
					document.getElementById(biao+"_end_place").innerHTML = data.endOrderQuantity;
					document.getElementById(biao+"_end_place_test").innerHTML = "("
							+ data.testEndOrderQuantity + ")";
					document.getElementById(biao+"_end_place_ratio").innerHTML = endBi
							.toFixed(2)
							+ "%";
					var cl=0;
					var click=clickObj[biao+9].click;
					if (click >0) {
						 cl = (data.orderQuantity / click) * 100;
						
					}
					document.getElementById(biao+"_place_ratio").innerHTML = cl
					.toFixed(2)
					+ "%";
					
					
					onSchedule();
				},
				error : function(er) {
					onSchedule();
				}
			});
}




function thisUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() -1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + da 
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate() + " 00:00:00";
	getQuickShopTop('to_day',5,startTime,endTime);
	
}
function toDayUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	getQuickShopTop('this_data',5,startTime,endTime);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('this_month',5,startTime,endTime);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('last_month',5,startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('last_week',5,startTime,endTime);
}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('this_week',5,startTime,endTime);
}
function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	getQuickShopTop('total_id',5,startTime,endTime);
	
}

function getTop() {
	toDayUserData();
	thisUserData();
	thisWeekUser();
	thisMonthUser();
	//getUserTop();
//	quickUserData();
	weekUser() ;
	monthUser();
	totalUser();
}


function getQuickShopList(pageNum, startTime, endTime) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = "/api/v1/communities/" + communityId
			+ "/users/1/orderHistories/getQuickShopList?pageSize=50&pageNum="
			+ pageNum + "&sort=5&startTime=" + startTime
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
							.append("<tr ><th>店家名称</th><th>下单量</th><th>成单量</th><th>下单人数</th><th>下单成单率</th><th>详情</th></tr>");

					for ( var i = 0; i < data.length; i++) {
						var endBi = 0;
						if (data[i].orderQuantity != 0) {
							endBi = (data[i].endOrderQuantity / data[i].orderQuantity) * 100;
						}
						var liList = "";
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>" + data[i].shopName + "</td>";
						liList += "<td>" + data[i].orderQuantity
								+ "<b class=\"greenword\">("
								+ data[i].testOrderQuantity + ")</b></td>";
						liList += "<td>" + data[i].endOrderQuantity
								+ "<b class=\"greenword\">("
								+ data[i].testEndOrderQuantity + ")</b></td>";
						liList += "<td>" + data[i].userNum
								+ "<b class=\"greenword\">("
								+ data[i].testUserNum + ")</b></td>";
						liList += "<td>" + endBi.toFixed(2) + "%</b></td>";
						liList += "<td><a onclick=\"userShopTry('"
								+ data[i].shopName + "','" + data[i].emobId
								+ "','" + startTime + "','" + endTime
								+ "')\">详情</a></b></td>";
						liList += "</tr>";
						repair_overman.append(liList);

					}
//					onSchedule();
				},
				error : function(er) {
					onSchedule();
					top.location = '/';
				}
			});
}

function userShopTry(shopName, shopId, startTime, endTime) {
	var path = "/api/v1/communities/" + communityId
			+ "/userStatistics/getUserList";

	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			var tryOut = data.listTryOut;
			var listUsers = data.listUsers;
			for ( var i = 0; i < tryOut.length; i++) {
				mapTryOut[tryOut[i].usersId] = "1";
			}
			getUserShop(shopName, shopId, startTime, endTime);
			onSchedule();
		},
		error : function(er) {
			onSchedule();
			top.location = '/';
		}
	});
}

function getUserShop(shopName, shopId, startTime, endTime) {
	var path = "/api/v1/communities/" + communityId
			+ "/users/1/orderHistories/getUserShop?sort=5&startTime=" + startTime + "&endTime=" + endTime;

	$
			.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					// var liList='';
					repair_overman
							.append("<tr ><th>店家名称</th><th>买家昵称</th><th>买家电话</th><th>下单时间</th><th>结单时间</th><th>金额</th></tr>");

					for ( var i = 0; i < data.length; i++) {
						var liList = "";

						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}
						if (mapTryOut[data[i].userId] == "1") {

							liList += "<td ><b class=\"greenword\">" + shopName + "</b></td>";

							liList += "<td><b class=\"greenword\">" + data[i].nickname + "</b></td>";
							liList += "<td><b class=\"greenword\">" + data[i].username + "</b></td>";
							liList += "<td><b class=\"greenword\">"
									+ toStringTime(data[i].startTime * 1000)
									+ "</b></td>";
							if (data[i].endTime == "0") {
								liList += "<td><b class=\"greenword\">未结单</b></td>";
							} else {
								liList += "<td><b class=\"greenword\">"
										+ toStringTime(data[i].endTime * 1000)
										+ "</b></td>";
							}

							liList += "<td><b class=\"greenword\">" + data[i].orderPrice + "</b></td>";
						} else {

							liList += "<td>" + shopName + "</td>";

							liList += "<td>" + data[i].nickname + "</td>";
							liList += "<td>" + data[i].username + "</td>";
							liList += "<td>"
									+ toStringTime(data[i].startTime * 1000)
									+ "</td>";
							if (data[i].endTime == "0") {
								liList += "<td>未结单</td>";
							} else {
								liList += "<td>"
										+ toStringTime(data[i].endTime * 1000)
										+ "</td>";
							}

							liList += "<td>" + data[i].orderPrice + "</td>";
						}

						liList += "</tr>";
						repair_overman.append(liList);
						onSchedule();
					}
				},
				error : function(er) {
					onSchedule();
					top.location = '/';
				}
			});
}

function quickShopData(sort) {
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"
			+ (myDate.getDate() + 1) + " 00:00:00";
	getClickAmountList(sort, 1, startTime, endTime,'日');
//	getClickAmountTop(sort, startTime, endTime, '日');
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
	getClickAmountList(sort, 1, startTime, endTime,'月');
//	getClickAmountTop(sort, startTime, endTime, '月');

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var sort = document.getElementById("master_repir_sort_fei").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountList(sort, 1, startTime, endTime, type);
	//getClickAmountTop(sort, startTime, endTime, type);

}
function weekMonth(sort) {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountList(sort, 1, startTime, endTime, '周');
//	getClickAmountTop(sort, startTime, endTime, '周');

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
	var sort = document.getElementById("master_repir_sort_fei").value;
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getClickAmountList(sort, 1, startTime, endTime);
//	getClickAmountTop(sort, startTime, endTime);

}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getQuickShopList(1, startTime, endTime);
//	getClickAmountTop(startTime, endTime);
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
		getClickAmountList(2, page_num, startTime, endTime);
	} else {
		getClickAmountList(2, page_num, startTime, endTime);
	}

//	getClickAmountTop(startTime, endTime);
}

function getShopsOrderDetailList(pageNum, shopId) {
	schedule();
	document.getElementById("shop_id_detail").value = shopId;
	document.getElementById("is_list_detail").value = "detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = "/api/v1/communities/" + communityId
			+ "/users/1/orderHistories/getQuickShopDetailList?pageSize=50"
			+ "&pageNum=" + pageNum + "&shopId=" + shopId + "&startTime="
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

			repair_overman.append("<tr class=\"odd\"><td>" + data[0].shopName
					+ "</td><td>点击量</td><td>下单量</td><td>下单比例</td>	</tr>");

			for ( var i = 0; i < data.length; i++) {

				var myDate = new Date(data[i].startTime * 1000);
				var month = myDate.getMonth();
				var date = myDate.getDate();
				var liList = "";
				if (i % 2 == 0) {
					liList += "<tr class=\"even\">";
				} else {

					liList += "<tr class=\"odd\">";
				}
				liList += "<td>" + month + "月" + date + "日"
						+ "</td><td >100000</td>";
				liList += "<td>" + data[i].orderQuantity
						+ "</td><td>100000</td></tr>";
				repair_overman.append(liList);

			}
			onSchedule();
		},
		error : function(er) {
			onSchedule();
			top.location = '/';
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

function getClickAmountList(sort, pageNum, startTime, endTime,type) {
//	schedule();
	document.getElementById("is_list_detail").value = "";
	
	
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/events_details_daily/statistics/9/" + start + "/" + end;
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {

			data = data.info;
			
			var clickUserListNum = new Array();
			var testClickUserListNum = new Array();
			var clickUserNum=0;
			var testClickUserNum=0;
			
			var clickSumNum=0;
			var testClickSumNum=0;
			
			for ( var i = 0; i < data.length; i++) {
				var clickList = data[i].userClick;
				for ( var j = 0; j < clickList.length; j++) {
					
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserListNum[testClickUserNum++] = clickList[j].emobId;
						testClickSumNum += clickList[j].click;
						// alert(clickList[j].emobId);
					} else {
						if(mapUser[clickList[j].emobId] =="1"){
							clickUserListNum[clickUserNum++] =clickList[j].emobId ;
							clickSumNum += clickList[j].click;
						}
					
					}

				}
			}
			
			// alert(testClickUserList.unique2().length);
			if(testClickUserListNum.length>1){
				 testClickUserListNum=testClickUserListNum.unique2();
			}
			if(clickUserListNum.length>1){
				 clickUserListNum=clickUserListNum.unique2();
			}
			
			
			
			
			
			var map = {};
			var click = 0;
			var testClick = 0;
			var clickUser = 0;
			
			var testClickUser = 0;
		
            var time;
            
            var total=0;
            
			for ( var i = 0; i < data.length; i++) {
				 
				var clickUserList = new Array();
				var testClickUserList = new Array();
				var clickList = data[i].userClick;
				time = getStringTime(data[i]._id.time * 24 * 60 * 60 * 1000);
				total+=data[i].total;
				for ( var j = 0; j < clickList.length; j++) {
					
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserList[testClickUser++] = clickList[j].emobId;
						testClick += clickList[j].click;
						// alert(clickList[j].emobId);
					} else {
						if(mapUser[clickList[j].emobId] =="1"){
						   clickUserList[clickUser++] = clickList[j].emobId;
						   click += clickList[j].click;
						}
						
					}

				}
				if(testClickUserList.length>1){
					 testClickUserList=testClickUserList.unique2();
					
				}
				if(clickUserList.length>1){
					 clickUserList=clickUserList.unique2();
				}
				
				var clickDetl={
						"click" : click,
						"testClick" : testClick,
				        "clickUser" : clickUserList.length,
				        "testClickUser" : testClickUserList.length,
				        "total" : total
				};
				map[time] = clickDetl;
				click = 0;
				testClickUser = 0;
				testClick=0;
				clickUser = 0;
				total = 0;
			}

			timeQuantum(startTime, endTime);

			timeDisplay(type);
			getUseAmountList(sort,pageNum, startTime, endTime, map,clickSumNum,testClickSumNum,clickUserListNum.length,testClickUserListNum.length);
		},
		error : function(er) {
		}
	});
}

function getUseAmountList(sort, pageNum, startTime, endTime, map,clickSumNum,testClickSumNum,clickUserNum,testClickUserNum) {
	var path = "/api/v1/communities/" + communityId
			+ "/users/1/orderHistories/getUseAmountList?pageSize=50&pageNum="
			+ pageNum + "&sort=" + sort + "&startTime=" + startTime
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

					var map2 = {};
					data = data.pageData;

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					repair_overman
							.append("<tr class=\"odd\"><td>统计时间</td><td>点击量</td><td>点击人数</td><td>下单量</td><td>下单比例</td><td>下单量人次</td>	<td>成单量</td><td>下单成单率</td><td>详情	</td></tr>");

					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate() - 1;
					var da2 = myDate.getDate() - 2;
					var startTime1 = myDate.getFullYear() + "-" + month + "-"
							+ da + " 00:00:00";
					var endTime1 = myDate.getFullYear() + "-" + month + "-"
							+ myDate.getDate() + " 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-"
							+ da2 + " 00:00:00";

					var startTime_day_long = stringToTime(startTime1) / 1000;
					var endTime_day_long = stringToTime(endTime1) / 1000;
					var startTime2_day_long = stringToTime(startTime2) / 1000;

					for ( var i = 0; i < data.length; i++) {
						map2[getStringTime(data[i].startTime * 1000)] = data[i];
					}
					var data2;
					var shi = show(getStringTime(stringToTime(startTime)),
							getStringTime(stringToTime(endTime)));
					var liList = "";
					
					var orderQuantitySum=0;
					var testOrderQuantitySum=0;
					var userNumSum=0;
					var testUserNumSum=0;
					var endOrderQuantitySum=0;
					var testEndOrderQuantitySum=0;
					
					for ( var i = shi.length - 2; i >=0; i--) {
						data2 = map2[shi[i]];
						if (data2 == undefined) {
							var data2 = {
								"startTime" : stringToTime(shi[i] + " 00:00:00") / 1000,
								"orderQuantity" : 0,
								"testOrderQuantity" : 0,
								"userNum" : 0,
								"testUserNum" : 0,
								"endOrderQuantity" : 0,
								"testEndOrderQuantity" : 0
							};
						}
						var bi = 0;
						var time = shi[i];
						var c = map[time];
					    var click=0;
	                    var testClick=0;
	                    var testClickUser=0;
	                    var clickUser=0;
	                    var total=0;
						if (c != undefined ) {
							
							click = c.click;
							testClick = c.testClick;
							testClickUser = c.testClickUser;
							clickUser = c.clickUser;
							total = c.total;
						} 
						
						if(click!=0){
							bi = ((data2.orderQuantity / click) * 100);
						}
						var endBi = 0;
						if (data2.orderQuantity != 0) {
							endBi = (data2.endOrderQuantity / data2.orderQuantity) * 100;
						}
						
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {
							liList += "<tr class=\"odd\">";
						}
						liList += "<td>";

							var myDate = new Date(data2.startTime * 1000);

							var startTimeFilter = myDate.getMonth() + 1 + "月"
									+ myDate.getDate() + "日";
							liList += startTimeFilter;

						if(clickUser==0){
							clickUser="无明细["+total+"]";
						}
						liList += "</td><td >" +click + "<b class=\"greenword\">("+testClick+")</b></td>";
						liList += "</td><td >" +clickUser + "<b class=\"greenword\">("+testClickUser+")</b></td>";
						liList += "<td>" + data2.orderQuantity
								+ "<b class=\"greenword\">("
								+ data2.testOrderQuantity + ")</b></td>";

						liList += "<td>" + bi.toFixed(2) + "%</td>";
						liList += "<td>" + data2.userNum
								+ "<b class=\"greenword\">("
								+ data2.testUserNum + ")</b></td>";
						liList += "<td>" + data2.endOrderQuantity
								+ "<b class=\"greenword\">("
								+ data2.testEndOrderQuantity + ")</b></td>";
						liList += "<td>" + endBi.toFixed(2) + "%</td>";
						liList += "<td><a onclick=\"selectData('"
								+ data2.startTime + "')\">详情</a></td></tr>";
						
						 orderQuantitySum+=data2.orderQuantity;
						 testOrderQuantitySum+=data2.testOrderQuantity;
						 userNumSum+=data2.userNum;
						 testUserNumSum+=data2.testUserNum;
						 endOrderQuantitySum+=data2.endOrderQuantity;
						 testEndOrderQuantitySum+=data2.testEndOrderQuantity;
						
					}
					
					var biNum=0;
					if(clickSumNum!=0){
						biNum = ((orderQuantitySum / clickSumNum) * 100);
					}
					var endBiNum = 0;
					if (orderQuantitySum != 0) {
						endBiNum = (endOrderQuantitySum / orderQuantitySum) * 100;
					}
					var liTop="";
					liTop+="<tr class=\"even\"><td>合计</td>";
					liTop+="<td>"+clickSumNum+ "<b class=\"greenword\">("+ testClickSumNum + ")</b></td>";
					liTop+="<td>"+clickUserNum+ "<b class=\"greenword\">("+ testClickUserNum + ")</b></td>";
					liTop+="<td>"+orderQuantitySum+"<b class=\"greenword\">("
					+ testOrderQuantitySum + ")</b></td>";
					liTop+="<td>" + biNum.toFixed(2) + "%</td>";
					liTop+="<td>" + userNumSum
								+ "<b class=\"greenword\">("
								+ testUserNumSum + ")</b></td>";
					liTop+="<td>" + endOrderQuantitySum
								+ "<b class=\"greenword\">("
								+ testEndOrderQuantitySum+ ")</b></td>";
					liTop+="<td>" + endBiNum.toFixed(2) + "%</td>";
					liTop+="<td>&nbsp;</td>";
					liTop+="</tr>";
					repair_overman.append(liTop);
					repair_overman.append(liList);
					document.getElementById("master_repir_sort_fei").value = sort;
//					onSchedule();
				},
				error : function(er) {
					onSchedule();
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
}
