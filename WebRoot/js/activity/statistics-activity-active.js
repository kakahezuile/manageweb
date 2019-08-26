var communityId=window.parent.document.getElementById("community_id_index").value;
var mapTryOut={};
var mapTryOut =window.parent.mapTryOut;
var clickObj=window.parent.clickObj;

function testTop(clickNum,clickObc) {
	for ( var key in clickObj) {
		var value = key.replace(/[^0-9]/ig,""); 
		var biao="";
		
		if(value==1){
			var obj=clickObj[key];
			
			
			var obj2=clickObj["this_data1"];
			var clickAddNum=(clickNum-obj2.clickNum);
			var clickAddUser=clickObc.clickUser-obj2.clickUser;
			var clickAddTest=clickObc.testClick-obj2.testClick;
			var clickAddUserTest=clickObc.testClickUser-obj2.testClickUser;
			var clickAdd=clickObc.click-obj2.click;
				
			
			biao=key.replace(/[0-9]/ig,"");
			if("this_data"==biao||"this_week"==biao||"this_month"==biao||"total_id"==biao){
				 var usList=new Array();
				 var usList2=new Array();
				 usList=obj.clickUserList;
				 usList2=clickObc.clickUserList;
				 usList=usList.concat(usList2);
				 if(usList.length>1){
					usList=unique3(usList);
				 }
			    var clickNumTop= parseInt(obj.clickNum)+parseInt(clickAddNum);
			    var clickTop= parseInt(obj.click)+parseInt(clickAdd);
				var clickBi=0;
				if(clickNumTop>0){
					clickBi=(clickTop/clickNumTop)*100;
				}
				
				document.getElementById(biao).innerHTML= parseInt(obj.click)+parseInt(clickAdd);
				document.getElementById(biao+"_user").innerHTML=parseInt(obj.clickUser)+parseInt(clickAddUser);
				document.getElementById(biao+"_test").innerHTML="("+(parseInt(obj.testClick)+ parseInt(clickAddTest))+")";
				document.getElementById(biao+"_user_test").innerHTML="("+ (parseInt(obj.testClickUser)+ parseInt(clickAddUserTest))+")";
			}else{
					biao=key.replace(/[0-9]/ig,"");
					
					if(obj.click==0){
						document.getElementById(biao).innerHTML="(无明细["+obj.total+"])";
					}else{
						document.getElementById(biao).innerHTML=obj.click;
					}
					
					
					document.getElementById(biao+"_user").innerHTML=obj.clickUser;
					document.getElementById(biao+"_test").innerHTML="("+obj.testClick+")";
					document.getElementById(biao+"_user_test").innerHTML="("+obj.testClickUser+")";
				
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
						
						
					}else if(clickList[k].emobId!=undefined&&clickList[k].emobId!=""){
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
		+ communityId + "/events_details_daily/statistics/1/" + start
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
							if(clickList[j].emobId!=undefined&&clickList[j].emobId!=""){
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
					//total="["+total+"]";
					click=0;
					clickUser=0;
				}
				
				
				var clickObc={
						"total":total,
						"click":click,
						"clickUser":clickUser,
						"testClick":testClick,
						"clickUserList":clickUserList,
						"testClickUser":testClickUserList.length
						
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
		getTop();
		thisClickNum();
		quickShopData();
	}else{
		schedule();
		setTimeout("getTryOut()", 2000);
		 //setInterval("alert(100)",5000);
	}
	
}


function newActivities() {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +(da+1)
			+ " 00:00:00";
	 var path="/api/v1/communities/"+communityId+"/activities/webIm/getAddActivity?startTime="+startTime+"&endTime="+endTime;
	 $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				document.getElementById("new_activities").innerHTML = data.acitvityNum;
			},
			error : function(er) {
			}
		});
}

function tryOut(emobGroupId) {
	  var path = "/api/v1/communities/"+communityId+"/userStatistics/getUserList";
		
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var tryOut=data.listTryOut;
				var listUsers=data.listUsers;
				for ( var i = 0; i < tryOut.length; i++) {
					mapTryOut[tryOut[i].usersId]="1";
				}
				newActivitiesListDetail(emobGroupId);
			},
			error : function(er) {
				top.location='/'; 
			}
		});
}



function newActivitiesListDetail(emobGroupId) {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +(da+1)
			+ " 00:00:00";
	var path="/api/v1/communities/"+communityId+"/activities/webIm/newActivitiesListDetail?emobGroupId="+emobGroupId+"&startTime="+startTime+"&endTime="+endTime;
	 $.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				var repair_overman = $("#statistics_list_id");
				repair_overman.empty();

				repair_overman
						.append("<tr ><th>用户昵称</th><th> 用户号码</th>	<th>加群时间</th><th></th></tr>");

				for ( var i = 0; i < data.length; i++) {

					var liList = "";
					if (i % 2 == 0) {
						liList += "<tr class=\"even\">";
					} else {

						liList += "<tr class=\"odd\">";
					}
                    if(mapTryOut[data[i].userId]=="1"){
                    	liList += "<td><b class=\"greenword\">" + data[i].nickname + "</b></td><td ><b class=\"greenword\">"
						+  data[i].username+ "</b></td>";
			        	liList += "<td><b class=\"greenword\">"+
				        toStringTime(data[i].createTime * 1000)
						+ "</b></td>";
                    }else{
                    	
                    	liList += "<td>" + data[i].nickname + "</td><td >"
						+  data[i].username+ "</td>";
				      liList += "<td>"+
				      toStringTime(data[i].createTime * 1000)
						+ "</td>";
                    }
				
							/*+ "<td></td>"*/
					liList += "</tr>";
					repair_overman.append(liList);

				}
			},
			error : function(er) {
			}
		});
}

function newActivitiesList(pageNum) {
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +(+da+1)
			+ " 00:00:00";
	
	var path = "/api/v1/communities/"+communityId+"/activities/webIm/newActivitiesList?startTime=" + startTime + "&endTime=" + endTime;

	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					data = data.info;


					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					repair_overman
							.append("<tr ><th>活动名称</th><th> 群组人数</th>	<th>详情</th><th></th></tr>");

					for ( var i = 0; i < data.length; i++) {

						var liList = "";
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>" + data[i].activityTitle + "</td><td >"
								+  data[i].addActivity+ "</td>";
						liList += "<td>"+
							"<a onclick=\"tryOut('"+data[i].emobGroupId+"');\">详情</a>"
								+ "</td>";
								/*+ "<td></td>"*/
						liList += "</tr>";
						repair_overman.append(liList);

					}
					document.getElementById("is_list_detail").value="detail";
				},
				error : function(er) {
				}
			});
}

function getClickAmountTop( startTime, endTime,type) {
	
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
			+ communityId
			+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
			+ communityId + "/modules/statistics/" + start+ "/" + (end-1);

//alert(path);
	
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
							
							
						}else if(clickList[k].emobId!=undefined&&clickList[k].emobId!=""){
						
								clickNum += clickList[k].click;
						}
					}
					//alert(info[j].serviceName+"---"+i);
				}
			}
		//	getQuickShopTop( startTime, endTime, type,click,clickNum);
			getClick( startTime, endTime, type,  clickNum);
		},
		error : function(er) {
		//	alert("网络连接失败...");
		}
	});
}


function  getClick( startTime, endTime, type,  clickNum) {
	
	var start = stringToTime(startTime) / 1000;
	var end = stringToTime(endTime) / 1000;
	var time = 0;
	var path = "/api/v1/communities/"
		+ communityId
		+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
		+ communityId + "/events_details_daily/statistics/1/" + start
		+ "/" + (end - 1);
	//alert(path);
$
		.ajax({
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
							if(clickList[j].emobId!=undefined&&clickList[j].emobId!=""){
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
				getQuickShopTop( startTime, endTime, type,  clickNum,click,testClick,clickUserList.length,testClickUserList.length,total);
			},
			error : function(er) {
				onSchedule();
			}
		});
}
function getQuickShopTop(biao,startTime, endTime) {
	var path = "/api/v1/communities/"+communityId+"/activities/webIm/getActivitiesTop?startTime="
			+ startTime + "&endTime=" + endTime;
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
					var ord = data.activitesNum / dates;
					document.getElementById(biao+"_add_user").innerHTML = data.addActivity;
					document.getElementById(biao+"_add_user_test").innerHTML ="("+ data.addTestActivity+")";
					document.getElementById(biao+"_add_activities").innerHTML = data.activityNum;

					document.getElementById("statistics_date_1").innerHTML = startTimeFilter;
					document.getElementById("statistics_date_2").innerHTML = endTimeFilter;
					document.getElementById("avg_day_order").innerHTML = ord.toFixed(0);
					
				},
				error : function(er) {
				}
			});
}


function getQuickShopList(pageNum, startTime, endTime,map) {

	var path = "/api/v1/communities/"+communityId+"/activities/webIm/getActivitiesDateList?pageSize=50&pageNum="
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
							.append("<tr ><th>活动名称</th><th> 群组人数</th><th>增加人数	</th><th></th></tr>");

					for ( var i = 0; i < data.length; i++) {

						var liList = "";
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}

						liList += "<td>" + data[i].activityTitle + "</td><td >"
								+  data[i].addActivity+ "</td>";
						liList += "<td>"
								+ data[i].activitesNum
								+ "</td>";
								/*+ "<td><a onclick=\"getShopsOrderDetailList('1','"
								+ data[i].activitiesId + "');\">详情</a></td>"*/
						liList		+= "</tr>";
						repair_overman.append(liList);

					}
					document.getElementById("is_list_detail").value="detail";
				},
				error : function(er) {
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
	getQuickShopTop('to_day',startTime,endTime);
	
}
function toDayUserData(){
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" +  myDate.getDate()
	+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +da + " 00:00:00";
	getQuickShopTop('this_data',startTime,endTime);
	
}

function thisMonthUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var month2 = myDate.getMonth()+2;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('this_month',startTime,endTime);
}
function monthUser() {
	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-1" + " 00:00:00";
	getQuickShopTop('last_month',startTime,endTime);
}
function weekUser() {
	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('last_week',startTime,endTime);
}
function thisWeekUser() {
	
	var d = getWeekBenUp();
	var startTime = d.start;
	var endTime = d.end;
	getQuickShopTop('this_week',startTime,endTime);
}
function totalUser() {
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var da=myDate.getDate()+1;
	var startTime="2015-06-01 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-"+ da + " 00:00:00";
	getQuickShopTop("total_id",startTime, endTime);
	
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


function quickShopData() {
	schedule();
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate();
	var startTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" +(da+1)
			+ " 00:00:00";
//	newActivities();
	getClickAmountList(1, startTime, endTime, '日');
	
	//getClickAmountTop(startTime, endTime);
	onSchedule();
}









function maintainMonth() {

	var myDate = new Date();
	var month = myDate.getMonth();
	var month2 = myDate.getMonth()+1;

	var startTime = myDate.getFullYear() + "-" + month + "-" + 1 + " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month2 + "-" + 1
			+ " 00:00:00";
	// getQuickShopList(1, startTime, endTime);
	getClickAmountList(1, startTime, endTime, '月');
	//getClickAmountTop(startTime, endTime);

}
function alterMonth(num) {

	var startTime = document.getElementById("master_repir_startTime").value;
	var type = document.getElementById("date_type_get").value;
	var d = alterDate(num, type, startTime);
	var startTime = d.start;
	var endTime = d.end;
	getClickAmountList(1, startTime, endTime, type);
	//getClickAmountTop(startTime, endTime);

}
function weekMonth() {

	var d = getPreviousWeekStartEnd();
	var startTime = d.start;
	var endTime = d.end;

	getClickAmountList(1, startTime, endTime, '周');
	//getClickAmountTop(startTime, endTime);

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
	var startTime = document.getElementById("txtBeginDate").value + " 00:00:00";
	var endTime = document.getElementById("txtEndDate").value + " 00:00:00";
	getClickAmountList(1, startTime, endTime);
//	getClickAmountTop(startTime, endTime);

}
function turnoverTurnover() {
	var startTime = document.getElementById("turnoverBeginDate").value
			+ " 00:00:00";
	var endTime = document.getElementById("turnoverEndDate").value
			+ " 00:00:00";
	getQuickShopList(1, startTime, endTime);
	getClickAmountTop(startTime, endTime);
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

	if (page_num != 0) {
		if (is_list_detail == "detail") {
		  var tim=	document.getElementById("time_day").value;
			var da = tim * 1000;
			var myDate = new Date(da);
			var month = myDate.getMonth() + 1;
			var da = myDate.getDate() + 1;
			var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
					+ " 00:00:00";
			var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
			getQuickShopList(page_num, startTime, endTime);
			
		} else {
			getClickAmountList(page_num, startTime, endTime);
		}

		getClickAmountTop(startTime, endTime);
	}
}

function getShopsOrderDetailList(pageNum, activityId) {
	document.getElementById("shop_id_detail").value = activityId;
	document.getElementById("is_list_detail").value = "detail";
	var startTime = document.getElementById("master_repir_startTime").value;
	var endTime = document.getElementById("master_repir_endTime").value;
	var path = "/api/v1/communities/"+communityId+"/activities/webIm/getActivitiesDetailList?pageSize=50"
			+ "&pageNum="
			+ pageNum
			+ "&activitiesId="
			+ activityId
			+ "&startTime=" + startTime + "&endTime=" + endTime;
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
							.append("<tr class=\"odd\"><td>统计时间</td><td>活动人数</td><td>增加人数</td>	</tr>");

					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var da = myDate.getDate() - 1;
					var da2 = myDate.getDate() - 2;
					var startTime = myDate.getFullYear() + "-" + month + "-"
							+ da + " 00:00:00";
					var endTime = myDate.getFullYear() + "-" + month + "-"
							+ myDate.getDate() + " 00:00:00";
					var startTime2 = myDate.getFullYear() + "-" + month + "-"
							+ da2 + " 00:00:00";

					var startTime_day_long = stringToTime(startTime) / 1000;
					var endTime_day_long = stringToTime(endTime) / 1000;
					var startTime2_day_long = stringToTime(startTime2) / 1000;
					for ( var i = 0; i < data.length; i++) {

						var liList = "";
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}
						// liList += "<tr class=\"odd\">";
						liList += "<td>";
						if (data[i].activityTime > startTime_day_long
								&& data[i].activityTime < endTime_day_long) {
							liList += "昨天";
						} else if (data[i].activityTime > startTime2_day_long
								&& data[i].activityTime < startTime_day_long) {
							liList += "前天";

						} else {

							var myDate = new Date(data[i].activityTime * 1000);

							var startTimeFilter = myDate.getMonth() + 1 + "月"
									+ myDate.getDate() + "日";
							liList += startTimeFilter;
						}
						liList += "</td><td >" + data[i].addActivity + "</td>";
						liList += "<td>" + data[i].activitesNum + "</td></tr>";
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


function getClickAmountList( pageNum, startTime, endTime,type) {
	var path = "/api/v1/communities/"+communityId+"/activities/webIm/getActivitiesTop?startTime="
			+ startTime + "&endTime=" + endTime;
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
					
					getClickAmountList2( pageNum, startTime, endTime,type,data.addActivity,data.addTestActivity,data.activityNum);

				},
				error : function(er) {
				}
			});
}



function getClickAmountList2( pageNum, startTime, endTime,type,addActivityAge,addTestActivityAge,activityNumAge) {
	   document.getElementById("is_list_detail").value="";
		
		var start = stringToTime(startTime) / 1000;
		var end = stringToTime(endTime) / 1000;
		var time = 0;
		var path = "/api/v1/communities/"
				+ communityId
				+ "/users/1/orderHistories/getClickAmount?url=http://115.28.73.37:9090/api/V1/communities/"
				+ communityId + "/events_details_daily/statistics/1/" + start + "/" +(end-1);
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
							if(clickList[j].emobId!=undefined&&clickList[j].emobId!=""){
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
							if(clickList[j].emobId!=undefined&&clickList[j].emobId!=""){
							   clickUserList[clickUser++] = clickList[j].emobId;
							   click += clickList[j].click;
							}else{
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
			getUseAmountList( pageNum, startTime, endTime,map,clickUserListNum.length,testClickUserListNum.length,addActivityAge,addTestActivityAge,activityNumAge);
		},
		error : function(er) {
		}
	});
}
function getUseAmountList(pageNum, startTime, endTime,map,clickUserListNum,testClickUserListNum,addActivityAge,addTestActivityAge,activityNumAge) {

	// document.getElementById("shop_sort_id").value;
	
	var d=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
	var en="";
	for ( var i = 0; i < d.length-1; i++) {
		en+="&sqlTime="+d[i]+" 00:00:00";
	}
	
	var path = "/api/v1/communities/"+communityId+"/activities/webIm/getActivitiesList?startTime=" + startTime + "&endTime=" + endTime+en;
	
	
	$.ajax({
				url : path,
				type : "GET",
				dataType : 'json',
				success : function(data) {
					
					data = data.info;
					var map2={};


					var repair_overman = $("#statistics_list_id");
					repair_overman.empty();

					// var liList='';
					//repair_overman.append("<tr class=\"odd\"><td>统计时间</td><td> 活动人数</td><td>点击量</td><td>增加人数</td>	<td>详情	</td></tr>");
					repair_overman.append("<tr class=\"odd\"><td>统计时间</td><td>发言条数</td><td>发言人数</td><td>群活跃度</td><td>参与群活动人数</td>	</tr>");

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
					
					 
                    for( var i = 0; i < data.length; i++){
						
						map2[getStringTime(data[i].createTime*1000)]=data[i];
						
					}
					var data2;
					var shi=show(getStringTime(stringToTime(startTime)),getStringTime(stringToTime(endTime)));
					var activitesNumTotal=0;
					var addActivityTotal=0;
					var addTestActivityTotal=0;
					var clickTotal=0;
					var testClickTotal=0;
					
					
					var liList = "";
					for ( var i = shi.length-2; i >=0 ; i--) {
						data2=map2[shi[i]];
						if(data2==undefined){
							var data2={
								"activityTime":stringToTime(shi[i]+" 00:00:00")/1000,
								"activitesNum":0,
								"addActivity":0,
								"addTestActivity":0
							};
						}
						var time=getStringTime(data2.activityTime*1000);
						
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
						if(clickUser==0){
							clickUser="无明细["+total+"]";
						}
						
						if (i % 2 == 0) {
							liList += "<tr class=\"even\">";
						} else {

							liList += "<tr class=\"odd\">";
						}
						  // liList += "<tr class=\"odd\">";
						   liList += "<td>";

							var myDate = new Date(data2.createTime * 1000);

							var startTimeFilter = myDate.getMonth() + 1 + "月"
									+ myDate.getDate() + "日";
							liList += startTimeFilter;
					  //	liList += "</td><td >" +data2.addActivity ;
							liList += "</td>" ;
							liList += "<td>" + click + "<b class=\"greenword\">("+testClick+")</b></td>" ;
							liList += "<td>" + clickUser + "<b class=\"greenword\">("+testClickUser+")</b></td>" ;
								liList+=	"<td >"+data2.addActivity +"<b class=\"greenword\">("+data2.addTestActivity+")</b></td>";
					
							liList+="<td>"+data2.activityNum + "</td></tr>";
							
							activitesNumTotal+=data2.activityNum;
							addActivityTotal+=data2.addActivity;
							addTestActivityTotal+=data2.addTestActivity;
							clickTotal+=click;
							testClickTotal+=testClick;
						   
					}
					var liTop="";
					liTop+="<tr class=\"even\"><td>合计</td>";
					liTop+="<td>"+clickTotal+ "<b class=\"greenword\">("+testClickTotal  + ")</b></td>";
					liTop+="<td>"+clickUserListNum+ "<b class=\"greenword\">("+ testClickUserListNum + ")</b></td>";
					liTop+="<td>"+addActivityAge+"<b class=\"greenword\">("
					+ addTestActivityAge + ")</b></td>";
					liTop+="<td>" + activityNumAge + "</td>";
					liTop+="</tr>";
					repair_overman.append(liTop);
					repair_overman.append(liList);
					
					
					document.getElementById("master_repir_startTime").value = startTime;
					document.getElementById("master_repir_endTime").value = endTime;
					document.getElementById("is_list_detail").value="";
					// document.getElementById("shop_sort_id").value=sort;
				},
				error : function(er) {
				}
			});

}

function selectData(tim) {
	document.getElementById("is_list_detail").value="detail";
	document.getElementById("time_day").value=tim;
	var da = tim * 1000;
	var myDate = new Date(da);
	var month = myDate.getMonth() + 1;
	var da = myDate.getDate() + 1;
	var startTime = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()
			+ " 00:00:00";
	var endTime = myDate.getFullYear() + "-" + month + "-" + da + " 00:00:00";
//	alert(startTime+"=="+endTime);
	getQuickShopList(1, startTime, endTime);
}
