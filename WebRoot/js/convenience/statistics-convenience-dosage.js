var mapTryOut = {};
var communityId = window.parent.document.getElementById("community_id_index").value;
var mapTryOut =window.parent.mapTryOut;
var clickObj=window.parent.clickObj;
var mapUser=window.parent.mapUser;
var mapClickList={};
var map={};

function testTop(clickNum,clickObc) {
	for (var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		
		if(value==3){
			var obj=clickObj[key];
			var obj2=clickObj["this_data3"];
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
				document.getElementById(biao+"_click").innerHTML=obj.click;
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
						
						
					}else if(mapUser[clickList[k].emobId]=="1"){
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


function thisClick(clickNum) {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate() + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/" + communityId + "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/" + communityId + "/events_details_daily/statistics/3/" + start + "/" + (end - 1);
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
			for ( var i = 0; i < data.length; i++) {
				var clickList = data[i].userClick;
				total+=data[i].total;
				for ( var j = 0; j < clickList.length; j++) {
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserList[testClickUser++] = clickList[j].emobId;
						testClick += clickList[j].click;
					} else {
						if(mapUser[clickList[j].emobId]=="1"){
							clickUserList[clickUser++] =clickList[j].emobId ;
							click += clickList[j].click;
						}
					}
				}
			}
			if(testClickUserList.length>1){
				testClickUserList=testClickUserList.unique2();
			}
			if(clickUserList.length>1){
				clickUserList=clickUserList.unique2();
			}
			var clickBi=0;
			if(clickNum>0){
				clickBi=(click/clickNum)*100;
			}
			var clickUser=clickUserList.length;
			if(clickUser==0){
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
		thisClickNum();
		getTop();
		quickShopData(2);
	}else{
		schedule();
		setTimeout("getTryOut()", 2000);
	}
}

function getTryOut2() {
	schedule();
	var path = "/api/v1/communities/" + communityId + "/userStatistics/getUserList";
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
			getTop();
			quickShopData(2);
		},
		error : function(er) {
			onSchedule();
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
							
						}else if(mapUser[clickList[k].emobId]=="1"){
							clickNum += clickList[k].click;
						}
					}
				}
			}
			getClick(biao,sort, startTime, endTime,   clickNum);
		},
		error : function(er) {
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
		+ communityId + "/events_details_daily/statistics/3/" + start
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
							if(mapUser[clickList[j].emobId]=="1"){
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
			}
		});
}

function getQuickShopTop(biao,sort, startTime, endTime) {
	var path = "/api/v1/communities/" + communityId + "/users/1/orderHistories/getQuickShopTop?sort=2&startTime=" + startTime + "&endTime=" + endTime;
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
			document.getElementById(biao+"_place_test").innerHTML ="("+ (data.testOrderQuantity || 0)+")";
			document.getElementById(biao+"_place_user").innerHTML = data.userNum;
			document.getElementById(biao+"_place_user_test").innerHTML = "(" + data.testUserNum + ")";
			document.getElementById(biao+"_end_place").innerHTML = data.endOrderQuantity;
			document.getElementById(biao+"_end_place_test").innerHTML = "(" + (data.testEndOrderQuantity || 0) + ")";
			document.getElementById(biao+"_end_place_ratio").innerHTML = endBi.toFixed(2) + "%";
			var cl=0;
			var click=clickObj[biao+3].click;
			if (click >0) {
				 cl = (data.orderQuantity / click) * 100;
			}
			document.getElementById(biao+"_place_ratio").innerHTML = cl .toFixed(2) + "%";
			onSchedule();
		},
		error : function(er) {
			onSchedule();
		}
	});
}

function getQuickShopTop4(biao,sort, startTime, endTime, clickNum,click,testClick,clickUser,testClickUser,total) {
	var path = "/api/v1/communities/" + communityId + "/users/1/orderHistories/getQuickShopTop?sort=2&startTime=" + startTime + "&endTime=" + endTime;
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
			var avgClick = click / dates;
			var clickRatio = 0;
			if (avgClick != 0) {
				clickRatio = ord / avgClick;
			}
			
			var clickBi = 0;
			if (clickNum != 0) {
				clickBi = (click / clickNum) * 100;
			}
			var endBi = 0;
			if (data.orderQuantity != 0) {
				endBi = (data.endOrderQuantity / data.orderQuantity) * 100;
			}
			document.getElementById(biao+"_click_ratio").innerHTML = clickBi.toFixed(2) + "%";
			if(clickUser==0){
				clickUser="无明细"+"["+total+"]";
			}
			document.getElementById(biao+"_click").innerHTML = click;
			document.getElementById(biao+"_click_test").innerHTML = "("+clickUser+")";
			document.getElementById(biao+"_place").innerHTML = data.orderQuantity;
			document.getElementById(biao+"_place_test").innerHTML ="("+ data.testOrderQuantity+")";
			document.getElementById(biao+"_place_user").innerHTML = data.userNum;
			document.getElementById(biao+"_place_user_test").innerHTML = "(" + data.testUserNum + ")";
			document.getElementById(biao+"_end_place").innerHTML = data.endOrderQuantity;
			document.getElementById(biao+"_end_place_test").innerHTML = "(" + data.testEndOrderQuantity + ")";
			document.getElementById(biao+"_end_place_ratio").innerHTML = endBi.toFixed(2) + "%";
			var cl=0;
			if (click != 0) {
				cl = (data.orderQuantity / click) * 100;
			}
			document.getElementById(biao+"_place_ratio").innerHTML = cl.toFixed(2) + "%";
			document.getElementById("avg_day_turnover").innerHTML = avgClick.toFixed(0);
			document.getElementById("avg_day_order").innerHTML = ord.toFixed(0);
			document.getElementById("avg_day_ratio").innerHTML = clickRatio.toFixed(0);
			
			if(biao=="to_day"){
				toDayUserData();
				return ;
			}
			if(biao=="this_data"){
				thisWeekUser();
				return ;
			}
			if(biao=="this_week"){
				weekUser();
				return ;
			}
			if(biao=="last_week"){
				thisMonthUser();
				return ;
			}
			if(biao=="this_month"){
				monthUser();
				return ;
			}
			if(biao=="last_month"){
				totalUser();
				return ;
			}
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
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +myDate.getDate() + " 00:00:00";
	getQuickShopTop('to_day',2,startTime,endTime);
}
function toDayUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate() + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	getQuickShopTop('this_data',2,startTime,endTime);
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('this_month',2,startTime,endTime);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('last_month',2,startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('last_week',2,startTime,endTime);
}
function thisWeekUser() {
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('this_week',2,startTime,endTime);
}
function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	getQuickShopTop('total_id',2,startTime,endTime);
}

function getTop() {
	toDayUserData();
	thisUserData();
	thisWeekUser();
	thisMonthUser();
	weekUser() ;
	monthUser();
	totalUser();
}

function getQuickShopList(pageNum, startTime, endTime) {
	var sort = document.getElementById("master_repir_sort_fei").value;
	var path = "/api/v1/communities/" + communityId + "/users/1/orderHistories/getQuickShopList?pageSize=50&pageNum=" + pageNum + "&sort=2&startTime=" + startTime + "&endTime=" + endTime;

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
			repair_overman.append("<tr ><th>店家名称</th><th>下单量</th><th>成单量</th><th>下单人数</th><th>下单成单率</th><th>详情</th></tr>");
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
				liList += "<td>" + data[i].orderQuantity + "<b class=\"greenword\">(" + data[i].testOrderQuantity + ")</b></td>";
				liList += "<td>" + data[i].endOrderQuantity + "<b class=\"greenword\">(" + data[i].testEndOrderQuantity + ")</b></td>";
				liList += "<td>" + data[i].userNum + "<b class=\"greenword\">(" + data[i].testUserNum + ")</b></td>";
				liList += "<td>" + endBi.toFixed(2) + "%</b></td>";
				liList += "<td><a onclick=\"userShopTry('" + data2.shopName + "','0','" + shi[i] + "')\">详情</a></b></td>";
				liList += "</tr>";
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

function userShopTry(shopName, shopId, startTime) {
	var path = "/api/v1/communities/" + communityId + "/userStatistics/getUserList";
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
			getUserShop(shopName, shopId, startTime);
			onSchedule();
		},
		error : function(er) {
			onSchedule();
			top.location = '/';
		}
	});
}


function getUserShop(shopName, shopId, startTime) {
	
	startTime+=" 00:00:00";
	var path = "/api/v1/communities/" + communityId + "/users/1/orderHistories/getUserShop?sort=2&startTime=" + startTime + "&endTime=0" ;
			$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;

					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					// var liList='';
					repair_overman
							.append("<tr ><th>店家名称</th><th>买家昵称</th><th>买家电话</th><th>下单时间</th><th>结单时间</th><th>金额</th><th>订单状态</th><th>支付方式</th><th>交易号</th><th>订单号</th><th>交易记录</th></tr>");

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
								liList += "<td><b class=\"greenword\"></b></td>";
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
								liList += "<td></td>";
							} else {
								liList += "<td>"
										+ toStringTime(data[i].endTime * 1000)
										+ "</td>";
							}

							liList += "<td>" + data[i].orderPrice + "</td>";
						}
						liList+="<td>";
						if(data[i].status=="new"){
							liList+="新建订单";
						}else if(data[i].status=="canceled"){
							liList+="取消";
						}else if(data[i].status=="renounce"){
							liList+="放弃";
						}else if(data[i].status=="paid"){
							liList+="已支付";
						}else if(data[i].status=="ended"){
							liList+="已结单";
						}else{
							liList+="进行中";
						}
						liList+="</td>";
						
						if(data[i].online=="yes"){
							var tradeType = data[i].tradeType=="alipay"?"支付宝支付":data[i].tradeType=="wxpay"?"微信支付":" ";
							liList+="<td>"+tradeType+"</td>";
						}else if(data[i].online=="no"){
							 liList+="<td> 线下支付</td>";
						}else{
							 liList+="<td> 未支付</td>";
						}
						
						liList+="<td>"+data[i].tradeNo+"</td>";
						liList+="<td>"+data[i].serial+"</td>";
						liList +="<td><a  onclick=\"getMessageListFromUser3('"+data[i].emobIdShop+"','"+data[i].emobIdUser+"')\">交易记录</a></td>";
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
	//schedule();
	document.getElementById("is_list_detail").value = "";
	
	
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/events_details_daily/statistics/3/" + start + "/" + end;
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
						if(mapUser[clickList[j].emobId]=="1"){
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
				mapClickList[time]=clickList;
				
				for ( var j = 0; j < clickList.length; j++) {
					
					if (mapTryOut[clickList[j].emobId] == "1") {
						testClickUserList[testClickUser++] = clickList[j].emobId;
						testClick += clickList[j].click;
						// alert(clickList[j].emobId);
					} else {
						if(mapUser[clickList[j].emobId]=="1"){
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
				clickUser = 0;
				testClick=0;
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
			+ pageNum + "&sort=2&startTime=" + startTime
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
							.append("<tr class=\"odd\"><td>统计时间</td><td>点击量</td><td>点击人数</td><td>下单量</td><td>下单人数</td><td>下单占比</td>	<td>成单量</td><td>下单成单率</td><th>详情</th></tr>");

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
								"testEndOrderQuantity" : 0,
								"shopName" :"",
								"emobId" :""
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
						liList += "</td><td ><b><a  onclick=\"getClickUser('"+shi[i]+"');\">" +click + "<b class=\"greenword\">("+testClick+")</b></a></b></td>";
						liList += "</td><td ><b><a  onclick=\"getClickUser('"+shi[i]+"');\">" +clickUser + "<b class=\"greenword\">("+testClickUser+")</b></a></b></td>";
						liList += "<td>" + data2.orderQuantity
								+ "<b class=\"greenword\">("
								+ data2.testOrderQuantity + ")</b></td>";

						
						liList += "<td>" + data2.userNum
								+ "<b class=\"greenword\">("
								+ data2.testUserNum + ")</b></td>";
						liList += "<td>" + bi.toFixed(2) + "%</td>";
						liList += "<td>" + data2.endOrderQuantity
								+ "<b class=\"greenword\">("
								+ data2.testEndOrderQuantity + ")</b></td>";
						liList += "<td>" + endBi.toFixed(2) + "%</td>";
						 liList += "<td><a onclick=\"userShopTry('"
								+ data2.shopName + "','0','" + shi[i] + "')\">详情</a></b></td>";
						liList += "</tr>";
						
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
					
					liTop+="<td>" + userNumSum
								+ "<b class=\"greenword\">("
								+ testUserNumSum + ")</b></td>";
					liTop+="<td>" + biNum.toFixed(2) + "%</td>";
					liTop+="<td>" + endOrderQuantitySum
								+ "<b class=\"greenword\">("
								+ testEndOrderQuantitySum+ ")</b></td>";
					liTop+="<td>" + endBiNum.toFixed(2) + "%</td>";
					liTop+="<td></td>";
					liTop+="</tr>";
					repair_overman.append(liTop);
					repair_overman.append(liList);
					document.getElementById("master_repir_sort_fei").value = sort;
				//	onSchedule();
				},
				error : function(er) {
					onSchedule();
				}
			});

}
function getClickUser(time) {
    var userList=mapClickList[time];
	var map2={};
	var map3={};
    var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
		    var dataUser=	data.listUsers;
		    var dataOut=	data.listTryOut;
			for ( var i = 0; i < dataUser.length; i++) {
				map2[dataUser[i].emobId]=dataUser[i];
			}
			for ( var i = 0; i < dataOut.length; i++) {
				map3[dataOut[i].emobId]="1";
			}
			var repair_overman = $("#statistics_list_id");
			repair_overman.empty();
			repair_overman
					.append("<tr class=\"odd\"><td>用户昵称</td><td>电话号码</td><td>点击次数</td></tr>");
			var liTop="";
			var liList="";
		
			for ( var i = 0; i < userList.length; i++) {
				
				if(map2[userList[i].emobId]==undefined){
					continue;
				}
				if (i % 2 == 0) {
					liList += "<tr class=\"even\">";
				} else {

					liList += "<tr class=\"odd\">";
				}
				if(map3[userList[i].emobId]==1){
					liList += "<td > <b class=\"greenword\">"+map2[userList[i].emobId].nickname+"</b></td><td> <b class=\"greenword\">"+map2[userList[i].emobId].username+"</b></td>";
				}else{
					
					if(map2[userList[i].emobId].username==""||map2[userList[i].emobId].username=="null"){
						liList += "<td >游客</td><td >游客</td>";
						
					}else{
						liList += "<td >"+map2[userList[i].emobId].nickname+"</td><td >"+map2[userList[i].emobId].username+"</td>";
					}
					
					
				}
				
                liList +="<td>"+userList[i].click+"</td></tr>";
			}
			
			repair_overman.append(liList);
			onSchedule();
			
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














var advisoryHistoryPageNum = 1;

var advisoryHistoryPageSize = 10;

var advisoryHistoryList;

var advisoryHistoryMinTime;

var advisoryHistoryMaxTime;

var advisoryHistoryEmobId;

var advisoryHistoryIndex;

var advisoryHistoryFirst = 0;

var advisoryHistoryLast = 0;

var advisoryHistoryNext = 0;

var advisoryHistoryPrev = 0;

var advisoryHistoryPageCount = 0;

var advisoryHisotrySum = 0;
var transactionMap={};
var messageArray;

function getMessageListFromUser3(from,to){
	schedule();
	$.ajax({
		url: "/api/v1/usersMessages/test?messageFrom="+from+"&messageTo="+to,
		type: "GET",
		dataType:"json",
		success:function(data){
			console.info(data);
			if(data.status == "yes"){
				data = data.info;
				var len = data.length;
				messageArray = new Array();
				for(var i = 0 ; i < len ; i++){
					var message = {
							"from":data[i].messageFrom ,
							"to":data[i].messageTo,
							"msg":data[i].msg,
							"timestamp":data[i].timestamp,
							"type":data[i].msgType,
							"data":data[i].msg,
							"url":data[i].url,
							"ext":{
								"serial":data[i].serial,
								"nickname":data[i].nickname,
								"avatar":data[i].avatar,
								"content":data[i].content,
								"CMD_CODE":data[i].CMD_CODE,
								"cc":data[i].CMD_DETAIL
							}
					};
					
					messageArray.push(message);
				}
				onSchedule();
				transactionMap[from+to] = messageArray;
				getMyTouSuMessageList3(from,to);
			}
		},
		error:function(er){
			onSchedule();
		}
	});
}

function getMyTouSuMessageList3(my,to) {


var myThis = $("#statistics_list_id");
myThis.html("");
var myClassName = my+to;
//tousuThisUser = myClassName;

if (transactionMap[myClassName] != undefined) {
	var myTouSuList = transactionMap[myClassName];
	for ( var i = 0; i < myTouSuList.length; i++) {
		var message = myTouSuList[i];
		var from = message.from;//消息的发送者
		var mestype = message.type;//消息发送的类型是群组消息还是个人消息
		var messageContent = message.data;//文本消息体
		var timestamp=message.timestamp;
		  var myDate=new Date(timestamp);
		    var sen=   myDate.getFullYear() + '-'
					+ ('0' + (myDate.getMonth() + 1)).slice(-2) + '-'
					+ ('0' + myDate.getDate()).slice(-2)+" "+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
			myThis.append("<div class=\"chat-time\"><span>"+sen+"</span></div>");
		var ext=message.ext;
		//   根据消息体的to值去定位那个群组的聊天记录
		var room = message.to;

		
		
		if (mestype!="txt") {
			var url=message.url;
			if(mestype=="audio"){
				myThis.append("<div class=\"text-left\"><img src=\"/"+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\">" +
						"<div class=\"left-speak\"><div class=\"radio-style\"><span class=\"radio-icon\">&nbsp;</span><audio controls=\"controls\">  "+
                          "<source src=\""+url+"\" /> "+
                          "</audio></div> </div>" +
						"</div></div><span class=\"chat-me\">&nbsp;</span></div>");

			}else {
				myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"/><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><img src=\""+url+"\"></img></div></div><span class=\"chat-me\">&nbsp;</span></div>");
			}
	
    } else {
			if (from == to) {
				if(ext.CMD_CODE=='200'||ext.CMD_CODE=='201'||ext.CMD_CODE=='202'||ext.CMD_CODE=='203'||ext.CMD_CODE=='204'||ext.CMD_CODE=='205'||ext.CMD_CODE=='206'||ext.CMD_CODE=='207'||ext.CMD_CODE=='208'||ext.CMD_CODE=='209'||ext.CMD_CODE=='210'){
					var li2=ext.cc;
					try {
		                var li3= JSON.parse(li2);
		                var li4=li3.orderDetailBeanList;
		                var lilL="";
		                lilL+="<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box order-bg\" style=\"max-width:220px;\">	<div class=\"right-speak\" >"+ext.serial+"</div>";

		                for ( var j = 0; j <li4.length; j++) {
		                			                	lilL+="<div class=\"order-list\"><b class=\"name\">"+li4[j].serviceName+"</b><b class=\"count\">X"+li4[j].count+"       "+li4[j].price
		                	+"</b></div>";
						}
		                lilL+=" <div class=\"order-totle\"> 总计:<b>" +li3.totalPrice+
		                			"</b></div>" +
		                			"" +
		                			"</div></div><span class=\"chat-me\">&nbsp;</span></div> ";
		                myThis.append(lilL);
					}catch (e) {
					} 
					
				}
				myThis.append("<div class=\"text-right\"><img src=\""+ext.avatar+"\"><div class=\"right-speak-frame\"><div class=\"right-speak-box\" style=\"max-width:220px;\"><div class=\"right-speak\" id=\""+to+"_"+from+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
				//myThis.append("<font style=\"text-align:right;width:100%;display:block;\">"+ messageContent + " : 我 </font><br/>");
			} else {
				if(ext.CMD_CODE=='200'||ext.CMD_CODE=='201'||ext.CMD_CODE=='202'||ext.CMD_CODE=='203'||ext.CMD_CODE=='204'||ext.CMD_CODE=='205'||ext.CMD_CODE=='206'||ext.CMD_CODE=='207'||ext.CMD_CODE=='208'||ext.CMD_CODE=='209'||ext.CMD_CODE=='210'){
					var li2=ext.cc;
					try {
		                var li3= JSON.parse(li2);
		                var li4=li3.orderDetailBeanList;
		                var lilL="";
		                lilL+="<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box order-bg\" style=\"max-width:220px;\">	<div class=\"left-speak\" >"+ext.serial+"</div>";

		                for ( var j = 0; j <li4.length; j++) {
		                			                	lilL+="<div class=\"order-list\"><b class=\"name\">"+li4[j].serviceName+"</b><b class=\"count\">X"+li4[j].count+"       "+li4[j].price
		                	+"</b></div>" ;
						}
		                lilL+="<div class=\"order-totle\"> 总计:<b>" +li3.totalPrice+
		                			"</b></div>" +
		                			"" +
		                			"</div></div><span class=\"chat-me\">&nbsp;</span></div> ";
		                myThis.append(lilL);
					}catch (e) {
					} 
				}
				myThis.append("<div class=\"text-left\"><img src=\""+ext.avatar+"\"><div class=\"left-speak-frame\"><div class=\"left-speak-box\" style=\"max-width:220px;\"><div class=\"left-speak\" id=\""+from+"_"+to+"_"+i+"\">"+messageContent+"</div></div></div><span class=\"chat-me\">&nbsp;</span></div>");
		}
		
		
		
		}
		
	}
}
}
